
package com.rekordb.rekordb.tourspot.ApiRequest.juso;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "errorMessage",
    "countPerPage",
    "totalCount",
    "errorCode",
    "currentPage"
})
@Generated("jsonschema2pojo")
public class Common {

    @JsonProperty("errorMessage")
    private String errorMessage;
    @JsonProperty("countPerPage")
    private String countPerPage;
    @JsonProperty("totalCount")
    private String totalCount;
    @JsonProperty("errorCode")
    private String errorCode;
    @JsonProperty("currentPage")
    private String currentPage;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    @JsonProperty("errorMessage")
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @JsonProperty("countPerPage")
    public String getCountPerPage() {
        return countPerPage;
    }

    @JsonProperty("countPerPage")
    public void setCountPerPage(String countPerPage) {
        this.countPerPage = countPerPage;
    }

    @JsonProperty("totalCount")
    public String getTotalCount() {
        return totalCount;
    }

    @JsonProperty("totalCount")
    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    @JsonProperty("errorCode")
    public String getErrorCode() {
        return errorCode;
    }

    @JsonProperty("errorCode")
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @JsonProperty("currentPage")
    public String getCurrentPage() {
        return currentPage;
    }

    @JsonProperty("currentPage")
    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
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
