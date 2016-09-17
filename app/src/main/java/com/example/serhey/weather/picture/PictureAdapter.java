package com.example.serhey.weather.picture;

import com.example.serhey.weather.R;

/**
 * Created by Serhey on 27.08.2016.
 */
public class PictureAdapter {



    public int setImage(String string){
        int ansverMethod = 0;
        StringBuilder newString = new StringBuilder(string);
        newString.reverse();

        switch (newString.toString()) {
            case "d10": ansverMethod = R.drawable.d10;
                break;
            case "d20": ansverMethod = R.drawable.d20;
                break;
            case "d31": ansverMethod = R.drawable.d31;
                break;
            case "d90": ansverMethod = R.drawable.d90;
                break;
            case "n10": ansverMethod = R.drawable.n01;
                break;
            case "n11": ansverMethod = R.drawable.n11;
                break;
            case "n30": ansverMethod = R.drawable.n30;
                break;
            case "n20": ansverMethod = R.drawable.n30;
                break;
            case "d30": ansverMethod = R.drawable.n30;
                break;
            case "d40": ansverMethod = R.drawable.n30;
                break;
            case "n40": ansverMethod = R.drawable.n30;
                break;
            case "n90": ansverMethod = R.drawable.d90;
                break;
            case "d01": ansverMethod = R.drawable.d90;
                break;
            case "n01": ansverMethod = R.drawable.d90;
                break;
            case "d11": ansverMethod = R.drawable.n11;
                break;
            case "n31": ansverMethod = R.drawable.d31;
                break;
                }
       return ansverMethod;
    }
    /*public static final int d10=0x7f02004b;
    public static final int d20=0x7f02004c;
    public static final int d31=0x7f02004d;
    public static final int d90=0x7f02004e;
    public static final int n01=0x7f02004f;
    public static final int n11=0x7f020050;
    public static final int n30=0x7f020051;*/

}
