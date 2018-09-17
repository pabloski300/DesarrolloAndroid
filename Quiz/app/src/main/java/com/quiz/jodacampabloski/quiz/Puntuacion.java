package com.quiz.jodacampabloski.quiz;

import android.support.annotation.NonNull;

public class Puntuacion implements Comparable {
    String name;
    int score;

    public Puntuacion(String name,int score){
        this.score = score;
        this.name = name;
    }



    @Override
    public int compareTo(@NonNull Object o) {
        int s = ((Puntuacion)o).score;
        return (s-score);
    }
}
