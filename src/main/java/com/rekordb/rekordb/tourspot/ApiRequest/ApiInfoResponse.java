package com.rekordb.rekordb.tourspot.ApiRequest;

import com.rekordb.rekordb.tourspot.domain.TourSpotDetail.DetailItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiInfoResponse {
    public response response;
    static class response{
        public body body;
        static class body{
            public items items;
            static class items{
                public List<DetailItem> item;
            }
        }
    }

    public List<DetailItem> getItems(){
        return this.response.body.items.item;
    }

}
