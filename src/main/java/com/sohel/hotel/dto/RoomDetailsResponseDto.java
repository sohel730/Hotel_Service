package com.sohel.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailsResponseDto {


    private Long id;
    private String roomNumber;
    private String type;
    private Double price;
}
