package jsonEntities;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "callerNumber",
        "callDateTime",
        "agentID",
        "q1Answer",
        "q2Answer",
        "evaluation",
        "language"
})
public class InboundSurveyReportJsonResponse {

    @JsonProperty("callerNumber")
    private String callerNumber;
    @JsonProperty("callDateTime")
    private String callDateTime;
    @JsonProperty("agentID")
    private String agentID;
    @JsonProperty("q1Answer")
    private String q1Answer;
    @JsonProperty("q2Answer")
    private String q2Answer;
    @JsonProperty("evaluation")
    private String evaluation;
    @JsonProperty("language")
    private String language;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public InboundSurveyReportJsonResponse() {
        callerNumber = "---";
        callDateTime = "---";
        agentID = "---";
        q1Answer = "---";
        q2Answer = "---";
        evaluation = "---";
        language = "---";
    }

    @JsonProperty("callerNumber")
    public String getCallerNumber() {
        return callerNumber;
    }

    @JsonProperty("callerNumber")
    public void setCallerNumber(String callerNumber) {
        this.callerNumber = callerNumber;
    }

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

    @JsonProperty("q1Answer")
    public String getQ1Answer() {
        return q1Answer;
    }

    @JsonProperty("q1Answer")
    public void setQ1Answer(String q1Answer) {
        this.q1Answer = q1Answer;
    }

    @JsonProperty("q2Answer")
    public String getQ2Answer() {
        return q2Answer;
    }

    @JsonProperty("q2Answer")
    public void setQ2Answer(String q2Answer) {
        this.q2Answer = q2Answer;
    }

    @JsonProperty("evaluation")
    public String getEvaluation() {
        return evaluation;
    }

    @JsonProperty("evaluation")
    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
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