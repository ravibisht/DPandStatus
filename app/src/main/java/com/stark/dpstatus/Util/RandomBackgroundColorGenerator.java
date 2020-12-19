package com.stark.dpstatus.Util;

import com.stark.dpstatus.R;

import java.util.Random;

public class RandomBackgroundColorGenerator {

    final static int[] RandomColorsArray = {
            R.color.black,
            R.color.pink,
            R.color.sky_color,
            R.color.green
    };
    private static Random random = new Random();

    public static int getRandom() {
        return random.nextInt(RandomColorsArray.length);
    }

    public static int getRandomColor() {
        return RandomColorsArray[getRandom()];
    }
}
