
package core.kafka.communication.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;
import java.io.Serializable;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sender_id",
    "action",
    "action_name",
    "action_properties",
    "action_date",
    "empty_area"
})
public class Action implements Serializable{

    @JsonProperty("sender_id")
    private String senderId;
    @JsonProperty("action")
    private String action;
    @JsonProperty("action_name")
    private String actionName;
    @JsonProperty("action_properties")
    private String actionProperties;
    @JsonProperty("action_date")
    private Object actionDate;
    @JsonProperty("empty_area")
    private Object emptyArea;

    @JsonProperty("sender_id")
    public String getSenderId() {
        return senderId;
    }

    @JsonProperty("sender_id")
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    @JsonProperty("action")
    public String getAction() {
        return action;
    }

    @JsonProperty("action")
    public void setAction(String action) {
        this.action = action;
    }

    @JsonProperty("action_name")
    public String getActionName() {
        return actionName;
    }

    @JsonProperty("action_name")
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    @JsonProperty("action_properties")
    public String getActionProperties() {
        return actionProperties;
    }

    @JsonProperty("action_properties")
    public void setActionProperties(String actionProperties) {
        this.actionProperties = actionProperties;
    }

    @JsonProperty("action_date")
    public Object getActionDate() {
        return actionDate;
    }

    @JsonProperty("action_date")
    public void setActionDate(Object actionDate) {
        this.actionDate = actionDate;
    }

    @JsonProperty("empty_area")
    public Object getEmptyArea() {
        return emptyArea;
    }

    @JsonProperty("empty_area")
    public void setEmptyArea(Object emptyArea) {
        this.emptyArea = emptyArea;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("senderId", senderId).append("action", action).append("actionName", actionName).append("actionProperties", actionProperties).append("actionDate", actionDate).append("emptyArea", emptyArea).toString();
    }

}
