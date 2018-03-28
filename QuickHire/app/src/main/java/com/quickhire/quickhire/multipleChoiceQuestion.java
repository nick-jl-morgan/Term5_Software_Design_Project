package com.quickhire.quickhire;

/**
 * Created by matth_000 on 2018-03-04.
 */

import java.util.Iterator;
import java.util.Vector;

public class multipleChoiceQuestion extends Question {
    private Vector<String> choices = new Vector<String>();

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


    @Override public String toJSON() {
        String string = "{\"type\":" + this.numericQuestionType + ",\"question\":\"" + this.questionText + "\",\"options\":[";

        ;
        //char Alpha = 'A';

        StringBuilder builder = new StringBuilder();
        builder.append(string);
        if(!choices.isEmpty()) {
            Iterator<String> iterator = choices.iterator();
            while (iterator.hasNext()) {
                //builder.append(("{\"" + Alpha + "\":\"" + iterator.next() + "\"}"));
                // Alpha++;
                builder.append("\"" + iterator.next() + "\"");
                if (iterator.hasNext())
                    builder.append(",");
            }
        }

        builder.append("]}");
        return builder.toString();
    }

    @Override
    public int getPicture() {
        return R.drawable.icon_multiple_choice;
    }

    @Override public String getType(){return "Multiple Choice";}

    @Override
    public Integer intType() {
        return 2;
    }

    @Override
    public Answer toAnswer() {
        return null;
    }
}
