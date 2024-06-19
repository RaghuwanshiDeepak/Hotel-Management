package com.microservice.user.UserService.domain;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class UserDetails {

    @Transient
    List<Rating> UserRating = new ArrayList<Rating>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String email;
    private int age;
    private Long contactNumber;
}
