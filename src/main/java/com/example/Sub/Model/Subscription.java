package com.example.Sub.Model;

import org.ldaptive.beans.Attribute;
import org.ldaptive.beans.Entry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Entry(
        dn = "distinguishedName",
        attributes = {
                @Attribute(name = "subscriptionId", property = "subscriptionId"),
                @Attribute(name = "supis", property = "supis"),
                @Attribute(name = "interGrpId", property = "email"),
                @Attribute(name = "anyUeInd", property = "phoneNumber"),
                @Attribute(name = "notifMethod", property = "notifMethod"),
                @Attribute(name = "subscribedEvents", property = "subscribedEvents"),
                @Attribute(name = "subsNotifUri", property = "subsNotifUri"),
                @Attribute(name = "subsNotifId", property = "subsNotifId"),
                @Attribute(name = "maxReportNbr", property = "maxReportNbr"),
                @Attribute(name = "expiry", property = "expiry"),
                @Attribute(name = "repPeriod", property = "repPeriod"),
                @Attribute(name = "suppFeat", property = "suppFeat"),
                @Attribute(name = "dnn", property = "dnn"),
                @Attribute(name = "objectClass", values = {"subscription"})

        })
public class Subscription {

        private Integer subscriptionId;
        private List<String> supis;
        private String interGrpId;
        private boolean anyUeInd;
        private String notifMethod;
        private String dnn;
        private List<String> subscribedEvents;
        private String subsNotifUri;
        private String subsNotifId;
        private Integer maxReportNbr;
        private String expiry;
        private Integer repPeriod;
        private String suppFeat;
        private Snssai snssai;
        private ArrayList<EventFilters> eventFilters;

        public Integer getSubscriptionId() {
                return subscriptionId;
        }

        public void setSubscriptionId(int subscriptionId) {
                this.subscriptionId = subscriptionId;
        }

        public List<String> getSupis() {
                return supis;
        }

        public void setSupis(List<String> supis) {
                this.supis = supis;
        }

        public String getInterGrpId() {
                return interGrpId;
        }

        public void setInterGrpId(String interGrpId) {
                this.interGrpId = interGrpId;
        }

        public boolean isAnyUeInd() {
                return anyUeInd;
        }

        public void setAnyUeInd(boolean anyUeInd) {
                this.anyUeInd = anyUeInd;
        }

        public String getNotifMethod() {
                return notifMethod;
        }

        public void setNotifMethod(String notifMethod) {
                this.notifMethod = notifMethod;
        }

        public String getDnn() {
                return dnn;
        }

        public void setDnn(String dnn) {
                this.dnn = dnn;
        }

        public List<String> getSubscribedEvents() {
                return subscribedEvents;
        }

        public void setSubscribedEvents(List<String> subscribedEvents) {
                this.subscribedEvents = subscribedEvents;
        }

        public String getSubsNotifUri() {
                return subsNotifUri;
        }

        public void setSubsNotifUri(String subsNotifUri) {
                this.subsNotifUri = subsNotifUri;
        }

        public String getSubsNotifId() {
                return subsNotifId;
        }

        public void setSubsNotifId(String subsNotifId) {
                this.subsNotifId = subsNotifId;
        }

        public Integer getMaxReportNbr() {
                return maxReportNbr;
        }

        public void setMaxReportNbr(Integer maxReportNbr) {
                this.maxReportNbr = maxReportNbr;
        }

        public String getExpiry() {
                return expiry;
        }

        public void setExpiry(String expiry) {
                this.expiry = expiry;
        }

        public Integer getRepPeriod() {
                return repPeriod;
        }

        public void setRepPeriod(int repPeriod) {
                this.repPeriod = repPeriod;
        }

        public String getSuppFeat() {
                return suppFeat;
        }

        public void setSuppFeat(String suppFeat) {
                this.suppFeat = suppFeat;
        }

        public Snssai getSnssai() {
                return snssai;
        }

        public void setSnssai(Snssai snssai) {
                this.snssai = snssai;
        }

        public ArrayList<EventFilters> getEventFilters() {
                return eventFilters;
        }

        public void setEventFilters(ArrayList<EventFilters> eventFilters) {
                this.eventFilters = eventFilters;
        }

        public Subscription() {
        }

        public Subscription(Integer subscriptionId, List<String> supis, String interGrpId, boolean anyUeInd, String notifMethod, String dnn, List<String> subscribedEvents, String subsNotifUri, String subsNotifId, Integer maxReportNbr, String expiry, Integer repPeriod, String suppFeat, Snssai snssai, ArrayList<EventFilters> eventFilters) {
                this.subscriptionId = subscriptionId;
                this.supis = supis;
                this.interGrpId = interGrpId;
                this.anyUeInd = anyUeInd;
                this.notifMethod = notifMethod;
                this.dnn = dnn;
                this.subscribedEvents = subscribedEvents;
                this.subsNotifUri = subsNotifUri;
                this.subsNotifId = subsNotifId;
                this.maxReportNbr = maxReportNbr;
                this.expiry = expiry;
                this.repPeriod = repPeriod;
                this.suppFeat = suppFeat;
                this.snssai = snssai;
                this.eventFilters = eventFilters;
        }

        @Override
        public String toString() {
                return "Subscription{" +
                        "subscriptionId=" + subscriptionId +
                        ", supis=" + supis +
                        ", interGrpId='" + interGrpId + '\'' +
                        ", anyUeInd=" + anyUeInd +
                        ", notifMethod='" + notifMethod + '\'' +
                        ", dnn='" + dnn + '\'' +
                        ", subscribedEvents=" + subscribedEvents +
                        ", subsNotifUri='" + subsNotifUri + '\'' +
                        ", subsNotifId='" + subsNotifId + '\'' +
                        ", maxReportNbr=" + maxReportNbr +
                        ", expiry='" + expiry + '\'' +
                        ", repPeriod=" + repPeriod +
                        ", suppFeat='" + suppFeat + '\'' +
                        ", snssai=" + snssai +
                        ", eventFilters=" + eventFilters +
                        '}';
        }
}
