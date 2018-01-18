import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.Scanner;

class tcpechoclient {
    public static void main(String args[]) {
        try {
            //user input
            Scanner scan = new Scanner(System.in);
            // user input for port and address
            String ipAddressInfo = ipAddress(scan);
            int portInfo = Integer.parseInt(portSelection(scan));

            SocketChannel sc = SocketChannel.open();
            sc.connect(new InetSocketAddress(ipAddressInfo, portInfo));
            Console cons = System.console();
            //user filename input
            String fileName = getFileName(cons);
            ByteBuffer buf = ByteBuffer.wrap(fileName.getBytes());
            sc.write(buf);
            ByteBuffer buf2 = ByteBuffer.allocate(5000);
            sc.read(buf2);
            buf2.flip();
            byte[] a = new byte[buf2.remaining()];
            buf2.get(a);
            String message = new String(a);
            //file found or not
            if(!message.equals("File not found: " + fileName)){
                System.out.println("File "+ fileName + " was found by the server and was saved to a new file.");
                //write into file
                PrintWriter writeToFile = new PrintWriter("fromServer"+fileName, "UTF-8");
                writeToFile.println(message);
                writeToFile.close();
            }
            else{
                System.out.println(message);
            }
            sc.close();
        } catch (IOException e) {
            System.out.println("Got an Exception: " + e);
        }
    }

    public static String portSelection(Scanner scan) {
        System.out.println("Enter a port to connect to:");
        String info = scan.next();
        return info;
    }

    public static String ipAddress(Scanner scan) {
        System.out.println("Enter an IP address to connect to:");
        String info = scan.next();
        return info;
    }

    public static String getFileName(Console cons) {
        String info = cons.readLine("Enter a file name: ");
        return info;
    }

}
