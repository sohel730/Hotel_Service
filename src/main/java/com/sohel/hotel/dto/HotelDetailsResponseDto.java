package com.sohel.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDetailsResponseDto {

    private Long id;
    private String  name;
    private String location;
    private Double rating;
    private LocalDateTime createdAt;

    private List<RoomDetailsResponseDto> roomDetailsResponseDto;


}
