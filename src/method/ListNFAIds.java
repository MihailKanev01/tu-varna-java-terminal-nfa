package method;

import method.imethod.ListNFAIdMethod;
import nfa.LoadedNFAS;
import nfa.NFA;

public class ListNFAIds implements ListNFAIdMethod {


    @Override
    public String getNFAIds() {
        StringBuilder sb = new StringBuilder("NFA Ids:");
        for(NFA nfa: LoadedNFAS.getInstance().getNFAS()){
            sb.append(nfa.getId()).append(" ");
        }
        return sb.toString();
    }
}
