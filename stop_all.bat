@echo off
echo Deteniendo contenedores Docker...
docker-compose -f W:\Escritorio\TFG\test-generator\backend\test-generator\src\main\resources\docker\docker-compose.mysql.yml down
docker-compose -f W:\Escritorio\TFG\test-generator\backend\test-generator\src\main\resources\docker\docker-compose.open-web-ui.yml down

echo Buscando procesos de Spring Boot...
for /f "tokens=2 delims=," %%A in ('tasklist /FI "IMAGENAME eq java.exe" /FO CSV /NH') do taskkill /F /PID %%A

echo Buscando procesos de Vue...
for /f "tokens=2 delims=," %%A in ('tasklist /FI "IMAGENAME eq node.exe" /FO CSV /NH') do taskkill /F /PID %%A

echo Todos los servicios han sido detenidos.
