package jsonEntities;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "numberOfCallsReceivedByAgent",
        "totalTalkTimeByAgent",
        "totalAuxTimeByAgent",
        "totalAbandonedCallByAgent"
})
public class AgentPerformanceReportJsonResponse {
    @JsonProperty("agentID")
    private String agentID;
    @JsonProperty("numberOfCallsReceivedByAgent")
    private String numberOfCallsReceivedByAgent;
    @JsonProperty("totalTalkTimeByAgent")
    private String totalTalkTimeByAgent;
    @JsonProperty("totalAuxTimeByAgent")
    private String totalAuxTimeByAgent;
    @JsonProperty("totalAbandonedCallByAgent")
    private String totalAbandonedCallByAgent;
    @JsonProperty("totalAcwByAgent")
    private String totalAcwByAgent;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public AgentPerformanceReportJsonResponse() {
        agentID = "-1";
        numberOfCallsReceivedByAgent = "-1";
        totalTalkTimeByAgent = "-1";
        totalAuxTimeByAgent = "-1";
        totalAbandonedCallByAgent = "-1";
        totalAcwByAgent = "-1";
    }

    @JsonProperty("agentID")
    public String getagentID() {
        return agentID;
    }

    @JsonProperty("agentID")
    public void setagentID(String agentID) {
        this.agentID = agentID;
    }

    @JsonProperty("numberOfCallsReceivedByAgent")
    public String getNumberOfCallsReceivedByAgent() {
        return numberOfCallsReceivedByAgent;
    }

    @JsonProperty("numberOfCallsReceivedByAgent")
    public void setNumberOfCallsReceivedByAgent(String numberOfCallsReceivedByAgent) {
        this.numberOfCallsReceivedByAgent = numberOfCallsReceivedByAgent;
    }

    @JsonProperty("totalTalkTimeByAgent")
    public String getTotalTalkTimeByAgent() {
        return totalTalkTimeByAgent;
    }

    @JsonProperty("totalTalkTimeByAgent")
    public void setTotalTalkTimeByAgent(String totalTalkTimeByAgent) {
        this.totalTalkTimeByAgent = totalTalkTimeByAgent;
    }

    @JsonProperty("totalAuxTimeByAgent")
    public String getTotalAuxTimeByAgent() {
        return totalAuxTimeByAgent;
    }

    @JsonProperty("totalAuxTimeByAgent")
    public void setTotalAuxTimeByAgent(String totalAuxTimeByAgent) {
        this.totalAuxTimeByAgent = totalAuxTimeByAgent;
    }

    @JsonProperty("totalAbandonedCallByAgent")
    public String getTotalAbandonedCallByAgent() {
        return totalAbandonedCallByAgent;
    }

    @JsonProperty("totalAbandonedCallByAgent")
    public void setTotalAbandonedCallByAgent(String totalAbandonedCallByAgent) {
        this.totalAbandonedCallByAgent = totalAbandonedCallByAgent;
    }

    @JsonProperty("totalAcwByAgent")
    public String getTotalAcwByAgent() {
        return totalAcwByAgent;
    }

    @JsonProperty("totalAcwByAgent")
    public void setTotalAcwByAgent(String totalAcwByAgent) {
        this.totalAcwByAgent = totalAcwByAgent;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
