package jsonEntities;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "startDateTime",
        "endDateTime",
        "finalDisposition",
        "skillset",
        "calltype"
})
public class CallsDetailsJsonRequest {

    @JsonProperty("startDateTime")
    private String startDateTime;
    @JsonProperty("endDateTime")
    private String endDateTime;
    @JsonProperty("finalDisposition")
    private List<String> finalDisposition = null;
    @JsonProperty("skillset")
    private List<String> skillset = null;
    @JsonProperty("calltype")
    private List<String> calltype = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("startDateTime")
    public String getStartDateTime() {
        return startDateTime;
    }

    @JsonProperty("startDateTime")
    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    @JsonProperty("endDateTime")
    public String getEndDateTime() {
        return endDateTime;
    }

    @JsonProperty("endDateTime")
    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    @JsonProperty("finalDisposition")
    public List<String> getFinalDisposition() {
        return finalDisposition;
    }

    @JsonProperty("finalDisposition")
    public void setFinalDisposition(List<String> finalDisposition) {
        this.finalDisposition = finalDisposition;
    }

    @JsonProperty("skillset")
    public List<String> getSkillset() {
        return skillset;
    }

    @JsonProperty("skillset")
    public void setSkillset(List<String> skillset) {
        this.skillset = skillset;
    }

    @JsonProperty("calltype")
    public List<String> getCalltype() {
        return calltype;
    }

    @JsonProperty("calltype")
    public void setCalltype(List<String> calltype) {
        this.calltype = calltype;
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