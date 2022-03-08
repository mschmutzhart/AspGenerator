import java.util.List;

public class AspRule {

	public enum RuleType {
		FACT, CONSTRAINT, RULE
	}

	private Atom head;
	private Body body;
	private RuleType type;


	public AspRule(Atom head, Body body, RuleType type) {
		this.head = head;
		this.body = body;
		this.type = type;
	}

	@Override
	public String toString() {
		return switch (type) {
			case FACT -> head.toString() + ".";
			case CONSTRAINT -> ":- " + body.toString() + ".";
			case RULE -> head.toString() + " :- " + body.toString() + ".";
		};
	}
}
