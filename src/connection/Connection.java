package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {
    public void startConnection(String serverHost,int serverPort) throws IOException {
        try (
                Socket echoSocket = new Socket ( serverHost, serverPort );
                PrintWriter out =
                        new PrintWriter ( echoSocket.getOutputStream (), true );
                BufferedReader in =
                        new BufferedReader (
                                new InputStreamReader( echoSocket.getInputStream () ) );
                BufferedReader stdIn =
                        new BufferedReader (
                                new InputStreamReader ( System.in ) )
        ) {
            sendUsername(stdIn,out);
            String question;
            while ((question = in.readLine()) != null) {
                getAnswerFromUser(stdIn, question);
            }
        }
    }

    private void sendUsername(BufferedReader stdIn, PrintWriter out) {
        System.out.println("Please enter your username:");
        String username;
        try {
            if((username = stdIn.readLine())!= null) {
                out.println(username);
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
