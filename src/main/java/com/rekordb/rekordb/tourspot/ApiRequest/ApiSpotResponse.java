package com.rekordb.rekordb.tourspot.ApiRequest;

import lombok.*;

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
            public int totalCount;
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
