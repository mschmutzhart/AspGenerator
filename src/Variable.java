public class Variable {

	private String identifier;

	public String getIdentifier() {
		return identifier;
	}

	public Variable(int inputSeed) {
		this.identifier = "X" + inputSeed;
	}
}
