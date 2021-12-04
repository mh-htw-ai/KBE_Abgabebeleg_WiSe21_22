package com.evilcorp.mwst_microservice;

public class MehrwertsteuerCalculator {

    public static double mwstValueStandard = 0.19;

    /**
     * calculates the mwst-portion off a given price without taxes
     * @param price = price of a product without mwst
     * @return mwst of that price
     */
    public static double calculateMwstValue(double price){
        double res = price * mwstValueStandard;
        return res;
    }

    /**
     * calculates the sum off a price without taxes and its corresponding mwst-value
     * @param price = price of a product without mwst
     * @return sum of that price and the mwst value
     */
    public static double calculatePriceWithMwst(double price){
        double res = price * (1 + mwstValueStandard);
        return res;
    }

    /**
     * calculates the mwst-portion off a given price with taxes
     * @param price = final price off a product
     * @return mwst of a price that already includes the mwst value
     */
    public static double calculateMwstFromPrice(double price){
        double res = price - (price / (1 + mwstValueStandard));
        return res;
    }
}
