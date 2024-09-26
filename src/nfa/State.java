package nfa;

import java.util.HashSet;
import java.util.Set;

public class State {
    private int id;
    private Set<Character> transitions;
    private boolean isAccepting;

    public State(int id, boolean isAccepting) {
        this.id = id;
        this.transitions = new HashSet<>();
        this.isAccepting = false;
    }

    public void addTransition(char symbol) {
        transitions.add(symbol);
    }

    public void setAccepting(boolean accepting) {
        this.isAccepting = accepting;
    }

    public int getId() {
        return id;
    }

    public Set<Character> getTransitions() {
        return transitions;
    }

    public boolean isAccepting() {
        return isAccepting;
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", transitions=" + transitions +
                ", isAccepting=" + isAccepting +
                '}';
    }
}
