import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;

class OriginalTcpEchoServer{
    public static void main(String args[]){
	try{
	    ServerSocketChannel c = ServerSocketChannel.open();
	    c.bind(new InetSocketAddress(9877));
	    while(true){
		SocketChannel sc = c.accept();
		ByteBuffer buffer = ByteBuffer.allocate(4096);
		sc.read(buffer);
		buffer.flip();
		byte[] a = new byte[buffer.remaining()];
		buffer.get(a);
		String message = new String(a);
		System.out.println("Got from client: "+message);
		buffer.rewind();
		sc.write(buffer);
		sc.close();
	    }
	} catch(IOException e){
	    System.out.println("Got an IO Exception");
	}
    }
}
