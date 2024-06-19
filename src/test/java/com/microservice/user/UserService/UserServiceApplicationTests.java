package com.microservice.user.UserService;

import com.microservice.user.UserService.domain.Hotel;
import com.microservice.user.UserService.domain.Rating;
import com.microservice.user.UserService.external.service.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
//class UserServiceApplicationTests {
//
//	@Test
//	void contextLoads() {
//	}
//
//	@Autowired
//	RatingService ratingService;
//
//	@Test
//	public void saveRating() {
//
//		Rating rating = Rating.builder().ratingId(10L).userId(20L).hotelId(10)
//				.feedback("This is created using feign client").build();
//		Rating savedRating = ratingService.saveRatingForUser(rating);
//		System.out.println(savedRating);
//	}
//}
//