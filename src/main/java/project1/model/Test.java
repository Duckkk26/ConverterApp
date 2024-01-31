package project1.model;

public class Test {
    public static void main(String[] args) {
        Decimal.parse("-20");
        System.out.println(Hexadecimal.convertSigned());
        System.out.println(Binary.convert());
        System.out.println(Binary.convertSigned());
        System.out.println(Hexadecimal.parseDecimalFromSigned("9F"));
        System.out.println(Binary.parseDecimalFromSigned("1111111110011111"));
    }
}
