package command;

import command.icommand.IHelpCommand;

public class HelpCommand implements IHelpCommand {
    @Override
    public String help() {
        return "Commands:close, open <file>, save, saveas <file>, exit "
                +"\nMethods:list, print <id> , empty <id>, deterministic <id>, recognize <id> <word>,union <id1> <id2>,concat <id1> <id2>,un <id> ";
    }
}
