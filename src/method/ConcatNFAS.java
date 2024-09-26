package method;

import method.imethod.ConcatNFASMethod;
import nfa.LoadedNFAS;
import nfa.NFA;
import nfa.State;

import java.util.HashSet;
import java.util.Set;

public class ConcatNFAS implements ConcatNFASMethod {
    @Override
    public String concat(int id1, int id2) {
        NFA nfa1 = findNFA(id1);
        NFA nfa2 = findNFA(id2);
        if (nfa1 == null || nfa2 == null) {
            return "One of the ids is not found";
        }
        NFA createdNFA=createConcatNFA(nfa1, nfa2);
        LoadedNFAS.getInstance().addNFA(createdNFA);
        return "The Id of the NFA is:"+createdNFA.getId();
    }

    private NFA findNFA(int id) {
        for (NFA nfa : LoadedNFAS.getInstance().getNFAS()) {
            if (nfa.getId() == id) {
                return nfa;
            }
        }
        return null;
    }

    private NFA createConcatNFA(NFA nfa1, NFA nfa2) {
        Set<State> states = new HashSet<>();
        Set<State> acceptingStates = new HashSet<>(nfa2.getAcceptingStates());

        states.addAll(nfa1.getStates());
        states.addAll(nfa2.getStates());

        for (State acceptingState : nfa1.getAcceptingStates()) {
            acceptingState.addTransition('\0');
            nfa2.getStartState().addTransition('\0');
        }

        State startState = nfa1.getStartState();

        Set<Character> concatAlphabetSet = new HashSet<>();
        for (Character c : nfa1.getAlphabet()) {
            concatAlphabetSet.add(c);
        }
        for (Character c : nfa2.getAlphabet()) {
            concatAlphabetSet.add(c);
        }
        Character[] concatAlphabet = concatAlphabetSet.toArray(new Character[0]);

        return new NFA(concatAlphabet, acceptingStates, startState, states);
    }
}
