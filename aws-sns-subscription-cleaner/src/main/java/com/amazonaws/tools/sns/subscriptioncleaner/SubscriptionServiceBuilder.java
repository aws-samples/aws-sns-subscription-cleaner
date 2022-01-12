package com.amazonaws.tools.sns.subscriptioncleaner;

import com.amazonaws.services.lambda.runtime.Context;
import software.amazon.awssdk.services.sns.SnsClient;

import static software.amazon.awssdk.regions.Region.of;

public class SubscriptionServiceBuilder {

    private final AppPropBuilder appPropBuilder;

    public SubscriptionServiceBuilder(AppPropBuilder appPropBuilder) {
        this.appPropBuilder = appPropBuilder;
    }

    public SubscriptionService build(final Context context){
        AppProps appProps = appPropBuilder.build();

        SnsClient snsClient = SnsClient.builder()
            .region(of(appProps.getRegion()))
            .build();

        Logger logger = new Logger(context.getLogger());

        TopicService topicService = new TopicService(snsClient);

        return new SubscriptionService(
            logger,
            appProps,
            snsClient,
            topicService
        );
    }
}
