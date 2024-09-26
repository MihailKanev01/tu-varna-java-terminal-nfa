package method;

import method.imethod.IsNFADeterministicMethod;
import nfa.LoadedNFAS;
import nfa.NFA;
import nfa.State;

import java.util.HashMap;
import java.util.Map;

public class IsNFADeterministic implements IsNFADeterministicMethod {
    private int id;

    public IsNFADeterministic(int id) {
        this.id = id;
    }

    @Override
    public String isNFADeterministic() {
        NFA nfa = findNFA(id);
        if(nfa==null)
            return "NFA Is Not Found";

        for (State state : nfa.getStates()) {
            Map<Character, Integer> transitionCount = new HashMap<>();

            for (Character symbol : state.getTransitions()) {
                transitionCount.put(symbol, transitionCount.getOrDefault(symbol, 0) + 1);

                if (transitionCount.get(symbol) > 1) {
                    return "NFA with id <" + nfa.getId() + "> is not deterministic";
                }
            }
        }

        // If all states have at most one transition per symbol, NFA is deterministic
        return "NFA with id <" + nfa.getId() + "> is deterministic";
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
