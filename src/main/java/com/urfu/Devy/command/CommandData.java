package main.java.com.urfu.Devy.command;

public class CommandData {
    private final String name;
    private final String[] args;

    public CommandData(String name, String[] args) {
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public String[] getArgs() {
        return args;
    }
    
}
