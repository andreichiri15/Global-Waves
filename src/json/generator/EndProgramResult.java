package json.generator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import library.user.helper.RevenueStats;

import java.util.HashMap;

public class EndProgramResult extends Result {
    private String command;
    private HashMap<String, RevenueStats> result;

    public EndProgramResult(final HashMap<String, RevenueStats> results) {
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
    public void setCommand(final String command) {
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

    /**
     *
     * @return
     */
    @JsonIgnore
    public int getTimestamp() {
        return timestamp;
    }
}
