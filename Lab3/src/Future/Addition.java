package Future;

import Model.Matrix;
import Model.Pair;

public class Addition extends MatrixOperation {
    private Matrix MatrixResult;
    Addition(Matrix a, Matrix b, Matrix Res) {
        super(a, b);
        MatrixResult = Res;
    }

    @Override
    public Object call() throws Exception {
        for(Pair<Integer, Integer> cell : this.cells) {
            int cellContent = this.MatrixA.getCellContent(cell.getItem1(), cell.getItem2()) +
                    this.MatrixB.getCellContent(cell.getItem1(), cell.getItem2());
            MatrixResult.setCellContent(cellContent, cell.getItem1(), cell.getItem2());
        }
        return MatrixResult;
    }
}
