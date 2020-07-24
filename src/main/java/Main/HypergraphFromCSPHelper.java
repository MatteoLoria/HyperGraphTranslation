package Main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import javax.xml.parsers.DocumentBuilderFactory;

import constraintTable.Table;
import domain.Domain;
import org.tukaani.xz.LZMAInputStream;
import org.w3c.dom.Document;
import org.xcsp.common.Condition;
import org.xcsp.common.Types.TypeFlag;
import org.xcsp.parser.callbacks.XCallbacks2;
import org.xcsp.parser.entries.ParsingEntry;
import org.xcsp.parser.entries.XVariables;
import org.xcsp.parser.entries.XVariables.XVarInteger;

import hypergraph.Edge;
import hypergraph.Hypergraph;

public class HypergraphFromCSPHelper implements XCallbacks2 {
	private Implem implem = new Implem(this);
	private Map<XVarInteger, String> mapVar = new LinkedHashMap<>();
	private Hypergraph H = new Hypergraph();
	private int iEdge = 0;
	private ArrayList<Table> tables = new ArrayList<>();
	private Domain domain = new Domain();

	public HypergraphFromCSPHelper(String filename) throws Exception {
		loadInstance(filename);
	}

	@Override
	public Implem implem() {
		return implem;
	}

	public Hypergraph getHypergraph() {
		return H;
	}

	public ArrayList<Table> getTables() {
		return tables;
	}

	public Domain getDomain() {
		return domain;
	}

	@Override
	public Document loadDocument(String fileName) throws Exception {
		if (fileName.endsWith("xml.lzma")) {
			LZMAInputStream input = new LZMAInputStream(new BufferedInputStream(new FileInputStream(fileName)));

			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
			return document;
		} else if (fileName.endsWith(".xml")) {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new FileInputStream(new File(fileName)));
		} else
			return null;
	}

	@Override
	public void buildVarInteger(XVarInteger xx, int minValue, int maxValue) {
		int[] temp = new int[maxValue];
		for (int i = 0; i < maxValue; ++i) {
			temp[i] = i + 1;
		}
		domain.addVar(stringify(xx), temp);
		String x = xx.id;
		mapVar.put(xx, x);
	}

	@Override
	public void buildVarInteger(XVarInteger xx, int[] values) {
		domain.addVar(stringify(xx), values);
		String x = xx.id;
		mapVar.put(xx, x);
	}

	private String trVar(Object x) {
		return mapVar.get((XVarInteger) x);
	}

	private String[] trVars(Object vars) {
		return Arrays.stream((XVarInteger[]) vars).map(x -> mapVar.get(x)).toArray(String[]::new);
	}

	private String[][] trVars2D(Object vars) {
		return Arrays.stream((XVarInteger[][]) vars).map(t -> trVars(t)).toArray(String[][]::new);
	}

	@Override
	public void buildCtrExtension(String id, XVarInteger[] list, int[][] tuples, boolean positive,
			Set<TypeFlag> flags) {
		/*System.out.println(id);
		System.out.println(Arrays.toString(list));
		System.out.println(Arrays.deepToString(tuples));
		System.out.println(positive);
		System.out.println(flags);*/
		// Arrays.stream(trVars(list)).forEach(t -> System.out.print(t));
		// System.out.println();
		H.addEdge(new Edge("E" + ++iEdge, trVars(list)));
		Table table = new Table();
		if (positive) {
			table.setConstraintType("supports");
		} else {
			table.setConstraintType("conflicts");
		}
		ArrayList<String> possibleValues = new ArrayList<>();
		for (XVarInteger xVarInteger : list) {
			possibleValues.add(stringify(xVarInteger));
		}
		table.addPossibleValues(possibleValues);
		for (int i = 0; i < tuples.length; ++i) {
			possibleValues = new ArrayList<>();
			for (int j = 0; j < tuples[i].length; ++j) {
				possibleValues.add(String.valueOf(tuples[i][j]));
			}
			table.addPossibleValues(possibleValues);
		}
		tables.add(table);
	}

	private String stringify(XVarInteger xVarInteger) {
		String e = xVarInteger.toString();
		e = e.replace("[", "L");
		e = e.replace("]", "J");
		return e;
	}

}
