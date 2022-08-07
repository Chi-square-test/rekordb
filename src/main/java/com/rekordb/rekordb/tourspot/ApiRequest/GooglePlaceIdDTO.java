package com.rekordb.rekordb.tourspot.ApiRequest;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GooglePlaceIdDTO {
    List<field> candidates;

    @Getter
    public static class field{
        String place_id;
        double rating;

    }
}
