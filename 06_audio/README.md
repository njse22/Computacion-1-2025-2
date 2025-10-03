# Proyecto de Streaming de Audio

Este proyecto demuestra una aplicación simple de cliente-servidor para transmitir audio en tiempo real desde un micrófono a un altavoz a través de una red.

## Archivos Principales

### 1. Cliente (`client/src/main/java/ui/Main.java`)

El programa cliente se encarga de:

- Conectarse a un servidor en la dirección `127.0.0.1` en el puerto `5000`.
- Capturar audio desde el micrófono del sistema.
- Utiliza un formato de audio estándar (44100 Hz, 16-bit, mono).
- Leer los datos del audio en un búfer.
- Enviar continuamente los datos de audio capturados al servidor a través de la conexión de red (socket).

### 2. Servidor (`server/src/main/java/ui/Main.java`)

El programa servidor realiza las siguientes acciones:

- Inicia un servidor y escucha conexiones entrantes en el puerto `5000`.
- Acepta una conexión de un cliente.
- Recibe los datos de audio enviados por el cliente.
- Reproduce el audio recibido en los altavoces del sistema en tiempo real.
- Utiliza el mismo formato de audio que el cliente para decodificar y reproducir el sonido.

## Cómo Usar

1. **Ejecutar el Servidor**: Inicia la ejecución de la clase `Main` en el directorio `server`. El servidor comenzará a esperar una conexión.
2. **Ejecutar el Cliente**: Inicia la ejecución de la clase `Main` en el directorio `client`. El cliente se conectará automáticamente al servidor.
3. **Transmitir**: Habla en el micrófono del dispositivo cliente. El audio se reproducirá en los altavoces del dispositivo servidor.
