
package core.kafka.communication.types;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "diskSize",
    "freeDiskPartition",
    "usableDiskPartition",
    "opconPingStatus",
    "coderPingStatus",
    "opconLiveStatus",
    "opconRecordStatus",
    "replayFiles"
})
public class Status implements Serializable{

    @JsonProperty("diskSize")
    private Object diskSize;
    @JsonProperty("freeDiskPartition")
    private Object freeDiskPartition;
    @JsonProperty("usableDiskPartition")
    private Object usableDiskPartition;
    @JsonProperty("opconPingStatus")
    private List<Boolean> opconPingStatus = new ArrayList<Boolean>();
    @JsonProperty("coderPingStatus")
    private List<Boolean> coderPingStatus = new ArrayList<Boolean>();
    @JsonProperty("opconLiveStatus")
    private List<Boolean> opconLiveStatus = new ArrayList<Boolean>();
    @JsonProperty("opconRecordStatus")
    private List<Record> opconRecordStatus = new ArrayList<Record>();
    @JsonProperty("replayFiles")
    private List<String> replayFiles = new ArrayList<String>();

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

    @JsonProperty("opconPingStatus")
    public List<Boolean> getOpconPingStatus() {
        return opconPingStatus;
    }

    @JsonProperty("opconPingStatus")
    public void setOpconPingStatus(List<Boolean> opconPingStatus) {
        this.opconPingStatus = opconPingStatus;
    }

    @JsonProperty("coderPingStatus")
    public List<Boolean> getCoderPingStatus() {
        return coderPingStatus;
    }

    @JsonProperty("coderPingStatus")
    public void setCoderPingStatus(List<Boolean> coderPingStatus) {
        this.coderPingStatus = coderPingStatus;
    }

    @JsonProperty("opconLiveStatus")
    public List<Boolean> getOpconLiveStatus() {
        return opconLiveStatus;
    }

    @JsonProperty("opconLiveStatus")
    public void setOpconLiveStatus(List<Boolean> opconLiveStatus) {
        this.opconLiveStatus = opconLiveStatus;
    }

    @JsonProperty("opconRecordStatus")
    public List<Record> getOpconRecordStatus() {
        return opconRecordStatus;
    }

    @JsonProperty("opconRecordStatus")
    public void setOpconRecordStatus(List<Record> opconRecordStatus) {
        this.opconRecordStatus = opconRecordStatus;
    }

    @JsonProperty("replayFiles")
    public List<String> getReplayFiles() {
        return replayFiles;
    }

    @JsonProperty("replayFiles")
    public void setReplayFiles(List<String> replayFiles) {
        this.replayFiles = replayFiles;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("diskSize", diskSize).append("freeDiskPartition", freeDiskPartition).append("usableDiskPartition", usableDiskPartition).append("opconPingStatus", opconPingStatus).append("coderPingStatus", coderPingStatus).append("opconLiveStatus", opconLiveStatus).append("opconRecordStatus", opconRecordStatus).append("replayFiles", replayFiles).toString();
    }

}
