package controllers;

import auth.Authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class AuthController {
    private final Authentication auth;
    private static AuthController authController;

    public AuthController(BufferedReader stdin, PrintWriter out, BufferedReader in, Socket s) {
        auth = new Authentication(stdin, out, in, s);
    }

    public static AuthController getAuthController(BufferedReader stdin, PrintWriter out, BufferedReader in, Socket s) {
        if (authController == null)
            authController = new AuthController(stdin, out, in, s);
        return authController;
    }

    public void startAuthentication() throws IOException {
        auth.startAuthentication();
    }
}
