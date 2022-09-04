
package com.rekordb.rekordb.tourspot.ApiRequest.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "reviews"
})
@Generated("jsonschema2pojo")
@Data
public class Result {

    @JsonProperty("reviews")
    private List<GoogleReview> googleReviews = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("reviews")
    public List<GoogleReview> getGoogleReviews() {
        return googleReviews;
    }

    @JsonProperty("reviews")
    public void setGoogleReviews(List<GoogleReview> googleReviews) {
        this.googleReviews = googleReviews;
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
