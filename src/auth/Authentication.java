package auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Authentication {
    BufferedReader stdIn;
    PrintWriter out;
    BufferedReader in;

    public Authentication(BufferedReader stdIn, PrintWriter out, BufferedReader in){
        this.stdIn = stdIn;
        this.out = out;
        this.in = in;
    }
    public void startAuthentication() throws IOException {
            sendUsername(stdIn,out);
            String question;
            while ((question = in.readLine()) != null) {
                String answer = getAnswerFromUser(stdIn, question);
                out.println(answer);
                out.flush();
            }
    }
    private void sendUsername(BufferedReader stdIn, PrintWriter out) {
        System.out.println("Please enter your username:");
        String username;
        try {
            if((username = stdIn.readLine())!= null) {
                out.println(username);
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
