package controllers;

import querying.Querying;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class QueryingController {
    private Querying querying;
    private static QueryingController queryingController;

    public QueryingController(BufferedReader stdin, PrintWriter out, BufferedReader in){
        querying = new Querying(stdin, out, in);
    }

    public static QueryingController getQueryingController(BufferedReader stdin, PrintWriter out, BufferedReader in){
        if (queryingController == null)
            queryingController = new QueryingController(stdin, out, in);
        return queryingController;
    }
}
