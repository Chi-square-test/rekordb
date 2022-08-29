package com.rekordb.rekordb.tourspot.ApiRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiIntroResponse {
    public response response;
    static class response{
        public body body;
        static class body{
            public items items;
            static class items{
                public List<Map<String,String>> item;
            }
        }
    }

    public List<Map<String,String>> getItems(){
        return this.response.body.items.item;
    }

}
