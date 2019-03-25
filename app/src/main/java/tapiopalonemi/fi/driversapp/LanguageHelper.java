package tapiopalonemi.fi.driversapp;

import android.content.Context;

import java.util.Locale;

public class LanguageHelper {

    static String result = "";

    public static String convertNumber(int number, Context context) {
//        String result = "";
        result = "";
        Locale primaryLocale = context.getResources().getConfiguration().getLocales().get(0);
        String locale = primaryLocale.getDisplayName();
        if (locale.contains("नेपाली")) {
            convert(number);
        }
        return result;

    }

    private static int convert(int number) {
        int modulo = number % 10;
        switch (modulo) {
            case 0:
                result = "०" + result;
                break;
            case 1:
                result = "१" + result;
                break;
            case 2:
                result = "२" + result;
                break;
            case 3:
                result = "३" + result;
                break;
            case 4:
                result = "४" + result;
                break;
            case 5:
                result = "५" + result;
                break;
            case 6:
                result = "६" + result;
                break;
            case 7:
                result = "७" + result;
                break;
            case 8:
                result = "८" + result;
                break;
            case 9:
                result = "९" + result;
                break;
        }
        number = number / 10;
        if (number > 0) {
            return convert(number);
        } else {
            return number;
        }
    }
}
