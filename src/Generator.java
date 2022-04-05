import java.util.*;

public class Generator {


	public static final double RULES_WITH_POSSIBLE_NEGATIVE_BODY_RATIO = 0.5;
	public static final double CONSTRAINT_TO_RULES_RATIO = 0.1;


	public static final int MAX_PREDICATE_ARITY = 3;

	//The next constant defines how much more likely a predicate with
	// arity n+1 is chosen over one with arity n.
	public static final int ARITY_PROBABILITY_BASE = 5;
	public static final boolean INCLUDE_FUNCTIONS = false;

	private final int size;
	private final Random rand;
	private final double FACTS_TO_RULES_RATIO;
	public final int PREDICATE_AMOUNT;
	public final int VARIABLE_AMOUNT;
	public final int CONSTANT_AMOUNT;
	public final int FUNCTOR_AMOUNT;
	private final List<Predicate> predicates = new LinkedList<>();
	private final List<Variable> variables = new LinkedList<>();
	private final List<Constant> constants = new LinkedList<>();
	private final List<Functor> functors = new LinkedList<>();

	public Generator(int size, Random rand) {
		this.size = size;
		this.rand = rand;
		this.FACTS_TO_RULES_RATIO = (size - Math.sqrt(size)) / size;
		this.PREDICATE_AMOUNT = (int) (Math.sqrt(size)) + 1;
		this.VARIABLE_AMOUNT = (int) (Math.sqrt(size)) + 1;
		this.CONSTANT_AMOUNT = (int) (Math.sqrt(size)) + 1;
		this.FUNCTOR_AMOUNT = (int) (Math.sqrt(size)) + 1;
	}

	public void generate() {
		System.out.println("%RULES_WITH_POSSIBLE_NEGATIVE_BODY_RATIO: " + RULES_WITH_POSSIBLE_NEGATIVE_BODY_RATIO + "\n" +
				"%CONSTRAINT_TO_RULES_RATIO: " + CONSTRAINT_TO_RULES_RATIO +  "\n" +
				"%MAX_PREDICATE_ARITY: " + MAX_PREDICATE_ARITY + "\n" +
				"%ARITY_PROBABILITY_BASE: " + ARITY_PROBABILITY_BASE);

		this.initialize();
		AspRule rule;

		for (int i = 0; i < size; i++) {
			double type = rand.nextDouble();
			if (type < FACTS_TO_RULES_RATIO) {
				rule = generateFact();
			}
			else if (rand.nextDouble() < CONSTRAINT_TO_RULES_RATIO) {
				rule = generateConstraint();
			}
			else {
				rule = generateRule();
			}
			System.out.println(rule);
		}
	}

	private void initialize() {
		for (int i = 0; i <= VARIABLE_AMOUNT; i++) {
			variables.add(new Variable(i));
		}
		for (int i = 0; i <= CONSTANT_AMOUNT; i++) {
			constants.add(new Constant(i));
		}
		for (int i = 0; i <= FUNCTOR_AMOUNT; i++) {
			functors.add(new Functor(i));
		}
		for (int i = 0; i <= PREDICATE_AMOUNT; i++) {
			int x = rand.nextInt((int) Math.pow(ARITY_PROBABILITY_BASE, MAX_PREDICATE_ARITY));
			for (int j = 0; j <= MAX_PREDICATE_ARITY; j++) {
				if (x <= (Math.pow(ARITY_PROBABILITY_BASE, j))) {
					predicates.add(new Predicate(i, j));
					break;
				}
			}
		}
	}

	private AspRule generateRule() {
		Atom head = generateAtom();
		Body body = generateBody(head);
		return new AspRule(head,body, AspRule.RuleType.RULE);
	}

	private AspRule generateFact() {
		Predicate predicate = predicates.get(rand.nextInt(predicates.size()));

		List<Term> terms = new LinkedList<>();
		for (int i = 0; i < predicate.getArity(); i++) {
			terms.add(generateConstantTerm());
		}
		return new AspRule(new Atom(predicate,terms),null, AspRule.RuleType.FACT);
	}

