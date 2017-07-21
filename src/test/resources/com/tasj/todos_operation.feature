@clean
Feature: Todos OperationsAt Active Tab

  Background:
    Given open TodoMVC page at active

    Scenario: edit task
      Given add task: A
      When edit task 'A' to have text 'A_edited'
      Then tasks: A_edited
      
     Scenario: delete task
       Given add task: A, B
       When delete task 'A'
       Then tasks: B

     Scenario:  complete task
       Given add task: A
       When toggle task 'A'
       Then no visible tasks

     Scenario: clear completed
       Given completed tasks: A, B
       When clear completed
       Then no visible tasks

     Scenario: reopen all tasks
       Given completed tasks: A, B
       When toggle all
       Then tasks: A, B

     Scenario: cancel edit
       Given add task: A, B
       When start and cancel edit B to B_edited and canceled
       Then visible tasks: A, B

     Scenario: confirm edit by press tab
       Given add task: A
       When confirm edit A to A_edited by press tab
       Then visible tasks: A_edited

     Scenario: confirm edit by click
       Given add task: A, B
       When confirm edit A to A_edited by click
       Then visible tasks: A_edited, B

     Scenario: delete task by emptying
       Given add task: A
       When delete task 'A' by emptying
       Then no tasks
