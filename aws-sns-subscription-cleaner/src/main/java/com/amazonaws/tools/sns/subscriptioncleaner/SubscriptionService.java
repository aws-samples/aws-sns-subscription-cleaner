package com.amazonaws.tools.sns.subscriptioncleaner;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

import static java.text.MessageFormat.format;

public class SubscriptionService {

    private final Logger logger;

    private final AppProps appProps;

    private final SnsClient snsClient;

    private final TopicService topicService;

    public SubscriptionService(
        Logger logger,
        AppProps appProps,
        SnsClient snsClient,
        TopicService topicService
    ) {
        this.logger = logger;
        this.appProps = appProps;
        this.snsClient = snsClient;
        this.topicService = topicService;
    }

    public void clean(){
        snsClient.listSubscriptionsPaginator().stream().forEach(this::clean);
    }

    private void clean(ListSubscriptionsResponse listSubscriptionsResponse){
        if(listSubscriptionsResponse.hasSubscriptions()){
            listSubscriptionsResponse.subscriptions().forEach(this::clean);
        }
    }

    private void clean(Subscription subscription){
        String topicArn = subscription.topicArn();
        String subscriptionArn = subscription.subscriptionArn();

        if (!topicService.isTopicExists(topicArn)){
            logAbandonedSubscriptionFound(topicArn, subscriptionArn);
            if (!appProps.isDryRun()){
                remove(subscriptionArn);
                logAbandonedSubscriptionDeleted(topicArn, subscriptionArn);
            }
        }
    }

    private void remove(String subscriptionArn){
        UnsubscribeRequest request = UnsubscribeRequest.builder()
            .subscriptionArn(subscriptionArn)
            .build();
        snsClient.unsubscribe(request);
    }

    private void logAbandonedSubscriptionFound(String topicArn, String subscriptionArn){
        logger.log(format(
            "Found subscription \"{0}\" that belongs to non existent topic \"{1}\".",
            subscriptionArn,
            topicArn
        ));
    }

    private void logAbandonedSubscriptionDeleted(String topicArn, String subscriptionArn){
        logger.log(format(
            "Deleted subscription \"{0}\" that belongs to non existent topic \"{1}\".",
            subscriptionArn,
            topicArn
        ));
    }
}
