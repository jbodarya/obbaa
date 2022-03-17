package org.broadband_forum.obbaa.dhcp;

import org.json.JSONObject;

import java.util.Map;

public class Entity {

    @Override
    public String toString() {
        Entity e = new Entity();
        e.setEntityName(this.entityName);
        e.setValues(this.values);
        return  new JSONObject(e).toString();
    }

    private String messageId;
    private String entityName;
    private Map values;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Map getValues() {
        return values;
    }

    public void setValues(Map values) {
        this.values = values;
    }
}
