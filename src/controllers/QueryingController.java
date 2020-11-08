package controllers;

import querying.Querying;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class QueryingController {
    private Querying querying;
    private static QueryingController queryingController;

    public QueryingController(BufferedReader stdin, PrintWriter out, BufferedReader in, Socket s, int port){
        querying = new Querying(stdin, out, in, s, port);
    }
    public static QueryingController getQueryingController(BufferedReader stdin, PrintWriter out, BufferedReader in, Socket s, int port){
        if (queryingController == null)
            queryingController = new QueryingController(stdin, out, in, s, port);
        return queryingController;
    }
    public void initQueryingMode() {
        querying.initConnection();
    }
}
