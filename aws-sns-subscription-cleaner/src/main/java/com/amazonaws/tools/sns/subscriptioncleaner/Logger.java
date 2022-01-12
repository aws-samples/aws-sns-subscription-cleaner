package com.amazonaws.tools.sns.subscriptioncleaner;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

import static java.text.MessageFormat.format;

public class Logger {

    private final LambdaLogger logger;

    public Logger(LambdaLogger logger) {
        this.logger = logger;
    }

    public void log(String message, Object ... args){
        logger.log(format(message, args));
        logger.log(System.lineSeparator());
    }
}
