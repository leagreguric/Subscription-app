package com.example.Sub.Model;


import org.ldaptive.LdapException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;


import java.util.List;

@Configuration
public class SubConfig {

    @Bean
    CommandLineRunner commandLineRunner(LdapSubscriptionRepository repository) throws LdapException {
        int subscriptionId = 7885299;
        List<String> supis = Arrays.asList("supis1", "supis2");
        String interGrpId = "interGrpId";
        boolean anyUeInd = true;
        String notifMethod = "email";
        String dnn = "dnnValue";
        List<String> subscribedEvents = Arrays.asList("event1", "event2");
        String subsNotifUri = "https://example.com/notification";
        String subsNotifId = "notificationId";
        int maxReportNbr = 5;
        String expiry = "2024-12-31";
        int repPeriod = 10;
        String suppFeat = "featureValue";

        return args -> {
            ArrayList<EventFilters> eventFilters = new ArrayList<>();
            Snssai snssai = new Snssai(101,"c");
            Subscription subscription = new Subscription(subscriptionId, supis, interGrpId, anyUeInd, notifMethod, dnn,
                    subscribedEvents, subsNotifUri, subsNotifId, maxReportNbr, expiry, repPeriod, suppFeat, snssai,
                    eventFilters);

            repository.saveSubscription(subscription);
        };
    }
}
