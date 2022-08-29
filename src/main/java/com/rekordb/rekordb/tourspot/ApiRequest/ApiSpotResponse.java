package com.rekordb.rekordb.tourspot.ApiRequest;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiSpotResponse<T> {
    public response response;


    public List<T> getItems(){
        return this.response.body.items.item;
    }

    class response{
        public body body;

    }
    class body{
        public items items;

    }
    class items{
        public List<T> item;
    }

}
