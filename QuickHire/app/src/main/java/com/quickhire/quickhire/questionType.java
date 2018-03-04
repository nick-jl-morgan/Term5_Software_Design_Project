package com.quickhire.quickhire;

/**
 * Created by matth_000 on 2018-03-04.
 */

public enum questionType {
    VIDEO(0),
    M_CHOICE(1),
    ESSAY(2);

    private int numVal;

    questionType(int numVal){
        this.numVal = numVal;
    }

    public int getNumVal(){
        return numVal;
    }
}
