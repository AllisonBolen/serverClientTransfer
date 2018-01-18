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
            while(true){
                SocketChannel sc = c.accept();
                ByteBuffer buffer = ByteBuffer.allocate(4096);
                sc.read(buffer);
                buffer.flip();
                byte[] a = new byte[buffer.remaining()];
                buffer.get(a);
                String message = new String(a);
                System.out.println("Got from client: "+message);
                sc.write(buffer);
                // new buffer to hold the data from the file
                String data = filePresent(message);
                ByteBuffer buffer2 = ByteBuffer.allocate(100000);
                sc.read(buffer2);
                buffer2.flip();
                buffer2 = ByteBuffer.wrap(data.getBytes());
                // write teh data to the
                // sc.write(buffer2);
                byte[] b = new byte[buffer2.remaining()];
                buffer2.get(b);
                String messages = new String(a);
                System.out.println("Got from server: " + messages);
                buffer.rewind();

                sc.close();
            }
        } catch(IOException e){
            System.out.println("Got an IO Exception");
        }
    }

    public static String portSelection(Scanner scan) {
        System.out.println("Enter a port to connect to:");
        String info = scan.next();
        return info;
    }

    public static String filePresent(String fileName) {
        System.out.println("here 1");

        File myFile = new File(fileName);
        if (myFile.exists() && !myFile.isDirectory()) {
            System.out.println("here 2");

            String out = "";
            try {
                System.out.println("here 3");
                Scanner fromFile = new Scanner(new FileReader(fileName));
                StringBuilder builder = new StringBuilder();
                while (fromFile.hasNextLine()) {
                    builder.append(fromFile.nextLine());
                }
                fromFile.close();
                out = builder.toString();
            } catch (IOException e) {
                System.out.println("Got an IO Exception: " + e);
            }
            System.out.println("here 4 " + out);
            return out;
        }
        System.out.println("here 5");

        return "File not found: " + fileName;
    }
}
