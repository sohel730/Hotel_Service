package com.sohel.hotel.mapper;

import com.sohel.hotel.dto.RoomDetailsResponseDto;
import com.sohel.hotel.dto.RoomRequestDto;
import com.sohel.hotel.entity.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomDetailsResponseDto toDto(Room room);

    Room roomToRoomRequestdto(RoomRequestDto roomRequestDto);
}
