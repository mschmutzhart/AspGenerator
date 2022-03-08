public class Term {

	public enum TermType {
		CONSTANT, VARIABLE, VARFUNCTION, CONSTFUNCTION
	}

	private Variable variable;
	private Constant constant;
	private Functor functor;
	private TermType type;

	public Term(Variable variable) {
		this.type = TermType.VARIABLE;
		this.variable = variable;
	}

	public Term(Constant constant) {
		this.type = TermType.CONSTANT;
		this.constant = constant;
	}

	public Term(Functor functor, Variable variable) {
		this.type = TermType.VARFUNCTION;
		this.functor = functor;
		this.variable = variable;
	}

	public Term(Functor functor, Constant constant) {
		this.type = TermType.CONSTFUNCTION;
		this.functor = functor;
		this.constant = constant;
	}

	public TermType getType() {
		return type;
	}

	public Variable getVariable() {
		return variable;
	}

	@Override
	public String toString() {
		return switch (type) {
			case CONSTANT -> constant.getIdentifier();
			case VARIABLE -> variable.getIdentifier();
			case VARFUNCTION -> functor.getIdentifier() + "(" + variable.getIdentifier() + ")";
			case CONSTFUNCTION -> functor.getIdentifier() + "(" + constant.getIdentifier() + ")";
		};
	}
}
