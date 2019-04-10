package com.cecula.jsonwrapper;

/**
 *
 * @author Segun Ogundipe <segun.ogundipe at cecula.com>
 */
public class ResponseWrapper {
    
    private String message;
    private String token;
    
    public ResponseWrapper(){}
    
    public ResponseWrapper(String message, String token){
        this.message = message;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
