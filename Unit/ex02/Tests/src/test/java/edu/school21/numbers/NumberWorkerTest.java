package edu.school21.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


class NumberWorkerTest {

    @ParameterizedTest
    @ValueSource(ints = {2, 5, 17, 41, 101})
    public void isPrimeForPrimes(int number) {
        NumberWorker test = new NumberWorker();
        assertTrue(test.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 9, 15, 24})
    public void isPrimeForNotPrimes(int number) {
        NumberWorker test = new NumberWorker();
        assertFalse(test.isPrime(number));
    }

    @ParameterizedTest()
    @ValueSource(ints = {1, 0, -1, -69, -504})
    public void isPrimeForIncorrectNumbers(int number) {
        NumberWorker test = new NumberWorker();
        assertThrows(IllegalNumberException.class, () -> test.isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    public void digitsSumCorrectSet(int input, int expected) {
        NumberWorker test = new NumberWorker();
        assertEquals(expected, test.digitsSum(input));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data_fails.csv", numLinesToSkip = 1)
    public void digitsSumIncorrectSet(int input, int expected) {
        NumberWorker test = new NumberWorker();
        assertEquals(expected, test.digitsSum(input));
    }
}