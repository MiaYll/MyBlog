package me.wangcai.myblog.utils;

import java.util.Random;

public class ColorUtil {

    private static final String[] COLOR = new String[]{
        "f4f0e6","d9d9f3","ceefe4","9dd3a8","bab8bd","a1d4e2","f0f0f0","CCCCCC"
    };

    public static String getRandomColor(){
        return COLOR[new Random().nextInt(COLOR.length)];
    }
}
