package ry.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import static java.lang.Thread.sleep;

/**
 *
 * @author ry
 */
public class MulticastSender {

    private InetAddress group;
    private MulticastSocket ms;

    public MulticastSender() {
        try {
            group = InetAddress.getByName("224.0.1.20");
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        try {
            ms = new MulticastSocket(4242);
            ms.joinGroup(group);
            byte[] data = "Hallo I'm a multicaster :)".getBytes();
            for (int i = 0; i < 10000; i++) {
                DatagramPacket dp = new DatagramPacket(data, data.length, group, 4242);
                ms.send(dp);
                sleep(1000);
                System.out.println("gesendet");
            }
            ms.leaveGroup(group);
            ms.close();
        } catch (IOException | InterruptedException ex) {
            try {
                ms.leaveGroup(group);
                ms.close();
            } catch (IOException ex1) {
                
            }
        }
    }

    public static void main(String[] args) {
        new MulticastSender();
    }
}
