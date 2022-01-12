package com.amazonaws.tools.sns.subscriptioncleaner;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SubscriptionServiceBuilderTest {

    private final AppPropBuilder appPropBuilder = mock(AppPropBuilder.class);

    private final AppProps appProps = mock(AppProps.class);

    private final Context context = mock(Context.class);

    private final SubscriptionServiceBuilder subscriptionServiceBuilder = new SubscriptionServiceBuilder(appPropBuilder);

    @Test
    public void shouldBuildSubscriptionService(){
        when(appPropBuilder.build()).thenReturn(appProps);
        when(appProps.getRegion()).thenReturn("eu-west-1");

        SubscriptionService subscriptionService = subscriptionServiceBuilder.build(context);

        assertNotNull(subscriptionService);
    }
}
