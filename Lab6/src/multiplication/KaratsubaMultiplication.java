package multiplication;

import polynomial.Polynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class KaratsubaMultiplication {
    private static Integer computePowerKaratsuba(int power, Polynomial pol1, Polynomial pol2) {
        int degree = pol1.getDegree();
        int sum1 = 0;
        int sum2 = 0;

        for (int s=0; s<power; s++) {
            int t = power - s;

            if (t <= degree) {
                if (s < t) {
                    int as = pol1.getCoefficient(s);
                    int at = pol1.getCoefficient(t);
                    int bs = pol2.getCoefficient(s);
                    int bt = pol2.getCoefficient(t);

                    sum1 += (as + at) * (bs + bt);
                    sum2 += (as * bs) + (at * bt);
                }
            }
        }
        int resCoeff = sum1 - sum2;

        if (power % 2 == 0) {
            int middle = power / 2;
            resCoeff += pol1.getCoefficient(middle) * pol2.getCoefficient(middle);
        }
        return resCoeff;
    }

    private static Future<Integer> computePowerKaratsubaParallel(int power, Polynomial pol1, Polynomial pol2) {
        return CompletableFuture.supplyAsync(() -> computePowerKaratsuba(power, pol1, pol2));
    }

    public static Polynomial multiplyKaratsubaSequential(Polynomial pol1, Polynomial pol2) {
        int resDegree = pol1.getDegree() + pol2.getDegree();
        List<Integer> resCoeffs = new ArrayList<>();

        for (int i = 0; i < resDegree; i++) {
            int resForPower = computePowerKaratsuba(i, pol1, pol2);
            resCoeffs.add(resForPower);
        }
        return Polynomial.withCoefficients(resDegree, resCoeffs);
    }

    public static Polynomial multiplyKaratsubaParallel(Polynomial pol1, Polynomial pol2) {
        int resDegree = pol1.getDegree() + pol2.getDegree();
        List<Integer> resCoeffs = new ArrayList<>();
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < resDegree; i++) {
            Future<Integer> futureResForPower = computePowerKaratsubaParallel(i, pol1, pol2);
            futures.add(futureResForPower);
        }

        for (int i=0; i< resDegree; i++) {
            try {
                Future<Integer> future = futures.get(i);
                resCoeffs.add(future.get(500, TimeUnit.MILLISECONDS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Polynomial.withCoefficients(resDegree, resCoeffs);
    }
}
