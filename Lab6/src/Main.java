import multiplication.KaratsubaMultiplication;
import multiplication.ON2Multiplication;
import polynomial.Polynomial;

import javax.annotation.processing.SupportedSourceVersion;

import static constants.Constants.NEW_LINE;

public class Main {
    public static final int POL_SYZE = 5;

    public static void main(String[] args) {
        Polynomial pol1 = Polynomial.random(POL_SYZE);
        Polynomial pol2 = Polynomial.random(POL_SYZE);
        Polynomial res;

        System.out.println("Polynomial 1: " + pol1);
        System.out.println("Polynomial 2: " + pol2);

        long start1 = System.nanoTime();
        res = ON2Multiplication.mulptiplyON2Sequential(pol1, pol2);
        long end1 = System.nanoTime();
        System.out.println(NEW_LINE + "O(n2) sequential: " + (end1 - start1) / 100000 + " ms");
        System.out.println("Result polynomial: " + res);

        long start2 = System.nanoTime();
        res = ON2Multiplication.multiplyON2Parallel(pol1, pol2);
        long end2 = System.nanoTime();
        System.out.println(NEW_LINE + "O(n2) parallel: " + (end1 - start1) / 100000 + " ms");
        System.out.println("Result polynomial: " + res);

        long start3 = System.nanoTime();
        res = KaratsubaMultiplication.multiplyKaratsubaSequential(pol1, pol2);
        long end3 = System.nanoTime();
        System.out.println(NEW_LINE + "Karatsuba sequential: " + (end1 - start1) / 100000 + " ms");
        System.out.println("Result polynomial: " + res);

        long start4 = System.nanoTime();
        res = KaratsubaMultiplication.multiplyKaratsubaParallel(pol1, pol2);
        long end4 = System.nanoTime();
        System.out.println(NEW_LINE + "Karatsuba parallel: " + (end1 - start1) / 100000 + " ms");
        System.out.println("Result polynomial: " + res);
    }
}
