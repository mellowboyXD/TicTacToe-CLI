@echo off
javac -d bin .\src\App.java
jar cfm game.jar manifest.mf -C bin .
java -jar game.jar
pause