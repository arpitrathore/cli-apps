package org.arpitrathore.service;

import static org.arpitrathore.constant.TodoConstants.JSON_FILE_PATH;
import static org.arpitrathore.constant.TodoConstants.SPACE;
import static org.arpitrathore.model.TodoStatus.DONE;
import static org.arpitrathore.model.TodoStatus.TODO;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import org.arpitrathore.model.Todo;
import org.arpitrathore.model.TodoStatus;


/**
 * @author arathore
 */
@ApplicationScoped
public class TodoService {

  private final JsonbConfig config = new JsonbConfig().withFormatting(true);
  private final Jsonb jsonb = JsonbBuilder.create(config);

  public void addTodo(final String[] args) throws IOException {
    ensureFileExists();

    final String message = buildMessage(args);

    final List<Todo> allTodos = getAllTodos();
    final Long id = getNextId(allTodos);
    allTodos.add(new Todo(id, message, TODO));
    writeAllTodos(allTodos);
  }

  private String buildMessage(final String[] args) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 1; i < args.length; i++) {
      builder.append(args[i]).append(SPACE);
    }
    return builder.toString().trim();
  }

  public void listTodos(final String[] args) throws IOException {
    ensureFileExists();
    final TodoStatus statusFilter = getStatusFilter(args);

    final List<Todo> allTodos = getAllTodos();
    if (statusFilter == null) {
      printTodos(allTodos);
    } else {
      printTodos(allTodos.stream().filter(todo -> todo.getStatus() == statusFilter).toList());
    }
  }

  private TodoStatus getStatusFilter(final String[] args) {
    if (args.length > 1) {
      final String filter = args[1];
      switch (filter) {
        case "--todo" -> {
          return TODO;
        }
        case "--done" -> {
          return DONE;
        }
      }
    }
    return null;
  }

  public void markDone(String idStr) throws IOException {
    final Long id = Long.valueOf(idStr);
    ensureFileExists();
    final List<Todo> allTodos = getAllTodos();
    for (var todo : allTodos) {
      if (Objects.equals(todo.getId(), id)) {
        todo.setStatus(DONE);
      }
    }
    writeAllTodos(allTodos);
  }

  public void markTodo(String idStr) throws IOException {
    final Long id = Long.valueOf(idStr);
    ensureFileExists();
    final List<Todo> allTodos = getAllTodos();
    for (var todo : allTodos) {
      if (Objects.equals(todo.getId(), id)) {
        todo.setStatus(TODO);
      }
    }
    writeAllTodos(allTodos);
  }

  public void removeTodo(final String idStr) throws IOException {
    final Long id = Long.valueOf(idStr);
    ensureFileExists();

    final List<Todo> allTodos = getAllTodos();
    allTodos.removeIf(todo -> Objects.equals(todo.getId(), id));
    writeAllTodos(allTodos);
  }

  public void clearData() throws IOException {
    System.out.println("Are you sure you want to clear all TODOs? (y/n)");
    final Scanner sc = new Scanner(System.in);
    final String ans = sc.nextLine();
    if (ans.equalsIgnoreCase("y")) {
      writeAllTodos(List.of());
    }
  }

  private void printTodos(final List<Todo> todos) {
    System.out.println(AsciiTable.getTable(AsciiTable.BASIC_ASCII, todos, Arrays.asList(
        new Column().header("Id").headerAlign(HorizontalAlign.CENTER)
            .dataAlign(HorizontalAlign.CENTER).with(todo -> todo.getId().toString()),
        new Column().header("Todo").dataAlign(HorizontalAlign.LEFT).with(Todo::getMessage),
        new Column().header("Status").headerAlign(HorizontalAlign.CENTER)
            .dataAlign(HorizontalAlign.CENTER).with(todo -> todo.getStatus().emoji))));
  }

  public void writeAllTodos(List<Todo> todos) throws IOException {
    jsonb.toJson(todos, new FileWriter(JSON_FILE_PATH));
  }

  public List<Todo> getAllTodos() throws IOException {
    ensureFileExists();
    try {
      List<Todo> todos = jsonb.fromJson(new FileReader(JSON_FILE_PATH), new ArrayList<Todo>() {
      }.getClass().getGenericSuperclass());
      Collections.sort(todos);
      return todos;
    } catch (Exception e) {
      return new ArrayList<>();
    }
  }

  Long getNextId(List<Todo> todos) {
    if (todos.isEmpty()) {
      return 1L;
    } else {
      return todos.get(todos.size() - 1).getId() + 1L;
    }
  }

  public void printRawJson() throws IOException {
    ensureFileExists();
    System.out.println("File path: " + JSON_FILE_PATH);
    System.out.println("Data:");
    System.out.println(Files.readString(Path.of(JSON_FILE_PATH)));
  }

  private void ensureFileExists() throws IOException {
    Path path = Path.of(JSON_FILE_PATH);
    if (Files.exists(path)) {
      return;
    }

    System.out.println("Data file doesnt exist, creating at: " + JSON_FILE_PATH);
    Files.createDirectories(path.getParent());
    Files.createFile(path);
  }


}
