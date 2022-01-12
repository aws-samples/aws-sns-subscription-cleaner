package com.amazonaws.tools.sns.subscriptioncleaner;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EnvironmentTest {

    private final Environment environment = new Environment();

    @Test
    public void shouldReturnValue(){
        assertThat(environment.get("DRY_RUN"), is((String)null));
    }
}
