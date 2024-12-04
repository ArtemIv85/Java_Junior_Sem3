package ru.geekbrains.lesson3.task2;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


/**
 * Externalizable - служебный интерфейс который можно использовать вместо Serializable,
 * но  который позволяет врусную управлять процессом сериализации
 */
public class ToDo implements Externalizable {

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

    public ToDo (String title) {
        this.title = title;
        this.isDone = false;
    }

    public ToDo(String title, boolean isDone) {
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

    //region Externalizable implementation
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(title);
        out.writeObject(isDone);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        title = (String) in.readObject();
        isDone = (boolean) in.readBoolean();
    }
    //endregion
}
