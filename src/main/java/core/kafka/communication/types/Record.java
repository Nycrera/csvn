
package core.kafka.communication.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "source",
    "status",
    "name",
    "start_time"
})
public class Record implements Serializable{

    @JsonProperty("source")
    private String source;
    @JsonProperty("status")
    private Boolean status;
    @JsonProperty("name")
    private String name;
    @JsonProperty("start_time")
    private Object startTime;

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("status")
    public Boolean getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Boolean status) {
        this.status = status;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("start_time")
    public Object getStartTime() {
        return startTime;
    }

    @JsonProperty("start_time")
    public void setStartTime(Object startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("source", source).append("status", status).append("name", name).append("startTime", startTime).toString();
    }

}
