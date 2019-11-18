package ThreadPool;

import Model.Matrix;
import Model.Pair;

public class Multiplication extends MatrixOperation {
    private Matrix MatrixResult;

    Multiplication(Matrix A, Matrix B, Matrix Res) {
        super(A, B);
        MatrixResult = Res;
    }

    @Override
    public void run() {
        for (Pair<Integer, Integer> cell : this.cells) {
            int cellContent = 0;
            for (int i = 0; i < this.MatrixResult.getRowLength(); i++) {
                cellContent = cellContent +
                        (MatrixA.getCellContent(cell.getItem1(), i)
                                * MatrixB.getCellContent(i, cell.getItem2()));
                this.MatrixResult.setCellContent(cellContent, cell.getItem1(), cell.getItem2());
            }
        }
    }
}
