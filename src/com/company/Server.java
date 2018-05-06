package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try {
//            final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            final ServerSocket serverSocket = new ServerSocket(5555);
            while (true) {
                final Socket accept = serverSocket.accept();
                final Thread thread = new Connection(accept);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Connection extends Thread {

    private Socket socket;

    public Connection(Socket accept) {
        socket = accept;
    }

    @Override
    public void run() {
        final InputStream inputStream;
        try {
            inputStream = socket.getInputStream();
            final OutputStream outputStream = socket.getOutputStream();
            final DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            final BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(inputStream));

            String s = null;
            int state = 1;
            while ((s = bufferedReader.readLine()) != null) {
                System.out.println(new String(s.getBytes("UTF-8")));
                if (state == 1 && s.equalsIgnoreCase("HI")) {
                    dataOutputStream.writeBytes("HI\r");
                    dataOutputStream.writeBytes("HI\r");
                    state++;
                } else if (state == 2 && s.equalsIgnoreCase("SEND")) {
                    dataOutputStream.writeBytes("OK\r");
                    state++;
                } else if (state == 3 && s.startsWith("SIZE:")) {
                    String[] split = s.split(":");
                    String sendPart = split[0];
                    String sizePart = split[1];
                    try {
                        Integer integer = Integer.valueOf(sizePart);
                    } catch (NumberFormatException e) {
                        dataOutputStream.writeBytes("NO\r");
                    }
                    dataOutputStream.writeBytes("OK\r");
                    state++;
                } else if (state == 4 && s.equalsIgnoreCase("")) {
                    System.out.println("koniec połączenia");
                }
            }
            bufferedReader.close();
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
