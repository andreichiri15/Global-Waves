package json.generator;

import commands.InputCommands;

import java.util.ArrayList;
import java.util.Map;

public class EndProgramResult extends Result {
    private String command;
    private ArrayList<Map<String, Map<String, Object>>> results;

    public EndProgramResult(InputCommands inputCommands, ArrayList<Map<String, Map<String, Object>>> results) {
        this.command = command;
        this.results = results;
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public void setCommand(String command) {
        this.command = command;
    }

    public ArrayList<Map<String, Map<String, Object>>> getResults() {
        return results;
    }

    public void setResults(ArrayList<Map<String, Map<String, Object>>> results) {
        this.results = results;
    }
}
