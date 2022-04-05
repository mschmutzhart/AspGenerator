public class Predicate {

	private String identifier;
	private int arity;

	public Predicate(int inputSeed, int arity) {
		this.identifier = "p" + inputSeed;
		this.arity = arity;
	}

	public String getIdentifier() {
		return identifier;
	}

	public int getArity() {
		return arity;
	}

	@Override
	public String toString() {
		return "Predicate{" +
				"identifier='" + identifier + '\'' +
				", arity=" + arity +
				'}';
	}
}
