package project1.model;

import java.util.List;

public class Binary extends BaseModel {
    public static void parse(String numStr) {
        subNum(numStr);
        parseNumPart();
        parseFractionalPart();
    }

    private static void parseNumPart() {
        number = 0;
        int n = numberPart.length() - 1;
        for (int i = 0; i <= n; i++) {
            number += (int) (Integer.parseInt("" + numberPart.charAt(i)) * Math.pow(2, n - i));
        }
    }

    private static void parseFractionalPart() {
        fraction = 0;
        int n = fractionalPart.length();
        for (int i = 0; i < n; i++) {
            fraction += Integer.parseInt("" + fractionalPart.charAt(i)) * Math.pow(2, -(i + 1));
        }
    }

    public static String convert() {
        convertNumberPart();
        convertFractionalPart();
        return getNum();
    }

    private static void convertNumberPart() {
        int temp = number;
        StringBuilder stringBuilder = new StringBuilder();
        if (number < 0) {
            stringBuilder.append("-");
            temp = -temp;
        }
        do {
            int r = temp % 2;
            stringBuilder.insert(0, r);
            temp /= 2;
        } while (temp != 0);
        numberPart = stringBuilder.toString();
    }

    private static void convertFractionalPart() {
        double temp = fraction;
        StringBuilder stringBuilder = new StringBuilder();
        do {
            int s = (int) (temp * 2);
            temp = temp * 2 - s;
            if (stringBuilder.length() == 19 && temp > 0.5) s += 1;
            stringBuilder.append(s);
        } while (temp != 0.0 && stringBuilder.length() != 20);
        fractionalPart = stringBuilder.toString();
    }
}
