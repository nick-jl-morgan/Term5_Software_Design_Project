package com.quickhire.quickhire;

/** questionType **************************************
 * Created by matth_000 on 2018-03-04.
 * Description: enumeration class for the question type.
 ******************************************************/
public enum questionType {
    VIDEO(0),
    ESSAY(1),
    M_CHOICE(2);

    private int numVal;

    questionType(int numVal){
        this.numVal = numVal;
    }

    public int getNumVal(){
        return numVal;
    }
}
