package pl.sda.com;

import com.sun.deploy.util.ArrayUtil;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class UDP {
    public static void main(String[] args) {
        try {
        final InetAddress ADDRESS = InetAddress.getByName("192.168.2.255");
            DatagramSocket datagramSocket = new DatagramSocket(6666);
            //192.168.2.255 - moj adres rozgloszeniowy
            UdpConnectionThread udpConnectionThread = new UdpConnectionThread(datagramSocket);
            udpConnectionThread.start();

            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length,
                    ADDRESS,6666);

            Scanner scanner = new Scanner(System.in);
            String s=null;
            while((s = scanner.nextLine())!=null){
                //s nie moze być większe niz buf.length
               byte[] name = Arrays.copyOf("Adrian".getBytes(),30);
               byte[] data = Arrays.copyOf(s.getBytes(),1024-30);
                System.arraycopy(name,0,buf,0,30);
                System.arraycopy(data,0,buf,31,993);
                packet.setData(buf);
                datagramSocket.send(packet);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class UdpConnectionThread extends Thread{

    private DatagramSocket socket;

    public UdpConnectionThread(DatagramSocket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                this.socket.receive(packet);
                byte[] data = packet.getData();
                byte[] imie = Arrays.copyOfRange(data, 0, 30);
                String stringImie = new String(imie);
                String dane = new String(Arrays.copyOfRange(data, 31, 1023));
                System.out.println("<"+stringImie+">:"+dane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}