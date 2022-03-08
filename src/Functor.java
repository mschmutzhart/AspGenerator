public class Functor {

	private String identifier;

	public String getIdentifier() {
		return identifier;
	}

	public Functor(int inputSeed) {
		this.identifier = "f" + inputSeed;
	}
}
