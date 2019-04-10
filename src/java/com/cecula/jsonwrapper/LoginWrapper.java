package com.cecula.jsonwrapper;

/**
 *
 * @author Segun Ogundipe <segun.ogundipe at cecula.com>
 */
public class LoginWrapper {
    
    private String email;
    private String password;
    
    public LoginWrapper(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
