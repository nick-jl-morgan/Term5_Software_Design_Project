package com.quickhire.quickhire;

/**
 * Created by matth_000 on 2018-03-04.
 */

import java.util.Iterator;
import java.util.Vector;

public class multipleChoiceQuestion extends Question {
    private Vector<String> choices;

    public multipleChoiceQuestion(String question){
        super(question, questionType.M_CHOICE);

    }

    public void addChoice(String choice){
        choices.add(choice);
    }

    public boolean removeChoice(char whichChoice){
        whichChoice = Character.toUpperCase(whichChoice);
        int position = ((int) whichChoice) - 65;

        if(0 <= position && position <= 25 & choices.size()>=position ) {
            choices.remove(position);
            return true;
        }
        else
            return false;
    }


    @Override public String toJSON(){
        String string =  "{\"type\":"+ this.numericQuestionType+",\"question\":\""+this.questionText+"\",\"options\":[";

        Iterator iterator = choices.iterator();
        char Alpha ='A';

        StringBuilder builder = new StringBuilder();
        builder.append(string);
        while(iterator.hasNext()){
            builder.append(("{\""+Alpha+"\":'"+iterator.next()+"\"}"));
            Alpha++;
        }
        builder.append("]}");
        return builder.toString();
    }

}
