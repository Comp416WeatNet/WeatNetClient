package querying;

import data.DataType;
import data.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Querying {
    private BufferedReader stdIn;
    private PrintWriter out;
    private BufferedReader in;

    public Querying(BufferedReader stdIn, PrintWriter out, BufferedReader in){
        this.stdIn = stdIn;
        this.out = out;
        this.in = in;
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
                    if (data.getType() == DataType.QUERYING_REPORT) {
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
        System.out.println("Please enter your username:");
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
