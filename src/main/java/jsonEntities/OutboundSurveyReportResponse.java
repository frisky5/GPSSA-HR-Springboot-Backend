package jsonEntities;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "timestamp",
        "language",
        "customerNumber",
        "q1",
        "q2",
        "q3",
        "q4",
        "q5"
})
public class OutboundSurveyReportResponse {

    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("language")
    private String language;
    @JsonProperty("customerNumber")
    private String customerNumber;
    @JsonProperty("q1")
    private String q1;
    @JsonProperty("q2")
    private String q2;
    @JsonProperty("q3")
    private String q3;
    @JsonProperty("q4")
    private String q4;
    @JsonProperty("q5")
    private String q5;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("customerNumber")
    public String getCustomerNumber() {
        return customerNumber;
    }

    @JsonProperty("customerNumber")
    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    @JsonProperty("q1")
    public String getQ1() {
        return q1;
    }

    @JsonProperty("q1")
    public void setQ1(String q1) {
        this.q1 = q1;
    }

    @JsonProperty("q2")
    public String getQ2() {
        return q2;
    }

    @JsonProperty("q2")
    public void setQ2(String q2) {
        this.q2 = q2;
    }

    @JsonProperty("q3")
    public String getQ3() {
        return q3;
    }

    @JsonProperty("q3")
    public void setQ3(String q3) {
        this.q3 = q3;
    }

    @JsonProperty("q4")
    public String getQ4() {
        return q4;
    }

    @JsonProperty("q4")
    public void setQ4(String q4) {
        this.q4 = q4;
    }

    @JsonProperty("q5")
    public String getQ5() {
        return q5;
    }

    @JsonProperty("q5")
    public void setQ5(String q5) {
        this.q5 = q5;
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