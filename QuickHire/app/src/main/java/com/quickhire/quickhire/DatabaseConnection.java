package com.quickhire.quickhire;

import com.quickhire.quickhire.User;
import com.quickhire.quickhire.jobPosting;

/**
 * Created by onick on 2018-03-04.
 */

public interface DatabaseConnection {

    public String GetUser(String username, String password);

}
