import React, { useState, useEffect, useRef } from 'react';
import {
  Box,
  TextField,
  Button,
  MenuItem,
  Select,
  FormControl,
  InputLabel,
  Typography,
  Divider,
  IconButton,
  InputAdornment,
  Fab
} from '@mui/material';
import { ExpandMore, ExpandLess, Clear, ArrowUpward } from '@mui/icons-material';
import QuestionCard from '../question_card/QuestionCard';
import QuestionService from '../../services/questionService';

const SearchPanel = ({
  searchTerm,
  onSearchChange,
  filteredCards,
  onToggleQuestion,
  showSelectedQuestions,
  updateCard,
  updateQuestion,
  triggerToast
}) => {
  const [filterOpen, setFilterOpen] = useState(false);
  const [recentlyGeneratedOpen, setRecentlyGeneratedOpen] = useState(true);
  const [otherQuestionsOpen, setOtherQuestionsOpen] = useState(true);
  const [subject, setSubject] = useState('');
  const [difficulty, setDifficulty] = useState('');
  const [topic, setTopic] = useState('');
  const [creatingNew, setCreatingNew] = useState(false);
  const [filteredResults, setFilteredResults] = useState([]);

  const [subjects, setSubjects] = useState([]);
  const [difficulties, setDifficulties] = useState([]);
  const [topics, setTopics] = useState([]);

  const scrollRef = useRef(null);

  const today = new Date().toLocaleDateString('en-CA');

  useEffect(() => {
    const fetchFilters = async () => {
      try {
        const [subjectList, difficultyList] = await Promise.all([
          QuestionService.getSubjects(),
          QuestionService.getDifficulties()
        ]);
        setSubjects(subjectList);
        setDifficulties(difficultyList);
        setFilteredResults(filteredCards);
      } catch (error) {
        console.error('Error fetching filter options:', error);
      }
    };
    fetchFilters();
  }, [filteredCards]);

  useEffect(() => {
    const fetchTopics = async () => {
      const selected = subjects.find(s => s.name === subject);
      if (selected?.id) {
        try {
          const topicList = await QuestionService.getTopicsBySubject(selected.id);
          setTopics(topicList);
        } catch (error) {
          console.error('Error fetching topics:', error);
        }
      } else {
        setTopics([]);
        setTopic('');
      }
    };
    fetchTopics();
  }, [subject, subjects]);

  const handleNewQuestion = () => {
    setCreatingNew(true);
  };

  const applyFilters = () => {
    const filtered = filteredCards.filter((card) =>
      (!subject || card.subject?.name === subject) &&
      (!difficulty || card.difficulty?.name === difficulty) &&
      (!topic || card.topic?.name === topic)
    );
    setFilteredResults(filtered);
  };

  const scrollToTop = () => {
    scrollRef.current?.scrollTo({ top: 0, behavior: 'smooth' });
  };

  const renderSelect = (label, value, setValue, options, disabled = false) => (
    <FormControl fullWidth sx={{ marginBottom: '12px', position: 'relative', backgroundColor: 'white', borderRadius: '4px' }} disabled={disabled}>
      <InputLabel sx={{ backgroundColor: 'white', px: 0.5 }}>{label}</InputLabel>
      <Select
        value={value}
        onChange={(e) => setValue(e.target.value)}
        IconComponent={value ? () => null : ExpandMore}
        endAdornment={value && (
          <InputAdornment position="end" sx={{ position: 'absolute', right: 8, top: 27, backgroundColor: 'white', zIndex: 1 }}>
            <IconButton onClick={() => setValue('')} size="small">
              <Clear fontSize="small" />
            </IconButton>
          </InputAdornment>
        )}
        sx={{ backgroundColor: 'white', borderRadius: '4px' }}
      >
        {options.map((opt) => (
          <MenuItem key={opt.id} value={opt.name}>{opt.name}</MenuItem>
        ))}
      </Select>
    </FormControl>
  );

  return (
    <Box
      sx={{
        width: '350px',
        height: `calc(100vh - 64px)`,
        overflow: 'hidden',
        position: 'fixed',
        top: '64px',
        left: 0,
        backgroundColor: '#f5f5f5',
        display: 'flex',
        flexDirection: 'column'
      }}
    >
      <Box
        ref={scrollRef}
        sx={{
          overflowY: 'auto',
          flexGrow: 1,
          padding: '16px',
          paddingBottom: '80px',
          position: 'relative'
        }}
      >
        <TextField
          fullWidth
          label="Search"
          variant="outlined"
          value={searchTerm}
          onChange={onSearchChange}
          sx={{ marginBottom: '16px' }}
        />

        <Box
          sx={{
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            cursor: 'pointer',
            padding: '8px',
            backgroundColor: '#e0e0e0',
            borderRadius: '6px',
            marginBottom: '8px'
          }}
          onClick={() => setFilterOpen(!filterOpen)}
        >
          <Typography variant="body2" sx={{ fontWeight: 'bold' }}>Filters</Typography>
          {filterOpen ? <ExpandLess /> : <ExpandMore />}
        </Box>

        {filterOpen && (
          <Box sx={{ backgroundColor: 'white', padding: '12px', borderRadius: '8px', marginBottom: '16px' }}>
            {renderSelect('Subject', subject, setSubject, subjects)}
            {renderSelect('Difficulty', difficulty, setDifficulty, difficulties)}
            {renderSelect('Topic', topic, setTopic, topics, !subject)}

            <Button
              fullWidth
              variant="contained"
              onClick={applyFilters}
              sx={{ backgroundColor: '#3f51b5', color: '#fff' }}
            >
              Search
            </Button>
          </Box>
        )}

        <Box>
          <Box display="flex" alignItems="center" justifyContent="center" sx={{ mt: 2, mb: 1 }}>
            <Typography variant="body2" sx={{ fontWeight: 'bold' }}>Recently Generated</Typography>
            <IconButton onClick={() => setRecentlyGeneratedOpen(!recentlyGeneratedOpen)}>
              {recentlyGeneratedOpen ? <ExpandLess /> : <ExpandMore />}
            </IconButton>
          </Box>
          <Divider />
          {recentlyGeneratedOpen && (
            <>
              <Button
                fullWidth
                variant="contained"
                onClick={handleNewQuestion}
                sx={{ backgroundColor: '#009688', color: '#fff', mt: 2 }}
              >
                Create Handmade Question
              </Button>
              {creatingNew && (
                <QuestionCard
                  id={null}
                  pregunta=""
                  opciones={["", "", "", ""]}
                  subject=""
                  difficulty=""
                  topic=""
                  generatedAt={today}
                  addToSelectList={onToggleQuestion}
                  updateCard={(id, question, choices, subject, difficulty, topic) => {
                    updateCard(id, question, choices, subject, difficulty, topic, today);
                    setCreatingNew(false);
                  }}
                  isCreating={true}
                  updateQuestion={updateQuestion}
                  triggerToast={triggerToast}
                />
              )}
              {filteredResults
                ?.filter(card => card.generatedAt === today)
                .map((card) => (
                  <QuestionCard
                    key={card.id}
                    id={card.id}
                    pregunta={card.question}
                    opciones={card.choices}
                    subject={card.subject?.name}
                    difficulty={card.difficulty?.name}
                    topic={card.topic?.name}
                    addToSelectList={onToggleQuestion}
                    updateCard={updateCard}
                    updateQuestion={updateQuestion}
                    triggerToast={triggerToast}
                  />
                ))}
            </>
          )}
        </Box>

        <Box>
          <Box display="flex" alignItems="center" justifyContent="center" sx={{ mt: 2, mb: 1 }}>
            <Typography variant="body2" sx={{ fontWeight: 'bold' }}>Other Questions</Typography>
            <IconButton onClick={() => setOtherQuestionsOpen(!otherQuestionsOpen)}>
              {otherQuestionsOpen ? <ExpandLess /> : <ExpandMore />}
            </IconButton>
          </Box>
          <Divider />
          {otherQuestionsOpen && (
            filteredResults
              ?.filter(card => card.generatedAt !== today)
              .map((card) => (
                <QuestionCard
                  key={card.id}
                  id={card.id}
                  pregunta={card.question}
                  opciones={card.choices}
                  subject={card.subject?.name}
                  difficulty={card.difficulty?.name}
                  topic={card.topic?.name}
                  addToSelectList={onToggleQuestion}
                  updateCard={updateCard}
                  updateQuestion={updateQuestion}
                  triggerToast={triggerToast}
                />
              ))
          )}
        </Box>

        <Fab
          color="primary"
          size="small"
          onClick={scrollToTop}
          sx={{
            position: 'fixed',
            bottom: '100px',
            left: 'calc(350px - 70px)',
            zIndex: 1000,
          }}
        >
          <ArrowUpward />
        </Fab>
      </Box>

      <Box sx={{ padding: '16px', backgroundColor: '#f5f5f5' }}>
        <Button
          fullWidth
          variant="contained"
          onClick={() => showSelectedQuestions(true)}
          sx={{
            backgroundColor: '#3f51b5',
            color: '#fff',
            '&:hover': { backgroundColor: '#303f9f' },
            padding: '12px',
            fontSize: '16px'
          }}
        >
          Preview
        </Button>
      </Box>
    </Box>
  );
};

export default SearchPanel;
