package com.amazonaws.tools.sns.subscriptioncleaner;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AppPropBuilderTest {

    private final Environment environment = mock(Environment.class);

    private final AppPropBuilder appPropBuilder = new AppPropBuilder(environment);

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenRegionNotSet(){
        when(environment.get("AWS_REGION")).thenReturn(null);

        appPropBuilder.build();
    }

    @Test
    public void shouldDryRunByDefault(){
        when(environment.get("AWS_REGION")).thenReturn("eu-west-1");
        when(environment.get("DRY_RUN")).thenReturn(null);

        AppProps appProps = appPropBuilder.build();

        assertThat(appProps.isDryRun(), is(true));
    }

    @Test
    public void shouldSetDryRunFalse(){
        when(environment.get("AWS_REGION")).thenReturn("eu-west-1");
        when(environment.get("DRY_RUN")).thenReturn("false");

        AppProps appProps = appPropBuilder.build();

        assertThat(appProps.isDryRun(), is(false));
    }
}
