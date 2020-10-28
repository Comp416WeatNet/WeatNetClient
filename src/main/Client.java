package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import connection.Connection;

public class Client {

    public static void main(String[] args) throws IOException {
        Connection connection = new Connection();
	// write your code here
        String serverHost = "localhost";
        int serverPort = 9999;
        connection.startConnection(serverHost,serverPort);

    }


}
