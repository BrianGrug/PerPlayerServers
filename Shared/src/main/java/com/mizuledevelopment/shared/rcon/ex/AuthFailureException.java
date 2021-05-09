package com.mizuledevelopment.shared.rcon.ex;

public class AuthFailureException extends RconClientException {

    public AuthFailureException() {
        super("Authentication failure");
    }

}
