
package model.specificvolume;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "layerId",
    "volumeAnnotationsVersion"
})

public class Layer {

    @JsonProperty("layerId")
    private String layerId;
    @JsonProperty("volumeAnnotationsVersion")
    private String volumeAnnotationsVersion;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("layerId")
    public String getLayerId() {
        return layerId;
    }

    @JsonProperty("layerId")
    public void setLayerId(String layerId) {
        this.layerId = layerId;
    }

    @JsonProperty("volumeAnnotationsVersion")
    public String getVolumeAnnotationsVersion() {
        return volumeAnnotationsVersion;
    }

    @JsonProperty("volumeAnnotationsVersion")
    public void setVolumeAnnotationsVersion(String volumeAnnotationsVersion) {
        this.volumeAnnotationsVersion = volumeAnnotationsVersion;
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
