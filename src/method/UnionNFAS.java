package method;

import method.imethod.UnionNFASMethod;
import nfa.LoadedNFAS;
import nfa.NFA;
import nfa.State;

import java.util.HashSet;
import java.util.Set;

public class UnionNFAS implements UnionNFASMethod {
    private int id1;
    private int id2;

    public UnionNFAS(int id1, int id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    @Override
    public String union() {
        NFA nfa1 = findNFA(id1);
        NFA nfa2 = findNFA(id2);
        if (nfa1 == null || nfa2 == null) {
            return "One Of The Ids Is Not Existing";
        }

        NFA unionNFA = createUnionNFA(nfa1, nfa2);
        LoadedNFAS.getInstance().addNFA(unionNFA);

        return "Union NFA created with id <" + unionNFA.getId() + ">";
    }

    private NFA findNFA(int id) {
        for (NFA nfa : LoadedNFAS.getInstance().getNFAS()) {
            if (nfa.getId() == id) {
                return nfa;
            }
        }
        return null;
    }

    private NFA createUnionNFA(NFA nfa1, NFA nfa2) {
        Set<State> states = new HashSet<>();
        Set<State> acceptingStates = new HashSet<>();

        State newStartState = new State(getNextStateId(), false);

        newStartState.addTransition('\0');
        nfa1.getStartState().addTransition('\0');
        nfa2.getStartState().addTransition('\0');

        states.add(newStartState);
        states.addAll(nfa1.getStates());
        states.addAll(nfa2.getStates());

        acceptingStates.addAll(nfa1.getAcceptingStates());
        acceptingStates.addAll(nfa2.getAcceptingStates());

        Set<Character> unionAlphabetSet = new HashSet<>();
        for (Character c : nfa1.getAlphabet()) {
            unionAlphabetSet.add(c);
        }
        for (Character c : nfa2.getAlphabet()) {
            unionAlphabetSet.add(c);
        }
        Character[] unionAlphabet = unionAlphabetSet.toArray(new Character[0]);

        return new NFA(unionAlphabet, acceptingStates, newStartState, states);
    }

    private int getNextStateId() {
        return NFA.idCounter;
    }
}
