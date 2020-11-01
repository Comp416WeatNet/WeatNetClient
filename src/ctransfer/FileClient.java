package ctransfer;

import java.io.*;
import java.net.*;

public class FileClient {
	
	public static void main(String[] argv) throws Exception {
		
		byte []b=new byte[20002];
		Socket s= new Socket("localhost",4333);
		InputStream is= s.getInputStream();
		FileOutputStream fr= new FileOutputStream("G:\\ttttttt.txt");
		is.read(b, 0, b.length);
		fr.write(b, 0, b.length);
	}

}
