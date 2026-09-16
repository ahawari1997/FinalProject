package Utils;

import Exceptions.FramworkException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Priceutills {
    private static final int SCALE = 2;

    private Priceutills(){
        // static utility
    }
    public static BigDecimal parse(String rawPrice) {
        if (rawPrice == null || rawPrice.isBlank()) {
            throw new FramworkException("Cannot parse a blank price string");
        }
        String cleaned = rawPrice.trim().replace("$", "");
        try {
            return round(new BigDecimal(cleaned));
        } catch (NumberFormatException e) {
            throw new FramworkException("Could not parse price from '" + rawPrice + "'", e);
        }
    }

    /**
     * Rounds to 2 decimal places, HALF_UP (e.g. 3.1984 -> 3.20).
     */
    public static BigDecimal round(BigDecimal value) {
        if (value == null) {
            throw new FramworkException("Cannot round a null value");
        }
        return value.setScale(SCALE, RoundingMode.HALF_UP);
    }


}
