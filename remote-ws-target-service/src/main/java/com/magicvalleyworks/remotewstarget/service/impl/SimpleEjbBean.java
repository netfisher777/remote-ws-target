package com.magicvalleyworks.remotewstarget.service.impl;

import com.magicvalleyworks.remotewstarget.service.api.ILocalSimpleEjb;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.Date;

@Stateless
@Local(value = ILocalSimpleEjb.class)
public class SimpleEjbBean implements ILocalSimpleEjb {
    @Override
    public String processData(String data) {
        return String.format("Your data: %s; Current time in ms: %d", data, new Date().getTime());
    }
}
