package method;

import method.imethod.IsNFAEmptyMethod;
import nfa.LoadedNFAS;
import nfa.NFA;
import nfa.State;

import java.util.HashSet;
import java.util.Set;

public class IsNFAEmpty implements IsNFAEmptyMethod {
    private int id;
    private NFA nfa;
    public IsNFAEmpty(int id) {
        this.id = id;
    }

    @Override
    public String isNFAEmpty() {
        Set<State> visitedStates = new HashSet<>();

            for(NFA nfa: LoadedNFAS.getInstance().getNFAS()) {
                if(nfa.getId()==id){
                    this.nfa = nfa;
                    if(explore(nfa.getStartState(), visitedStates)){
                        return "NFA is empty";
                    }
                    else {
                        return "NFA is not empty";
                    }
                }
            }
            return "Id Not Found";

    }
    private boolean explore(State currentState, Set<State> visitedStates) {
        if (visitedStates.contains(currentState)) {
            return false;
        }

        visitedStates.add(currentState);

        if (nfa.getAcceptingStates().contains(currentState)) {
            return false;
        }

        for (Character symbol : nfa.getAlphabet()) {
            for (State nextState : nfa.getStates()) {
                if (currentState.getTransitions().contains(symbol)) {
                    if (!explore(nextState, visitedStates)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
