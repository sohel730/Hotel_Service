package com.sohel.hotel.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequestDto {



    @NotBlank(message = "Hotel name is required")
    private String  name;

    @NotBlank(message = "location name is required")
    private String location;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be >=1")
    @Max(value = 5, message = "Rating must be <=5")
    private Double rating;



    private List<RoomRequestDto> roomDetailsRequestDto;


}
