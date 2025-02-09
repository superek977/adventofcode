package org.example.util;

import javax.print.attribute.standard.DateTimeAtCreation;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Logger {
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";
    public void info(Object message) {
        System.out.println("INFO: " +LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " " + message.toString());
    }
    public void error(Object message) {
        System.out.println(RED + "ERROR: " +LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " " + message.toString());
    }
    public void warning(Object message) {
        System.out.println(YELLOW + "WARNING: " +LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " " + message.toString());
    }
}
