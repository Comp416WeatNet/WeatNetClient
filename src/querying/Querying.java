package querying;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Querying {
    BufferedReader stdIn;
    PrintWriter out;
    BufferedReader in;
    public Querying(BufferedReader stdIn, PrintWriter out, BufferedReader in){
        this.stdIn = stdIn;
        this.out = out;
        this.in = in;
    }
}
