package com.microservice.user.UserService.service;

import com.microservice.user.UserService.domain.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    UserDetails newUser(UserDetails user);

    UserDetails getUserByUserId(Long userId);

    List<UserDetails> getAllUser();

    boolean deleteUser(Long userId);

    UserDetails getUserDetailsWithRatingAndHotelDetails(Long userId);

    List<UserDetails> getAllUsersRatings();

     
}
