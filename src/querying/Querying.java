package querying;

import data.DataType;
import data.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Querying {
    private BufferedReader stdIn;
    private PrintWriter out;
    private BufferedReader in;
    private Socket s;

    public Querying(BufferedReader stdIn, PrintWriter out, BufferedReader in, Socket s){
        this.stdIn = stdIn;
        this.out = out;
        this.in = in;
        this.s = s;
    }

    public void initConnection() {
        sendCityName();
        String resp;
        DataType data;
        String file;
        try {
            while ((resp = in.readLine()) != null) {
                data = new DataType(resp);
                if(data.getPhase() == DataType.QUERYING_PHASE) {
                    if(data.getType() == DataType.QUERYING_TIMEOUT) {
                        out.close();
                        in.close();
                        stdIn.close();
                        s.close();
                        break; // authentication failed
                    } else if (data.getType() == DataType.QUERYING_REPORT) {
                        file = data.getPayload();
                        //TODO: need to handle recieving file
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendCityName() {
        System.out.println("You have now entered the Query'ing phase.");
        String cityName;
        try {
            if((cityName = stdIn.readLine())!= null) {
                String token = Token.token;
                String payload = token + cityName;
                out.println(new DataType(DataType.QUERYING_PHASE, DataType.QUERYING_REQUEST, payload).getData());
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
