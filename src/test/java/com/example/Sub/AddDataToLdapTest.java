/*package com.example.Sub;

import com.example.Sub.Model.EventFilters;
import com.example.Sub.Model.Snssai;
import com.example.Sub.Model.Subscription;
import com.example.Sub.Service.SubscriptionsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class AddDataToLdapTest {

    @Autowired
    private SubscriptionsService ldapService;

    @Test
    public void testAddDataToLdap() {
        List<String> listaStringova = Arrays.asList("Prvi string", "Drugi string", "Treći string");
        Subscription subscription = new Subscription();
        subscription.setSupis(listaStringova);
        subscription.setDnn("dnn");
        subscription.setInterGrpId("inter grip id");
        subscription.setAnyUeInd(true);
        subscription.setNotifMethod("notif method");
        subscription.setSubscribedEvents(listaStringova);
        subscription.setSubsNotifUri("subs notif uri");
        subscription.setSubsNotifId("subsnotif id");
        subscription.setMaxReportNbr(1);
        subscription.setExpiry("1.1.2031");
        subscription.setRepPeriod(3600);
        subscription.setSuppFeat("supp feat");
        ldapService.addToLdap(subscription);

        Snssai snssai = new Snssai();
        snssai.setSst("sdf");
        snssai.setSd("sd");
        snssai.setCn("cn");
        ldapService.addToLdap(snssai);

        EventFilters eventFilters = new EventFilters();
        eventFilters.setEventFilterId(listaStringova);

        eventFilters.setInstanceTypes(listaStringova);
        eventFilters.setPtpProfiles(listaStringova);
        eventFilters.setTransProtocols(listaStringova);
        ldapService.addToLdap(eventFilters);

    }
}
*/
