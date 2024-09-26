package method;

import method.imethod.PrintNFASMethod;
import nfa.LoadedNFAS;
import nfa.NFA;

public class PrintNFA implements PrintNFASMethod {
    private int id;
    public PrintNFA(int id) {
        this.id = id;
    }

    @Override
    public String printNFAS() {
        String found = "Not Found:";
        StringBuilder str = new StringBuilder();
        for (NFA nfa : LoadedNFAS.getInstance().getNFAS()) {
            if(nfa.getId() == this.id) {
                found = "Found";
                str.append(nfaToString(nfa)).append("\n");
                break;
            }
        }
        str.append(found);
        return str.toString();
    }

    private String nfaToString(NFA nfa) {
        StringBuilder sb = new StringBuilder();
        sb.append("NFA ID: ").append(nfa.getId()).append("\n");
        sb.append("States: ").append(nfa.getStates()).append("\n");
        sb.append("Start State: ").append(nfa.getStartState()).append("\n");
        sb.append("Accepting States: ").append(nfa.getAcceptingStates()).append("\n");
        sb.append("Alphabet: ");
        for (Character c : nfa.getAlphabet()) {
            sb.append(c).append(",");
        }
        // Remove the trailing comma
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("\n");
        return sb.toString();
    }
}
