package project1.model;

class BaseModel {
    static String numberPart = "";
    static String fractionalPart = "";
    static int number;
    static double fraction;

    static void subNum(String numStr) {
        String[] list = numStr.split("\\.");
        numberPart = list[0];
        if (list.length == 2) fractionalPart = list[1];
    }

    static String getNum() {
        while (!fractionalPart.isEmpty())
            if (fractionalPart.endsWith("0"))
                fractionalPart = fractionalPart.substring(0, fractionalPart.length() - 1);
            else break;
        if (fractionalPart.isEmpty())
            return numberPart;
        return numberPart + "." + fractionalPart;
    }
}
