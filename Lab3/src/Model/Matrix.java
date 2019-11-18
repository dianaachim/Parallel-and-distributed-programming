package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Matrix {
    private int rowLength;
    private int columnLength;
    private List<List<Integer>> content;

    private static final int minValueContent = 0;
    private static final int maxValueContent = 9;

    public Matrix(int rows, int cols, String flag) {
        rowLength = rows;
        columnLength = cols;
        if (flag.equals("empty")) {
            this.generateEmptyMatrix();
        } else {
            this.generateRandomMatrix();
        }
    }

    private void generateEmptyMatrix() {
        content = new ArrayList<>(rowLength);
        for  (int row = 0; row < rowLength; row++) {
            List<Integer> columnContent = new ArrayList<>(this.rowLength);
            for (int col = 0; col < columnLength; col ++) {
                columnContent.add(0);
            }
            content.add(columnContent);
        }
    }

    private void generateRandomMatrix() {
        Random random = new Random();
        content = new ArrayList<>(rowLength);
        for (int row = 0; row < rowLength; row++) {
            List<Integer> columnContent = new ArrayList<>(columnLength);
            for (int col = 0; col < columnLength; col ++) {
                int cellValue = random.nextInt((maxValueContent - minValueContent) + 1) + minValueContent;
                columnContent.add(cellValue);
            }
            content.add(columnContent);
        }
    }

    public int getRowLength() {
        return this.rowLength;
    }

    public int getColumnLength() {
        return this.columnLength;
    }

    public Integer getCellContent(int rowIndex, int colIndex) {
        return this.content.get(rowIndex).get(colIndex);
    }

    public void setCellContent(int newValue, int rowIndex, int colIndex) {
        this.content.get(rowIndex).set(colIndex, newValue);
    }

    @Override
    public String toString() {
        StringBuilder matrix = new StringBuilder();
        for (int row = 0; row < this.rowLength; row++) {
            matrix.append(this.content.get(row).toString()).append("\n");
        }
        return matrix.toString();
    }
}
