package com.amazonaws.tools.sns.subscriptioncleaner;

import org.junit.Test;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.ListSubscriptionsRequest;
import software.amazon.awssdk.services.sns.model.ListSubscriptionsResponse;
import software.amazon.awssdk.services.sns.model.Subscription;
import software.amazon.awssdk.services.sns.model.UnsubscribeRequest;
import software.amazon.awssdk.services.sns.paginators.ListSubscriptionsIterable;

import static java.text.MessageFormat.format;
import static org.mockito.Mockito.*;

public class SubscriptionServiceTest {

    private final Logger logger = mock(Logger.class);

    private final AppProps appProps = mock(AppProps.class);

    private final SnsClient snsClient = mock(SnsClient.class);

    private final TopicService topicService = mock(TopicService.class);

    private final SubscriptionService subscriptionService = new SubscriptionService(
        logger,
        appProps,
        snsClient,
        topicService
    );

    private void prepareSubscriptionAndTopic(String topicArn, String subscriptionArn, boolean dryRun, boolean topicExists){
        when(appProps.isDryRun()).thenReturn(dryRun);
        when(snsClient.listSubscriptions(listSubscriptionsRequest())).thenReturn(listSubscriptionsResponse(topicArn, subscriptionArn));
        when(snsClient.listSubscriptionsPaginator()).thenReturn(new ListSubscriptionsIterable(snsClient, listSubscriptionsRequest()));
        when(topicService.isTopicExists(topicArn)).thenReturn(topicExists);
    }

    @Test
    public void shouldCleanSubscriptionIfTopicNotExists(){
        String topicArn = "foo:bar";
        String subscriptionArn = "foz:baz";
        prepareSubscriptionAndTopic(topicArn, subscriptionArn, false, false);

        subscriptionService.clean();

        verify(snsClient).unsubscribe(unsubscribeRequest(subscriptionArn));
    }

    @Test
    public void shouldNotCleanSubscriptionIfTopicExists(){
        String topicArn = "foo:bar";
        String subscriptionArn = "foz:baz";
        prepareSubscriptionAndTopic(topicArn, subscriptionArn, false, true);

        subscriptionService.clean();

        verify(snsClient, never()).unsubscribe(unsubscribeRequest(subscriptionArn));
    }

    @Test
    public void shouldNotCleanSubscriptionIfDryRun(){
        String topicArn = "foo:bar";
        String subscriptionArn = "foz:baz";
        prepareSubscriptionAndTopic(topicArn, subscriptionArn, true, false);

        subscriptionService.clean();

        verify(snsClient, never()).unsubscribe(unsubscribeRequest(subscriptionArn));
    }

    @Test
    public void shouldLogFindAndDeleteSubscriptionIfTopicNotExists(){
        String topicArn = "foo:bar";
        String subscriptionArn = "foz:baz";
        prepareSubscriptionAndTopic(topicArn, subscriptionArn, false, false);

        subscriptionService.clean();

        verify(logger).log(format("Found subscription \"{0}\" that belongs to non existent topic \"{1}\".", subscriptionArn, topicArn));
        verify(logger).log(format("Deleted subscription \"{0}\" that belongs to non existent topic \"{1}\".", subscriptionArn, topicArn));
    }

    @Test
    public void shouldNotLogFindAndDeleteSubscriptionIfTopicExists(){
        String topicArn = "foo:bar";
        String subscriptionArn = "foz:baz";
        prepareSubscriptionAndTopic(topicArn, subscriptionArn, false, true);

        subscriptionService.clean();

        verify(logger, never()).log(format("Found subscription {0} that belongs to non existent topic {1}.", subscriptionArn, topicArn));
        verify(logger, never()).log(format("Deleted subscription {0} that belongs to non existent topic {1}.", subscriptionArn, topicArn));
    }

    private UnsubscribeRequest unsubscribeRequest(String subscriptionArn){
        return UnsubscribeRequest.builder()
            .subscriptionArn(subscriptionArn)
            .build();
    }

    private ListSubscriptionsRequest listSubscriptionsRequest(){
        return ListSubscriptionsRequest.builder().build();
    }

    private ListSubscriptionsResponse listSubscriptionsResponse(String topicArn, String subscriptionArn){
        return ListSubscriptionsResponse.builder().subscriptions(Subscription.builder().topicArn(topicArn).subscriptionArn(subscriptionArn).build()).build();
    }
}
