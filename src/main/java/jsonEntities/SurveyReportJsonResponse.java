package jsonEntities;


import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "callDateTime",
        "agentID",
        "callerNumber",
        "q1Answer",
        "evaluation",
        "langauge"
})
public class SurveyReportJsonResponse {

    @JsonProperty("callDateTime")
    private String callDateTime;
    @JsonProperty("agentID")
    private String agentID;
    @JsonProperty("callerNumber")
    private String callerNumber;
    @JsonProperty("q1Answer")
    private String q1Answer;
    @JsonProperty("evaluation")
    private String evaluation;
    @JsonProperty("langauge")
    private String langauge;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("callDateTime")
    public String getCallDateTime() {
        return callDateTime;
    }

    @JsonProperty("callDateTime")
    public void setCallDateTime(String callDateTime) {
        this.callDateTime = callDateTime;
    }

    @JsonProperty("agentID")
    public String getAgentID() {
        return agentID;
    }

    @JsonProperty("agentID")
    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    @JsonProperty("callerNumber")
    public String getCallerNumber() {
        return callerNumber;
    }

    @JsonProperty("callerNumber")
    public void setCallerNumber(String callerNumber) {
        this.callerNumber = callerNumber;
    }

    @JsonProperty("q1Answer")
    public String getQ1Answer() {
        return q1Answer;
    }

    @JsonProperty("q1Answer")
    public void setQ1Answer(String q1Answer) {
        this.q1Answer = q1Answer;
    }

    @JsonProperty("evaluation")
    public String getEvaluation() {
        return evaluation;
    }

    @JsonProperty("evaluation")
    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    @JsonProperty("langauge")
    public String getLangauge() {
        return langauge;
    }

    @JsonProperty("langauge")
    public void setLangauge(String langauge) {
        this.langauge = langauge;
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
