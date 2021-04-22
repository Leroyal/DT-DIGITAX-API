package com.digitax.payload.response;

import org.json.simple.JSONObject;

import java.util.List;

public class JwtResponse {
    private JSONObject user;
    private Object session;

    /**
     * Constructor
     * @param sessionObj Object containing session information.
     * @param userDetailsObj Object containing user detail information.
     */
    public JwtResponse(Object sessionObj, JSONObject userDetailsObj) {
        this.user = userDetailsObj;
        this.session = sessionObj;
    }

    /**
     * Getter to retrieve user object.
     * @return User object.
     */
    public JSONObject getUser() {
        return user;
    }

    /**
     * Setter for creating a user object.
     * @param user User object.
     */
    public void setUser(List<String> user) {
        this.user = (JSONObject) user;
    }

    /**
     * Getter to retrieve session object.
     * @return Session object.
     */
    public Object getSession() {
        return session;
    }

    /**
     * Setter for create a session object.
     * @param session Session object.
     */
    public void setSession(Object session) {
        this.session = session;
    }
}