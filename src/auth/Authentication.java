package auth;

import controllers.QueryingController;
import data.DataType;
import data.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Authentication {
    BufferedReader stdIn;
    PrintWriter out;
    BufferedReader in;
    Socket s;

    public Authentication(BufferedReader stdIn, PrintWriter out, BufferedReader in, Socket s){
        this.stdIn = stdIn;
        this.out = out;
        this.in = in;
    }
    public void startAuthentication() throws IOException {
            sendUsername(stdIn,out);
            String question;
            String answer;
            DataType resp;
            while ((question = in.readLine()) != null) {
                resp = new DataType(question);
                if (resp.getPhase() == DataType.AUTH_PHASE) {
                    if (resp.getType() == DataType.AUTH_FAIL) {
                        out.close();
                        in.close();
                        stdIn.close();
                        s.close();
                        break; // authentication failed
                    } else if (resp.getType() == DataType.AUTH_SUCCESS) {
                        Token connectionToken = new Token(resp);
                        QueryingController.getQueryingController(stdIn, out, in, s).initQueryingMode();
                        break;
                    } else if (resp.getType() == DataType.AUTH_CHALLENGE) {
                        question = resp.getPayload();
                        answer = getAnswerFromUser(stdIn, question);
                        out.println(new DataType(DataType.AUTH_PHASE, DataType.AUTH_REQUEST, answer).getData());
                        out.flush();
                    }
                }
            }
    }
    private void sendUsername(BufferedReader stdIn, PrintWriter out) {
        System.out.println("Please enter your username:");
        String username;
        try {
            if((username = stdIn.readLine())!= null) {
                DataType data = new DataType(DataType.AUTH_PHASE, DataType.AUTH_REQUEST, username);
                out.println(data.getData());
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String getAnswerFromUser(BufferedReader stdIn, String question) {
        System.out.println(question + ":");
        String userInput;
        try {
            userInput = stdIn.readLine();
            return userInput;
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed";
        }
    }
}
