package Future;

import Model.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Main {
    private static Matrix addition(int numberOfThreads, int numberOfTasks, Matrix MatrixA, Matrix MatrixB) throws ExecutionException, InterruptedException {
        int rows = MatrixA.getRowLength();
        int cols = MatrixA.getColumnLength();
        Matrix MatrixResult = new Matrix(rows, cols, "empty");

        List<Addition> callables = new ArrayList<>();

        for (int idx = 0; idx < numberOfTasks; idx++) {
            callables.add(new Addition(MatrixA, MatrixB, MatrixResult));
        }

        // divide the matrix operation between tasks
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int index = ( row * cols + col ) % numberOfTasks;
                callables.get(index).addCellToToDoList(row, col);
            }
        }

        List<FutureTask> tasks = new ArrayList<>();

        for (Addition callable : callables) {
            tasks.add(new FutureTask(callable));
        }

        for (FutureTask task : tasks) {
            Thread thread = new Thread(task);
            thread.start();
        }

        for (FutureTask task : tasks) {
            task.get();
        }

        Thread.sleep(100); // done only so that you can see the actual result

        return MatrixResult;
    }

    private static Matrix multiplication(int numberOfThreads, int numberOfTasks, Matrix MatrixA, Matrix MatrixB) throws InterruptedException, ExecutionException {
        int rows = MatrixA.getRowLength();
        int cols = MatrixB.getColumnLength();
        Matrix MatrixResult = new Matrix(rows, cols, "empty");

        List<Multiplication> callables = new ArrayList<>();

        for (int idx = 0; idx < numberOfTasks; idx++) {
            callables.add(new Multiplication(MatrixA, MatrixB, MatrixResult));
        }

        // divide the matrix operation between tasks
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int index = ( row * cols + col ) % numberOfTasks;
                callables.get(index).addCellToToDoList(row, col);
            }
        }

        List<FutureTask> tasks = new ArrayList<>();

        for (Multiplication callable : callables) {
            tasks.add(new FutureTask(callable));
        }

        for (FutureTask task : tasks) {
            Thread thread = new Thread(task);
            thread.start();
        }

        for (FutureTask task : tasks) {
            task.get();
        }

        Thread.sleep(100); // done only so that you can see the actual result

        return MatrixResult;
    }

    private static void printMenu() {
        System.out.println("-------Menu---------");
        System.out.println("1.Add");
        System.out.println("2.Multiply");
        System.out.println("0.Exit");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command = "initial";
        List<String> collectedExecutionTimesAddition = new ArrayList<>();
        List<String> collectedExecutionTimesMultiplication = new ArrayList<>();

        printMenu();

        while (!command.equals("0")) {
            System.out.println("Input operation: ");
            command = scanner.nextLine();
            if (command.equals("1")) {
                System.out.println("Matrix dimensions: ");
                System.out.println("- rows: ");
                int matrixRows = scanner.nextInt();
                scanner.nextLine();
                System.out.println("- columns: ");
                int matrixColumns = scanner.nextInt();
                scanner.nextLine();
                Matrix MatrixA = new Matrix(matrixRows, matrixColumns, "random");
                Matrix MatrixB = new Matrix(matrixRows, matrixColumns, "random");

                System.out.println("Number of tasks that will perform the addition: ");
                int numberOfTasks = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Number of threads that will perform the task: ");
                int numberOfThreads = scanner.nextInt();
                scanner.nextLine();

                try {
                    float startTime = System.nanoTime() / 1000000;
                    Matrix MatrixResult = addition(numberOfThreads, numberOfTasks, MatrixA, MatrixB);
                    float endTime = System.nanoTime() / 1000000;
                    float estimatedTime = endTime - startTime;

                    System.out.println("Elapsed time for operation: " + estimatedTime);
                    String message = "Elapsed time for matrix size (" + matrixRows + "," + matrixColumns + "): " + estimatedTime + " for " + numberOfTasks + " tasks split between " + numberOfThreads + " threads";
                    collectedExecutionTimesAddition.add(message);


                    System.out.println("MatrixA: ");
                    System.out.println(MatrixA);
                    System.out.println("MatrixB: ");
                    System.out.println(MatrixB);
                    System.out.println("Addition result: ");
                    System.out.println(MatrixResult);

                } catch (InterruptedException | ExecutionException ex) {
                    ex.printStackTrace();
                }
            }
            if (command.equals("2")) {
                System.out.println("Matrix A dimensions: ");
                System.out.println("- rows: ");
                int matrixARows = scanner.nextInt();
                scanner.nextLine();
                System.out.println("- columns: ");
                int matrixAColumns = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Matrix B dimensions: ");
                System.out.println("- rows: ");
                int matrixBRows = scanner.nextInt();
                scanner.nextLine();
                System.out.println("- columns: ");
                int matrixBColumns = scanner.nextInt();
                scanner.nextLine();

                if (matrixAColumns == matrixBRows) {
                    Matrix MatrixA = new Matrix(matrixARows, matrixAColumns, "random");
                    Matrix MatrixB = new Matrix(matrixBRows, matrixBColumns, "random");

                    System.out.println("Number of tasks that will perform the addition: ");
                    int numberOfTasks = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Number of threads that will perform the task: ");
                    int numberOfThreads = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        float startTime = System.nanoTime() / 1000000;
                        Matrix MatrixResult = multiplication(numberOfThreads, numberOfTasks, MatrixA, MatrixB);
                        float endTime = System.nanoTime() / 1000000;
                        float estimatedTime = endTime - startTime;

                        System.out.println("Estimated time for operation: " + estimatedTime);

                        System.out.println("MatrixA: ");
                        System.out.println(MatrixA);
                        System.out.println("MatrixB: ");
                        System.out.println(MatrixB);
                        System.out.println("Multiplication result: ");
                        System.out.println(MatrixResult);

                        System.out.println("Elapsed time for operation: " + estimatedTime);
                        String message = "Elapsed time for matrices of sizes (" + matrixARows + "," + matrixAColumns + ") and (" + matrixBRows + "," + matrixBColumns + "): " + estimatedTime + " for " + numberOfTasks + " tasks split between " + numberOfThreads + " threads";
                        collectedExecutionTimesMultiplication.add(message);

                    } catch (InterruptedException | ExecutionException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("Number of columns of matrix A needs to be equal to number of rows of matrix B");
                }
            }
            if (command.equals("log")) {
                System.out.println("1. Log for addition");
                System.out.println("2. Log for multiplication");
                command = scanner.nextLine();

                if (command.equals("1")) {
                    System.out.println(collectedExecutionTimesAddition);
                } else {
                    System.out.println(collectedExecutionTimesMultiplication);
                }
            }
        }
    }
}

