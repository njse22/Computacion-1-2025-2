package ui;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.LineUnavailableException;

public class Main {
    public static void main(String[] args) {
        try {

            // conexión con el servidor
            Socket socket = new Socket("127.0.0.1", 5000);

            // Definir el formato de audio
            AudioFormat format = new AudioFormat(44100, 16, 1, true, true);

            // Optener información del microfono -> conexión con el hardware especifico
            DataLine.Info infoMicrophone = new DataLine.Info(TargetDataLine.class, format);

            // Conexión con el microfono
            TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(infoMicrophone);

            // abrir el microfono
            microphone.open(format);
            // el microfono empieza a escuchar
            microphone.start();

            // Conexión del audio con el socket
            OutputStream os = socket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);

            // Codificar el audio en bytes
            byte[] buffer = new byte[10240];
            while (true){
                int byteRead = microphone.read(buffer, 0, buffer.length);

                // Enviarlo por el socket
                bos.write(buffer, 0, byteRead);
                bos.flush();
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }    
    }
	
}
