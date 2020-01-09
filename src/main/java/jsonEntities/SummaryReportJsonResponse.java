package jsonEntities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "callsAfterWorkingHours",
        "mostFrequentNumber",
        "numberOfIvrCalls",
        "maxTalkTime",
        "numberOfComplainCalls",
        "minimumWaitTime",
        "minimumTalkTime",
        "numberOfAbandonedCalls",
        "maximumWaitingTime",
        "totalNumberOfAllCalls",
        "totalCallsOfGeneralArabic",
        "totalCallsOfGeneralEnglish",
        "totalCallsOfComplaintsArabic",
        "totalCallsOfComplaintsEnglish"
})
public class SummaryReportJsonResponse {

    @JsonProperty("callsAfterWorkingHours")
    private String callsAfterWorkingHours;
    @JsonProperty("mostFrequentNumber")
    private String mostFrequentNumber;
    @JsonProperty("numberOfIvrCalls")
    private String numberOfIvrCalls;
    @JsonProperty("maxTalkTime")
    private String maxTalkTime;
    @JsonProperty("numberOfComplainCalls")
    private String numberOfComplainCalls;
    @JsonProperty("minimumWaitTime")
    private String minimumWaitTime;
    @JsonProperty("minimumTalkTime")
    private String minimumTalkTime;
    @JsonProperty("numberOfAbandonedCalls")
    private String numberOfAbandonedCalls;
    @JsonProperty("maximumWaitingTime")
    private String maximumWaitingTime;
    @JsonProperty("maximumNumberOfWaitingCalls")
    private String maximumNumberOfWaitingCalls;
    @JsonProperty("totalNumberOfAllCalls")
    private String totalNumberOfAllCalls;
    @JsonProperty("totalCallsOfGeneralArabic")
    private String totalCallsOfGeneralArabic;
    @JsonProperty("totalCallsOfGeneralEnglish")
    private String totalCallsOfGeneralEnglish;
    @JsonProperty("totalCallsOfComplaintsArabic")
    private String totalCallsOfComplaintsArabic;
    @JsonProperty("totalCallsOfComplaintsEnglish")
    private String totalCallsOfComplaintsEnglish;

    public SummaryReportJsonResponse() {
        callsAfterWorkingHours = "-1";
        mostFrequentNumber = "-1";
        numberOfIvrCalls = "-1";
        maxTalkTime = "-1";
        numberOfComplainCalls = "-1";
        minimumWaitTime = "-1";
        minimumTalkTime = "-1";
        numberOfAbandonedCalls = "-1";
        maximumWaitingTime = "-1";
        maximumNumberOfWaitingCalls = "-1";
        totalNumberOfAllCalls = "-1";
        totalCallsOfGeneralArabic = "-1";
        totalCallsOfGeneralEnglish = "-1";
        totalCallsOfComplaintsArabic = "-1";
        totalCallsOfComplaintsEnglish = "-1";
    }

    @JsonProperty("maximumNumberOfWaitingCalls")
    public String getmaximumNumberOfWaitingCalls() {
        return maximumNumberOfWaitingCalls;
    }

    @JsonProperty("maximumNumberOfWaitingCalls")
    public void setmaximumNumberOfWaitingCalls(String maximumNumberOfWaitingCalls) {
        this.maximumNumberOfWaitingCalls = maximumNumberOfWaitingCalls;
    }

    @JsonProperty("callsAfterWorkingHours")
    public String getCallsAfterWorkingHours() {
        return callsAfterWorkingHours;
    }

    @JsonProperty("callsAfterWorkingHours")
    public void setCallsAfterWorkingHours(String callsAfterWorkingHours) {
        this.callsAfterWorkingHours = callsAfterWorkingHours;
    }

    @JsonProperty("mostFrequentNumber")
    public String getMostFrequentNumber() {
        return mostFrequentNumber;
    }

    @JsonProperty("mostFrequentNumber")
    public void setMostFrequentNumber(String mostFrequentNumber) {
        this.mostFrequentNumber = mostFrequentNumber;
    }

    @JsonProperty("numberOfIvrCalls")
    public String getNumberOfIvrCalls() {
        return numberOfIvrCalls;
    }

