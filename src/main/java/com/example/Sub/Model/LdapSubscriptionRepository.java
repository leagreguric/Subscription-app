package com.example.Sub.Model;
import com.example.Sub.Model.EventFilters;
import com.example.Sub.Model.Snssai;
import com.example.Sub.Model.Subscription;
import org.ldaptive.*;
import org.ldaptive.beans.reflect.DefaultLdapEntryMapper;
import org.ldaptive.handler.ResultPredicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository
public class LdapSubscriptionRepository  {
    @Value("${ldap.adress}")
    private String adress;
    @Value("${ldap.user}")
    private String user;
    @Value("${ldap.password}")
    private String password;
    @Value("${ldap.baseDn}")
    private String baseDn;

    public String getBaseDn() {
        return baseDn;
    }

    public SingleConnectionFactory openConnection() throws LdapException {
        try {
            SingleConnectionFactory cf = new SingleConnectionFactory(adress + "/" + baseDn);
            cf.initialize();
            BindOperation bind = new BindOperation(cf);
            BindResponse res = bind.execute(SimpleBindRequest.builder()
                    .dn(user)
                    .password(password)
                    .build());
            if (res.isSuccess()) {
                System.out.println("Successfull bind");
                return cf;
            }
            else {
            System.out.println("Bind failed: " + res);
            return null;
        }
        } catch (LdapException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ResponseEntity<Subscription> searchSubscription(int subscriptionId)  {
        DefaultLdapEntryMapper<Subscription> subscriptionMapper=new DefaultLdapEntryMapper<>();
        DefaultLdapEntryMapper<Snssai> snssaiMapper=new DefaultLdapEntryMapper<>();
        DefaultLdapEntryMapper<EventFilters> eventFiltersMapper = new DefaultLdapEntryMapper<>();
        Subscription subscription = new Subscription();
        Snssai snssai = new Snssai();

        try {
            SingleConnectionFactory cf = this.openConnection();
            SearchOperation search = SearchOperation.builder().factory(cf).throwIf(ResultPredicate.NOT_SUCCESS).build();
            SearchResponse res = search.execute(SearchRequest.builder()
                    .dn("subscriptionId=" + subscriptionId + "," + baseDn)
                    .filter("(|(objectClass=subscription)(objectClass=snssai)(objectClass=eventFilters))")
                    .build());
            for (LdapEntry entry : res.getEntries()) {
                subscriptionMapper.map(entry, subscription);
                snssaiMapper.map(entry, snssai);
                subscription.setSnssai(snssai);
                EventFilters eventFilters = new EventFilters();
                ArrayList<EventFilters> eventFiltersList = new ArrayList<>();
                eventFiltersMapper.map(entry, eventFilters);
                eventFiltersList.add(eventFilters);
                subscription.setEventFilters(eventFiltersList);
                subscription.setSubscriptionId(subscriptionId);
            }
            cf.close();
        }catch(LdapException e){
            String resultCode = e.getResultCode().toString();
            if("NO_SUCH_OBJECT".equals(resultCode)) return new ResponseEntity("SUBSCRIPTION_NOT_FOUND", HttpStatus.NOT_FOUND);
            return new ResponseEntity("BAD_REQUEST",HttpStatus.BAD_REQUEST);
        }
        System.out.println(new ResponseEntity(subscription, HttpStatus.OK));
        return new ResponseEntity(subscription, HttpStatus.OK);
    }

   public void saveSubscription(Subscription subscription) throws LdapException {
        try {
            int eventFiltercounter=0;
            SingleConnectionFactory cf = openConnection();
            AddOperation addOperation = AddOperation.builder().factory(cf).build();
            if (cf!=null){
                System.out.println("Connected");
            }
            DefaultLdapEntryMapper<Subscription> subscriptionMapper=new DefaultLdapEntryMapper<>();
            LdapEntry subscriptionEntry=new LdapEntry();
            subscriptionMapper.map(subscription,subscriptionEntry);
            subscriptionEntry.setDn("subscriptionId= "+subscription.getSubscriptionId() + "," + baseDn);
            addOperation.execute(AddRequest.builder().dn(subscriptionEntry.getDn()).attributes(subscriptionEntry.getAttributes()).build());

            Snssai snssai=subscription.getSnssai();
            DefaultLdapEntryMapper<Snssai> snssaiMapper=new DefaultLdapEntryMapper<>();
            LdapEntry snssaiEntry=new LdapEntry();
            String cn= snssai.getSst()+"-"+snssai.getSd();
            snssai.setCn(cn);
            snssaiMapper.map(snssai ,snssaiEntry);
            snssaiEntry.setDn("cn="+cn+ ",subscriptionId=" + subscription.getSubscriptionId() + "," + baseDn);
            addOperation.execute(AddRequest.builder().dn(snssaiEntry.getDn()).attributes(snssaiEntry.getAttributes()).build());

           for (EventFilters eventFilter : subscription.getEventFilters()) {
               DefaultLdapEntryMapper<EventFilters> eventFiltersMapper = new DefaultLdapEntryMapper<>();
                LdapEntry eventFiltersEntry = new LdapEntry();
                eventFiltersMapper.map(eventFilter, eventFiltersEntry);
                eventFiltersEntry.setDn("eventFilterId=" + eventFiltercounter + "," + "subscriptionId=" + subscription.getSubscriptionId() + "," + baseDn);
                addOperation.execute(AddRequest.builder().dn(eventFiltersEntry.getDn()).attributes(eventFiltersEntry.getAttributes()).build());
                eventFiltercounter++;
            }
           cf.close();

        } catch (LdapException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<String> getRelatedDns(int subscriptionId) throws LdapException {
        List<String> relatedDns = new ArrayList<>();
        try {
            SingleConnectionFactory cf = openConnection();
            SearchOperation search = SearchOperation.builder().factory(cf).throwIf(ResultPredicate.NOT_SUCCESS).build();
            SearchResponse res = search.execute(SearchRequest.builder()
                    .dn("subscriptionId=" + subscriptionId + "," + baseDn)
                    .filter("(|(objectClass=subscription)(objectClass=snssai)(objectClass=eventFilters))")
                    .build());

            for (LdapEntry entry : res.getEntries()) {
                relatedDns.add(entry.getDn());
            }
        } catch (LdapException e) {
            e.printStackTrace();
            throw e;
        }
        System.out.println("Dns: "+relatedDns);
        return relatedDns;
    }

}
