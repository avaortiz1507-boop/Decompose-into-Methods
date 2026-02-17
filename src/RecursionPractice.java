public class RecursionPractice {

    // Method to calculate factorial of a number
    public static int factorial(int n) {
        if (n == 0) {
            return 1; // Base case: factorial of 0 is 1
        }

        return n * factorial(n - 1); // Recursive case: n! = n * (n-1)!

        // return (n == 0) ? 1 : n * factorial(n - 1);
    }

    // Method to calculate the sum of an array of integers
    public static int sumArray(int[] arr, int index) {
        if (index == arr.length) {
            return 0;
        }
        // 2 + result of sumArray with the next index (index + 1)
        return arr[index] + sumArray(arr, index + 1);
        // return (index >= arr.length) ? 0 : arr[index] + sumArray(arr, index + 1);
    }

    // Method to calculate the nth Fibonacci number
    public static int fibonacci(int n) {
        if (n == 0) {
            return 0; // Base case: Fibonacci of 0 is 0
        }
        if (n == 1) {
            return 1; // Base case: Fibonacci of 1 is 1
        }

        return fibonacci(n - 1) + fibonacci(n - 2); // Recursive case: F(n) = F(n-1) + F(n-2)

        // return (n == 0) ? 0 : (n == 1) ? 1 : fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static void main(String[] args) {

        /*
         * main method to test all three methods
         * Call factorial(5) and print the result.
         * Call sumArray using an array like {2,4,6,8}.
         * Call fibonacci(7) and print the result.
         */

        // Testing factorial method
        int factorialResult = factorial(5);
        System.out.println("Factorial of 5: " + factorialResult);

        // testing sumArray method
        int[] array = { 2, 4, 6, 8 };
        int sumResult = sumArray(array, 0);
        System.out.println("Sum of array {2, 4, 6, 8}: " + sumResult);

        // Testing fibonacci method
        int fibonacciResult = fibonacci(7);
        System.out.println("7th Fibonacci number: " + fibonacciResult);
    }
}