package jsonEntities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "agentId",
        "agentName"
})
public class AgentsResponse {

    @JsonProperty("agentId")
    private String agentId;

    @JsonProperty("agentName")
    private String agentName;

    @JsonProperty("selected")
    private boolean selected;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("agentId")
    public String getAgentId() {
        return agentId;
    }

    @JsonProperty("agentId")
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @JsonProperty("agentName")
    public String getAgentName() {
        return agentName;
    }

    @JsonProperty("agentName")
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    @JsonProperty("selected")
    public boolean getSelected() {
        return selected;
    }

    @JsonProperty("selected")
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}