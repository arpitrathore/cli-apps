package org.arpitrathore;

import static org.arpitrathore.constant.TodoConstants.JSON_FILE_PATH;
import static org.arpitrathore.constant.TodoConstants.WELCOME_MESSAGE;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;
import org.arpitrathore.service.TodoService;

@QuarkusMain
public class CliApp implements QuarkusApplication {

  @Inject
  private TodoService todoService;

  @Override
  public int run(String... args) {
    //System.out.println("Received args: " + Arrays.toString(args));
    if (args.length < 1) {
      printWelcomeMessage();
      return 0;
    }

    var subCommand = args[0];

    try {
      switch (subCommand) {
        case "ls", "list" -> todoService.listTodos(args);
        case "add" -> todoService.addTodo(args);
        case "rm" -> todoService.removeTodo(args[1]);
        case "done" -> todoService.markDone(args[1]);
        case "undone" -> todoService.markTodo(args[1]);
        case "raw" -> todoService.printRawJson();
        case "clear" -> todoService.clearData();
        case "datadir" -> System.out.println(JSON_FILE_PATH);
        default -> printWelcomeMessage();
      }
      return 0;
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }
  }

  private static void printWelcomeMessage() {
    System.out.println(WELCOME_MESSAGE);
  }
}