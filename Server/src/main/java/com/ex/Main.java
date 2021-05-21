package com.ex;

public class Main {
    /**
     * The main method is entry point for the application.
     * @param args string array of command line arguments
     */
    public static void main(String[] args)
    {
        AbstractApplication app = new ERSApplication();
        app.run();
    }
}
