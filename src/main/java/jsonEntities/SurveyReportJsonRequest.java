package jsonEntities;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "startDateTime",
        "endDateTime",
        "agentID",
        "surveyType"
})
public class SurveyReportJsonRequest {

    @JsonProperty("startDateTime")
    private String startDateTime;
    @JsonProperty("endDateTime")
    private String endDateTime;
    @JsonProperty("agentID")
    private List<String> agentID = null;
    @JsonProperty("surveyType")
    private String surveyType;
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

    @JsonProperty("agentID")
    public List<String> getAgentID() {
        return agentID;
    }

    @JsonProperty("agentID")
    public void setAgentID(List<String> agentID) {
        this.agentID = agentID;
    }

    @JsonProperty("surveyType")
    public String getSurveyType() {
        return surveyType;
    }

    @JsonProperty("surveyType")
    public void setSurveyType(String surveyType) {
        this.surveyType = surveyType;
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