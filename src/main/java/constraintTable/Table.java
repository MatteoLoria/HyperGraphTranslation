package constraintTable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Table {

    ArrayList<ArrayList<String>> values;
    String constraintType;

    public Table() {
        values = new ArrayList<>();
    }

    public void toFile(BufferedWriter out) throws IOException {
        out.append(constraintType).append("\n");
        for (int i = 0; i < values.size(); ++i) {
            for (int j = 0; j < values.get(i).size(); ++j) {
                if (j == values.get(i).size() - 1) {
                    out.append(values.get(i).get(j));
                } else {
                    out.append(values.get(i).get(j) + ",");
                }
            }
            out.append("\n");
        }
    }

    public void setConstraintType(String constraintType) {
        this.constraintType = constraintType;
    }

    public void addPossibleValues(ArrayList<String> possibleValues) {
        values.add(possibleValues);
    }

}
