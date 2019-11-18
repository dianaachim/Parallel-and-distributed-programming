package ThreadPool;

import Model.Matrix;
import Model.Pair;

import java.util.ArrayList;
import java.util.List;

abstract class MatrixOperation implements Runnable {
    Matrix MatrixA;
    Matrix MatrixB;
    List<Pair<Integer, Integer>> cells;

    MatrixOperation(Matrix A, Matrix B) {
        MatrixA = A;
        MatrixB = B;
        cells = new ArrayList<>();
    }

    void addCellToToDoList(int row, int column) {
        cells.add(new Pair<>(row, column));
    }
}
