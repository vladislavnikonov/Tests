package edu.school21.numbers;

public class NumberWorker {

    public boolean isPrime(int number) {
        if (number <= 1)
            throw new IllegalNumberException();
        int d = 2;
        while (d * d <= number && number % d != 0) {
            d++;
        }
        return d * d > number;
    }

    public int digitsSum(int number) {
        int sum = 0;

        while (number != 0) {
            sum += Math.abs(number % 10);
            number /= 10;
        }

        return sum;
    }
}
