package domain;


import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

public class Domain {

    private HashMap<String, int[]> map;

    public Domain() {
        map = new HashMap<>();
    }

    public void addVar(String var, int[] domain) {
        map.put(var, domain);
    }

    public void toFile(BufferedWriter out) throws IOException {
        for (String var: map.keySet()) {
            out.append(var).append("\n");
            int[] domain = map.get(var);
            for (int i = 0; i < domain.length; ++i) {
                if (i == domain.length - 1) {
                    out.append(Integer.toString(domain[i])).append("\n");
                } else {
                    out.append(Integer.toString(domain[i])).append(" ");
                }
            }
        }
    }

}
