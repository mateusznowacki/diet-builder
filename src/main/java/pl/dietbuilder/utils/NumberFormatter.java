package pl.dietbuilder.utils;

public class NumberFormatter {

    public static double formatDoubleWithComma(String number) {
        if (number.contains(",")) {
            number = number.replace(",", ".");
        }
        return Double.valueOf(number);
    }


}
