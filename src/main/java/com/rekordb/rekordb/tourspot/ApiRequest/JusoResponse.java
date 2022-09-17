package com.rekordb.rekordb.tourspot.ApiRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JusoResponse {
    public Results results;
    static class Results{
        public Common commmon;
        public Juso[] juso;
        static class Common {
            public int totalCount;
        }
        static class Juso{
            public String roadAddr;
            public String sggNm;
        }
    }
}
