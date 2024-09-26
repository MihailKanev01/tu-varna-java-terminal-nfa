package method;

import method.imethod.IsWordRecognizedMethod;
import nfa.LoadedNFAS;
import nfa.NFA;
import nfa.State;

import java.util.HashSet;
import java.util.Set;

public class IsWordRecognized implements IsWordRecognizedMethod {
    private int id;

    public IsWordRecognized(int id) {
        this.id = id;
    }

    @Override
    public String isWordRecognized(String word) {
        NFA nfa = findNFA(id);
        if(nfa==null)
            return "NFA Is Not Found";
        Set<State> currentStates = new HashSet<>();
        currentStates.add(nfa.getStartState());

        for (char symbol : word.toCharArray()) {
            Set<State> nextStates = new HashSet<>();
            for (State state : currentStates) {
                for (State nextState : nfa.getStates()) {
                    if (state.getTransitions().contains(symbol)) {
                        nextStates.add(nextState);
                    }
                }
            }
            currentStates = nextStates;
        }

        for (State state : currentStates) {
            if (nfa.getAcceptingStates().contains(state)) {
                return "NFA with id <" + nfa.getId() + "> recognizes the word <" + word + ">";
            }
        }

        return "NFA with id <" + nfa.getId() + "> does not recognize the word <" + word + ">";
    }
    private NFA findNFA(int id){
        for(NFA nfa: LoadedNFAS.getInstance().getNFAS()){
            if(nfa.getId() == id){
                return nfa;
            }
        }
        return null;
    }
}
