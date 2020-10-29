package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import controllers.AuthController;

public class Client {
    public static final String DEFAULT_SERVER_HOST = "localhost";
    public static final int DEFAULT_SERVER_PORT = 9999;

    public Client(String serverHost, int serverPort){
        AuthController authController;
        try (
                Socket s = new Socket ( serverHost, serverPort );
                PrintWriter out =
                        new PrintWriter ( s.getOutputStream (), true );
                BufferedReader in =
                        new BufferedReader (
                                new InputStreamReader( s.getInputStream () ) );
                BufferedReader stdIn =
                        new BufferedReader (
                                new InputStreamReader ( System.in ) )
        ) {
            authController = AuthController.getAuthController(stdIn,out,in,s);
            authController.startAuthentication();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
