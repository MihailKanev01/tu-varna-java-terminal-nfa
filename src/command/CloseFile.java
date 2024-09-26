package command;

import command.icommand.CloseFileCommand;
import nfa.LoadedNFAS;

import java.util.HashSet;

public class CloseFile implements CloseFileCommand {

    @Override
    public void close() {
        LoadedNFAS.getInstance().setNFAS(new HashSet<>());
        LoadedNFAS.getInstance().setFileName(null);
    }
}
