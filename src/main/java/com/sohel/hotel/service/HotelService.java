package com.sohel.hotel.service;

import com.sohel.hotel.dto.HotelDetailsResponseDto;
import com.sohel.hotel.dto.HotelRequestDto;
import com.sohel.hotel.dto.PagedResponse;

public interface HotelService  {

    HotelDetailsResponseDto getHotelById(Long id);

    PagedResponse getAllHotelWithPagination(int pageNo , int pageSize, String sortField, String sortDirection);

    HotelDetailsResponseDto createHotel(HotelRequestDto hotelRequestDto);

    void deleteByHotelId(Long id);

    HotelDetailsResponseDto updateHotel(HotelRequestDto hotelRequestDto, Long id);


}
