package querying;

import java.io.BufferedReader;
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
}
