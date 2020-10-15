package main.java.com.urfu.Devy.bot;
import main.java.com.urfu.Devy.ToDo.ToDo;
import main.java.com.urfu.Devy.ToDo.ToDoTask;
import main.java.com.urfu.Devy.command.CommandData;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.CommandsController;
import main.java.com.urfu.Devy.command.parser.ParseCommandException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GroupInfo {
    protected final String id;
    protected final HashMap<String, MessageSender> senders;
    protected final Map<String, ToDo> toDoLists;

    public GroupInfo(String id) {
        this.id = id;
        senders = new HashMap<>();
        toDoLists = new HashMap<>();
    }

    public void addSender(MessageSender sender) throws CommandException {
        var senderId = sender.getId();
        if (senders.containsKey(senderId))
            throw new CommandException("The channel had already been added.");
        senders.put(sender.getId(), sender);
    }

    public void removeSender(MessageSender sender) throws CommandException {
        var senderId = sender.getId();
        if (!senders.containsKey(senderId))
            throw new CommandException("The channel was not found.");
        senders.remove(senderId);
    }

    public void addToDo(ToDo toDo) throws CommandException {
        if (toDoLists.containsKey(toDo.getId()))
            throw new CommandException("The ToDo list was already added.");
        toDoLists.put(toDo.getId(), toDo);
    }

    public Boolean removeToDo(ToDo toDo) throws CommandException {
        return removeToDo(toDo.getId());
    }

    public Boolean removeToDo(String toDoId) throws CommandException {
        if (!toDoLists.containsKey(toDoId))
            throw new CommandException("The ToDo list %s was not founded.".formatted(toDoId));
        return toDoLists.remove(toDoId) != null;
    }

    public ToDo getToDo(String toDoId) throws CommandException {
        if (!toDoLists.containsKey(toDoId))
            throw new CommandException("The ToDo list \"%s\" was not founded.".formatted(toDoId));
        return toDoLists.get(toDoId);
    }

    public Collection<ToDo> getAllToDo() { return toDoLists.values(); }
    public String getId() {
        return id;
    }

    public void execute(CommandData data, String senderId) throws CommandException, ParseCommandException {
        CommandsController
                .createCommand(this, senders.get(senderId), data)
                .execute();
    }

    public void sendMessage(String message, String senderId) {
        if (!senders.containsKey(senderId))
            throw new IllegalArgumentException("The channel was not founded.");
        senders.get(senderId).send(message);
    }

    public ArrayList<ToDoTask> getAllUserTasks(String target){
        var result = new ArrayList<ToDoTask>();
        for(var todo : toDoLists.values())
            for(var task : todo.getTasks().values())
                if(task.getExecutor().equals(target))
                    result.add(task);
        if(result.size() == 0)
            throw new IllegalArgumentException("No user with this name: \"%s\"".formatted(target));
        return result;
    }
}