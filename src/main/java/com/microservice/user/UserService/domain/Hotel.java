package com.microservice.user.UserService.domain;

import com.microservice.user.UserService.util.RoomType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Hotel {

    private Integer hotelId;

    private String  name;

    private String  location;

    private String  about;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private String contactEmail;

    private Long contactNumber;
}
