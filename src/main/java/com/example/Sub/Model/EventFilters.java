package com.example.Sub.Model;

import org.ldaptive.LdapEntry;
import org.ldaptive.beans.Attribute;
import org.ldaptive.beans.Entry;
import org.springframework.ldap.core.LdapAttribute;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Entry(dn = "distinguishedName", attributes = {
        @Attribute(name = "eventFiltersId", property = "eventFiltersId"),
        @Attribute(name = "instanceTypes", property = "instanceTypes"),
        @Attribute(name = "transProtocols", property = "transProtocols"),
        @Attribute(name = "ptpProfiles", property = "ptpProfiles"),
        @Attribute(name = "objectClass", values = { "eventFilters" })
})
public class EventFilters {

    private int eventFilterId;
    private List<String> instanceTypes;
    private List<String> ptpProfiles;
    private List<String> transProtocols;

    public List<String> getInstanceTypes() {
        return instanceTypes;
    }

    public void setInstanceTypes(List<String> instanceTypes) {
        this.instanceTypes = instanceTypes;
    }

    public int getEventFilterId() {
        return eventFilterId;
    }

    public void setEventFilterId(int eventFilterId) {
        this.eventFilterId = eventFilterId;
    }

    public List<String> getPtpProfiles() {
        return ptpProfiles;
    }

    public void setPtpProfiles(List<String> ptpProfiles) {
        this.ptpProfiles = ptpProfiles;
    }

    public List<String> getTransProtocols() {
        return transProtocols;
    }

    public void setTransProtocols(List<String> transProtocols) {
        this.transProtocols = transProtocols;
    }

    public EventFilters() {
    }

    public EventFilters(List<String> instanceTypes, List<String> ptpProfiles, List<String> transProtocols) {
        this.instanceTypes = instanceTypes;
        this.ptpProfiles = ptpProfiles;
        this.transProtocols = transProtocols;
    }

    @Override
    public String toString() {
        return "EventFilters{" +
                "ptpProfiles=" + ptpProfiles +
                ", transProtocols=" + transProtocols +
                '}';
    }


}
