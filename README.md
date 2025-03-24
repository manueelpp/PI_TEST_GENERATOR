
# Test Generator Application

Esta es una aplicación desarrollada con **Spring Boot** para el backend, **Vite** para el frontend y conectada a través de Docker a **OpenWebUI**, **MySQL**, y **Ollama** para interactuar con el modelo de lenguaje **Llama 3.1**. Este archivo `.README` proporciona información sobre cómo instalar y configurar todo el entorno necesario para ejecutar la aplicación.

## Requisitos previos

Antes de comenzar, asegúrate de tener instalados los siguientes programas:

- **Java 21** (para ejecutar Spring Boot)
- **Docker Desktop**: [Descargar Docker Desktop](https://www.docker.com/products/docker-desktop)
- **Ollama** (para correr el modelo de Llama 3.1): [Descargar Ollama](https://ollama.com)

### Configuración de Docker Desktop

1. **Instalar Docker Desktop** desde [aquí](https://www.docker.com/products/docker-desktop).
2. **Activar virtualización en la BIOS**: Asegúrate de que la virtualización esté habilitada en tu BIOS. Esto es necesario para que Docker funcione correctamente.

## Instalación y Configuración

### Paso 1: Clonar el repositorio

Clona este repositorio en tu máquina local:

```
git clone https://github.com/manueelpp/PI_TEST_GENERATOR.git
```

### Paso 2: Configuración de Ollama

1. Descarga e instala **Ollama** desde [Ollama](https://ollama.com).
2. Abre una terminal o CMD y ejecuta:

```
ollama
```

3. Dirígete a [Biblioteca de modelos](https://ollama.com/library/llama3.1) y copia el comando para descargar el modelo que más te convenga
4. Asegúrate de que el modelo **que hayas descargado** esté disponible y corriendo localmente.

```
ollama list
```

### Paso 3: Configuración de Docker

Asegúrate de que Docker esté corriendo en tu máquina local y que tienes los contenedores adecuados para **OpenWebUI** y **MySQL**.

1. **Configura OpenWebUI** para que use el modelo de Ollama.
   
   En tu archivo `docker-compose.yml` (en el directorio correspondiente), asegúrate de que OpenWebUI esté configurado para conectarse a Ollama:

   ```yaml
   services:
     open-webui:
       image: ghcr.io/open-webui/open-webui:cuda
       container_name: open_webui
       restart: always
       ports:
         - "3000:8080"                                            # Mapea el puerto 3000 del host al puerto 8080 del contenedor
       environment:
         - WEBUI_GPU=true                                         # Habilita la GPU si está disponible
         - OLLAMA_API_BASE_URL=http://host.docker.internal:11434
       runtime: nvidia                                            # Usar GPU con el runtime NVIDIA
       volumes:
         - open-webui:/app/backend/data                           # Persistencia de datos usando un volumen
       extra_hosts:
         - "host.docker.internal:host-gateway"                    # Permite que el contenedor acceda a la máquina anfitriona
       networks:
         - openwebui_network

   networks:
     openwebui_network:
       driver: bridge

   volumes:
     open-webui:
   ```

2. **Configura MySQL** para que OpenWebUI y Spring Boot puedan interactuar con la base de datos.

   En tu archivo `docker-compose.yml` también incluye la configuración de MySQL:

   ```yaml
   services:
     mysql:
       image: mysql:8.0                          # Imagen oficial de MySQL versión 8.0
       container_name: <container_name>          # Nombre del contenedor
       restart: always                           # El contenedor se reiniciará automáticamente si falla
       environment:                              # Variables de entorno para configurar MySQL
         MYSQL_ROOT_PASSWORD: <your_password>    # Contraseña del usuario root
         MYSQL_DATABASE: <your_db_name>          # Nombre de la base de datos por defecto
         MYSQL_USER: <your_user>                 # Nombre del usuario que crearemos
         MYSQL_PASSWORD: <your_password>         # Contraseña del usuario
       ports:
         - "3306:3306"                           # Mapea el puerto 3306 del contenedor al 3306 del host
       volumes:
         - mysql_data:/var/lib/mysql             # Almacena los datos en un volumen persistente

   volumes: # Definimos un volumen para persistencia de datos
     mysql_data:
   ```

### Paso 4: Configuración del archivo `.properties`

En el archivo `src/main/resources/application.properties`, configura las conexiones a la base de datos y la URL de OpenWebUI:

```properties
spring.application.name=test-generator

# Configuración de la conexión a la base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/test_generator?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=<your_user> 
spring.datasource.password=<your_password>

# Open-WebUI
openwebui.api.url=http://localhost:3000
openwebui.api.key=<your_api-key> 


```

### Paso 5: Ejecutar la aplicación

1. **Levanta los contenedores Docker**:

```
docker-compose -f <nombre del archivo docker-compose.yml> up -d
```

2. **Inicia el backend Spring Boot**:

   Desde la carpeta del proyecto, ejecuta:

```
./mvnw spring-boot:run
```

3. **Inicia el frontend React + Vue**:

   Desde la carpeta de tu frontend, ejecuta en PowerShell:

```
npm run dev
```

### Paso 6: Acceso a la aplicación

- **Swagger UI**: Puedes acceder a la documentación de la API en [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).
- **Interfaz OpenWebUI**: Puedes acceder a OpenWebUI en [http://localhost:3000](http://localhost:3000).

---

## Descripción de las tecnologías utilizadas

- **Spring Boot**: Framework para el desarrollo del backend.
- **Vite** + **React**: Frameworks de frontend.
- **OpenWebUI**: Interfaz web para interactuar con tu modelo de lenguaje.
- **Ollama**: Software para ejecutar modelos Llama 3.1 localmente.
- **MySQL**: Base de datos para persistencia de datos.
- **Docker**: Contenedores para facilitar la ejecución de OpenWebUI, MySQL y otros servicios.

## Solución de problemas

1. **Error al conectar a MySQL**: Asegúrate de que el contenedor de MySQL esté corriendo correctamente. Puedes revisar los logs de Docker con `docker logs mysql_test_generator_db`.
2. **Error 401 al conectar con OpenWebUI**: Verifica que la clave de la API en el archivo `application.properties` sea correcta y esté activa.



---

Este archivo `.README` proporciona una descripción completa sobre cómo configurar y ejecutar el sistema. Si tienes alguna duda o problema, no dudes en abrir un **issue** en el repositorio.
