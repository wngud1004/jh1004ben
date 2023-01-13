package Vocaburay;

import java.util.ArrayList;
/*
어휘집에 단어 하나를 정의 해주는 데어터 클래스
 */

public class Word {
    private String eng;
    private ArrayList<String> korWords = new ArrayList<>();

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public ArrayList<String> getKorWords() {
        return korWords;
    }

    public void setKorWords(ArrayList<String> korWords) {
        this.korWords = korWords;
    }

    public Word(String eng, ArrayList<String> korWords) {
        this.eng = eng;
        this.korWords = korWords;
    }
}
