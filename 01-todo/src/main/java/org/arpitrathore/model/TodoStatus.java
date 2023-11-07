package org.arpitrathore.model;

/**
 * @author arathore
 */
public enum TodoStatus {

  TODO("\uD83D\uDD34"), DONE("\uD83D\uDFE2");

  public final String emoji;

  TodoStatus(String emoji) {
    this.emoji = emoji;
  }
}
