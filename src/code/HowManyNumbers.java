package code;

import java.util.List;

public class HowManyNumbers {

    public static List<Long> findAll(final int sumDigits, final int numDigits) {
        // Your code here!!
        /**
         * find numbers for which
         * 1. Digits sum to sumDigits
         * 2. Number of digits is numDigits
         * 3. digits are incrementing
         *
         * Max value = numDigits * 9
         *      If maxValue < sumDigits, return empty
         *  MinValue = numDigits
         *      if minValue> sumDigits, return empty
         *
         *  to find digit combos which sum to the target:
         *  for i = 1 to numDigits
         *      if(1 + (i-1)*9)<sum
         *      try 2
         */
        return null;
    }
}
