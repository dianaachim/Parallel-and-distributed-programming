package Future;

import Model.Matrix;
import Model.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

abstract class MatrixOperation implements Callable {
    Matrix MatrixA;
    Matrix MatrixB;
    List<Pair<Integer, Integer>> cells; // one thread will hold a list of cells which to add or multiply

    MatrixOperation(Matrix a, Matrix b) {
        this.MatrixA = a;
        this.MatrixB = b;
        this.cells = new ArrayList<>();
    }

    void addCellToToDoList(int row, int column) {
        this.cells.add(new Pair<>(row, column));
    }
}
