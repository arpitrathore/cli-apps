package org.arpitrathore.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.io.Serializable;

/**
 * @author arathore
 */
@RegisterForReflection
public class Todo implements Serializable, Comparable<Todo> {

  private Long id;

  private String message;

  private TodoStatus status;

  public Todo() {
  }

  public Todo(final String message) {
    this.message = message;
  }

  public Todo(final Long id, final String message, final TodoStatus status) {
    this.id = id;
    this.message = message;
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public TodoStatus getStatus() {
    return status;
  }

  public void setStatus(final TodoStatus status) {
    this.status = status;
  }

  @Override
  public int compareTo(final Todo other) {
    return Long.compare(id, other.id);
  }

  @Override
  public String toString() {
    return "Todo{" + "id=" + id + ", message='" + message + '\'' + ", status=" + status + '}';
  }
}
