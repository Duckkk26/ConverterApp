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
            if (numberPart.charAt(i) == '-') continue;
            number += (int) (Integer.parseInt("" + numberPart.charAt(i)) * Math.pow(2, n - i));
        }
        if (numberPart.charAt(0) == '-') number = -number;
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
        int temp = Math.abs(number);
        StringBuilder stringBuilder = new StringBuilder();
        do {
            int r = temp % 2;
            stringBuilder.insert(0, r);
            temp /= 2;
        } while (temp != 0);
        if (number < 0) {
            stringBuilder.insert(0, "-");
        }
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

    public static String convertSigned() {
        StringBuilder signedNum = new StringBuilder();
        convert();
        if (!fractionalPart.isEmpty()) return "N/A";
        if (numberPart.startsWith("-")) {
            int r = 1;
            for (int i = numberPart.length() - 1; i >0; i--) {
                char c = (numberPart.charAt(i) == '1') ? '0' : '1';
                if (c == '1' && r == 1) {
                    c = '0';
                } else {
                    c = (char) (c + r);
                    r = 0;
                }
                signedNum.insert(0, c);
            }
            if (signedNum.charAt(0) == '0') signedNum.insert(0, '1');
            while (signedNum.length() != 16 && signedNum.length() != 32 && signedNum.length() != 64) {
                signedNum.insert(0, '1');
            }
        } else {
            signedNum.append(numberPart);
            while (signedNum.length() != 16 && signedNum.length() != 32 && signedNum.length() != 64) {
                signedNum.insert(0, '0');
            }
        }
        return signedNum.toString();
    }

    public static String parseDecimalFromSigned(String numStr) {
        List<Integer> length = List.of(8, 16, 32, 64);
        if (!length.contains(numStr.length()) || numStr.contains(".")) return "N/A";
        int s = Integer.parseInt("" + numStr.charAt(0));
        int n = numStr.length() - 1;
        int res = 0;
        res -= (int) (s * Math.pow(2, n));
        for (int i = 1; i <= n; i++) {
            res += (int) (Integer.parseInt("" + numStr.charAt(i)) * Math.pow(2, n - i));
        }
        return String.valueOf(res);
    }

    public static String sum(List<String> binList) {
        StringBuilder res = new StringBuilder(binList.get(0));
        for (int i = 1; i < binList.size(); i++) {
            String r = "0";
            String binNum = binList.get(i);
            for (int j = binNum.length() - 1; j >= 0; j--) {
                if (res.charAt(j) == binNum.charAt(j)) {
                    res.replace(j, j + 1, r);
                    r = "0";
                    if (binNum.charAt(j) == '1') r = "1";
                } else {
                    if (r.equals("1")) {
                        res.replace(j, j +1, "0");
                    }
                    else {
                        res.replace(j, j + 1, "1");
                    }
                }
            }
        }
        return res.toString();
    }

    public static String xor(List<String> binList) {
        StringBuilder res = new StringBuilder(binList.get(0));
        for (int i = 1; i < binList.size(); i++) {
            String binNum = binList.get(i);
            for (int j = binNum.length() - 1; j >= 0; j--) {
                if (res.charAt(j) == binNum.charAt(j)) {
                    res.replace(j, j + 1, "0");
                } else {
                    res.replace(j, j + 1, "1");
                }
            }
        }
        return res.toString();
    }
}
