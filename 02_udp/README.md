# Proyecto UDP - PeerA y PeerB

Este proyecto contiene una configuración de Gradle para una aplicación Java distribuida en dos subproyectos: PeerA y PeerB.

## Instalación de Gradle en Ubuntu Linux

A continuación se describe el proceso para instalar Gradle (versión binary-only) en un sistema operativo Ubuntu.

### 1. Descargar Gradle

Descargue la distribución binaria de Gradle desde el sitio web oficial. Puede encontrar la versión 9.0 en el siguiente enlace:
[https://gradle.org/releases/](https://gradle.org/releases/)

O puede usar `wget` para descargarla directamente desde la terminal (si estas en un sistema operativo Linux):

```bash
wget https://services.gradle.org/distributions/gradle-9.0.0-bin.zip 
```

Cree un directorio para Gradle y descomprima el archivo descargado.

```bash
sudo mkdir ~/Software/gradle
sudo unzip -d ~/Software/gradle gradle-9.0.0-bin.zip
```

### 3. Configurar las variables de entorno

Para que Gradle funcione correctamente, debe configurar la variable de entorno `GRADLE_HOME` y agregar el directorio `bin` de Gradle a su `PATH`. Edite su archivo `~/.bashrc` para agregar las siguientes líneas al final:

```bash
vim ~/.bashrc
```

Agregue lo siguiente:

```bash
export GRADLE_HOME=/opt/gradle/gradle-9.0
export JAVA_HOME=/usr/lib/jvm/java-1.17.0-openjdk-amd64
export PATH=$GRADLE_HOME/bin:$JAVA_HOME/bin:${PATH}
```

Guarde el archivo y aplique los cambios en su sesión actual de la terminal:

```bash
source ~/.bashrc
```

### 4. Verificar la instalación

Para confirmar que Gradle se ha instalado correctamente, ejecute el siguiente comando:

```bash
gradle -v
```

Debería ver la versión de Gradle que acaba de instalar.



## Resumen de la Configuración de Gradle

### `settings.gradle`

Este archivo define la estructura del proyecto multi-módulo.

- `rootProject.name = '02_udp'`: Establece el nombre del proyecto raíz como `02_udp`.
- `include 'PeerA'`: Incluye el subproyecto llamado `PeerA`  (Sender). 
- `include 'PeerB'`: Incluye el subproyecto llamado `PeerB`  (Receiver). 

### `build.gradle` (Raíz)

Este es el script de construcción principal que define la configuración común para todos los subproyectos.

- `plugins { id 'java'; id 'application' }`: Aplica los plugins de Java y Application a nivel raíz.
- `subprojects { ... }`: Define una configuración que se aplica a todos los subproyectos (`PeerA` y `PeerB`).
  - `apply plugin: 'java'`: Aplica el plugin de Java.
  - `apply plugin: 'application'`: Aplica el plugin de Application, permitiendo que cada subproyecto sea una aplicación ejecutable.
  - `repositories { mavenCentral() }`: Configura Maven Central como el repositorio para descargar dependencias.

### `PeerA/build.gradle`

Este archivo contiene la configuración específica para el subproyecto `PeerA`.

- `application { mainClass = "ui.Main" }`: Especifica que la clase principal para la ejecución de la aplicación es `ui.Main`.
- `jar { ... }`: Configura la creación del archivo JAR.
  - `attributes 'Main-Class': 'ui.Main'`: Añade el atributo `Main-Class` al manifiesto del JAR, haciendo que el JAR sea autoejecutable.
  - `archiveBaseName = 'peer-a'`: Establece el nombre base del archivo JAR generado a `peer-a`.

### `PeerB/build.gradle`

La configuración para el subproyecto `PeerB` es idéntica a la de `PeerA`, pero para su propio contexto.

- `application { mainClass = "ui.Main" }`: Define `ui.Main` como la clase principal.
- `jar { ... }`: Configura el JAR para que sea autoejecutable con la clase `ui.Main` y nombra el archivo resultante como `peer-b.jar`.



> Este resumen ha sido generado por el modelo LLM de Gemini y revisado por @njse22
