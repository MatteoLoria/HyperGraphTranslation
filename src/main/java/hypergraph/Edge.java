package hypergraph;

import java.util.ArrayList;
import java.util.List;

public class Edge {
	private String name;
	/** Vertices are ordered in every edge. */
	private ArrayList<String> vertices;

	public Edge(String name) {
		this.name = name;
		vertices = new ArrayList<String>(200);
	}

	public Edge(String name, String[] strings) {
		this.name = name;
		vertices = new ArrayList<String>(strings.length);
		for (String s : strings) {
			vertices.add(s);
		}
	}

	public String getName() {
		return name;
	}

	public boolean contains(String v) {
		return vertices.contains(v);
	}

	public void addVertex(String v) {
		vertices.add(v);
	}

	public void renameVertex(String v, String newName) {
		int pos = vertices.indexOf(v);
		vertices.set(pos, newName);
	}

	public List<String> getVertices() {
		return vertices;
	}

	public String toString() {
		String s = "";

		s += stringify(name) + "(";
		for (String v : vertices) {
			s += stringify(v) + ",";
		}
		s = s.substring(0, s.length() - 1);
		s += ")";

		return s;
	}

	private String stringify(String s) {
		String newS = new String(s);

		newS = newS.replace('[', 'L');
		newS = newS.replace(']', 'J');

		return newS;
	}

}
