    package nfa;

    import java.util.Set;

    public class NFA {
        public static int idCounter=1;
        private int id;
        private Set<State> states;
        private State startState;
        private Set<State> acceptingStates;
        private Character[] alphabet;

        public NFA(int id, Set<State> states, State startState, Set<State> acceptingStates, Character[] alphabet) {
            this.id = id;
            this.states = states;
            this.startState = startState;
            this.acceptingStates = acceptingStates;
            this.alphabet = alphabet;
            idCounter++;
        }

        public NFA(Character[] alphabet, Set<State> acceptingStates, State startState, Set<State> states) {
            this.id=idCounter++;
            this.alphabet = alphabet;
            this.acceptingStates = acceptingStates;
            this.startState = startState;
            this.states = states;
        }

        public int getId() {
            return id;
        }

        public Set<State> getStates() {
            return states;
        }

        public State getStartState() {
            return startState;
        }

        public Set<State> getAcceptingStates() {
            return acceptingStates;
        }

        public Character[] getAlphabet() {
            return alphabet;
        }
    }
