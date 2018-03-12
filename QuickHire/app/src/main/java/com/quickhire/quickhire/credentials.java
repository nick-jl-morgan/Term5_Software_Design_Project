package com.quickhire.quickhire;

/**
 * Created by matth_000 on 2018-03-06.
 */

public class credentials {
    private String access_token, refresh_token;

    public credentials(){
        this.access_token="void";
        this.refresh_token="void";
    }

    public credentials(String A, String R){
        this.access_token=A;
        this.refresh_token=R;
    }

    String getAccessToken(){
        return this.access_token;
    }

    String getRefreshToken(){
        return this.refresh_token;
    }

}
