package nfa;

import java.util.HashSet;
import java.util.Set;

public class LoadedNFAS {
        private static LoadedNFAS instance = null;
        private HashSet<NFA> nfas;
        private String fileName;
        private LoadedNFAS() {
            nfas = new HashSet<>();
        }
        public static LoadedNFAS getInstance() {
            if (instance == null) {
                instance = new LoadedNFAS();
            }
            return instance;
        }
        public void setNFAS(HashSet<NFA> nfas){
            this.nfas = nfas;
        }
        public Set<NFA> getNFAS(){
            return nfas;
        }
        public String getFileName(){
            return fileName;
        }
        public void setFileName(String fileName){
            this.fileName = fileName;
        }
        public void addNFA(NFA nfa){
            nfas.add(nfa);
        }
}
