package com.amazonaws.tools.sns.subscriptioncleaner;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LoggerTest {

    private final LambdaLogger lambdaLogger = mock(LambdaLogger.class);

    private final Logger logger = new Logger(lambdaLogger);

    @Test
    public void shouldLog(){
        logger.log("foo {0}", "bar");

        verify(lambdaLogger).log("foo bar");
    }

}
