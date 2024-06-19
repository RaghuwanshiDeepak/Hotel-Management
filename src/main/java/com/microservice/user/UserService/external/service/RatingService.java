package com.microservice.user.UserService.external.service;

import com.microservice.user.UserService.domain.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "RATING-SERVICE")
@Service
public interface RatingService {

    @PostMapping("/save-rating")
    Rating saveRatingForUser(@RequestBody Rating rating);

    @PutMapping("/update-rating/{ratingId}")
    Rating updateRatingBasedOnUserId(@PathVariable("ratingId") Long ratingId, Rating rating);

    @DeleteMapping("delete-rating/{ratingId}")
    void deleteRating(@PathVariable("ratingId") Long ratingId);

    @GetMapping("/getRatingByUserId/{ratingId}")
    Rating getRatingByRatingId(Long ratingId);

}
