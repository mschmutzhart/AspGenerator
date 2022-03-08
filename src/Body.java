import java.util.List;

public class Body {

	private List<Atom> posBody;
	private List<Atom> negBody;


	public Body(List<Atom> posBody, List<Atom> negBody) {
		this.posBody = posBody;
		this.negBody = negBody;
	}

	@Override
	public String toString() {
		String retval = "";
		for (Atom a : posBody) {
			retval += a.toString() + ", ";
		}
		for (Atom a : negBody) {
			retval += "not " + a.toString() + ", ";
		}
		return retval.substring(0,retval.length() - 2);
	}
}
