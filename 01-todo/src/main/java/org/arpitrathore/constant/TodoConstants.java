package org.arpitrathore.constant;

/**
 * @author arathore
 */
public class TodoConstants {

  public static final String JSON_FILE_PATH =
      System.getProperty("user.home") + "/.arp/todo/data.json";

  public static final String WELCOME_MESSAGE = """
      Welcome to TODO App
      Commands:
        ls, list              [--all (default), --todo\uD83D\uDD34, --done\uD83D\uDFE2]
        add <todo>            Add a new task
        done <todo-id>        Mark a task as Done
        undone <todo-id>      Move a completed task back to TODO
        rm <todo-id>          Remove a task by its id
        clear                 Clear All tasks
        datadir               Print the path of data file
        raw                   Print the raw data file
      """;

  public static final String SPACE = " ";
}
