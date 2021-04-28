package com.digitax.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class SessionResponse {

    private String accessToken;

    private long expirationMiliSecond;

    private Date utcExpirationTime;

    private Date ptExpirationTime;


    public SessionResponse(String accessToken, long jwtExpirationMs) {
        this.accessToken = accessToken;
        this.expirationMiliSecond = jwtExpirationMs;
        this.utcExpirationTime = new Date((new Date()).getTime() + jwtExpirationMs);
        this.ptExpirationTime = new Date((new Date()).getTime() + jwtExpirationMs);
    }


}
