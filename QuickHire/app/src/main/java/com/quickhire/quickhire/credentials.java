package com.quickhire.quickhire;

/** Application ***************************************************************************
 * Created by matth_000 on 2018-03-06.
 * Description: credentials of the user for the tokens.
 * @param - String access_token: token that allows the user to access the application.
 *          String refresh_token: token used to refresh authentication.
 ******************************************************************************************/
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
