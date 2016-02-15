package com.nest.protect;

public class App {

    public static void main(String[] args) throws Exception {
        new ProtectEmailNotifier().run();
        System.in.read();
    }

}
