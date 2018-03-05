# JUnit quickstart

See https://github.com/junit-team/junit4/wiki/getting-started.

Compile the `Calculator` class:

```
$ javac Calculator.java
```

Compile the `CalculatorTest.java` class:

```
$ javac -cp .:junit-4.12.jar:hamcrest-1.3.jar CalculatorTest.java
```

Run:

```
$ java -cp .:junit-4.12.jar:hamcrest-1.3.jar org.junit.runner.JUnitCore CalculatorTest
```
