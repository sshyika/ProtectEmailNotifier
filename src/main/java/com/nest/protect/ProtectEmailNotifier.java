package com.nest.protect;

import com.nest.protect.connector.NestConnector;

public class ProtectEmailNotifier {

    public static void main(String[] args) throws Exception {
        Config config = Config.load("config.properties");
        NestConnector nest = NestConnector.build(config);
        nest.addHomeStateListener(m -> System.out.println(m));
        System.in.read();
    }

}
