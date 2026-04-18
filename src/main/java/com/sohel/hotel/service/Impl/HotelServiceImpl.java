package com.sohel.hotel.service.Impl;

import com.sohel.hotel.dto.HotelDetailsResponseDto;
import com.sohel.hotel.dto.HotelRequestDto;
import com.sohel.hotel.dto.PagedResponse;
import com.sohel.hotel.entity.Hotel;
import com.sohel.hotel.entity.Room;
import com.sohel.hotel.exception.ResourceNotFoundException;
import com.sohel.hotel.mapper.HotelMapper;
import com.sohel.hotel.repository.HotelRepository;
import com.sohel.hotel.repository.RoomRepository;
import com.sohel.hotel.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final HotelMapper hotelMapper;


    @Cacheable(value = "hotels",key="#id")
    @Override
    public HotelDetailsResponseDto getHotelById(Long id) {


        log.info("Fetching hotel with id: {}", id);

        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> {

            log.error("Hotel not found with this id: {}", id);
            return new ResourceNotFoundException("hotel not found");
        });


        log.info("Hotel fetched successfully with id: {}", id);

        return hotelMapper.toDto(hotel);

    }

    @Cacheable(value = "hotels",key = "#pageNo +' _' + #pageSize + '_' + #sortField + '-' + #sortDirection" )
    @Override
    public PagedResponse getAllHotelWithPagination(int pageNo, int pageSize, String sortField, String sortDirection) {

        log.info("Fetching hotels form Db");

        // handling edge cases here for the parameters

        pageNo = (pageNo < 0) ? pageNo : 0;
        pageSize = (pageSize <= 0) ?10:pageSize;
        sortField = (sortField != null && !sortField.isBlank()) ? sortField : "id";
        sortDirection = (sortDirection != null && sortDirection.equalsIgnoreCase("desc")) ? "desc" : "asc";


        Sort sort=Sort.by(sortField);

        sort="desc".equalsIgnoreCase(sortDirection)? sort.descending():sort.ascending();

        Pageable pageable= PageRequest.of(pageNo,pageSize,sort);

        Page<Hotel> pageHotel=hotelRepository.findAll(pageable);

        log.info("Fetched Hotels Successfully form Db");

       List<HotelDetailsResponseDto> listHotel= pageHotel.getContent().stream().map(hotelMapper::toDto).toList();


       return new PagedResponse(
             listHotel,
             pageHotel.getNumber(),
             pageHotel.getSize(),
             pageHotel.getTotalElements(),
             pageHotel.getTotalPages(),
             pageHotel.isLast()
       );
    }

    @CachePut(value = "hotels", key="#result.id")
    @Override
    public HotelDetailsResponseDto createHotel(HotelRequestDto hotelRequestDto) {


        log.info("Creating hotel  record in Db");

        Hotel hotel=hotelMapper.hotelRequestDtoToHotel(hotelRequestDto);

        if(hotel.getRooms()!=null){

            for(Room r:hotel.getRooms()){

                r.setHotel(hotel);
            }
        }

        Hotel saved=hotelRepository.save(hotel);

        log.info("Hotel record created in db successfully with id: {}",saved.getId());
        return hotelMapper.toDto(saved);
    }


    @CacheEvict(value = "hotels", key = "#id")
    @Override
    public void deleteByHotelId(Long id) {

        log.info("Deleting Hotel with id: {}", id);

        if(!hotelRepository.existsById(id))
        {

            log.info("Hotel not found with id: {}", id);

            throw new ResourceNotFoundException("Hotel not found: " + id);
        }


        hotelRepository.deleteById(id);

        log.info("Hotel deleted Successfully with id: {}" , id);
    }

    @CachePut(value = "hotels", key="#id")
    @Transactional
    @Override
    public HotelDetailsResponseDto updateHotel(HotelRequestDto hotelRequestDto, Long id) {

         log.info("Updating Hotel with id: {}",id );

         Hotel incomingHotel=hotelMapper.hotelRequestDtoToHotel(hotelRequestDto);

         Hotel existingHotel=hotelRepository.findById(id).orElseThrow(()->{

             log.error("Hotel not found with id: {}", id);
             return new ResourceNotFoundException("Hotel not found with id: {}"+id);
         });

         existingHotel.setName(incomingHotel.getName());
         existingHotel.setLocation(incomingHotel.getLocation());
         existingHotel.setRating(incomingHotel.getRating());


         List<Room> updatedRooms=new ArrayList<>();

         if(incomingHotel.getRooms()!=null){


             for(Room r:incomingHotel.getRooms())
             {


                if(r.getId()!=null){

                    Room existingRoom=roomRepository.findById(r.getId()).orElseThrow(()->{

                        log.error("Room not found with id: {}",r.getId());
                        return new ResourceNotFoundException("Room not found with id: "+r.getId());
                    });

                    existingRoom.setRoomNumber(r.getRoomNumber());
                    existingRoom.setType(r.getType());
                    existingRoom.setPrice(r.getPrice());
                    updatedRooms.add(existingRoom);


                }
                else {
                    r.setHotel(existingHotel);
                    updatedRooms.add(r);
                }
             }

            existingHotel.setRooms(updatedRooms);


         }

      Hotel savedHotel=hotelRepository.save(existingHotel);

         log.info("Hotel updated successfully with id: {}", id);

        return hotelMapper.toDto(savedHotel);
    }


}
