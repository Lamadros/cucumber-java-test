package com.tasj.helpers;


import com.codeborne.selenide.WebDriverRunner;

import java.util.List;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.*;


public class GivenHelpers {

    private static void given(Filter filter, Task... tasks){
        ensureUrlIsOpened(filter);
        prepareTasks(tasks);
        executeJavaScript("location.reload()");
        ensurePageIsLoaded();
    }

    public static void givenAtActive(Task... tasks){
        given(Filter.ACTIVE, tasks);
    }

    public static void givenAtActive(TaskType tasktype, List<String> tasksTexts){
        given(Filter.ACTIVE,taskArray(tasktype,tasksTexts));
    }

    public static Task[] taskArray(TaskType type, List<String> tasksTexts){
        Task[] tasksArray = new Task[tasksTexts.size()];
        for (int i = 0; i < tasksTexts.size(); i++){
            tasksArray[i] = new Task(tasksTexts.get(i) ,type);
        }
        return tasksArray;
    }

    public static void ensurePageIsLoaded(){
        $("#new-todo").shouldBe(enabled);
    }

    public static void ensureUrlIsOpened(Filter filter){

        if (!(WebDriverRunner.url().equals(filter.url())))
            open(filter.url());

        ensurePageIsLoaded();
    }

    public static void prepareTasks(Task... tasks){
        StringBuilder tasksString = new StringBuilder();

        tasksString.append("localStorage.setItem('todos-troopjs', \"[");
        for (Task task: tasks){
            tasksString.append("{\\\"completed\\\":").append((task.taskType == TaskType.COMPLETED)? "true,": "false,");
            tasksString.append(" \\\"title\\\":\\\""+task.taskText +"\\\"},");
        }
        if(tasks.length != 0)
            tasksString.deleteCharAt(tasksString.lastIndexOf(","));

        tasksString.append("]\")");
        executeJavaScript(tasksString.toString());
    }

    public enum TaskType {
        ACTIVE,
        COMPLETED;
    }

    public enum Filter {
        ALL(""),
        ACTIVE("/#/active"),
        COMPLETED("/#/completed");

        String subUrl;


        Filter(String subUrl){
            this.subUrl = subUrl;
        }

        public String url(){
            return "https://todomvc4tasj.herokuapp.com"+subUrl;
        }
    }

    public static class Task {
        String taskText;
        TaskType taskType;

        public Task(String taskText, TaskType taskType) {
            this.taskText = taskText;
            this.taskType = taskType;
        }
    }

}


