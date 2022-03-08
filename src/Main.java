import java.util.Random;

public class Main {

	public static void main(String[] args) {
		if(args.length > 2 || args.length < 1) {
			System.out.println("usage: aspgen <size> [<seed>]");
			System.exit(1);
		}
		int size = 0;
		try {
			size = Integer.parseInt(args[0]);
		}
		catch (NumberFormatException e) {
			System.err.println("Argument" + args[0] + " must be an integer.");
			System.exit(1);
		}

		int seed;
		Random rand = new Random();
		if (args.length > 1) {
			try {
				seed = Integer.parseInt(args[1]);
				rand = new Random(seed);
			}
			catch (NumberFormatException e) {
				System.err.println("Argument" + args[1] + " must be an integer.");
				System.exit(1);
			}
		}
		Generator gen = new Generator(size, rand);
		gen.generate();
	}
}
