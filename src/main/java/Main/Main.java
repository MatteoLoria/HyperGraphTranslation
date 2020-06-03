package Main;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) throws Exception {
		if (args.length != 0) {
			String type = args[0];
			for (int i = 0; i < args.length - 1; i++)
				args[i] = args[i + 1];
			if (type.equals("-convert")) {
				String format = args[0];
				for (int i = 0; i < args.length - 1; i++)
					args[i] = args[i + 1];
				if (format.equals("-csp")) {
					MainConvertCSP.main(Arrays.copyOf(args, args.length - 2));
				}
			}
		}
	}

}
