package com.amazonaws.tools.sns.subscriptioncleaner;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

public class AppPropsTest {

    private final AppProps appProps = new AppProps();

    @Test
    public void shouldReturnRegion(){
        AppProps appProps = new AppProps();

        appProps.setRegion("eu-west-1");

        assertThat(appProps.getRegion(), is("eu-west-1"));
    }

    @Test
    public void shouldReturnDryRun(){
        AppProps appProps = new AppProps();

        appProps.setDryRun(false);

        assertThat(appProps.isDryRun(), is(false));
    }

    @Test
    public void shouldReturnDryRunAsDefault(){
        assertTrue(appProps.isDryRun());
    }
}
