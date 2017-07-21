package com.tasj;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.tasj.CustomConditions.exactTexts;
import static com.tasj.helpers.GivenHelpers.TaskType.COMPLETED;
import static com.tasj.helpers.GivenHelpers.givenAtActive;



public class TodoMVCdefs {
    static ElementsCollection tasks = $$("#todo-list>li");

    public static SelenideElement startEdit (String oldTaskText, String newTaskText){
        tasks.find(exactText(oldTaskText)).doubleClick();
        return tasks.find(cssClass("editing")).$(".edit").setValue(newTaskText);
    }

    @Given("^open TodoMVC page at active$")
    public void openTodoMVCPageAtActive() {
        open("https://todomvc4tasj.herokuapp.com/#/active");
    }

    @Given("^add task: (.*)$")
    public void addTask(List<String> taskTexts){
        for(String text: taskTexts){
            $("#new-todo").setValue(text).pressEnter();
        }
    }

    @And("^edit task '(\\w+)' to have text '(\\w+)'$")
    public void editTaskAToA_edited(String oldTaskText, String newTaskText){
        startEdit(oldTaskText, newTaskText).pressEnter();
    }


    @Then("^tasks: (.*)$")
    public void assertTasks(List<String> taskTexts){
        tasks.shouldHave(exactTexts(taskTexts));
    }

    @When("^delete task '(.*)'$")
    public void delete(String taskText){
        tasks.find(exactText(taskText)).hover().$(".destroy").click();
    }

    @When("^toggle task '(.*)'$")
    public void toogle(String taskText){
        tasks.find(exactText(taskText)).$(".toggle").click();
    }

    @Then("^no visible tasks$")
    public void noVisibleTasks(){
        tasks.filterBy(visible).shouldBe(empty);
    }

    @When("^clear completed$")
    public void clearCompleted(){
        $("#clear-completed").click();
    }

    @Given("^completed tasks: (.*)$")
    public void completedTasks(List<String> taskTexts){
        givenAtActive(COMPLETED, taskTexts);
    }

    @When("^toggle all$")
    public void toggleAll(){
        $("#toggle-all").click();
    }

    @When("^start and cancel edit (\\w+) to (.*)$")
    public void startAndCancelEditBToB_editedAndCanceled(String oldTaskText, String newTaskText){
        startEdit(oldTaskText, newTaskText).pressEscape();
    }

    @Then("^visible tasks: (.*)$")
    public void visibleTasks(List<String> taskTexts){
        tasks.filterBy(visible).shouldHave(exactTexts(taskTexts));
    }

    @When("^confirm edit (\\w+) to (\\w+) by press tab$")
    public void confirmEditByPressTab(String oldTaskText, String newTaskText){
        startEdit(oldTaskText, newTaskText).pressTab();
    }

    @When("^confirm edit (\\w+) to (\\w+) by click$")
    public void confirmEditByClick(String oldTaskText, String newTaskText){
        startEdit(oldTaskText, newTaskText);
        $("#new-todo").click();
    }

    @When("^delete task '(\\w+)' by emptying$")
    public void deleteTaskAByEmptying(String TaskText){
        startEdit(TaskText, "").pressEnter();
    }

    @Then("^no tasks$")
    public void noTasks(){
        tasks.shouldBe(empty);
    }
}
