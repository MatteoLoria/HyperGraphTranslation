package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import constraintTable.Table;
import domain.Domain;
import hypergraph.Hypergraph;

public class MainConvertCSP {

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < args.length; i++) {
			File file = new File(args[i]);
			File[] files;
			if (file.isDirectory()) {
				files = file.listFiles();
			} else {
				files = new File[1];
				files[0] = file;
			}
			processFiles(files);
		}
	}

	public static void processFiles(File[] files) throws Exception {
		for (File file : files) {
			if (file.isDirectory()) {
				// System.out.println("Directory: " + file.getName());
				processFiles(file.listFiles()); // Calls same method again.
			} else if (file.getName().contains("xml")) {
				System.out.println("+ Converting:" + file.getPath());
				System.out.println("++ Read");
				HypergraphFromCSPHelper csp2hg = new HypergraphFromCSPHelper(file.getPath());

				Hypergraph H = csp2hg.getHypergraph();
				ArrayList<Table> tables = csp2hg.getTables();
				Domain domain = csp2hg.getDomain();

				System.out.println("++ Output");

				String path = file.getPath();
				String outputName = "";
				if (path.contains("\\"))
					outputName = "output" + path.substring(path.lastIndexOf("\\")+1, path.lastIndexOf("."))+"\\";
				else
					outputName = "output" + path.substring(0, path.lastIndexOf("."))+"\\";
				System.out.println(outputName);
				String hypergraphFile = outputName + path.substring(0, path.lastIndexOf(".")) + "hypergraph.hg";

				Path newFilePath = Paths.get(hypergraphFile);
				Files.createDirectories(newFilePath.getParent());
				if (!Files.exists(newFilePath))
					Files.createFile(newFilePath);
				Files.write(Paths.get(hypergraphFile), H.toFile(), Charset.forName("UTF-8"));

				BufferedWriter outTables = new BufferedWriter(new FileWriter(outputName + path.substring(0, path.lastIndexOf(".")) + "tables.hg"));
				try {
					for (Table t : tables) {
						t.toFile(outTables);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					outTables.close();
				}

				BufferedWriter outDomain = new BufferedWriter(new FileWriter(outputName + path.substring(0, path.lastIndexOf(".")) + "domain.hg"));
				try {
					domain.toFile(outDomain);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					outDomain.close();
				}
			}
		}
	}

}
