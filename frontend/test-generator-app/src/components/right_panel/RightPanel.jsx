import React, { useState } from 'react';
import {
  Box,
  TextField,
  Button,
  Checkbox,
  FormControlLabel,
  Divider,
  Typography,
  CircularProgress
} from '@mui/material';
import QuestionService from '../../services/questionService';

const RightPanel = ({
  title,
  setTitle,
  includeHeader,
  setIncludeHeader,
  setLogo,
  handleGeneratePDF,
  onQuestionsGenerated,
  triggerToast,

}) => {
  const [file, setFile] = useState(null);
  const [text, setText] = useState('');
  const [logoFile, setLogoFile] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleGenerateQuestions = async () => {
    if (!file && !text.trim()) {
      triggerToast('Please upload a file or enter text to generate questions.');
      return;
    }

    if (text.length > 300) {
      triggerToast('Text too long. Maximum 300 characters allowed.', 'error');
      return;
    }

    setLoading(true);
    try {
      let result;
      if (file) {
        result = QuestionService.generateQuestions(file, text).then(() => {
          if (onQuestionsGenerated) onQuestionsGenerated(result);
          setLoading(false);
        }).catch(() => {
          triggerToast('Error generating questions.');
          setLoading(false);
        });
      } else {
        result = QuestionService.askQuestion(text).then(() => {
          if (onQuestionsGenerated) onQuestionsGenerated(result);
          setLoading(false);
        }).catch(() => {
          triggerToast('Error generating questions.');
          setLoading(false);
        });
      }

    } catch (error) {
      triggerToast('Error generating questions.');
    } finally {

    }
  };

  return (
    <Box
      sx={{
        width: '320px',
        height: `calc(100vh - 64px)`,
        overflowY: 'auto',
        position: 'fixed',
        top: '64px',
        right: 0,
        backgroundColor: '#ffffff',
        padding: '20px',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'flex-start',
        borderLeft: '1px solid #ddd',
        boxShadow: '-2px 0 5px rgba(0,0,0,0.1)'
      }}
    >
      <Typography variant="h6" sx={{ textAlign: 'center', marginBottom: '12px', fontWeight: 'bold' }}>
        Generate New Questions
      </Typography>

      <Typography variant="body2" sx={{ textAlign: 'center', fontWeight: 'bold', borderBottom: '1px solid #ccc', marginBottom: '8px' }}>
        Content File
      </Typography>
      <Button
        variant="contained"
        component="label"
        sx={{
          backgroundColor: '#009688',
          color: '#fff',
          fontWeight: 'bold',
          textTransform: 'none',
          padding: '10px',
          borderRadius: '6px',
          '&:hover': { backgroundColor: '#00796b' },
          marginBottom: '16px'
        }}
      >
        {file ? file.name : 'Upload File'}
        <input
          type="file"
          accept=".txt,.pdf,.docx"
          hidden
          onChange={(e) => setFile(e.target.files[0])}
        />
      </Button>

      <Typography variant="body2" sx={{ textAlign: 'center', fontWeight: 'bold', borderBottom: '1px solid #ccc', marginBottom: '8px' }}>
        Generation Instructions
      </Typography>
      <TextField
        fullWidth
        label="Text"
        variant="outlined"
        value={text}
        onChange={(e) => setText(e.target.value)}
        multiline
        rows={6}
        sx={{ marginBottom: '16px' }}
      />

      <Button
        fullWidth
        variant="contained"
        onClick={handleGenerateQuestions}
        disabled={loading}
        sx={{
          backgroundColor: '#009688',
          color: '#fff',
          fontWeight: 'bold',
          '&:hover': { backgroundColor: '#00796b' },
          padding: '12px',
          fontSize: '16px',
          borderRadius: '8px',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          gap: 1
        }}
      >
        {loading ? (
          <>
            <CircularProgress size={20} sx={{ color: '#fff' }} />
            Generating...
          </>
        ) : (
          'Generate Questions'
        )}
      </Button>

      <Divider sx={{ marginY: '20px' }} />

      <Typography variant="h6" sx={{ textAlign: 'center', marginBottom: '12px', fontWeight: 'bold' }}>
        Generate Exam File
      </Typography>

      <Typography variant="body2" sx={{ textAlign: 'center', fontWeight: 'bold', borderBottom: '1px solid #ccc', marginBottom: '8px' }}>
        Exam Title
      </Typography>
      <TextField
        fullWidth
        label="Title"
        variant="outlined"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        sx={{ marginBottom: '16px' }}
      />

      <FormControlLabel
        control={
          <Checkbox
            checked={includeHeader}
            onChange={(e) => setIncludeHeader(e.target.checked)}
          />
        }
        label="Include Header"
      />

      <Typography variant="body2" sx={{
        textAlign: 'center',
        fontWeight: 'bold',
        paddingBottom: '4px',
        borderBottom: '1px solid #ccc',
        marginBottom: '8px',
        color: includeHeader ? '#000' : '#aaa',
        fontStyle: includeHeader ? 'normal' : 'italic'
      }}>
        Header Logo
      </Typography>

      <Button
        variant="contained"
        component="label"
        disabled={!includeHeader}
        sx={{
          backgroundColor: includeHeader ? '#673AB7' : '#e0e0e0',
          color: '#fff',
          fontWeight: 'bold',
          textTransform: 'none',
          padding: '10px',
          borderRadius: '6px',
          '&:hover': {
            backgroundColor: includeHeader ? '#5E35B1' : '#e0e0e0'
          },
          marginBottom: '16px'
        }}
      >
        {logoFile ? logoFile.name : 'Upload Logo'}
        <input
          type="file"
          accept="image/*"
          hidden
          disabled={!includeHeader}
          onChange={(e) => {
            const file = e.target.files[0];
            setLogoFile(file);
            setLogo(file);
          }}
        />
      </Button>

      <Button
        fullWidth
        variant="contained"
        onClick={handleGeneratePDF}
        sx={{
          backgroundColor: '#673AB7',
          color: '#fff',
          fontWeight: 'bold',
          '&:hover': { backgroundColor: '#5E35B1' },
          padding: '12px',
          fontSize: '16px',
          borderRadius: '8px'
        }}
      >
        Generate Test
      </Button>
    </Box>
  );
};

export default RightPanel;
