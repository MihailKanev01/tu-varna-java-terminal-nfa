package command;

import command.icommand.ISaveCommand;
import nfa.LoadedNFAS;
import nfa.NFA;
import nfa.State;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Set;

public class SaveCommand implements ISaveCommand {

    @Override
    public String save() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Root element
            Element rootElement = doc.createElement("nfas");
            doc.appendChild(rootElement);

            // Iterate over NFAs
            for (NFA nfa : LoadedNFAS.getInstance().getNFAS()) {
                Element nfaElement = doc.createElement("nfa");
                nfaElement.setAttribute("id", String.valueOf(nfa.getId()));
                rootElement.appendChild(nfaElement);

                // States element
                Element statesElement = doc.createElement("states");
                nfaElement.appendChild(statesElement);

                for (State state : nfa.getStates()) {
                    Element stateElement = doc.createElement("state");
                    stateElement.setAttribute("id", String.valueOf(state.getId()));

                    Element isAcceptingElement = doc.createElement("isAccepting");
                    isAcceptingElement.appendChild(doc.createTextNode(String.valueOf(state.isAccepting())));
                    stateElement.appendChild(isAcceptingElement);

                    Element transitionsElement = doc.createElement("transitions");
                    for (Character transition : state.getTransitions()) {
                        if (isValidXmlCharacter(transition)) {
                            Element transitionElement = doc.createElement("transition");
                            transitionElement.appendChild(doc.createTextNode(String.valueOf(transition)));
                            transitionsElement.appendChild(transitionElement);
                        }
                    }
                    stateElement.appendChild(transitionsElement);
                    statesElement.appendChild(stateElement);
                }

                // Start state element
                Element startStateElement = doc.createElement("startState");
                startStateElement.appendChild(doc.createTextNode(String.valueOf(nfa.getStartState().getId())));
                nfaElement.appendChild(startStateElement);

                // Accepting states element
                Element acceptingStatesElement = doc.createElement("acceptingStates");
                StringBuilder acceptingStatesStr = new StringBuilder();
                for (State state : nfa.getAcceptingStates()) {
                    acceptingStatesStr.append(state.getId()).append(",");
                }
                if (acceptingStatesStr.length() > 0) {
                    acceptingStatesStr.setLength(acceptingStatesStr.length() - 1);
                }
                acceptingStatesElement.appendChild(doc.createTextNode(acceptingStatesStr.toString()));
                nfaElement.appendChild(acceptingStatesElement);

                // Alphabet element
                Element alphabetElement = doc.createElement("alphabet");
                StringBuilder alphabetStr = new StringBuilder();
                for (Character character : nfa.getAlphabet()) {
                    alphabetStr.append(character).append(",");
                }
                if (alphabetStr.length() > 0) {
                    alphabetStr.setLength(alphabetStr.length() - 1); // Remove trailing comma
                }
                alphabetElement.appendChild(doc.createTextNode(alphabetStr.toString()));
                nfaElement.appendChild(alphabetElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(LoadedNFAS.getInstance().getFileName()));

            transformer.transform(source, result);

            return "File saved successfully.";

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
            return "Error occurred while saving the file.";
        }
    }

    private boolean isValidXmlCharacter(char ch) {
        // Check if the character is valid XML
        return ch != '\u0000'; // Exclude the null character
    }
}
