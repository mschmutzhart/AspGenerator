import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Atom {

	private Predicate predicate;
	private List<Term> terms;
	private Set<Variable> occurringVariables;

	public Atom(Predicate predicate, List<Term> terms) {
		this.predicate = predicate;
		this.terms = terms;
		Set<Variable> occurringVariables = new LinkedHashSet<>();
		for (Term term : terms) {
			if (term.getType() == Term.TermType.VARIABLE || term.getType() == Term.TermType.VARFUNCTION) {
				occurringVariables.add(term.getVariable());
			}
		}
		this.occurringVariables = occurringVariables;
	}

	@Override
	public String toString() {
		String retval = predicate.getIdentifier();
		if (predicate.getArity() > 0) {
			retval += "(";
			for (int i = 0; i < predicate.getArity(); i++) {
				retval += terms.get(i) + ",";
			}
			retval = retval.substring(0,retval.length() - 1);
			retval += ")";
		}
		return retval;
	}

	public Set<Variable> getOccurringVariables() {
		return occurringVariables;
	}
}
