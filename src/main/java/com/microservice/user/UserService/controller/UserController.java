package com.microservice.user.UserService.controller;

import com.microservice.user.UserService.domain.UserDetails;
import com.microservice.user.UserService.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;


    @PostMapping("/new-user")
    private ResponseEntity<UserDetails> createUser(@RequestBody UserDetails user) {
        ResponseEntity<UserDetails> response;
        if (Objects.nonNull(user)) {
            UserDetails newUser = userService.newUser(user);
            response = ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } else {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return response;
    }

    @GetMapping("/getAllUsers")
    private ResponseEntity<List<UserDetails>> getAllUsers() {
        ResponseEntity<List<UserDetails>> response;
        List<UserDetails> allUser = userService.getAllUser();
        if (Objects.nonNull(allUser) && !allUser.isEmpty()) {
            response = ResponseEntity.status(HttpStatus.OK).body(allUser);
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return response;
    }

    @DeleteMapping("deleteUser/{UserId}")
    private ResponseEntity<String> deleteHotelByHotelId(@PathVariable Long userId) {
        ResponseEntity<String> response;
        if (Objects.nonNull(userId)) {
            boolean isDeleted = userService.deleteUser(userId);
            if (isDeleted) {
                response = ResponseEntity.status(HttpStatus.ACCEPTED).
                        body("Hotel deleted on the given hotel id");
            } else {
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).
                        body("There is no such hotel present with the given hotel id");
            }
        } else {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body("Please look into the issue there is issue present inside ur code");
        }
        return response;
    }

    @GetMapping("/getUser/{UserId}")
    private ResponseEntity<UserDetails> getUserByUserId(@PathVariable Long userId) {
        ResponseEntity<UserDetails> response;
        UserDetails user = Objects.nonNull(userId) ? userService.getUserByUserId(userId) : null;
        if (Objects.nonNull(user)) {
            response = ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            response = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }
        return response;
    }

    @GetMapping("/getUserDetailsWithRatingAndHotelDetails/{userId}")
    @CircuitBreaker(name = "ratingHotel",fallbackMethod = "ratingHotelFallBack")
    private ResponseEntity<UserDetails> getUserDetailsWithRatingAndHotelDetails(@PathVariable Long userId,Exception ex){
        ResponseEntity<UserDetails> response;
        UserDetails user = Objects.nonNull(userId) ? userService.getUserDetailsWithRatingAndHotelDetails(userId) : null;
        if (Objects.nonNull(user)) {
            response = ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            response = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }
        return response;
    }

//    creating fallback method for circuit breaker
    public ResponseEntity<UserDetails> ratingHotelFallBack(Long userId) {
//        log.info("Fall back is executed because of rating service is down {}", ex.getMessage());
        UserDetails userDetail = UserDetails.builder().userId(userId)
                .name(null)
                .UserRating(null)
                .contactNumber(null)
                .email(null)
                .build();
        return new ResponseEntity<>(userDetail, HttpStatus.OK);
    }

    @GetMapping("/getAllUsersRatings")
    private ResponseEntity<List<UserDetails>> getAllUsersRatingsWithHotels() {
        ResponseEntity<List<UserDetails>> response;
        List<UserDetails> allUser = userService.getAllUsersRatings();
        if (Objects.nonNull(allUser) && !allUser.isEmpty()) {
            response = ResponseEntity.status(HttpStatus.OK).body(allUser);
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return response;
    }


}
