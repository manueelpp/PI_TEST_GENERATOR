package com.test_generator.shared.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class Constants {

    // Common constants
    public static final String ID = "id";
    public static final String MESSAGE = "message";
    public static final String MESSAGES = "messages";
    public static final String CONTENT = "content";
    public static final String FILE = "file";
    public static final String FILES = "files";
    public static final String SYSTEM = "system";
    public static final String MODEL = "model";
    public static final String OLLAMA_MODEL = "llama3.1:latest";
    public static final String ROLE = "role";
    public static final String USER = "user";
    public static final String TYPE = "type";
    public static final String CHOICES = "choices";

    //
    public static final Integer RECENT_MINUTES = 10;

    // ENTITIES
    public static final String SAMPLE_ENTITY = "Sample";
    public static final String DIFFICULTY_ENTITY = "Difficulty";
    public static final String QUESTION_ENTITY = "Question";
    public static final String SUBJECT_ENTITY = "Subject";
    public static final String TOPIC_ENTITY = "Topic";

    //PROMPTS_INSTRUCTIONS
    public static final String DEFAULT_TEST_PROMPT = "Inventate un examen tipo test de 30 preguntas sobre el contenido que te voy a decir a continuación. con 4 opciones cada pregunta. Dividelasas por dificultad en: 10 fáciles, 10 mediumes y 10 difíciles.";
    public static final String USER_PROMPT_FLAG = "TEXTO INTODUCIDO POR EL USUARIO: ";
    public static final String INSTRUCTIONS_PROMPT_FLAG = "\nINSTRUCCIONES: \n";
    public static final String LANGUAGE_ANSWER_INSTRUCTIONS = "Las preguntas y las opciones deben estar en el mismo idioma que el texto introducido por el usuario.\n";
    public static final String ABOUT_FILE_CONTENT = " sobre el contenido del archivo.";
    public static final String CSV_PROMPT_INSTRUCTIONS_WITHOUT_FILES = """
📌 INSTRUCCIONES PARA GENERAR CSV CON PREGUNTAS

🛑 REGLAS GENERALES
1️⃣ Céntrate exclusivamente en el contenido que te proporcionaré a continuación. Los ejemplos que verás más abajo son solo guías para el formato, no debes basarte en ellos para las preguntas.
2️⃣ Las preguntas deben corresponder al contenido del archivo, si no hay archivo centrarse en lo que pide el usuario.
3️⃣ Solo responde con el contenido del archivo CSV.
   - ❌ No agregues explicaciones, introducciones ni comentarios adicionales.
   - ✅ Tu respuesta debe comenzar directamente con la línea de cabecera del CSV, seguida por el contenido generado.

📌 FORMATO DEL CSV (OBLIGATORIO)
Cada campo se separa con "::" (doble dos puntos).
   - ❌ No uses "/", ",", "|" u otros separadores.
   - ✅ La primera línea SIEMPRE debe ser la cabecera.
   - ✅ Correcto: materia::tema::dificultad::pregunta::opcion1::opcion2::opcion3::opcion4::respuesta

Reglas para la columna "respuesta":
   - 🚨 Debe ser exactamente una de las opciones dadas.
   - ❌ Incorrecto: respuesta::Sí/No (NO puede ser texto libre).
   - ✅ Correcto: respuesta::Sí.
   - ❌ NO omitas la respuesta en preguntas de opción múltiple, ni tipo test. En el resto sí.
   
Reglas para la columna "dificultad":
   - 🚨 Debe ser exactamente una de estas opciones (easy, medium, hard).
   - ❌ Incorrecto: NO puede estar en otro idioma que no sea inglés
   - ✅ Correcto: Siempre en Inglés y una de estas opciones (easy, medium, hard).

Si una opción está vacía, usa "::" para mantener la estructura.
✅ Ejemplo:
Linux::comandos::medium::¿Cómo ver archivos ocultos?::ls -a::::::ls -a

Preguntas de desarrollo NO tienen opciones ni respuesta:
✅ Ejemplo:
Historia::Antigua Roma::hard::Describe la influencia de la República Romana en los sistemas de gobierno modernos::::::::

Si la pregunta es de desarrollo, por favor no incluyas opciones ni respuestas. Ejemplo:
Historia::España 1700-1960::hard::Describe las principales consecuencias de la Guerra Civil Española::::::::

🚨 CORRECCIÓN DE ERRORES FRECUENTES
🔴 NO OMITAS la columna "respuesta" en preguntas tipo test.
   - ❌ Linux::comandos::medium::¿Qué comando se usa para crear un directorio?::mkdir::rm::cp::ls
   - ✅ Linux::comandos::medium::¿Qué comando se usa para crear un directorio?::mkdir::rm::cp::ls::mkdir

🔴 Las preguntas multirespuesta deben separarse con "|" en la columna "respuesta"
   - ❌ Linux::redes::medium::¿Cómo se configura la conexión a Internet en Linux?::ifconfig ip addr add::sudo apt-get install network-manager::nmcli c conn-add::nmtui::nmcli c conn-mod
   - ✅ Linux::redes::medium::¿Cómo se configura la conexión a Internet en Linux?::ifconfig ip addr add::sudo apt-get install network-manager::nmcli c conn-add::nmtui::nmcli c conn-add|nmcli c conn-mod

¡¡¡Los ejemplos que verás más abajo son solo guías para el formato, no debes basarte en ellos para tu respuesta!!!
📌 EJEMPLOS DE DISTINTAS RESPUESTAS:
Matemáticas::Álgebra::medium::¿Cuál es el resultado de 2 + 2?::3::4::5::6::4
Matemáticas::Geometría::easy::¿Cuántos lados tiene un triángulo?::3::4::5::6::3
Matemáticas::Algebra::hard::¿Cuál es la expresión (3x^2 - 2x + 1) en su forma factorizada?::(x - 1)(3x - 1)::(x + 1)(3x - 1)::(x - 1)(3x + 1)::(x - 1)(3x - 1)
Linux::comandos::medium::¿Qué comando se usa para listar archivos?::ls::dir::cat::pwd::ls
Historia::Edad Media::hard::¿En qué año cayó Constantinopla?::1453::1492::1517::1776::1453
Historia::Antigua Roma::hard::Describe la influencia de la República Romana en los sistemas de gobierno modernos::::::::::
Historia::Segunda Guerra Mundial::medium::¿La Segunda Guerra Mundial terminó en 1945?::Verdadero::Falso::::::Verdadero
Ciencias::Física::medium::¿Cuál es la unidad de medida de la fuerza?::Newton::Joule::Watt::Pascal::Newton
Ciencias::Biología::easy::¿Los seres humanos tienen 23 pares de cromosomas?::Verdadero::Falso::::::Verdadero
Química::Elementos::easy::¿Cuál es el símbolo químico del agua?::H2O::O2::CO2::HCl::H2O
Geografía::Continentes::medium::¿África es el continente más grande del mundo?::Verdadero::Falso::::::Falso
Historia::España 1700-1960::hard::Describe las principales consecuencias de la Guerra Civil Española::::::::


🚀 REGLAS FINALES
🔹 No expliques tu respuesta.
🔹 No agregues comentarios.
🔹 Lo más importante es satisfacer la petición del usuario
🔹 Los ejemplos de las instrucciones son únicamente ejemplos.
🔹 Solo responde con el contenido CSV en el formato correcto.
🔹 No añadas números para identificar las preguntas
""";

    public static final String CSV_PROMPT_INSTRUCTIONS_WITH_FILES = """
📌 INSTRUCCIONES PARA GENERAR CSV CON PREGUNTAS

🛑 REGLAS GENERALES
1️⃣ Genera un examen basado en el contenido del archivo proporcionado.
2️⃣ Solo responde con el contenido del archivo CSV.
   - ❌ No agregues explicaciones, introducciones ni comentarios adicionales.
   - ✅ Tu respuesta debe comenzar directamente con la línea de cabecera del CSV, seguida por el contenido generado.

📌 FORMATO DEL CSV (OBLIGATORIO)
Cada campo se separa con "::" (doble dos puntos).
   - ❌ No uses "/", ",", "|" u otros separadores.
   - ✅ La primera línea SIEMPRE debe ser la cabecera.
   - ✅ Correcto: materia::tema::dificultad::pregunta::opcion1::opcion2::opcion3::opcion4::respuesta

Reglas para la columna "respuesta":
   - 🚨 Debe ser exactamente una de las opciones dadas.
   - ❌ Incorrecto: respuesta::Sí/No (NO puede ser texto libre).
   - ✅ Correcto: respuesta::Sí.
   - ❌ NO omitas la respuesta en preguntas de opción múltiple.
   
Reglas para la columna "dificultad":
   - 🚨 Debe ser exactamente una de estas opciones (easy, medium, hard).
   - ❌ Incorrecto: NO puede estar en otro idioma que no sea inglés
   - ✅ Correcto: Siempre en Inglés y una de estas opciones (easy, medium, hard).

Si una opción está vacía, usa "::" para mantener la estructura.

Preguntas de desarrollo NO tienen opciones ni respuesta:

🚨 CORRECCIÓN DE ERRORES FRECUENTES
🔴 NO OMITAS la columna "respuesta"
   Ejemplos:
   - ❌ Linux::comandos::medium::¿Qué comando se usa para crear un directorio?::mkdir::rm::cp::ls
   - ✅ Linux::comandos::medium::¿Qué comando se usa para crear un directorio?::mkdir::rm::cp::ls::mkdir
 
🔴 Las preguntas multirespuesta deben separarse con "|" en la columna "respuesta"
   Ejemplos:
   - ❌ Linux::redes::medium::¿Cómo se configura la conexión a Internet en Linux?::ifconfig ip addr add::sudo apt-get install network-manager::nmcli c conn-add::nmtui::nmcli c conn-mod
   - ✅ Linux::redes::medium::¿Cómo se configura la conexión a Internet en Linux?::ifconfig ip addr add::sudo apt-get install network-manager::nmcli c conn-add::nmtui::nmcli c conn-add|nmcli c conn-mod


🚀 REGLAS FINALES
🔹 No expliques tu respuesta.
🔹 No agregues comentarios.
🔹 Lo más importante es satisfacer la petición del usuario
🔹 Solo responde con el contenido CSV en el formato correcto.
🔹 No añadas números para identificar las preguntas
""";

    public static final String PROMPT_WITHOUT_FILES_TEMPLATE = USER_PROMPT_FLAG + "%s" + INSTRUCTIONS_PROMPT_FLAG + LANGUAGE_ANSWER_INSTRUCTIONS + CSV_PROMPT_INSTRUCTIONS_WITHOUT_FILES;
    public static final String PROMPT_WITH_FILES_TEMPLATE = USER_PROMPT_FLAG + "%s" + ABOUT_FILE_CONTENT + INSTRUCTIONS_PROMPT_FLAG + LANGUAGE_ANSWER_INSTRUCTIONS + CSV_PROMPT_INSTRUCTIONS_WITH_FILES;

    // EXCEPTIONS - MESSAGES
    public static final String EXCEPTION = "Exception: ";
    public static final String EXCEPTION_HAS_OCURRED = "Se ha producido una excepcion: {}";
    public static final String NOT_FOUND = " not found";
    public static final String WITH_ID = " with id ";
    public static final String WITH_NAME = " with name ";
    public static final String AND_RELATED_WITH = " and related with ";
    public static final String WAS_DELETED_SUCCESSFULLY = ", was deleted successfully";
    public static final String VALID_FILES_EXTENSIONS = "PDF, CSV, XML, HTML, Excel (XLSX), Word (DOCX), PPTX, TXT, MD, JSON.";
    public static final String TOPIC_MANDATORY_FIELDS = "Name and subject are mandatory fields.";

    // EXCEPTIONS - API
    public static final String API_UPLOAD_FILE_FAILED = "File upload failed";
    public static final String OPENWEBUI_NOT_AVAILABLE = "OpenWebUI service isn't available. Please, try it later.";
    public static final String RESPONSE_GENERATION_FAILURE = "Unable to generate a useful response. Please try again with more specific details about the topic, subject, or the type of exam questions you need.";
    public static final String ERROR_SENDING_REQUEST_TO_OPENWEBUI = "Error al enviar solicitud a OpenWebUI: ";
    public static final String UNAUTHORIZED_EXCEPTION_OPENWEBUI = "Unauthorized: ";

    // API - RESPONSES
    public static final String OPENWEBUI_AVAILABLE = "OpenWebUI service is available.";

    // API - URLs
    public static final String OPENWEBUI_CHAT_REQUEST_URL = "/api/chat/completions?temporary-chat=true";
    public static final String OPENWEBUI_UPLOAD_FILE_REQUEST_URL = "/api/v1/files/";
    public static final String OPENWEBUI_STATUS_URL = "/api/health";

}
