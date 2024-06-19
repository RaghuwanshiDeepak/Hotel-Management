package com.microservice.user.UserService.service;

import com.microservice.user.UserService.domain.Hotel;
import com.microservice.user.UserService.domain.Rating;
import com.microservice.user.UserService.domain.UserDetails;
import com.microservice.user.UserService.exception.ResourceNotFoundException;
import com.microservice.user.UserService.external.service.HotelService;
import com.microservice.user.UserService.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {


    @Autowired
    RestTemplate restTemplate;
    Logger loggerFactory = LoggerFactory.getLogger(UserServiceImplementation.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelService hotelService;

    @Override
    public UserDetails newUser(UserDetails user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails getUserByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("No user available with the given user id !!!!"));
    }

    @Override
    public List<UserDetails> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public boolean deleteUser(Long userId) {
        boolean isDeleted = false;
        try {
            userRepository.deleteById(userId);
            isDeleted = true;
        } catch (Exception ex) {
            throw new ResourceNotFoundException("No Data present to delete with the given user id");
        }
        return isDeleted;
    }

    @Override
    public UserDetails getUserDetailsWithRatingAndHotelDetails(Long userId) {
        //get user from database with the help of user repository
        UserDetails userDetails = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("No user available with the given user id !!!!"));
        //Here we will fetch rating from Rating-service
        //We need to call this service to get rating http://localhost:8081//getRatingByUserId/%7BuserId%7D
        //if any service call another service it is communicate through http client
//        Rating rating = restTemplate.getForObject("http://localhost:8080/rating/getRatingByUserId/" + userDetails.getUserId(), Rating.class);
//        loggerFactory.info("ObjectValue {}", rating);
//        assert rating != null;
//        userDetails.setUserRating(List.of(rating));
        return userDetails;
    }

    @Override
    public List<UserDetails> getAllUsersRatings() {
        Rating rating = null;
        Hotel hotel = null;
        List<UserDetails> listOfAllUser = userRepository.findAll();
        if (!listOfAllUser.isEmpty()) {
            for (UserDetails userDetails : listOfAllUser) {
                int userId = Math.toIntExact(userDetails.getUserId());
                rating = restTemplate.getForObject("http://localhost:8080/rating/getRatingByUserId/" + userId, Rating.class);
                if (rating != null && rating.getHotelId() != null) {
//                    hotel = restTemplate.getForObject("http://localhost:9545/getById/" + rating.getHotelId(), Hotel.class);
                    hotel = hotelService.getHotel(rating.getHotelId());
                    rating.setHotel(hotel);
                    listOfAllUser.get(listOfAllUser.indexOf(userDetails)).setUserRating(Collections.singletonList(rating));
                }
            }
        }
        return listOfAllUser;
    }

}
