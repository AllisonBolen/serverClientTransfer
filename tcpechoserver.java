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
                // Sends the bytes to buffer
                sc.read(buffer);
                // You need to flip in order to read the info.
                buffer.flip();
                byte[] a = new byte[buffer.remaining()];
                // Sets the bytes
                buffer.get(a);
                String message = new String(a);
                System.out.println("Got from client: "+message);
                // new buffer to hold the data from the file
                String data = filePresent(message);

                ByteBuffer buffer2 = ByteBuffer.allocate(100000);

                buffer2 = ByteBuffer.wrap(data.getBytes());

                buffer.rewind();
                sc.write(buffer2);

                System.out.println("Got from server: " + message);
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
        File myFile = new File(fileName);

        if (myFile.exists() && !myFile.isDirectory()) {

            String out = "";
            try {
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
            return out;
        }

        return "File not found: " + fileName;
    }
}
