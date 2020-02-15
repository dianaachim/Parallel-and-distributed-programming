package multiplication;

import polynomial.Polynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ON2Multiplication {
    private static Integer computePowerON2(int power, Polynomial pol1, Polynomial pol2) {
        int resultCoeff = 0;
        int degree1 = pol1.getDegree();
        int degree2 = pol2.getDegree();

        for (int i=0; i<=power/2; i++) {
            int complement = power - i;
            if (i <= degree1 && complement <= degree2) {
                resultCoeff +=pol1.getCoefficient(i) * pol2.getCoefficient(complement);
            }

            if (i == complement) continue;

            if (i<=degree2 && complement <=degree1) {
                resultCoeff += pol2.getCoefficient(i) * pol1.getCoefficient(complement);
            }
        }
        return resultCoeff;
    }

    private static Future<Integer> computePowerON2Parallel(int power, Polynomial pol1, Polynomial pol2){
        return CompletableFuture.supplyAsync(() ->
                computePowerON2(power, pol1, pol2));
    }

    public static Polynomial mulptiplyON2Sequential(Polynomial pol1, Polynomial pol2) {
        int resDegree = pol1.getDegree() + pol2.getDegree();
        List<Integer> resCoeff = new ArrayList<>();

        for (int i =0 ; i<=resDegree; i++) {
            int resultForPower = computePowerON2(i, pol1, pol2);
            resCoeff.add(resultForPower);
        }
        return Polynomial.withCoefficients(resDegree, resCoeff);
    }

    public static Polynomial multiplyON2Parallel(Polynomial pol1, Polynomial pol2) {
        int resDegree = pol1.getDegree() + pol2.getDegree();
        List<Integer> resCoeff = new ArrayList<>();
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i =0 ; i<=resDegree; i++) {
            Future<Integer> futureForPower = computePowerON2Parallel(i, pol1, pol2);
            futures.add(futureForPower);
        }

        for (int i =0 ; i<=resDegree; i++) {
            try {
                Future<Integer> future = futures.get(i);
                resCoeff.add(future.get(500, TimeUnit.MILLISECONDS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Polynomial.withCoefficients(resDegree, resCoeff);
    }
}
