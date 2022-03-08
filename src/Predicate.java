public class Predicate {

	private String identifier;
	private int arity;

	public Predicate(int inputSeed, int arity) {
		this.identifier = "p" + inputSeed;
		this.arity = arity;
	}

	public Predicate(int inputSeed) {
		this.identifier = "p" + inputSeed;
	}

	public String getIdentifier() {
		return identifier;
	}

	public int getArity() {
		return arity;
	}
}
