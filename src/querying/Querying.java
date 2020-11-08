package querying;

import data.DataType;
import data.Token;
import main.Client;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Querying {
    private BufferedReader stdIn;
    private PrintWriter cout;
    private BufferedReader cin;
    private Socket cs;
    private Socket ds;
    private DataInputStream dis;
    private OutputStream dos;

    public Querying(BufferedReader stdIn, PrintWriter cout, BufferedReader cin, Socket cs, int port) {
        try {
            this.stdIn = stdIn;
            this.cout = cout;
            this.cin = cin;
            this.cs = cs;
            this.ds = new Socket(Client.DEFAULT_SERVER_HOST, port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initConnection() {

        sendCityName();
        String resp;
        DataType data;
        String fileHash;
        try {
            while ((resp = cin.readLine()) != null) {
                data = new DataType(resp);
                if (data.getPhase() == DataType.QUERYING_PHASE) {
                    if (data.getType() == DataType.QUERYING_TIMEOUT) {
                        cout.close();
                        cin.close();
                        stdIn.close();
                        cs.close();
                        break;
                    } else if (data.getType() == DataType.QUERYING_REPORT) {
                        fileHash = data.getPayload();
                        String filename = getFiles(ds);
                        File file = new File(filename);
                        int receivedFileHash = hashFile(file);
                        if (fileHash.equals(String.valueOf(receivedFileHash))) {
                            System.out.println("File hash and received hash are the same.");
                            printFile(file);
                            DataType dt = new DataType(DataType.QUERYING_PHASE, DataType.QUERYING_SUCCESS, "File received." + "@" + Token.token);
                            cout.println(dt.getData());
                        } else {
                            System.out.println("File hash and received hash are different.");
                            DataType dt = new DataType(DataType.QUERYING_PHASE, DataType.QUERYING_FAIL, "File failed to receive." + "@" + Token.token);
                            cout.println(dt.getData());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printFile(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
            for (String line; (line = br.readLine()) != null; ) {
                System.out.println(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(file.toString());
    }

    private int hashFile(File myFile) throws IOException {
        byte[] mybytearray = new byte[(int) myFile.length()];

        FileInputStream fis = new FileInputStream(myFile);
        BufferedInputStream bis = new BufferedInputStream(fis);

        DataInputStream dis = new DataInputStream(bis);
        dis.readFully(mybytearray, 0, mybytearray.length);
        int hash = Arrays.hashCode(mybytearray);
        return hash;
    }

    private String getFiles(Socket ds) {
        int bytesRead;
        long size = 0;
        try {
            InputStream in = null;
            in = ds.getInputStream();
            dis = new DataInputStream(in);
            String fileName = dis.readUTF();
            dos = new FileOutputStream(fileName);
            size = dis.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytesRead = dis.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                dos.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void sendCityName() {
        System.out.println("You have now entered the querying phase.\nPlease enter a city name and a number:");
        String cityName;
        try {
            if ((cityName = stdIn.readLine()) != null) {
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
