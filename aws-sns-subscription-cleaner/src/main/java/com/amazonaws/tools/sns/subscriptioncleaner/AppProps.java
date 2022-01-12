package com.amazonaws.tools.sns.subscriptioncleaner;

public class AppProps {

    private String region;

    private boolean dryRun = true;

    public boolean isDryRun() {
        return dryRun;
    }

    public void setDryRun(boolean dryRun) {
        this.dryRun = dryRun;
    }

    public String getRegion(){
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
