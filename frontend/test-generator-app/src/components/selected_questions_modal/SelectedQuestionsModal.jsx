import React from 'react';
import { Box, Typography, Divider } from '@mui/material';

const SelectedQuestionsModal = ({ selectedQuestions }) => {
  const getUnderlineColor = (difficulty) => {
    switch (difficulty?.toUpperCase()) {
      case 'EASY':
        return '#4caf50';
      case 'MEDIUM':
        return '#ff9800';
      case 'HARD':
        return '#f44336';
      default:
        return '#9e9e9e';
    }
  };

  return (
    <Box
      sx={{
        position: 'fixed',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        backgroundColor: '#ffffff',
        padding: '24px',
        boxShadow: '0px 4px 12px rgba(0, 0, 0, 0.1)',
        borderRadius: '8px',
        maxWidth: '500px',
        width: '100%',
        maxHeight: '65vh',
        overflowY: 'auto',
      }}
    >
      <Typography
        variant="h6"
        sx={{ marginBottom: '12px', fontWeight: 'bold', textAlign: 'center' }}
      >
        Exam Previsualization
      </Typography>
      <Divider sx={{ marginBottom: '16px' }} />

      {selectedQuestions.length > 0 ? (
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
          {selectedQuestions.map((q, index) => {
            const validOptions = q.options?.filter(opt => opt.trim()) || [];
            const underlineColor = getUnderlineColor(q.difficulty);

            return (
              <Box key={index}>
                <Typography
                  variant="body1"
                  sx={{
                    wordBreak: 'break-word',
                    whiteSpace: 'normal',
                    lineHeight: 1.6,
                  }}
                >
                  <Box
                    component="span"
                    sx={{
                      borderBottom: `3px solid ${underlineColor}`,
                      paddingBottom: '2px',
                      marginRight: '6px',
                      fontWeight: 'bold',
                    }}
                  >
                    {index + 1}.
                  </Box>
                  {q.question}
                </Typography>

                {validOptions.length > 0 && (
                  <Box sx={{ marginLeft: '16px', marginTop: '6px' }}>
                    {validOptions.map((option, optIndex) => (
                      <Typography
                        key={optIndex}
                        variant="body2"
                        sx={{
                          wordBreak: 'break-word',
                          whiteSpace: 'normal',
                          lineHeight: 1.5,
                        }}
                      >
                        - {option}
                      </Typography>
                    ))}
                  </Box>
                )}
              </Box>
            );
          })}
        </Box>
      ) : (
        <Typography variant="body1" sx={{ textAlign: 'center', color: 'gray' }}>
          No questions selected.
        </Typography>
      )}
    </Box>
  );
};

export default SelectedQuestionsModal;
