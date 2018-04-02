package com.quickhire.quickhire;

/**
 * Created by matth_000 on 2018-03-03.
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class User implements java.io.Serializable{
    private int userID=9999;
    private String username;
    private credentials creds = new credentials();

    private static User theUser = null;

    private User(){
    }

    //sinlgeton constuctor
    private User(String username){
        this.username = username;
    }

    public static User getUser(){
        if(theUser == null){
            theUser = new User();
        }
        return theUser;         //may return null.
    }

    public static void createUser(String username){
        theUser = new User(username);
    }

    public void setCreds(credentials creds){
        this.creds=creds;
    }

    //returns the credentials (i.e. Java Web Token) associated with this user's most recent login.
    //Required for all HTTP requests.
    public String getCredentials(){
        return this.creds.getAccessToken();
    }

    public void saveUser(){
        try {
            FileOutputStream stream = new FileOutputStream("user.data");
            ObjectOutputStream objectStream = new ObjectOutputStream(stream);
            objectStream.writeObject(theUser);
        }
        catch(java.io.FileNotFoundException e){
            e.printStackTrace();
        }
        catch(java.io.IOException e){
            e.printStackTrace();
        }
    }

    //Never used. Early iteration plan for saving user. Was never fully implemented.
//    public boolean recoverUser(){
//        boolean recovered;
//        if(theUser == null) {
//            try {
//                FileInputStream stream = new FileInputStream("user.data");
//                ObjectInputStream objectStream = new ObjectInputStream(stream);
//                theUser  = (User) objectStream.readObject();
//                recovered = true;
//            } catch (java.io.FileNotFoundException e) {
//                e.printStackTrace();
//                recovered=false;
//            } catch (java.io.IOException e) {
//                e.printStackTrace();
//                recovered=false;
//            } catch(java.lang.ClassNotFoundException e){
//                e.printStackTrace();
//                recovered=false;
//            }
//
//        }
//        else{
//            recovered =false;
//        }
//        return recovered;
//    }


}
