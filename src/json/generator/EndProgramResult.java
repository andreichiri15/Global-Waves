package json.generator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import commands.InputCommands;
import library.user.helper.RevenueStats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EndProgramResult extends Result {
    private String command;
    private HashMap<String, RevenueStats> result;

    public EndProgramResult(HashMap<String, RevenueStats> results) {
        this.command = "endProgram";
        this.result = results;
    }

    /**
     *
     * @return
     */
    @Override
    public String getCommand() {
        return command;
    }

    /**
     *
     * @param command
     */
    @Override
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     *
     * @return
     */
    public HashMap<String, RevenueStats> getResult() {
        return result;
    }

    /**
     *
     * @param results
     */
    public void setResult(final HashMap<String, RevenueStats> results) {
        this.result = results;
    }

    @JsonIgnore
    public int getTimestamp() {
        return timestamp;
    }
}
