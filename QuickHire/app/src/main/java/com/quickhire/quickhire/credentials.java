package com.quickhire.quickhire;

/**
 * Created by matth_000 on 2018-03-06.
 */

public class credentials {
    private String accessToken, refreshToken;

    public credentials(String A, String R){
        this.accessToken=A;
        this.refreshToken=R;
    }

    String getAccessToken(){
        return this.accessToken;
    }

    String getRefreshToken(){
        return this.refreshToken;
    }

}
