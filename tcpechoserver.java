import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.Scanner;

class tcpechoserver {
    public static void main(String args[]) {
        try {
            //user input
            Scanner scan = new Scanner(System.in);
            int portInfo = Integer.parseInt(portSelection(scan));

            ServerSocketChannel c = ServerSocketChannel.open();

            c.bind(new InetSocketAddress(portInfo));
            while (true) {
                SocketChannel sc = c.accept();
                ByteBuffer buffer = ByteBuffer.allocate(6000);
                sc.read(buffer);
                buffer.flip();
                byte[] a = new byte[buffer.remaining()];
                buffer.get(a);
                String message = new String(a);
                System.out.println("Got from client: " + message);
                buffer.rewind();
                sc.write(buffer);
                if(filePresent(message)){
                    System.out.println("here 1");
                    File myFile = new File("./"+message);

                    buffer = ByteBuffer.wrap(message.getBytes());
                    sc.write(buffer);
                    //send
                    FileChannel sbc=FileChannel.open(myFile.toPath());
                    ByteBuffer buf2=ByteBuffer.allocate(10000000);

                    int bytesread=sbc.read(buf2);

                    while(bytesread != -1) {
                        buf2.flip();
                        sc.write(buf2);
                        buf2.compact();
                        bytesread = sbc.read(buf2);
                        System.out.println("looooppp");
                    }
                }

                sc.close();
            }
        } catch (IOException e) {
            System.out.println("Got an IO Exception: "+ e);
        }
    }

    public static String portSelection(Scanner scan) {
        System.out.println("Enter a port to connect to:");
        String info = scan.next();
        return info;
    }

    public static boolean filePresent(String fileName){
        System.out.println("here 1");

        File myFile = new File("./"+fileName);
        if(myFile.exists() && !myFile.isDirectory()) {
           return true;
        }
        return false;
    }
}
