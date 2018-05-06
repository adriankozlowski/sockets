package client;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Adrian.Kozlowski on 2017-03-14.
 */
public class Client {


    public static void main(String... args) {

        try {//TO JEST KLIENT
            String toSend = "Cześć uczę się JAVY";
            Socket socket = new Socket("localhost", 5554);

            final InputStream inputStream = socket.getInputStream();
            final OutputStream outputStream = socket.getOutputStream();

            final BufferedReader readFromSocket = new BufferedReader(
                    new InputStreamReader(inputStream)
            );
            final DataOutputStream writeToSocket =
                    new DataOutputStream(outputStream);

            writeToSocket.writeBytes("CZESC\n");

            readFromSocket.close();
            writeToSocket.close();



//
//            writeToSocket.writeBytes("HI\r");
//            int state = 1;
//            String s = null;
//            while ((s = readFromSocket.readLine()) != null) {
//
//                System.out.println(s);
//                if (state == 1 && s.equalsIgnoreCase("HI")) {
//                    writeToSocket.writeBytes("SEND\r");
//                    state++;
//                } else if (state == 2 && s.equalsIgnoreCase("OK")) {
//                    writeToSocket.writeBytes("SIZE:" + toSend.getBytes().length + "\r");
//                    state++;
//                } else if (state == 3 && s.equalsIgnoreCase("OK")) {
//                    writeToSocket.writeUTF(toSend + "\r\r");
//                    state++;
//                } else if (state == 5 && s.equalsIgnoreCase("OK")) {
//                    readFromSocket.close();
//                    writeToSocket.close();
//                }
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
