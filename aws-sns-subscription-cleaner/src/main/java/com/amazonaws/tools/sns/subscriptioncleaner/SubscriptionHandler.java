package com.amazonaws.tools.sns.subscriptioncleaner;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;

public class SubscriptionHandler implements RequestHandler<ScheduledEvent, Void> {

    private final SubscriptionServiceBuilder subscriptionServiceBuilder;

    public SubscriptionHandler() {
        this.subscriptionServiceBuilder = new SubscriptionServiceBuilder(new AppPropBuilder(new Environment()));
    }

    public SubscriptionHandler(SubscriptionServiceBuilder subscriptionServiceBuilder) {
        this.subscriptionServiceBuilder = subscriptionServiceBuilder;
    }

    public Void handleRequest(final ScheduledEvent input, final Context context) {
        SubscriptionService subscriptionService = subscriptionServiceBuilder.build(context);

        subscriptionService.clean();

        return null;
    }
}
