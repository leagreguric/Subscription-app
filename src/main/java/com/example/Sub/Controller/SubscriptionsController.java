package com.example.Sub.Controller;

import com.example.Sub.Model.EventFilters;
import com.example.Sub.Model.Snssai;
import com.example.Sub.Model.Subscription;
import com.example.Sub.Service.SubscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path="student-test-app/v1")
public class SubscriptionsController {

    private final SubscriptionsService subscriptionsService;

    @Autowired
    public SubscriptionsController(SubscriptionsService subscriptionsService) {
        this.subscriptionsService = subscriptionsService;
    }



    @GetMapping("subscriptions/{subscriptionId}")
    public  ResponseEntity<?> getSubscription(
            @PathVariable(value = "subscriptionId", required = true) int subscriptionId,
            @RequestParam(name = "subscribedEvent", required = false) String subscribedEvent,
            @RequestParam(name = "snssai", required = false) String snssai) {

      return subscriptionsService.getSubscription(subscriptionId, subscribedEvent, snssai);

    }

    @PostMapping("/subscriptions")
    public ResponseEntity<?> saveSubscription(@RequestBody Subscription subscription) {
        return subscriptionsService.createSubscription(subscription);
    }

    @DeleteMapping("subscriptions/{subscriptionId}")
    public ResponseEntity<?> deleteSubscription(@PathVariable(value = "subscriptionId") int subscriptionId) {
       return subscriptionsService.deleteEntry(subscriptionId);
    }






}