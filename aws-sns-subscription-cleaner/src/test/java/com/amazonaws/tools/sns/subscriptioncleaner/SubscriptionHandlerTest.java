package com.amazonaws.tools.sns.subscriptioncleaner;

import com.amazonaws.services.lambda.runtime.Context;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class SubscriptionHandlerTest {

    private final Context context = mock(Context.class);

    private final SubscriptionService subscriptionService = mock(SubscriptionService.class);

    private final SubscriptionServiceBuilder subscriptionServiceBuilder = mock(SubscriptionServiceBuilder.class);

    private final SubscriptionHandler subscriptionHandler = new SubscriptionHandler(subscriptionServiceBuilder);

    @Test
    public void shouldCleanSubscriptions(){
        when(subscriptionServiceBuilder.build(context)).thenReturn(subscriptionService);

        subscriptionHandler.handleRequest(null, context);

        verify(subscriptionService).clean();
    }
}
