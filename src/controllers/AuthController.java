package controllers;

import auth.Authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthController {
    private Authentication auth;
    private static AuthController authController;

    public AuthController(BufferedReader stdin, PrintWriter out, BufferedReader in) {
        auth = new Authentication(stdin, out, in);
    }

    public static AuthController getAuthController(BufferedReader stdin, PrintWriter out, BufferedReader in){
        if (authController == null)
            authController = new AuthController(stdin, out, in);
        return authController;
    }

    public void startAuthentication() throws IOException {
        auth.startAuthentication();
    }
}
