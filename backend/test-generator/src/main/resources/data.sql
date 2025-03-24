-- Eliminar contenido existente
DELETE FROM questions;
DELETE FROM topics;
DELETE FROM subjects;
DELETE FROM enum_difficulties;

-- Reiniciar AUTO_INCREMENT
ALTER TABLE questions AUTO_INCREMENT = 1;
ALTER TABLE topics AUTO_INCREMENT = 1;
ALTER TABLE subjects AUTO_INCREMENT = 1;
ALTER TABLE enum_difficulties AUTO_INCREMENT = 1;

-- ENUM_DIFFICULTIES
INSERT INTO enum_difficulties (name) VALUES
('EASY'),
('MEDIUM'),
('HARD');

-- SUBJECTS
INSERT INTO subjects (name) VALUES
('MATH'),
('SCIENCE'),
('HISTORY');

-- TOPICS (asociados a las asignaturas por orden)
INSERT INTO topics (name, fk_subject_id) VALUES
('ALGEBRA', 1),
('PHYSICS', 2),
('WORLD WAR II', 3);

-- QUESTIONS
INSERT INTO questions (fk_subject_id, fk_topic_id, fk_difficulty_id, question, choices, answer, generated_date) VALUES
(1, 1, 1, 'Solve for x: 2x + 4 = 10', '2||3||4', '3', '2025-03-16T09:00:00'),
(2, 2, 1, 'What is the speed of light?', '3x10^8||1.5x10^8||5x10^8||None of the above', '3x10^8', '2025-03-16T09:10:00'),
(3, 3, 1, 'When did World War II start?', '1937||1939||1941', '1939', '2025-03-16T09:20:00'),
(1, 1, 2, 'Factor: x^2 - 9', 'x+3||x-3||x^2+9||(x+3)(x-3)', '(x+3)(x-3)', '2025-03-16T09:30:00'),
(2, 2, 3, 'What is Newton''s second law?', 'F=ma||E=mc^2||V=IR', 'F=ma', '2025-03-16T09:40:00'),
(3, 3, 2, 'Name one Axis power country in WWII.', 'Germany||Italy||Japan||All of the above', 'All of the above', '2025-03-16T09:50:00'),
(1, 1, 1, 'What is 5 * 5?', '10||15||20||25', '25', '2025-03-16T10:00:00'),
(2, 2, 2, 'Which particle has no charge?', 'Proton||Electron||Neutron', 'Neutron', '2025-03-16T10:10:00'),
(3, 3, 1, 'WWII ended in?', '1943||1944||1945||1946', '1945', '2025-03-16T10:20:00'),
(2, 2, 1, 'What is H2O?', 'Water', 'Water', '2025-03-16T10:30:00'),
(1, 1, 3, 'Solve: x^2 = 16', '4||-4||4 and -4', '4 and -4', '2025-03-16T10:40:00'),
(1, 1, 2, 'What is the derivative of x^2?', '', '2x', '2025-03-16T10:50:00'),
(3, 3, 3, 'Main cause of WWII?', 'Treaty of Versailles||Hitler''s expansion||Economic crisis', 'Hitler''s expansion', '2025-03-16T11:00:00'),
(2, 2, 2, 'What unit is used for force?', 'Newton||Joule||Watt', 'Newton', '2025-03-16T11:10:00'),
(2, 2, 2, 'This question has no options.', '', '', '2025-03-16T11:15:00');