    @JsonProperty("numberOfIvrCalls")
    public void setNumberOfIvrCalls(String numberOfIvrCalls) {
        this.numberOfIvrCalls = numberOfIvrCalls;
    }

    @JsonProperty("maxTalkTime")
    public String getMaxTalkTime() {
        return maxTalkTime;
    }

    @JsonProperty("maxTalkTime")
    public void setMaxTalkTime(String maxTalkTime) {
        this.maxTalkTime = maxTalkTime;
    }

    @JsonProperty("numberOfComplainCalls")
    public String getNumberOfComplainCalls() {
        return numberOfComplainCalls;
    }

    @JsonProperty("numberOfComplainCalls")
    public void setNumberOfComplainCalls(String numberOfComplainCalls) {
        this.numberOfComplainCalls = numberOfComplainCalls;
    }

    @JsonProperty("minimumWaitTime")
    public String getMinimumWaitTime() {
        return minimumWaitTime;
    }

    @JsonProperty("minimumWaitTime")
    public void setMinimumWaitTime(String minimumWaitTime) {
        this.minimumWaitTime = minimumWaitTime;
    }

    @JsonProperty("minimumTalkTime")
    public String getMinimumTalkTime() {
        return minimumTalkTime;
    }

    @JsonProperty("minimumTalkTime")
    public void setMinimumTalkTime(String minimumTalkTime) {
        this.minimumTalkTime = minimumTalkTime;
    }

    @JsonProperty("numberOfAbandonedCalls")
    public String getNumberOfAbandonedCalls() {
        return numberOfAbandonedCalls;
    }

    @JsonProperty("numberOfAbandonedCalls")
    public void setNumberOfAbandonedCalls(String numberOfAbandonedCalls) {
        this.numberOfAbandonedCalls = numberOfAbandonedCalls;
    }

    @JsonProperty("maximumWaitingTime")
    public String getMaximumWaitingTime() {
        return maximumWaitingTime;
    }

    @JsonProperty("maximumWaitingTime")
    public void setMaximumWaitingTime(String maximumWaitingTime) {
        this.maximumWaitingTime = maximumWaitingTime;
    }

    @JsonProperty("totalNumberOfAllCalls")
    public String getTotalNumberOfAllCalls() {
        return totalNumberOfAllCalls;
    }

    @JsonProperty("totalNumberOfAllCalls")
    public void setTotalNumberOfAllCalls(String totalNumberOfAllCalls) {
        this.totalNumberOfAllCalls = totalNumberOfAllCalls;
    }


    @JsonProperty("totalCallsOfGeneralArabic")
    public String getTotalCallsOfGeneralArabic() {
        return totalCallsOfGeneralArabic;
    }

    @JsonProperty("totalCallsOfGeneralArabic")
    public void setTotalCallsOfGeneralArabic(String totalCallsOfGeneralArabic) {
        this.totalCallsOfGeneralArabic = totalCallsOfGeneralArabic;
    }

    @JsonProperty("totalCallsOfGeneralEnglish ")
    public String getTotalCallsOfGeneralEnglish() {
        return totalCallsOfGeneralEnglish;
    }

    @JsonProperty("totalCallsOfGeneralEnglish  ")
    public void setTotalCallsOfGeneralEnglish(String totalCallsOfGeneralEnglish) {
        this.totalCallsOfGeneralEnglish = totalCallsOfGeneralEnglish;
    }


    @JsonProperty("totalCallsOfComplaintsArabic ")
    public String getTotalCallsOfComplaintsArabic() {
        return totalCallsOfComplaintsArabic;
    }

    @JsonProperty("totalCallsOfComplaintsArabic  ")
    public void setTotalCallsOfComplaintsArabic(String totalCallsOfComplaintsArabic) {
        this.totalCallsOfComplaintsArabic = totalCallsOfComplaintsArabic;
    }


    @JsonProperty("totalCallsOfComplaintsEnglish ")
    public String getTotalCallsOfComplaintsEnglish() {
        return totalCallsOfComplaintsEnglish;
    }

    @JsonProperty("totalCallsOfComplaintsEnglish  ")
    public void setTotalCallsOfComplaintsEnglish(String totalCallsOfComplaintsEnglish) {
        this.totalCallsOfComplaintsEnglish = totalCallsOfComplaintsEnglish;
    }

}