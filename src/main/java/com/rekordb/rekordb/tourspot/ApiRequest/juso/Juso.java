
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
    "zipNo",
    "emdNm",
    "rn",
    "jibunAddr",
    "siNm",
    "sggNm",
    "admCd",
    "udrtYn",
    "lnbrMnnm",
    "roadAddr",
    "korAddr",
    "lnbrSlno",
    "buldMnnm",
    "bdKdcd",
    "rnMgtSn",
    "liNm",
    "mtYn",
    "buldSlno"
})
@Generated("jsonschema2pojo")
public class Juso {

    @JsonProperty("zipNo")
    private String zipNo;
    @JsonProperty("emdNm")
    private String emdNm;
    @JsonProperty("rn")
    private String rn;
    @JsonProperty("jibunAddr")
    private String jibunAddr;
    @JsonProperty("siNm")
    private String siNm;
    @JsonProperty("sggNm")
    private String sggNm;
    @JsonProperty("admCd")
    private String admCd;
    @JsonProperty("udrtYn")
    private String udrtYn;
    @JsonProperty("lnbrMnnm")
    private String lnbrMnnm;
    @JsonProperty("roadAddr")
    private String roadAddr;
    @JsonProperty("korAddr")
    private String korAddr;
    @JsonProperty("lnbrSlno")
    private String lnbrSlno;
    @JsonProperty("buldMnnm")
    private String buldMnnm;
    @JsonProperty("bdKdcd")
    private String bdKdcd;
    @JsonProperty("rnMgtSn")
    private String rnMgtSn;
    @JsonProperty("liNm")
    private String liNm;
    @JsonProperty("mtYn")
    private String mtYn;
    @JsonProperty("buldSlno")
    private String buldSlno;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("zipNo")
    public String getZipNo() {
        return zipNo;
    }

    @JsonProperty("zipNo")
    public void setZipNo(String zipNo) {
        this.zipNo = zipNo;
    }

    @JsonProperty("emdNm")
    public String getEmdNm() {
        return emdNm;
    }

    @JsonProperty("emdNm")
    public void setEmdNm(String emdNm) {
        this.emdNm = emdNm;
    }

    @JsonProperty("rn")
    public String getRn() {
        return rn;
    }

    @JsonProperty("rn")
    public void setRn(String rn) {
        this.rn = rn;
    }

    @JsonProperty("jibunAddr")
    public String getJibunAddr() {
        return jibunAddr;
    }

    @JsonProperty("jibunAddr")
    public void setJibunAddr(String jibunAddr) {
        this.jibunAddr = jibunAddr;
    }

    @JsonProperty("siNm")
    public String getSiNm() {
        return siNm;
    }

    @JsonProperty("siNm")
    public void setSiNm(String siNm) {
        this.siNm = siNm;
    }

    @JsonProperty("sggNm")
    public String getSggNm() {
        return sggNm;
    }

    @JsonProperty("sggNm")
    public void setSggNm(String sggNm) {
        this.sggNm = sggNm;
    }

    @JsonProperty("admCd")
    public String getAdmCd() {
        return admCd;
    }

    @JsonProperty("admCd")
    public void setAdmCd(String admCd) {
        this.admCd = admCd;
    }

    @JsonProperty("udrtYn")
    public String getUdrtYn() {
        return udrtYn;
    }

    @JsonProperty("udrtYn")
    public void setUdrtYn(String udrtYn) {
        this.udrtYn = udrtYn;
    }

    @JsonProperty("lnbrMnnm")
    public String getLnbrMnnm() {
        return lnbrMnnm;
    }

    @JsonProperty("lnbrMnnm")
    public void setLnbrMnnm(String lnbrMnnm) {
        this.lnbrMnnm = lnbrMnnm;
    }

    @JsonProperty("roadAddr")
    public String getRoadAddr() {
        return roadAddr;
    }

    @JsonProperty("roadAddr")
    public void setRoadAddr(String roadAddr) {
        this.roadAddr = roadAddr;
    }

    @JsonProperty("korAddr")
    public String getKorAddr() {
        return korAddr;
    }

    @JsonProperty("korAddr")
    public void setKorAddr(String korAddr) {
        this.korAddr = korAddr;
    }

    @JsonProperty("lnbrSlno")
    public String getLnbrSlno() {
        return lnbrSlno;
    }

    @JsonProperty("lnbrSlno")
    public void setLnbrSlno(String lnbrSlno) {
        this.lnbrSlno = lnbrSlno;
    }

    @JsonProperty("buldMnnm")
    public String getBuldMnnm() {
        return buldMnnm;
    }

    @JsonProperty("buldMnnm")
    public void setBuldMnnm(String buldMnnm) {
        this.buldMnnm = buldMnnm;
    }

    @JsonProperty("bdKdcd")
    public String getBdKdcd() {
        return bdKdcd;
    }

    @JsonProperty("bdKdcd")
    public void setBdKdcd(String bdKdcd) {
        this.bdKdcd = bdKdcd;
    }

    @JsonProperty("rnMgtSn")
    public String getRnMgtSn() {
        return rnMgtSn;
    }

    @JsonProperty("rnMgtSn")
    public void setRnMgtSn(String rnMgtSn) {
        this.rnMgtSn = rnMgtSn;
    }

    @JsonProperty("liNm")
    public String getLiNm() {
        return liNm;
    }

    @JsonProperty("liNm")
    public void setLiNm(String liNm) {
        this.liNm = liNm;
    }

    @JsonProperty("mtYn")
    public String getMtYn() {
        return mtYn;
    }

    @JsonProperty("mtYn")
    public void setMtYn(String mtYn) {
        this.mtYn = mtYn;
    }

    @JsonProperty("buldSlno")
    public String getBuldSlno() {
        return buldSlno;
    }

    @JsonProperty("buldSlno")
    public void setBuldSlno(String buldSlno) {
        this.buldSlno = buldSlno;
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
