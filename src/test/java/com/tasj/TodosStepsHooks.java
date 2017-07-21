package com.tasj;

import cucumber.api.java.After;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;


public class TodosStepsHooks {

    @After("@clean")
    public void clearData(){
        executeJavaScript("localStorage.clear()");
        open("http://todomvc4tasj.herokuapp.com/");
    }
}
