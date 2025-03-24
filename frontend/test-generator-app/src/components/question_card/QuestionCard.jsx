import React, { useState, useEffect } from 'react';
import {
  Card,
  CardContent,
  Typography,
  Box,
  TextField,
  IconButton,
  Autocomplete,
  FormControl
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import CheckIcon from '@mui/icons-material/Check';
import DeleteIcon from '@mui/icons-material/Delete';
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward';
import CloseIcon from '@mui/icons-material/Close';
import QuestionService from '../../services/questionService';

const QuestionCard = ({
  id,
  pregunta,
  opciones = [],
  subject = '',
  difficulty = '',
  topic = '',
  addToSelectList,
  updateCard,
  isCreating,
  updateQuestion,
  triggerToast
}) => {
  const [selected, setSelected] = useState(false);
  const [isEditing, setIsEditing] = useState(isCreating ?? false);
  const [editedQuestion, setEditedQuestion] = useState(pregunta);
  const [editedOptions, setEditedOptions] = useState(
    isEditing
      ? [...(opciones ?? []), '', '', '', ''].slice(0, 4)
      : (opciones ?? []).filter(opt => opt?.trim() !== '')
  );

  const [editedSubject, setEditedSubject] = useState(subject);
  const [editedDifficulty, setEditedDifficulty] = useState(difficulty);
  const [editedTopic, setEditedTopic] = useState(topic);
  const [errors, setErrors] = useState({ subject: false, difficulty: false, topic: false });
  const [subjectOptions, setSubjectOptions] = useState([]);
  const [difficultyOptions, setDifficultyOptions] = useState([]);
  const [topicOptions, setTopicOptions] = useState([]);
  const [animate, setAnimate] = useState(false);

  useEffect(() => {
    setAnimate(true);
  }, []);

  useEffect(() => {
    if (isCreating) {
      setIsEditing(true);
      loadSelectOptions();
    }
  }, [isCreating]);

  useEffect(() => {
    const selected = subjectOptions.find(s => s.name === editedSubject);
    if (selected?.id) {
      QuestionService.getTopicsBySubject(selected.id).then(setTopicOptions);
    } else {
      setTopicOptions([]);
    }
  }, [editedSubject, subjectOptions]);

  const loadSelectOptions = async () => {
    try {
      const [subjects, difficulties] = await Promise.all([
        QuestionService.getSubjects(),
        QuestionService.getDifficulties()
      ]);
      setSubjectOptions(subjects);
      setDifficultyOptions(difficulties);
    } catch (error) {
      console.error('Failed to load select options', error);
    }
  };

  const handleCardClick = () => {
    if (!isEditing) {
      setSelected(!selected);
      addToSelectList(id, editedQuestion, editedOptions, difficulty);
    }
  };

  const handleEditClick = async (e) => {
    e.stopPropagation();
    setIsEditing(true);
    const safeOptions = [...(opciones ?? []), '', '', '', ''].slice(0, 4);
    setEditedOptions(safeOptions);
    setEditedQuestion(pregunta);
    setEditedSubject(subject);
    setEditedDifficulty(difficulty);
    setEditedTopic(topic);
    await loadSelectOptions();
  };


  const handleCancelEdit = (e) => {
    e.stopPropagation();

    if (selected) {
      setSelected(false);
      addToSelectList(id, editedQuestion, editedOptions, difficulty);
    }

    if (isCreating) {
      updateCard(id, null, null, null, null, null);
      return;
    }

    setIsEditing(false);
    setEditedQuestion(pregunta);
    setEditedOptions(opciones?.filter(opt => opt.trim() !== '') ?? []);
    setEditedSubject(subject);
    setEditedDifficulty(difficulty);
    setEditedTopic(topic);
    setErrors({ subject: false, difficulty: false, topic: false });
  };

  const validateFields = () => {
    const newErrors = {
      subject: !editedSubject,
      difficulty: !editedDifficulty,
      topic: !editedTopic,
      question: !editedQuestion?.trim()
    };
    setErrors(newErrors);
    return !Object.values(newErrors).some(Boolean);
  };

  const handleSaveClick = () => {
    if (!validateFields()) return;
    setIsEditing(false);
    const cleanedOptions = editedOptions.filter(opt => opt.trim() !== '');
    updateCard(id, editedQuestion, cleanedOptions, editedSubject, editedDifficulty, editedTopic);
    if (selected) {
      setSelected(false);
      addToSelectList(id, editedQuestion, editedOptions, difficulty);
    }
  };

  const handleDeleteClick = (e) => {
    e.stopPropagation();
    updateQuestion(id, null).then(() => {
      setIsEditing(false);
      try {
        updateCard(id, null, null, null, null, null);
        triggerToast?.('Question deleted successfully', 'success');
        if (selected)
          addToSelectList(id, editedQuestion, editedOptions, difficulty);
      } catch (error) { }

    });
  };

  const handleUploadClick = (e) => {
    e.stopPropagation();
    if (!validateFields()) return;
    const cleanedOptions = editedOptions.filter(opt => opt.trim() !== '');
    const question = {
      subject: editedSubject,
      topic: editedTopic,
      difficulty: editedDifficulty,
      question: editedQuestion,
      choices: cleanedOptions
    };
    try {
      updateQuestion(id, question);
      triggerToast?.('Question uploaded successfully', 'success');
    } catch (error) {
      console.error('Upload failed', error);
      triggerToast?.('Failed to upload question', 'error');
    }
  };

  const selectColor = () => {
    const level = isEditing ? editedDifficulty : difficulty;
    if (level === 'EASY') return '#C7DCA7';
    if (level === 'MEDIUM') return '#F9B572';
    if (level === 'HARD') return '#DC8686';
    return '#ffffff';
  };


  return (
    <Box sx={{
      backgroundColor: selectColor(),
      padding: '6px',
      borderRadius: '12px',
      maxWidth: 420,
      margin: '10px auto'
    }}>
      <Card
        onClick={handleCardClick}
        sx={{
          backgroundColor: 'white',
          borderRadius: '8px',
          padding: '8px',
          border: selected ? '2px solid #3f51b5' : '2px solid transparent',
          boxShadow: selected
            ? '0 0 12px rgba(63, 81, 181, 0.4)'
            : '0 0 8px rgba(0, 0, 0, 0.1)',
          cursor: 'pointer',
          transition: 'all 0.3s ease',
          transform: selected
            ? 'scale(1.02)'
            : animate
              ? 'scale(1)'
              : 'scale(0.8)',
          position: 'relative'
        }}
      >
        <CardContent>
          <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', position: 'relative' }}>
            {isEditing ? (
              <TextField
                fullWidth
                variant="outlined"
                value={editedQuestion}
                onChange={(e) => {
                  setEditedQuestion(e.target.value);
                  setErrors(prev => ({ ...prev, question: false }));
                }}
                sx={{ fontSize: '16px', mb: 2 }}
                label="Question"
                error={errors.question}
                helperText={errors.question ? 'This field is required' : ''}
              />
            ) : (
              <Typography
                variant="h6"
                sx={{
                  fontSize: '16px',
                  overflow: 'hidden',
                  textOverflow: 'ellipsis',
                  whiteSpace: 'nowrap',
                  maxWidth: 'calc(100%)'
                }}
              >
                {editedQuestion}
              </Typography>
            )}
            <Box sx={{ position: 'absolute', top: -25, right: -25 }}>
              <Box
                sx={{
                  backgroundColor: 'white',
                  borderBottomLeftRadius: '100%',
                  width: 30,
                  height: 30,
                  position: 'relative',
                  zIndex: 1
                }}
              >
                <IconButton
                  onClick={isEditing ? handleCancelEdit : handleEditClick}
                  sx={{
                    color: isEditing ? 'red' : '#2e7d32',
                    backgroundColor: 'transparent',
                    position: 'absolute',
                    top: -1,
                    right: -1,
                    padding: '4px',
                    '&:hover': { backgroundColor: 'transparent', opacity: 0.8 }
                  }}
                  size="small"
                >
                  {isEditing ? <CloseIcon fontSize="small" /> : <EditIcon fontSize="small" />}
                </IconButton>
              </Box>
            </Box>
          </Box>

          <Box sx={{ mt: 1 }}>
            {editedOptions?.map((opcion, index) => (
              (isEditing || opcion.trim()) && (
                <Box key={index} sx={{ display: 'flex', alignItems: 'center', mb: 1 }}>
                  {opcion.trim() && !isEditing && (
                    <Box
                      component="span"
                      sx={{
                        display: 'inline-block',
                        width: '6px',
                        height: '6px',
                        backgroundColor: '#3f51b5',
                        borderRadius: '50%',
                        marginRight: '8px',
                        flexShrink: 0
                      }}
                    />
                  )}
                  {isEditing ? (
                    <TextField
                      fullWidth
                      variant="outlined"
                      size="small"
                      value={editedOptions[index]}
                      onChange={(e) => {
                        setEditedOptions((prev) => prev.map((opt, i) => (i === index ? e.target.value : opt)));
                      }}
                      label={`Option ${index + 1}`}
                    />
                  ) : (
                    <Typography
                      variant="body2"
                      sx={{
                        fontSize: '14px',
                        marginLeft: '20px',
                        overflow: 'hidden',
                        textOverflow: 'ellipsis',
                        whiteSpace: 'nowrap',
                        maxWidth: '100%'
                      }}
                    >
                      {opcion}
                    </Typography>
                  )}
                </Box>
              )
            ))}
          </Box>

          {isEditing && (
            <Box sx={{ mt: 3 }}>
              <FormControl fullWidth sx={{ mb: 2 }} error={errors.subject}>
                <Autocomplete
                  freeSolo
                  options={subjectOptions.map(s => s.name)}
                  value={editedSubject}
                  onChange={(_, newValue) => {
                    setEditedSubject(newValue || '');
                    setErrors(prev => ({ ...prev, subject: false }));
                    if (!newValue) setEditedTopic('');
                  }}
                  onInputChange={(_, newInput) => {
                    setEditedSubject(newInput);
                    setErrors(prev => ({ ...prev, subject: false }));
                    if (!newInput) setEditedTopic('');
                  }}
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      label="Subject"
                      error={errors.subject}
                      helperText={errors.subject ? 'This field is required' : ''}
                    />
                  )}
                />
              </FormControl>

              <FormControl fullWidth sx={{ mb: 2 }} error={errors.difficulty}>
                <Autocomplete
                  options={difficultyOptions.map(d => d.name)}
                  value={editedDifficulty}
                  onChange={(_, newValue) => {
                    setEditedDifficulty(newValue || '');
                    setErrors(prev => ({ ...prev, difficulty: false }));
                  }}
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      label="Difficulty"
                      error={errors.difficulty}
                      helperText={errors.difficulty ? 'This field is required' : ''}
                    />
                  )}
                />
              </FormControl>


              <FormControl fullWidth sx={{ mb: 2 }} error={errors.topic}>
                <Autocomplete
                  freeSolo
                  options={topicOptions.map(t => t.name)}
                  value={editedTopic}
                  onChange={(_, newValue) => {
                    setEditedTopic(newValue || '');
                    setErrors(prev => ({ ...prev, topic: false }));
                  }}
                  onInputChange={(_, newInput) => {
                    setEditedTopic(newInput);
                    setErrors(prev => ({ ...prev, topic: false }));
                  }}
                  disabled={!editedSubject}
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      label="Topic"
                      error={errors.topic}
                      helperText={
                        errors.topic
                          ? 'This field is required'
                          : !editedSubject
                            ? 'Select a subject first'
                            : ''
                      }
                    />
                  )}
                />
              </FormControl>
            </Box>
          )}

          {isEditing && (
            <Box sx={{ display: 'flex', justifyContent: 'space-between', px: 2, pb: 1 }}>
              <IconButton sx={{ color: 'blue', background: 'white' }} size="small" onClick={handleUploadClick}>
                <ArrowUpwardIcon fontSize="small" />
              </IconButton>
              <IconButton sx={{ color: 'green', background: 'white' }} size="small" onClick={handleSaveClick}>
                <CheckIcon fontSize="small" />
              </IconButton>
              <IconButton sx={{ color: 'red', background: 'white' }} size="small" onClick={handleDeleteClick}>
                <DeleteIcon fontSize="small" />
              </IconButton>
            </Box>
          )}
        </CardContent>
      </Card>
    </Box>
  );
};

export default QuestionCard;