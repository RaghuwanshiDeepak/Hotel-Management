package com.microservice.user.UserService.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Rating {

    private Long ratingId;
    private Long userId;
    private Integer hotelId;
    private  Long rating;
    private  String feedback;
    private Hotel hotel;
}
