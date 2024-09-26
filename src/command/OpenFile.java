package command;

import command.icommand.OpenFileCommand;
import nfa.LoadedNFAS;
import nfa.NFA;
import nfa.State;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class OpenFile implements OpenFileCommand {

    @Override
    public void openFile(String fileName) {
        try {
            File file = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nfaNodes = doc.getElementsByTagName("nfa");

            LoadedNFAS.getInstance().setNFAS(new HashSet<>());
            LoadedNFAS.getInstance().setFileName(fileName);

            for (int i = 0; i < nfaNodes.getLength(); i++) {
                Element nfaElement = (Element) nfaNodes.item(i);

                // Extract NFA attributes
                int id = Integer.parseInt(nfaElement.getAttribute("id"));
                Set<State> states = extractStates(nfaElement.getElementsByTagName("state"));
                State startState = extractStartState(nfaElement.getElementsByTagName("startState").item(0).getTextContent(), states);
                Set<State> acceptingStates = extractAcceptingStates(nfaElement.getElementsByTagName("acceptingStates").item(0).getTextContent(), states);
                String alphabetStr = nfaElement.getElementsByTagName("alphabet").item(0).getTextContent();
                Character[] alphabet = alphabetStr.replaceAll(",", "").chars().mapToObj(c -> (char) c).toArray(Character[]::new);

                // Create NFA object
                NFA nfa = new NFA(id, states, startState, acceptingStates, alphabet);

                // Add NFA to LoadedNFAS
                LoadedNFAS.getInstance().addNFA(nfa);
            }

            // Now you have the NFA objects loaded into LoadedNFAS
            // You can access them as needed
            for (NFA nfa : LoadedNFAS.getInstance().getNFAS()) {
                System.out.println("NFA ID: " + nfa.getId());
                System.out.println("States: " + nfa.getStates());
                System.out.println("Start State: " + nfa.getStartState());
                System.out.println("Accepting States: " + nfa.getAcceptingStates());

                // Concatenate characters into a string
                StringBuilder alphabetBuilder = new StringBuilder();
                for (Character character : nfa.getAlphabet()) {
                    alphabetBuilder.append(character).append(",");
                }
                // Remove the trailing comma
                String alphabetString = alphabetBuilder.toString();
                if (alphabetString.length() > 0) {
                    alphabetString = alphabetString.substring(0, alphabetString.length() - 1);
                }
                System.out.println("Alphabet: " + alphabetString);
                System.out.println();
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    private Set<State> extractStates(NodeList stateNodes) {
        Set<State> states = new HashSet<>();
        for (int i = 0; i < stateNodes.getLength(); i++) {
            Element stateElement = (Element) stateNodes.item(i);
            int stateId = Integer.parseInt(stateElement.getAttribute("id"));
            boolean isAccepting = Boolean.parseBoolean(stateElement.getElementsByTagName("isAccepting").item(0).getTextContent());
            State state = new State(stateId, isAccepting);

            // Extract transitions
            NodeList transitionNodes = stateElement.getElementsByTagName("transition");
            for (int j = 0; j < transitionNodes.getLength(); j++) {
                String transition = transitionNodes.item(j).getTextContent();
                if (transition.length() == 1) {
                    state.addTransition(transition.charAt(0));
                } else {
                    System.err.println("Invalid transition: " + transition + ". Transition should be a single character.");
                }
            }

            states.add(state);
        }
        return states;
    }

    private State extractStartState(String startStateId, Set<State> states) {
        int id = Integer.parseInt(startStateId);
        return states.stream().filter(state -> state.getId() == id).findFirst().orElse(null);
    }

    private Set<State> extractAcceptingStates(String acceptingStatesStr, Set<State> states) {
        Set<State> acceptingStates = new HashSet<>();
        String[] acceptingStateIds = acceptingStatesStr.split(",");
        for (String id : acceptingStateIds) {
            int stateId = Integer.parseInt(id);
            State state = states.stream().filter(s -> s.getId() == stateId).findFirst().orElse(null);
            if (state != null) {
                acceptingStates.add(state);
            }
        }
        return acceptingStates;
    }
}
