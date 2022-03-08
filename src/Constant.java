public class Constant {

	private String identifier;

	public String getIdentifier() {
		return identifier;
	}

	public Constant(int inputSeed) {
		this.identifier = "c" + inputSeed;
	}
}
