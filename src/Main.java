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

		Random rand;
		int seed = (int) (1000 * Math.random()) + size;
		if (args.length > 1) {
			try {
				seed = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException e) {
				System.err.println("Argument" + args[1] + " must be an integer.");
				System.exit(1);
			}
		}
		rand = new Random(seed);


		System.out.println("% Size: " + size + ", Seed: " + seed);

		Generator gen = new Generator(size, rand);
		gen.generate();
	}
}
