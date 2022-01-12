package com.amazonaws.tools.sns.subscriptioncleaner;

import static java.lang.System.getenv;

public class Environment {

    public String get(String name){
        return getenv(name);
    }
}
