package querying;

import ctransfer.FileClient;
import data.DataType;
import data.Token;
import main.Client;

import java.io.*;
import java.net.Socket;

public class Querying {
    private BufferedReader stdIn;
    private PrintWriter cout;
    private BufferedReader cin;
    private Socket cs;
    private Socket ds;
    private DataInputStream dis;
    private OutputStream dos;

    public Querying(BufferedReader stdIn, PrintWriter cout, BufferedReader cin, Socket cs, int port){
        try {
            this.stdIn = stdIn;
            this.cout = cout;
            this.cin = cin;
            this.cs = cs;
            this.ds = new Socket(Client.DEFAULT_SERVER_HOST, port);

            InputStream in = ds.getInputStream();
            dis = new DataInputStream(in);
            String fileName = dis.readUTF();
            dos = new FileOutputStream(fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initConnection() {

        sendCityName();
        String resp;
        DataType data;
        String file;
        try {
            while ((resp = cin.readLine()) != null) {
                data = new DataType(resp);
                if(data.getPhase() == DataType.QUERYING_PHASE) {
                    if(data.getType() == DataType.QUERYING_TIMEOUT) {
                        cout.close();
                        cin.close();
                        stdIn.close();
                        cs.close();
                        break; // authentication failed
                    } else if (data.getType() == DataType.QUERYING_REPORT) {
                        file = data.getPayload();
                        //TODO: need to handle receiving file
                    }
                }
                getFiles(ds);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getFiles(Socket ds) {
        int bytesRead;
        long size = 0;
        try {
            size = dis.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytesRead = dis.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1)
            {
                dos.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendCityName() {
        System.out.println("You have now entered the querying phase.\nPlease enter a query text:");
        String cityName;
        try {
            if((cityName = stdIn.readLine())!= null) {
                String token = Token.token;
                String payload = token + "@" + cityName;
                cout.println(new DataType(DataType.QUERYING_PHASE, DataType.QUERYING_REQUEST, payload).getData());
                cout.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
