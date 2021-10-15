package com.adsys.superquiz;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Quiz {
    private final List<Question> questionList;
    private int currentQuestion;
    public int fails;
    public int corrects;

    public Quiz(Context context){
        questionList = new ArrayList<>();
        questionList.add(new Question(R.drawable.meryl, context.getString(R.string.q_meryl), false));
        questionList.add(new Question(R.drawable.moroc, context.getString(R.string.q_moroc), false));
        questionList.add(new Question(R.drawable.gin, context.getString(R.string.q_gin), true));
        questionList.add(new Question(R.drawable.unic, context.getString(R.string.q_unic), true));
        questionList.add(new Question(R.drawable.frien, context.getString(R.string.q_frien), false));
        questionList.add(new Question(R.drawable.bone, context.getString(R.string.q_bone), false));
        questionList.add(new Question(R.drawable.period, context.getString(R.string.q_perio), true));
        questionList.add(new Question(R.drawable.walt, context.getString(R.string.q_diney), true));
        Collections.shuffle(questionList);
        fails = 0;
        corrects = 0;
        currentQuestion = 0;
    }

    public int length(){return questionList.size();}

    private void setQuestionByIndex(int index){
        if (index >= 0 && index < questionList.size()){
            currentQuestion  = index;
        }
    }

    public void setPreviousQuestion(){setQuestionByIndex(currentQuestion-1);}

    public void setNextQuestion(){setQuestionByIndex(currentQuestion+1);}

    public void answerQuestion(boolean answer){
        boolean isCorrect = questionList.get(currentQuestion).check(answer);
        if (isCorrect){
            corrects++;
        }else{
            fails++;
        }
    }

    public Question getCurrentQuestion(){
        return questionList.get(currentQuestion);
    }

    public int getQuestionNumber(){return currentQuestion;}

}
