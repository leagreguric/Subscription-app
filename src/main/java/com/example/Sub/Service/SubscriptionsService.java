package com.example.Sub.Service;
import com.example.Sub.Model.LdapSubscriptionRepository;
import com.example.Sub.Model.EventFilters;
import com.example.Sub.Model.Snssai;
import com.example.Sub.Model.Subscription;
import org.ldaptive.handler.ResultPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.ldaptive.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class SubscriptionsService {

    private final LdapSubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionsService( LdapSubscriptionRepository subscriptionRepository) {

        this.subscriptionRepository=subscriptionRepository;
    }

    public ResponseEntity<?> getSubscription(int subscriptionId, String subscribedEvent, String snssai){
        ResponseEntity<Subscription> responseEntity=subscriptionRepository.searchSubscription(subscriptionId);

        if(responseEntity.getStatusCode().toString().equals("404 NOT_FOUND")) return responseEntity;
        if(responseEntity.getStatusCode().toString().equals("400 BAD_REQUEST")) return responseEntity;

        Subscription subscription=responseEntity.getBody();

        if (!isValidExpiry(subscription.getExpiry())) {
            deleteEntry(subscriptionId);
            return new ResponseEntity<>("RECORD_EXPIRED",HttpStatus.FORBIDDEN);
        }

        if (subscribedEvent != null && subscription.getSubscribedEvents() !=null) {
            return new ResponseEntity<>("EVENT_NOT_ALLOWED", HttpStatus.FORBIDDEN);
        }

        if (snssai != null && subscription.getSnssai() != null) {
            boolean snssaiExists = false;
            Snssai snssaiObj = subscription.getSnssai();
            if (snssaiObj.getCn().equals(snssai)) {
                snssaiExists = true;
            }
            if (snssaiExists) {
                return new ResponseEntity<>(subscription, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("SNSSAI_NOT_ALLOWED", HttpStatus.FORBIDDEN);
            }
        }

        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }


    public ResponseEntity<?> createSubscription(Subscription subscription) {

       if (subscription.getSupis().isEmpty() || subscription.getSnssai()==null|| subscription.getDnn().isEmpty() || subscription.getEventFilters().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Mandatory parameters are missing");
        }

        if (!isValidType(subscription)) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Invalid parameter types");
        }

        if (!isValidExpiry(subscription.getExpiry())) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Expiry is not valid");
        }

        UUID uuid = UUID.randomUUID();
        Integer subscriptionId = uuid.hashCode();

        try {

            subscription.setSubscriptionId(subscriptionId);
            subscriptionRepository.saveSubscription(subscription);

            String location = "/ntsctsf-time-sync/v1/subscriptions/" + subscriptionId;
            return ResponseEntity.status(HttpStatus.CREATED).header("Location", location).body(subscription);
        } catch (LdapException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private boolean isValidType(Subscription subscription) {
        boolean isValid = true;

        if (!(subscription.getSubscriptionId() instanceof Integer)) {
            isValid = false;
            if (subscription.getSubscriptionId()==null) isValid=true;
        }
        else isValid=true;

        for (String supisItem : subscription.getSupis()) {
            if (!(supisItem instanceof String)) {
                isValid = false;
                if(supisItem==null) isValid=true;
            }
            else isValid=true;
        }
        if (!(subscription.getInterGrpId() instanceof String)) {
            isValid = false;
            System.out.println("IntrerGrpId");
            if(subscription.getInterGrpId()==null) isValid=true;
        }
        else isValid=true;

        if (subscription.isAnyUeInd() != true && subscription.isAnyUeInd() != false) {
            isValid = false;
            System.out.println("isAnyeUeInd");

        }
        else isValid=true;

        if (!(subscription.getNotifMethod() instanceof String)) {
            isValid = false;
            System.out.println("NotifMethod");
            if(subscription.getNotifMethod()==null) isValid=true;
        }
        else isValid=true;

        if (!(subscription.getDnn() instanceof String)) {
            isValid = false;
            System.out.println("dnn");
            if(subscription.getDnn()==null) isValid=true;
        }
        else isValid=true;

        for (String subscribedEvent : subscription.getSubscribedEvents()) {
            if (!(subscribedEvent instanceof String)) {
                isValid = false;
                System.out.println("subscribedEvent");
                if(subscribedEvent==null) isValid=true;
            }
            else isValid=true;
        }

        if (!(subscription.getSubsNotifUri() instanceof String)) {
            System.out.println("SubsNotifUri");
            isValid = false;
            if(subscription.getSubsNotifUri()==null) isValid=true;
        }
        else isValid=true;

        if (!(subscription.getMaxReportNbr() instanceof Integer)) {
            isValid = false;
            System.out.println("MaxReportNbr");
            if(subscription.getMaxReportNbr()==null) isValid=true;
        }
        else isValid=true;

        if (!(subscription.getExpiry() instanceof String)) {
            System.out.println("Expiry");
            isValid = false;
        }
        else isValid=true;

        if (!(subscription.getRepPeriod() instanceof Integer)) {
            System.out.println("RepPeriod");
            isValid = false;
        }
        else isValid=true;

        if (!(subscription.getSuppFeat() instanceof String)) {
            System.out.println("SuppFeat");
            isValid = false;
        }
        else isValid=true;

        if (!(subscription.getSnssai() instanceof Snssai)) {
            System.out.println("Snssai");
            isValid = false;
        }
        else isValid=true;

        for (EventFilters eventFilter : subscription.getEventFilters()) {
            if (!(eventFilter instanceof EventFilters)) {
                isValid = false;
                System.out.println("eventFilters");
                break;
            }
            else isValid=true;
        }

        return isValid;
    }

    private boolean isValidExpiry(String expiry) {
        if (expiry == null) {
            return true;
        } else {
            LocalDateTime expiryDateTime = LocalDateTime.parse(expiry, DateTimeFormatter.ISO_DATE_TIME);
            if (expiryDateTime.isBefore(LocalDateTime.now())) return false;
            else return true;
        }
    }

    public ResponseEntity<?> deleteEntry(int subscriptionId) {
        try {
            SingleConnectionFactory cf = subscriptionRepository.openConnection();

            ResponseEntity<Subscription> responseEntity=subscriptionRepository.searchSubscription(subscriptionId);
            if(responseEntity.getStatusCode().toString().equals("404 NOT_FOUND")) return responseEntity;

            List<String> relatedDns = subscriptionRepository.getRelatedDns(subscriptionId);
            for (String dn : relatedDns) {
                DeleteOperation delete = new DeleteOperation(cf);
                delete.execute(new DeleteRequest(dn));
                System.out.println("Successfully deleted related entry: " + dn);
            }
            DeleteOperation delete = new DeleteOperation(cf);
            String dn = ("subscriptionId= " + subscriptionId + "," + subscriptionRepository.getBaseDn());
            delete.execute(new DeleteRequest(dn));
            System.out.println("Successfully deleted entry in LDAP database");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (LdapException e) {
            System.out.println("Failed to delete entry in LDAP database: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}












    


