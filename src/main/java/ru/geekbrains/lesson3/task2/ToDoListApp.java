package ru.geekbrains.lesson3.task2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ToDoListApp {
    public static final String FILE_JSON = "tasks.json";
    public static final  String FILE_BIN = "taska.bin";
    public static final String FILE_XML = "tasks.xml";

    public static final ObjectMapper objectMapper = new ObjectMapper(); // JSON документ
    private static final XmlMapper xmlMapper = new XmlMapper();  // XML документ


    //region Метод добалвения новой задачи
    public static void addNewTask(Scanner scanner, List<ToDo> tasks){
        System.out.println("Введите название новой задачи");
        String newTaskTitle = scanner.nextLine();
        tasks.add( new ToDo(newTaskTitle,false));
        saveTasksToFile(FILE_JSON, tasks);
        saveTasksToFile(FILE_BIN, tasks);
        saveTasksToFile(FILE_XML, tasks);
    }
    //endregion


    //region  Сохранение Tasks  в файл
    public static void saveTasksToFile(String filename, List<ToDo> tasks){
        try{
            if (filename.endsWith(".json")){
                objectMapper.configure(SerializationFeature.INDENT_OUTPUT,true);
                objectMapper.writeValue(new File(filename),tasks);
            }else if (filename.endsWith(".bin")){
                try (ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream(filename))){
                    oos.writeObject(tasks);
                }
            } else if (filename.endsWith(".xml")){
                //String s = xmlMapper.writeValueAsString(tasks);
                xmlMapper.writeValue(new File(filename),tasks);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //endregion


    //region Метод загрузки данных из файла
    public static List<ToDo> loadTasksFromFile(String fileName){
        List<ToDo>  tasks = new ArrayList<>();
        File file = new File(fileName);
        if (file.exists()){
            try{
                if (fileName.endsWith(".json")){
                    tasks = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, ToDo.class));
                }else if (fileName.endsWith(".bin")){
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
                        tasks = (List<ToDo>) ois.readObject();
                    }

                }else if (fileName.endsWith(".xml")){
                    tasks = xmlMapper.readValue(file, xmlMapper.getTypeFactory().constructCollectionType(List.class, ToDo.class));

                }
            }catch (IOException |ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        return tasks;
    }
    //endregion

    //region Метод пометки задачи как выполнено

    /**
     * Метод пометки задачи как выполненной и сразу записывает изменения в файл
     * @param scanner - просто сканер
     * @param tasks - Колекция задач сласса ToDO
     */
    public static void markTaskAsDone (Scanner scanner, List<ToDo> tasks){
        System.out.println("Введите порядковый номер задачи для отметки как выполненной");
        String input = scanner.nextLine();

        try{
            int taskNumber = Integer.parseInt((input))-1;
            if (taskNumber >= 0 && taskNumber < tasks.size()){
                tasks.get(taskNumber).setDone(true);
                saveTasksToFile(FILE_JSON, tasks);
                saveTasksToFile(FILE_BIN, tasks);
                saveTasksToFile(FILE_XML, tasks);
                System.out.println("Задача отмечена как выполненая.");
            } else {
                System.out.println("Некорректный номер задачи. Попробуйте снова.");
            }
        } catch (NumberFormatException e){
            System.out.println("Некорректный ввод. Попробуйте снова.");
        }
    }

    //endregion

    //region Метод вывода всех задач
    public static void displayTasks (List<ToDo> tasks){
        System.out.println("Список задач");
        for (int i=0 ; i < tasks.size();i++){
            ToDo task = tasks.get(i);
            String status = task.isDone() ? "[x]" : "[ ]";
            System.out.println((i+1) + " - " + status + " "+task.getTitle());
        }
    }

    //endregion
}
