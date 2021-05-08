package com.mizuledevelopment.master.rcon.ex;

public class AuthFailureException extends RconClientException {

    public AuthFailureException() {
        super("Authentication failure");
    }

}
