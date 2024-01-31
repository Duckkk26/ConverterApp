package project1.model;

import java.util.List;

public class Hexadecimal extends BaseModel {
    private static final List<Character> list = List.of('0', '1', '2', '3', '4', '5', '6', '7',
                                                        '8', '9', 'A', 'B', 'C', 'D', 'E', 'F');

    public static void parse(String numStr) {
        subNum(numStr);
        parseNumPart();
        parseFractionalPart();
    }

    private static void parseNumPart() {
        number = 0;
        int n = numberPart.length() - 1;
        for (int i = 0; i <= n; i++) {
            number += (int) (list.indexOf(numberPart.charAt(i)) * Math.pow(16, n - i));
        }
    }

    private static void parseFractionalPart() {
        fraction = 0;
        int n = fractionalPart.length();
        for (int i = 0; i < n; i++) {
            fraction += list.indexOf(fractionalPart.charAt(i)) * Math.pow(16, -(i + 1));
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
            int r = temp % 16;
            stringBuilder.insert(1, list.get(r));
            temp /= 16;
        } while (temp != 0);
        numberPart = stringBuilder.toString();
    }

    private static void convertFractionalPart() {
        double temp = fraction;
        StringBuilder stringBuilder = new StringBuilder();
        do {
            int s = (int) (temp * 16);
            temp = temp * 16 - s;
            if (stringBuilder.length() == 19 && temp > 0.5) s += 1;
            stringBuilder.append(list.get(s));
        } while (temp != 0.0 && stringBuilder.length() != 20);
        fractionalPart = stringBuilder.toString();
    }

    public static String convertSigned() {
        StringBuilder signedNum = new StringBuilder();
        convertNumberPart();
        if (numberPart.startsWith("-")) {
            int r = 1;
            for (int i = numberPart.length() - 1; i >0; i--) {
                char c = list.get(15 - list.indexOf(numberPart.charAt(i)));
                if (c == 'F' && r == 1) {
                    c = '0';
                } else {
                    c = list.get(list.indexOf(c) + r);
                    r = 0;
                }
                signedNum.insert(0, c);
            }
            if (list.indexOf(signedNum.charAt(0)) <= 7) signedNum.insert(0, 'F');
            while (signedNum.length() != 4 && signedNum.length() != 8 && signedNum.length() != 16) {
                signedNum.insert(0, 'F');
            }
        } else {
            while (signedNum.length() != 4 && signedNum.length() != 8 && signedNum.length() != 16) {
                signedNum.insert(0, '0');
            }
        }
        return signedNum.toString();
    }
}
