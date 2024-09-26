package menu;

import command.*;
import command.icommand.CloseFileCommand;
import command.icommand.OpenFileCommand;
import nfa.LoadedNFAS;

import java.util.Scanner;

public class FileMenu {
    Scanner scanner;

    public FileMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void open(){
        boolean closed=false;
        do {
            System.out.println("You are in file menu:");
            String input = scanner.nextLine();
            String[] words=input.split(" ");
            switch (words[0]){
                case "open":
                    if(words.length==2) {
                        OpenFileCommand openFileCommand = new OpenFile();
                        openFileCommand.openFile(words[1]);
                        System.out.println("File opened!");
                        FunctionMenu functionMenu = new FunctionMenu(scanner);
                        functionMenu.open();
                    }
                    break;
                case "func":
                    if(words.length==1) {
                        FunctionMenu functionMenu = new FunctionMenu(scanner);
                        functionMenu.open();
                    }
                    break;
                case "close":
                    if(words.length==1) {
                        CloseFileCommand closeFileCommand = new CloseFile();
                        closeFileCommand.close();
                        System.out.println("File closed!");
                    }
                    break;
                case "save":
                    if(words.length==1&& LoadedNFAS.getInstance().getFileName() != null){
                        System.out.println("Debug1");
                        SaveCommand saveCommand = new SaveCommand();
                        System.out.println(saveCommand.save());
                    }

                    break;
                case "saveas":
                    if(words.length==2&& LoadedNFAS.getInstance().getFileName() != null){
                        SaveAsCommand save = new SaveAsCommand();
                        System.out.println(save.saveAs(words[1]));
                    }
                    break;
                case "help":
                    HelpCommand helpCommand = new HelpCommand();
                    System.out.println(helpCommand.help());
                    break;
                case "exit":
                    scanner.close();
                    closed=true;
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        }while (!closed);
    }

}
