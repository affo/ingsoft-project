# Example projects for Software Engineering course (A.A. 2017/18)

The repository contains projects examples for the SE course.

 + ```beat``` contains an MVC example and JavaFX examples

Other folders are listed in lessons.

## Lesson 1 - Testing

The purpose of this lecture is to understand _the basics of software testing in
practice_. I provide a simple example of using JUnit outside of a Maven project
(credits to [JUnit
quickstart](https://github.com/junit-team/junit4/wiki/getting-started)). If you
explore the `chat` project, you can see an example of testing the simple model
of a chat system (with groups). I use [Mockito](http://site.mockito.org/) for
mocking object in case we have dependencies among them.

The source folders for this lesson are:

 + `junit-quickstart` (see README inside);
 + `chat` project.

## Lesson 2 - Socket programming

The source folder is `socket`. See the [README](socket/README.md) inside.

## Lesson 3 - Java RMI

The source folder is `rmi`. See the [README](rmi/README.md) inside.

## Lesson 4 - Graphical User Interfaces

The source folders are `rmitter-view` and `fx-example`.

We will proceed by introducing the Java Swing library by implementing the GUI
for RMItter (our favorite Twitter implementation).

The code for the Swing implementation is
[here](rmitter-view/src/main/java/gui/SwingGui.java). The project specifies
`deib.ingsoft18.rmitter` as dependency, so remember to:

```
$ cd rmi/rmitter
$ mvn install
```

Before compiling `rmitter-view`.

The graphical interface is far from being complete: it only displays every
notification and allows a user to post. The interface does not allow to login,
however, it allows to "connect" to someones account by providing its username
and secret token. The secret token can be obtained by launching a RMItter client
in its text-only version (as we did in the previous lesson).

As second example, we will introduce the JavaFX framework by implementing the
`fx-example` project. The interface simply consists in one frame in which the
user can switch between two different screens and can modify the state of the
application (represented by some integers). The screens offer the possibility to
take a snapshot of the current state. When a snapshot is taken, it is
"persisted" in a new frame.

__NOTE__ that the `rmitter-view` contains other implementations for RMItter's
GUI:

 + `FXGui`: uses the programmatic APIs of JavaFX;
 + `FXGuiCSS`: shows how to remove style from implementation and put it into a separate file;
 + `FXMLGui`: uses FXML to re-implement the same interface.
