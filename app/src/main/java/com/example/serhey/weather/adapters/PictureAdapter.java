package com.example.serhey.weather.adapters;

import com.example.serhey.weather.R;

/**
 * Created by Serhey on 27.08.2016.
 */
public class PictureAdapter {

    public int setImage(String string){
        int answerMethod = 0;
        StringBuilder newString = new StringBuilder(string);
        newString.reverse();

        switch (newString.toString()) {
            case "d10": answerMethod = R.drawable.d10;
                break;
            case "d20": answerMethod = R.drawable.d20;
                break;
            case "d31": answerMethod = R.drawable.d31;
                break;
            case "d90": answerMethod = R.drawable.d90;
                break;
            case "n10": answerMethod = R.drawable.n01;
                break;
            case "n11": answerMethod = R.drawable.n11;
                break;
            case "n30": answerMethod = R.drawable.n30;
                break;
            case "n20": answerMethod = R.drawable.n30;
                break;
            case "d30": answerMethod = R.drawable.n30;
                break;
            case "d40": answerMethod = R.drawable.n30;
                break;
            case "n40": answerMethod = R.drawable.n30;
                break;
            case "n90": answerMethod = R.drawable.d90;
                break;
            case "d01": answerMethod = R.drawable.d90;
                break;
            case "n01": answerMethod = R.drawable.d90;
                break;
            case "d11": answerMethod = R.drawable.n11;
                break;
            case "n31": answerMethod = R.drawable.d31;
                break;
                }
       return answerMethod;
    }
}
