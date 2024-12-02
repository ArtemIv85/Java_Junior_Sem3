package ru.geekbrains.lesson3.task2;

import java.io.Serializable;

public class ToDoV1 implements Serializable {

    //region поля
    /**
     * Наименование задачи
     */
    private String title;

    /**
     * Статус задачи
     */
    private boolean isDone;

    //endregion

    //region Конструкторы

    public ToDoV1(String title, boolean isDone) {
        this.title = title;
        this.isDone = isDone;
    }

    //endregion

    //region Методы

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    //endregion

}
