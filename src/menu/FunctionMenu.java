package menu;

import method.*;
import method.imethod.*;

import java.util.Scanner;

public class FunctionMenu {
    Scanner scanner;

    public FunctionMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void open(){
        boolean closed=false;
        do {
            System.out.println("Function Menu:");
            String input = scanner.nextLine();
            String[] words= input.split(" ");
            switch (words[0]) {
                case "list":
                    if(words.length==1){
                        ListNFAIdMethod listNFAIdMethod =
                                new ListNFAIds();
                        System.out.println(listNFAIdMethod.getNFAIds());
                    }
                    break;
                case "print":
                    if(words.length==2) {
                        PrintNFASMethod printNFASMethod =
                                new PrintNFA(Integer.parseInt(words[1]));
                        System.out.println(printNFASMethod.printNFAS());
                    }
                    break;
                case "empty":
                    if(words.length==2) {
                        IsNFAEmptyMethod emptyMethod = new IsNFAEmpty(Integer.parseInt(words[1]));
                        System.out.println(emptyMethod.isNFAEmpty());
                    }
                    break;
                case "deterministic":
                    if(words.length==2){
                        IsNFADeterministicMethod isNFADeterministicMethod =
                                new IsNFADeterministic(Integer.parseInt(words[1]));
                        System.out.println(isNFADeterministicMethod.isNFADeterministic());
                    }
                    break;
                case "recognize":
                    if(words.length==3){
                        IsWordRecognizedMethod recognizedMethod =
                                new IsWordRecognized(Integer.parseInt(words[1]));
                        System.out.println(recognizedMethod.isWordRecognized(words[2]));
                    }
                    break;
                case "union":
                    if(words.length==3){
                        UnionNFASMethod unionNFASMethod =
                                new UnionNFAS(Integer.parseInt(words[1]), Integer.parseInt(words[2]));
                        System.out.println(unionNFASMethod.union());
                    }
                    break;
                case "concat":
                    if(words.length==3){
                        ConcatNFASMethod concatNFASMethod =
                                new ConcatNFAS();
                        System.out.println(concatNFASMethod.concat(Integer.parseInt(words[1]), Integer.parseInt(words[2])));
                    }
                case "un":
                    if(words.length==2){
                        UnNFAMethod unNFAMethod=new UnNFA();
                        System.out.println(unNFAMethod.un(Integer.parseInt(words[1])));
                    }
                    break;
                case "reg":
                    if(words.length==2){
                        CreateRegexNFAMethod createRegexNFAMethod =
                                new CreateRegexNFA();
                        System.out.println(createRegexNFAMethod.createRegexNFA(""));
                    }
                    break;
                case "close":
                    closed=true;
                    break;
            }

        }while(!closed);
    }
}
