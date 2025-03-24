import React, { useState, useEffect } from 'react';
import { Box } from '@mui/material';
import jsPDF from 'jspdf';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import SearchPanel from '../search_panel/SearchPanel';
import RightPanel from '../right_panel/RightPanel';
import SelectedQuestionsModal from '../selected_questions_modal/SelectedQuestionsModal';
import QuestionService from '../../services/questionService';

const CardList = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedQuestions, setSelectedQuestions] = useState([]);
  const [tempSelectedQuestions, setTempSelectedQuestions] = useState([]);
  const [showSelectedQuestions, setShowSelectedQuestions] = useState(false);
  const [title, setTitle] = useState('');
  const [includeHeader, setIncludeHeader] = useState(false);
  const [logo, setLogo] = useState(null);
  const [cards, setCards] = useState([]);
  const [justUpdated, setJustUpdated] = useState(false);

  const fetchQuestions = async () => {
    try {
      const data = await QuestionService.getQuestions();
      const sorted = data.content.sort(
        (a, b) => new Date(b.generatedAt) - new Date(a.generatedAt)
      );
      setCards(sorted);
    } catch (error) {
      console.error('Failed to fetch questions:', error);
      triggerToast('Error fetching questions');
    }
  };


  const isUUID = (id) => /^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/i.test(id);
  const isInt = (id) => typeof id === 'number' || /^\d+$/.test(id);

  useEffect(() => {
    fetchQuestions();
  }, []);

  const uploadQuestion = async (id, question) => {
    try {
      if (id != null && isInt(id) && question != null) {
        await QuestionService.updateQuestion(id, question);
      } else if (!id || isUUID(id) && question != null) {
        await QuestionService.createQuestion(question);
      } else if (question == null && id != null && !isUUID(id)) {
        await QuestionService.deleteQuestion(id);
      }
      setJustUpdated(true);
    } catch (error) {
      console.error('Error updating question:', error);
      triggerToast('Error updating question');
    }
  };

  const triggerToast = (message, type = 'error') => {
    const style = {
      backgroundColor: type === 'success' ? '#43a047' : '#d32f2f',
      color: '#fff',
      fontSize: '14px',
      minHeight: '60px',
      borderRadius: '8px',
      padding: '12px 16px',
      boxShadow: '0px 2px 8px rgba(0,0,0,0.15)',
    };

    toast[type](message, {
      position: 'top-right',
      style,
    });
  };

  const updateCard = (id, newQuestion, newOptions, subject, difficulty, topic) => {
    if (justUpdated || newQuestion == null) {
      fetchQuestions();
      setJustUpdated(false);
      return;
    }

    if (id == null) {
      const newCard = {
        id: crypto.randomUUID(),
        question: newQuestion,
        choices: newOptions,
        subject: subject ? { name: subject.name ?? subject } : null,
        difficulty: difficulty ? { name: difficulty.name ?? difficulty } : null,
        topic: topic ? { name: topic.name ?? topic } : null,
        generatedAt: new Date().toLocaleDateString('en-CA')
      };

      setCards((prevCards) => {
        const updated = [newCard, ...prevCards];
        return updated.sort((a, b) => new Date(b.generatedAt) - new Date(a.generatedAt));
      });

    } else if (newQuestion == null) {
      setCards((prevCards) => prevCards.filter((card) => card.id !== id));
    } else {
      setCards((prevCards) => {
        const updated = prevCards.map((card) =>
          card.id === id
            ? {
              ...card,
              question: newQuestion,
              choices: newOptions,
              subject: subject ? { name: subject.name ?? subject } : null,
              difficulty: difficulty ? { name: difficulty.name ?? difficulty } : null,
              topic: topic ? { name: topic.name ?? topic } : null,
              generatedAt: new Date().toLocaleDateString('en-CA')
            }
            : card
        );
        return updated.sort((a, b) => new Date(b.generatedAt) - new Date(a.generatedAt));
      });
    }
  };


  const handleSearchChange = (event) => setSearchTerm(event.target.value);

  // ✅ Aquí está el único cambio importante
  const handleQuestionToggle = (id, question, options, difficulty) => {
    setTempSelectedQuestions((prevSelected) => {
      const exists = prevSelected.some((q) => q.id === id);
      return exists
        ? prevSelected.filter((q) => q.id !== id)
        : [...prevSelected, { id, question, options, difficulty }];
    });
  };

  const handlePreview = () => {
    setSelectedQuestions(tempSelectedQuestions);
    setShowSelectedQuestions(true);
  };

  const handleGeneratePDF = async () => {
    const doc = new jsPDF();
    const pageHeight = doc.internal.pageSize.height;
    const pageWidth = doc.internal.pageSize.width;
    let yOffset = includeHeader ? 60 : 20;

    if (includeHeader) {
      doc.setFontSize(12);
      doc.text('Nombre: ___________', 10, 20);
      doc.text('Apellidos: ___________', 105, 20);
      doc.text('Curso: ___________', 10, 30);
      doc.text('Grupo: ___________', 105, 30);

      if (logo) {
        const imgData = await new Promise((resolve) => {
          const reader = new FileReader();
          reader.onload = (e) => resolve(e.target.result);
          reader.readAsDataURL(logo);
        });

        const img = new Image();
        img.src = imgData;

        await new Promise((resolve, reject) => {
          img.onload = () => {
            const width = 30;
            const height = width / (img.width / img.height);
            doc.addImage(imgData, 'PNG', 160, 10, width, height);
            resolve();
          };
          img.onerror = reject;
        });
      }

      doc.line(10, 40, 200, 40);
    }

    doc.setFontSize(18);
    const textWidth = doc.getStringUnitWidth(title) * doc.internal.getFontSize() / doc.internal.scaleFactor;
    const textOffset = (pageWidth - textWidth) / 2;
    doc.text(title, textOffset, yOffset);
    yOffset += 10;

    doc.setFontSize(12);

    selectedQuestions.forEach((q, index) => {
      const questionLines = doc.splitTextToSize(`${index + 1}. ${q.question}`, 180);
      const lineHeight = 6;
      const compactLineHeight = 7;

      if (yOffset + questionLines.length * lineHeight > pageHeight - 20) {
        doc.addPage();
        yOffset = 20;
      }

      doc.text(questionLines, 10, yOffset);
      yOffset += questionLines.length === 1 ? 10 : questionLines.length * compactLineHeight;

      if (q.options) {
        q.options.forEach((option) => {
          if (option.trim()) {
            const optionLines = doc.splitTextToSize(`- ${option}`, 175);
            doc.text(optionLines, 15, yOffset);
            yOffset += optionLines.length === 1 ? 10 : optionLines.length * compactLineHeight;
          }
        });
      }

      yOffset += 6;
    });

    const pdfBlob = doc.output('blob');
    const pdfUrl = URL.createObjectURL(pdfBlob);
    window.open(pdfUrl, '_blank');
  };


  const filteredCards = cards
    .map((card) => ({
      ...card,
      selected: tempSelectedQuestions.some((q) => q.question === card.question),
    }))
    .filter((card) => card.question?.toLowerCase().includes(searchTerm.toLowerCase()));

  return (
    <>
      <ToastContainer
        autoClose={4000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="colored"
      />

      <Box sx={{ display: 'flex' }}>
        <SearchPanel
          searchTerm={searchTerm}
          onSearchChange={handleSearchChange}
          filteredCards={filteredCards}
          onToggleQuestion={handleQuestionToggle}
          showSelectedQuestions={handlePreview}
          updateCard={updateCard}
          updateQuestion={uploadQuestion}
          triggerToast={triggerToast}
        />
        <RightPanel
          title={title}
          setTitle={setTitle}
          includeHeader={includeHeader}
          setIncludeHeader={setIncludeHeader}
          setLogo={setLogo}
          handleGeneratePDF={handleGeneratePDF}
          onQuestionsGenerated={fetchQuestions}
          triggerToast={triggerToast}
        />
        <SelectedQuestionsModal selectedQuestions={selectedQuestions} />
      </Box>
    </>
  );
};

export default CardList;
