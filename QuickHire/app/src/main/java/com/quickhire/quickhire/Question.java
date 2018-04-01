package com.quickhire.quickhire;

/** Question ***************************************************************************
 * Created by matth_000 on 2018-03-04.
 * Description: question object to store the questions of the job posting.
 * @param - String questionText: the question being asked
 *          String answer: answer to the question
 *          String qType: type of question being asked
 *          int id: id of the question in the database
 *          int position: position in the list of questions
 *          int numericQuestionType: question type numerically
 ******************************************************************************************/
public abstract class Question {
    protected String questionText;
    private String answer;
    private questionType qType;
    private Integer id;
    protected int position;
    protected int numericQuestionType; //useful incase of changes to enumeration and also database interactions

    public Question(String questionText, questionType qType){
        this.questionText= questionText;
        this.qType = qType;
        this.numericQuestionType=qType.getNumVal();
    }

    public abstract String toJSON(); //for display and transport.

    public abstract int getPicture();

    public abstract String getType();

    public abstract Integer intType();

    public abstract Answer toAnswer();

    public void setAnswer(String value){answer = value; }

    public Integer getId(){return id;}

    public String getAnswer(){return answer;}

    public void setId(Integer value){id = value;}

    public String getQuestionText(){return questionText; }

    public int getPosition() {return position;}

    public void setPosition(int newPosition){position = newPosition;}

    public void setText(String text){ questionText = text; }

}
