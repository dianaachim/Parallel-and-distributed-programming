package ThreadPool;

import Model.Matrix;
import Model.Pair;

public class Addition extends MatrixOperation{
    private Matrix MatrixResult;
    Addition(Matrix A, Matrix B, Matrix Res) {
        super(A, B);
        MatrixResult = Res;
    }

    @Override
    public void run() {
        for (Pair<Integer, Integer> cell: cells) {
            int cellContent = MatrixA.getCellContent(cell.getItem1(), cell.getItem2()) +
                    MatrixB.getCellContent(cell.getItem1(), cell.getItem2());
            MatrixResult.setCellContent(cellContent, cell.getItem1(), cell.getItem2());
        }
    }
}
