# Resumen de Clases y su Interacción

Este repositorio contiene dos clases de Java, `Sender` y `Reciver`, que demuestran una comunicación básica utilizando el protocolo UDP (User Datagram Protocol).

## Clases

### `Sender.java`

La clase `Sender` se encarga de enviar un mensaje a través de un socket UDP. 

- **Inicialización**: Crea un `DatagramSocket` para la comunicación.
- **Mensaje**: Define un mensaje de tipo `String` ("Hola desde el Sender").
- **Empaquetado**: Convierte el mensaje a bytes y lo encapsula en un `DatagramPacket`.
- **Direccionamiento**: Se conecta a una dirección IP específica (`192.168.131.23`) y al puerto `5000`.
- **Envío**: Utiliza el método `socket.send()` para transmitir el paquete.

### `Reciver.java`

La clase `Reciver` (Receiver) está diseñada para escuchar en un puerto específico y recibir mensajes UDP.

- **Puerto de Escucha**: Crea un `DatagramSocket` que escucha en el puerto `5000`.
- **Buffer**: Prepara un buffer de bytes para almacenar los datos entrantes.
- **Recepción**: Utiliza un `DatagramPacket` para capturar la información que llega al socket y espera activamente con `socket.receive()`.
- **Procesamiento**: Una vez recibido el paquete, extrae los datos, los convierte a `String` y los imprime en la consola, junto con la dirección IP del remitente.

## Interacción

La interacción es una comunicación unidireccional simple:

1. El `Reciver` se inicia primero y se queda esperando (bloqueado) en el puerto `5000` a que llegue un paquete.
2. El `Sender` se ejecuta y envía un paquete UDP con el mensaje "Hola desde el Sender" a la dirección IP `192.168.131.23` en el puerto `5000`.
3. El `Reciver` recibe el paquete, extrae el mensaje y lo muestra en la consola, finalizando su ejecución.



![](/home/i2t/Git/icesi/2025-2/computacion1/Computacion-1-2025-2/01_udp/doc/grafico.png)
