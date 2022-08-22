
package core.kafka.communication.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;
import java.io.Serializable;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "diskSize",
    "freeDiskPartition",
    "usableDiskPartition",
    "systemLiveStatus",
    "consoleRecordStatus",
    "displayRecordStatus"
})
public class Status implements Serializable{

    @JsonProperty("diskSize")
    private Object diskSize;
    @JsonProperty("freeDiskPartition")
    private Object freeDiskPartition;
    @JsonProperty("usableDiskPartition")
    private Object usableDiskPartition;
    @JsonProperty("systemLiveStatus")
    private Object systemLiveStatus;
    @JsonProperty("consoleRecordStatus")
    private Object consoleRecordStatus;
    @JsonProperty("displayRecordStatus")
    private Object displayRecordStatus;

    @JsonProperty("diskSize")
    public Object getDiskSize() {
        return diskSize;
    }

    @JsonProperty("diskSize")
    public void setDiskSize(Object diskSize) {
        this.diskSize = diskSize;
    }

    @JsonProperty("freeDiskPartition")
    public Object getFreeDiskPartition() {
        return freeDiskPartition;
    }

    @JsonProperty("freeDiskPartition")
    public void setFreeDiskPartition(Object freeDiskPartition) {
        this.freeDiskPartition = freeDiskPartition;
    }

    @JsonProperty("usableDiskPartition")
    public Object getUsableDiskPartition() {
        return usableDiskPartition;
    }

    @JsonProperty("usableDiskPartition")
    public void setUsableDiskPartition(Object usableDiskPartition) {
        this.usableDiskPartition = usableDiskPartition;
    }

    @JsonProperty("systemLiveStatus")
    public Object getSystemLiveStatus() {
        return systemLiveStatus;
    }

    @JsonProperty("systemLiveStatus")
    public void setSystemLiveStatus(Object systemLiveStatus) {
        this.systemLiveStatus = systemLiveStatus;
    }

    @JsonProperty("consoleRecordStatus")
    public Object getConsoleRecordStatus() {
        return consoleRecordStatus;
    }

    @JsonProperty("consoleRecordStatus")
    public void setConsoleRecordStatus(Object consoleRecordStatus) {
        this.consoleRecordStatus = consoleRecordStatus;
    }

    @JsonProperty("displayRecordStatus")
    public Object getDisplayRecordStatus() {
        return displayRecordStatus;
    }

    @JsonProperty("displayRecordStatus")
    public void setDisplayRecordStatus(Object displayRecordStatus) {
        this.displayRecordStatus = displayRecordStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("diskSize", diskSize).append("freeDiskPartition", freeDiskPartition).append("usableDiskPartition", usableDiskPartition).append("systemLiveStatus", systemLiveStatus).append("consoleRecordStatus", consoleRecordStatus).append("displayRecordStatus", displayRecordStatus).toString();
    }

}
