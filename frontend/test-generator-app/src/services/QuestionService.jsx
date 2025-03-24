import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

const handleResponse = (response) => response.data;

const handleError = (error) => {
  console.error('API Error:', error.response || error.message);
  throw error.response ? error.response.data : error.message;
};

const QuestionService = {

  getQuestions: async () => {
    try {
      const response = await axios.get('http://localhost:8080/questions');
      return response.data;
    } catch (error) {
      console.error('Error fetching questions:', error);
      throw error;
    }
  },
  
  createQuestion: async (questionData) => {
    try {
      const response = await api.post('http://localhost:8080/questions', questionData);
      return handleResponse(response);
    } catch (error) {
      return handleError(error);
    }
  },

  updateQuestion: async (id, questionData) => {
    try {
      const response = await api.put(`http://localhost:8080/questions/${id}`, questionData);
      return handleResponse(response);
    } catch (error) {
      return handleError(error);
    }
  },

  deleteQuestion: async (id) => {
    try {
      const response = await api.delete(`http://localhost:8080/questions/${id}`);
      return handleResponse(response);
    } catch (error) {
      return handleError(error);
    }
  },

  generateQuestions: async (file, prompt = "Genera preguntas tipo test sobre el contenido del archivo") => {
    try {
      const formData = new FormData();
      formData.append('file', file);
      formData.append('prompt', prompt);
  
      const response = await axios.post('http://localhost:8080/api/ai/ask-with-file', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
  
      return handleResponse(response);
    } catch (error) {
      return handleError(error);
    }
  },

  askQuestion: async (prompt) => {
    try {
      const response = await axios.post(
        'http://localhost:8080/api/ai/ask',
        null,
        {
          params: { prompt },
          headers: { 'Content-Type': 'application/json' },
        }
      );
      return response.data;
    } catch (error) {
      console.error('Error in askQuestion:', error);
      throw error.response?.data || error.message;
    }
  },

  getSubjects: async () => {
    try {
      const response = await api.get('/subjects');
      return response.data.content;
    } catch (error) {
      return handleError(error);
    }
  },

  getDifficulties: async () => {
    try {
      const response = await api.get('/difficulties');
      return response.data.content;
    } catch (error) {
      return handleError(error);
    }
  },

  getTopicsBySubject: async (subjectId) => {
    try {
      const response = await api.get(`/topics/subject/${subjectId}`);
      return response.data.content;
    } catch (error) {
      return handleError(error);
    }
  },

};

export default QuestionService;
