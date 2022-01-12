package com.amazonaws.tools.sns.subscriptioncleaner;

import static java.lang.Boolean.parseBoolean;
import static java.lang.System.getenv;
import static java.util.Objects.requireNonNull;

public class AppPropBuilder {

    private final Environment environment;

    public AppPropBuilder(Environment environment) {
        this.environment = environment;
    }

    public AppProps build(){
        AppProps appProps = new AppProps();
        appProps.setRegion(getRegion());
        appProps.setDryRun(isDryRun());

        validate(appProps);

        return appProps;
    }

    private void validate(AppProps appProps){
        requireNonNull(appProps.getRegion(), "Region must be specified.");
    }

    private String getRegion(){
        return environment.get("AWS_REGION");
    }

    private boolean isDryRun(){
        String dryRunStr = environment.get("DRY_RUN");
        if(dryRunStr == null || dryRunStr.isEmpty()){
            return true;
        }
        return parseBoolean(dryRunStr);
    }
}
