package com.amazonaws.tools.sns.subscriptioncleaner;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.GetTopicAttributesRequest;
import software.amazon.awssdk.services.sns.model.NotFoundException;

import java.util.HashSet;
import java.util.Set;

public class TopicService {

    private final Set<String> checkedTopics = new HashSet<>();

    private final Set<String> existingTopics = new HashSet<>();

    private final SnsClient snsClient;

    public TopicService(SnsClient snsClient) {
        this.snsClient = snsClient;
    }

    public boolean isTopicExists(String topicArn){
        if(!checkedTopics.contains(topicArn)){
            isUncheckedTopicExist(topicArn);
        }

        return existingTopics.contains(topicArn);
    }

    private void isUncheckedTopicExist(String topicArn){
        boolean topicExists = isTopicExistRemote(topicArn);
        checkedTopics.add(topicArn);
        if (topicExists){
            existingTopics.add(topicArn);
        }
    }

    private boolean isTopicExistRemote(String topicArn){
        try {
            snsClient.getTopicAttributes(GetTopicAttributesRequest.builder().topicArn(topicArn).build());
            return true;
        } catch (NotFoundException e){
            return false;
        }
    }
}
