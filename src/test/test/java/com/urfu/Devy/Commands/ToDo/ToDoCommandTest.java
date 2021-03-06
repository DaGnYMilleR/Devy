package test.java.com.urfu.Devy.Commands.ToDo;

import main.java.com.urfu.Devy.todo.ToDo;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.command.CommandException;
import test.java.com.urfu.Devy.Commands.CommandTester;

public abstract class ToDoCommandTest extends CommandTester {

    protected void addToDo(GroupInfo group, ToDo todo) {
        try {
            group.addToDo(todo);
        } catch (CommandException e) {
            throw new AssertionError(e.getMessage());
        }
    }
}
