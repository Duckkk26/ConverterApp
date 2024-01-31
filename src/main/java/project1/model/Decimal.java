package project1.model;

public class Decimal extends BaseModel {
    public static void parse(String numStr) {
        subNum(numStr);
        parseNumPart();
        parseFractionalPart();
    }

    private static void parseNumPart() {
        number = Integer.parseInt(numberPart);
    }

    private static void parseFractionalPart() {
        fraction = Double.parseDouble("0." + fractionalPart);
    }

    public static String convert() {
        convertNumberPart();
        convertFractionalPart();
        return getNum();
    }

    private static void convertNumberPart() {
        numberPart = String.valueOf(number);
    }

    private static void convertFractionalPart() {
        fractionalPart = String.valueOf(fraction);
        fractionalPart = fractionalPart.replace("0.", "");
    }
}
