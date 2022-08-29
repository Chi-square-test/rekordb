package com.rekordb.rekordb.tourspot.ApiRequest;


import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.CommonItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiCommonResponse {
    public response response;
    static class response{
        public body body;
        static class body{
            public items items;
            static class items{
                public List<CommonItem> item;
            }
        }
    }

    public List<CommonItem> getItems(){
        return this.response.body.items.item;
    }

}
