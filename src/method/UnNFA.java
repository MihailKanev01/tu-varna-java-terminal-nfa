package method;

import method.imethod.UnNFAMethod;
import nfa.LoadedNFAS;
import nfa.NFA;
import nfa.State;

import java.util.HashSet;
import java.util.Set;

public class UnNFA implements UnNFAMethod {
    @Override
    public String un(int id) {
        NFA nfa = findNFA(id);
        if (nfa == null) {
            return "Id doesnt exist";
        }

        NFA unNFA = createUnNFA(nfa);
        LoadedNFAS.getInstance().addNFA(unNFA);

        return "Id Of NFA:"+unNFA.getId();
    }

    private NFA findNFA(int id) {
        for (NFA nfa : LoadedNFAS.getInstance().getNFAS()) {
            if (nfa.getId() == id) {
                return nfa;
            }
        }
        return null;
    }

    private NFA createUnNFA(NFA originalNFA) {
        Set<State> states = new HashSet<>(originalNFA.getStates());
        Set<State> acceptingStates = new HashSet<>(originalNFA.getAcceptingStates());

        State newStartState = new State(getNextStateId(), false);
        states.add(newStartState);

        newStartState.addTransition('\0');
        originalNFA.getStartState().addTransition('\0');

        for (State acceptingState : originalNFA.getAcceptingStates()) {
            acceptingState.addTransition('\0');
            originalNFA.getStartState().addTransition('\0');
        }


        State startState = newStartState;

        Character[] alphabet = originalNFA.getAlphabet();

        return new NFA(alphabet, acceptingStates, startState, states);
    }

    private int getNextStateId() {
        return NFA.idCounter;
    }
}