	private AspRule generateConstraint() {
		Body body = generateBody(null);
		return new AspRule(null, body, AspRule.RuleType.CONSTRAINT);
	}

	private Atom generateAtom() {
		Predicate predicate = predicates.get(rand.nextInt(predicates.size()));

		List<Term> terms = new LinkedList<>();
		for (int i = 0; i < predicate.getArity(); i++) {
			terms.add(generateTerm());
		}

		return new Atom(predicate,terms);
	}

	/*
	private Atom generateAtom(List<Variable> neededVariables) {
		Predicate predicate = predicates.get(rand.nextInt(predicates.size()));

		while (predicate.getArity() < neededVariables.size()) {
			predicate = predicates.get(rand.nextInt(predicates.size()));
		}

		List<Term> terms = new LinkedList<>();
		for (int i = 0; i < predicate.getArity(); i++) {
			if(i < neededVariables.size()) {
				terms.add(generateTerm(neededVariables.get(i)));
			}
			else {
				terms.add(generateTerm());
			}
		}

		return new Atom(predicate,terms);
	} */

	private Body generateBody(Atom head) {
		List<Atom> negBody = new LinkedList<>();
		Set<Variable> neededVariables;
		if (head == null) {
			neededVariables = new LinkedHashSet<>();
		}
		else {
			neededVariables = new LinkedHashSet<>(head.getOccurringVariables());
		}
		Atom bodyAtom;

		if (rand.nextDouble() < RULES_WITH_POSSIBLE_NEGATIVE_BODY_RATIO) {
			int negBodyLength = rand.nextInt(2) + 1;
			for(int i = 0; i < negBodyLength; i++) {
				bodyAtom = generateAtom();
				negBody.add(bodyAtom);
				neededVariables.addAll(bodyAtom.getOccurringVariables());
			}
		}

		List<Atom> posBody;
		do {
			posBody = new LinkedList<>();
			int posBodyLength = rand.nextInt(4) + 1;
			for(int i = 0; i < posBodyLength; i++) {
				bodyAtom = generateAtom();
				posBody.add(bodyAtom);
			}
		} while (isUnsafe(neededVariables,posBody));

		return new Body(posBody, negBody);
	}

	private boolean isUnsafe(Set<Variable> neededVariables, List<Atom> posBody) {
		Set<Variable> occurringVariables = new LinkedHashSet<>();
		for (Atom atom: posBody) {
			occurringVariables.addAll(atom.getOccurringVariables());
		}
		return (!occurringVariables.containsAll(neededVariables));
	}

	private Term generateTerm() {
		switch (rand.nextInt(2 + (INCLUDE_FUNCTIONS ? 1 : 0))) {
			case 0:
				return new Term(variables.get(rand.nextInt(variables.size())));
			case 1:
				return new Term(constants.get(rand.nextInt(constants.size())));
			case 2:
				if (rand.nextBoolean()) {
					return new Term(functors.get(rand.nextInt(functors.size())),
							variables.get(rand.nextInt(variables.size())));
				}
				else {
					return new Term(functors.get(rand.nextInt(functors.size())),
							constants.get(rand.nextInt(constants.size())));
				}
		}
		return null;
	}

	public Term generateConstantTerm() {
		return switch (rand.nextInt(1 + (INCLUDE_FUNCTIONS ? 1 : 0))) {
			case 0 -> new Term(constants.get(rand.nextInt(constants.size())));
			case 1 -> new Term(functors.get(rand.nextInt(functors.size())),
					constants.get(rand.nextInt(constants.size())));
			default -> null;
		};
	}
	/*
	public Term generateTerm(Variable neededVariable) {
		return switch (rand.nextInt(1 + (INCLUDE_FUNCTIONS ? 1 : 0))) {
			case 0 -> new Term(neededVariable);
			case 1 -> new Term(functors.get(rand.nextInt(functors.size())),
					neededVariable);
			default -> null;
		};
	} */
}
