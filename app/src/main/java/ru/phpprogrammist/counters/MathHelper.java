package ru.phpprogrammist.counters;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathHelper {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static Double toDouble(String s){
        s = s.trim();
        try {
            return Double.parseDouble(s);

        } catch (NumberFormatException nfe) {
            try{
                String digits = s.replaceAll(",",".");
                digits = digits.replaceAll("[^\\d.]","");
                return Double.parseDouble(digits);
            }catch (Exception e) {
                return 0d;
            }
        }
    }
}
