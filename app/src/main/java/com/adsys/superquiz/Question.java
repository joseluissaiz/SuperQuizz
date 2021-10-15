package com.adsys.superquiz;

public class Question {

    public int image;
    public final String question;
    public final boolean correctAnswer;
    public boolean isAnswered = false;
    public boolean myAnswer;

    public Question(int image, String question, boolean correctAnswer){
        if (image == -1){
            this.image = R.drawable.not_available;
        }else{
            this.image = image;
        }
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    public boolean check(boolean myAnswer){
        isAnswered = true;
        this.myAnswer = myAnswer;
        return isCorrect();
    }

    public boolean isCorrect(){
        return (Boolean.valueOf(myAnswer).equals(correctAnswer));
    }

}
