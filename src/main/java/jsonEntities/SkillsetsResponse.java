package jsonEntities;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "skillset"
})
public class SkillsetsResponse {

    @JsonProperty("skillset")
    private String skillset;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("skillset")
    public String getSkillset() {
        return skillset;
    }

    @JsonProperty("skillset")
    public void setSkillset(String skillset) {
        this.skillset = skillset;
    }
}