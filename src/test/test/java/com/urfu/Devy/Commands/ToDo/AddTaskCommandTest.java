package test.java.com.urfu.Devy.Commands.ToDo;

import main.java.com.urfu.Devy.todo.ToDo;
import main.java.com.urfu.Devy.todo.ToDoTask;
import main.java.com.urfu.Devy.group.GroupInfo;
import main.java.com.urfu.Devy.command.Command;
import main.java.com.urfu.Devy.command.CommandException;
import main.java.com.urfu.Devy.command.commands.todo.AddTaskCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.com.urfu.Devy.sender.EmptySender;
public class AddTaskCommandTest extends ToDoCommandTest {

    @BeforeEach
    public void SetUp() {
        group = new GroupInfo("1");
        addToDo(group, new ToDo("1"));
        sender = new EmptySender();
    }

    @Test
    public void assertWhenNoArguments() {
        assertHandle(new String[0], "Incorrect count of arguments.");
    }

    @Test
    public void handleWhenIncorrectArgumentsAmount() {
        assertHandle(new String[] { "1", "2", "3" }, "Incorrect count of arguments.");
    }

    @Test
    public void handleWhenTaskWithTheSameIdAlreadyExists() {
        try {
            group.getToDo("1").addTask(new ToDoTask("1", "", "", "hello"));
        } catch (CommandException e) {
            throw new AssertionError(e.getMessage());
        }
        assertHandle(new String[] { "1", "1", "", "", "hello" }, "The task has been added.");
    }

    @Test
    public void handleWhenToDoDoesNotExists() {
        assertHandle(new String[] { "2", "1", "", "", "hello"}, "The ToDo list \"2\" was not founded.");
    }

    @Test
    public void throwExceptionWhenWrongCountOfArguments() {
        assertHandle(new String[] { "1" }, "Incorrect count of arguments.");
        assertHandle(new String[] { "1", "2" }, "Incorrect count of arguments.");
    }

    @Test
    public void addTaskWhenDoesNotExist() {
        Assertions.assertDoesNotThrow(() -> {
            createCommandWithArgs(new String[] {"1", "1", "", "", "hello"}).execute();
            Assertions.assertTrue(group.getToDo("1").hasTask("1"));
        });
    }

    @Override
    protected Command createCommandWithArgs(String[] args) {
        return new AddTaskCommand(group, sender, args);
    }
}
