package com.sohel.hotel.mapper;


import com.sohel.hotel.dto.HotelDetailsResponseDto;
import com.sohel.hotel.dto.HotelRequestDto;
import com.sohel.hotel.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel ="spring", uses = RoomMapper.class)
public interface HotelMapper {


@Mapping(source = "rooms", target = "roomDetailsResponseDto")
HotelDetailsResponseDto toDto(Hotel hotel);

@Mapping(source = "roomDetailsRequestDto", target = "rooms")
Hotel hotelRequestDtoToHotel(HotelRequestDto hotelRequestDto);





}
