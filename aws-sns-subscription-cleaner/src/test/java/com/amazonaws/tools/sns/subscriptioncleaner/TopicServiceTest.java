package com.amazonaws.tools.sns.subscriptioncleaner;

import org.junit.Test;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.GetTopicAttributesRequest;
import software.amazon.awssdk.services.sns.model.GetTopicAttributesResponse;
import software.amazon.awssdk.services.sns.model.NotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TopicServiceTest {

    private final SnsClient snsClient = mock(SnsClient.class);

    private final TopicService topicService = new TopicService(snsClient);

    @Test
    public void shouldTopicExist(){
        String topicArn = "foo:bar";
        when(snsClient.getTopicAttributes(GetTopicAttributesRequest.builder().topicArn(topicArn).build())).thenReturn(GetTopicAttributesResponse.builder().build());

        boolean result = topicService.isTopicExists(topicArn);

        assertThat(result, is(true));
    }

    @Test
    public void shouldTopicNotExist(){
        String topicArn = "foo:bar";
        when(snsClient.getTopicAttributes(GetTopicAttributesRequest.builder().topicArn(topicArn).build())).thenThrow(NotFoundException.class);

        boolean result = topicService.isTopicExists(topicArn);

        assertThat(result, is(false));
    }
}
