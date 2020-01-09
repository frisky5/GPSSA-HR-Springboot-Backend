package jsonEntities;


import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "callid",
        "agentid",
        "calldatetime",
        "callingnumber",
        "ringtime",
        "skillset",
        "finaldisposition",
        "queuetime"
})
public class CallsDetailsJsonResponse {

    @JsonProperty("callid")
    private String callid;
    @JsonProperty("agentid")
    private String agentid;
    @JsonProperty("calldatetime")
    private String calldatetime;
    @JsonProperty("callingnumber")
    private String callingnumber;
    @JsonProperty("ringtime")
    private String ringtime;
    @JsonProperty("skillset")
    private String skillset;
    @JsonProperty("finaldisposition")
    private String finaldisposition;
    @JsonProperty("queuetime")
    private String queuetime;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("callid")
    public String getCallid() {
        return callid;
    }

    @JsonProperty("callid")
    public void setCallid(String callid) {
        this.callid = callid;
    }

    @JsonProperty("agentid")
    public String getAgentid() {
        return agentid;
    }

    @JsonProperty("agentid")
    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    @JsonProperty("calldatetime")
    public String getCalldatetime() {
        return calldatetime;
    }

    @JsonProperty("calldatetime")
    public void setCalldatetime(String calldatetime) {
        this.calldatetime = calldatetime;
    }

    @JsonProperty("callingnumber")
    public String getCallingnumber() {
        return callingnumber;
    }

    @JsonProperty("callingnumber")
    public void setCallingnumber(String callingnumber) {
        this.callingnumber = callingnumber;
    }

    @JsonProperty("ringtime")
    public String getRingtime() {
        return ringtime;
    }

    @JsonProperty("ringtime")
    public void setRingtime(String ringtime) {
        this.ringtime = ringtime;
    }

    @JsonProperty("skillset")
    public String getSkillset() {
        return skillset;
    }

    @JsonProperty("skillset")
    public void setSkillset(String skillset) {
        this.skillset = skillset;
    }

    @JsonProperty("finaldisposition")
    public String getFinaldisposition() {
        return finaldisposition;
    }

    @JsonProperty("finaldisposition")
    public void setFinaldisposition(String finaldisposition) {
        this.finaldisposition = finaldisposition;
    }

    @JsonProperty("queuetime")
    public String getQueuetime() {
        return queuetime;
    }

    @JsonProperty("queuetime")
    public void setQueuetime(String queuetime) {
        this.queuetime = queuetime;
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