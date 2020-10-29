package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import auth.Authentication;
import controllers.AuthController;

public class Client {

    public static void main(String[] args) throws IOException {
        Authentication authentication;
        String serverHost = "localhost";
        int serverPort = 9999;
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
            authentication = new Authentication(stdIn, out, in);
            authentication.startAuthentication();
        }
    }


}
