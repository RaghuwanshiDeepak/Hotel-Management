package com.microservice.user.UserService.external.service;

import com.microservice.user.UserService.domain.Hotel;
import com.microservice.user.UserService.domain.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "HOTEL-SERVICE")
@Service
public interface HotelService {

    @GetMapping("hotel/getById/{hotelId}")
    Hotel getHotel(@PathVariable("hotelId") Integer hotelId);


}
