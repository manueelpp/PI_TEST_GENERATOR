@echo off

echo Iniciando contenedores Docker...
docker-compose -f W:\Escritorio\TFG\test-generator\backend\test-generator\src\main\resources\docker\docker-compose.mysql.yml up -d
docker-compose -f W:\Escritorio\TFG\test-generator\backend\test-generator\src\main\resources\docker\docker-compose.open-web-ui.yml up -d

echo Iniciando backend Spring Boot...
start /b cmd /c "cd /d W:\Escritorio\TFG\test-generator\backend\test-generator && mvn spring-boot:run"

echo Esperando a que el backend esté completamente iniciado...
:esperar_backend
timeout /t 5 >nul
netstat -an | find "LISTENING" | find ":8080" >nul
if errorlevel 1 goto esperar_backend

echo Backend iniciado correctamente.

echo Iniciando frontend Vue...
start /b cmd /c "cd /d W:\Escritorio\TFG\test-generator\frontend\test-generator-app && npm run dev"

echo Abriendo el navegador en la URL del frontend...
start http://localhost:5173/

echo Todo ha sido iniciado.
pause


