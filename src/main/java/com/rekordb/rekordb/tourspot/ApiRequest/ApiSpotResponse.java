package com.rekordb.rekordb.tourspot.ApiRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiSpotResponse {
    public response response;
    static class response{
        public body body;
        static class body{
            public items items;
            static class items{
                public ArrayList<ApiItemDTO> item;
            }
        }
    }

    public List<ApiItemDTO> getItems(){
        return this.response.body.items.item;
    }

}
