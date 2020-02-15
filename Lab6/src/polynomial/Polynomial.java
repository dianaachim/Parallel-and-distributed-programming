package polynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Random;

import static constants.Constants.PLUS;
import static constants.Constants.X_AT_POWER;

public class Polynomial {
    protected int degree;
    protected List<Integer> coefficients;

    public Polynomial(int dgr, List<Integer> coeff) {
        degree = dgr;
        coefficients = coeff;
    }

    public int getDegree() {return degree;}

    public int getCoefficient(int index) {
        return coefficients.get(index);
    }

    @Override
    public String toString() {
        List<String> stringCoefficients = new ArrayList<>();

        if (coefficients.get(0) != 0)
            stringCoefficients.add(coefficients.get(0).toString());

        for (int i = 0; i < degree; i++) {
            if (coefficients.get(i) != 0)
                stringCoefficients.add(coefficients.get(i) + X_AT_POWER + i);
        }

        return String.join(PLUS, stringCoefficients);
    }

    public static Polynomial random(int degree) {
        Random random = new Random();
        List<Integer> coeffs = new ArrayList<>();

        for (int i = 0; i<=degree; i++) {
            int coefficient =random.nextInt(5 + 1);
            coeffs.add(coefficient);
        }
        return new Polynomial(degree, coeffs);
    }

    public static Polynomial withCoefficients(int degree, List<Integer> coeffs) {
        return new Polynomial(degree, coeffs);
    }
}
