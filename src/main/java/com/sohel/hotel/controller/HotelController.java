package com.sohel.hotel.controller;

import com.sohel.hotel.dto.HotelDetailsResponseDto;
import com.sohel.hotel.dto.HotelRequestDto;
import com.sohel.hotel.dto.PagedResponse;
import com.sohel.hotel.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotel")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final HotelService hotelService;


    @GetMapping("/{id}")
    public ResponseEntity<HotelDetailsResponseDto> getHotelById(@PathVariable Long id) {

        return ResponseEntity.ok(hotelService.getHotelById(id));

    }


    @GetMapping("/")
    public ResponseEntity<PagedResponse> getAllHotelWithPagination(

            @RequestParam(value = "pageNo",required = false, defaultValue ="0") int pageNo,
            @RequestParam(value = "pageSize", required = false,defaultValue = "10") int pageSize,
            @RequestParam(value = "sortField",required = false,defaultValue = "id") String sortField,
            @RequestParam(value = "sortDirection",required = false,defaultValue = "asc") String sortDirection
    )
    {
        log.info("Received request to fetch hotels: pageNo={}, pageSize={}, sortField={}, sortDirection={}",
                pageNo, pageSize, sortField, sortDirection);

        return ResponseEntity.ok(hotelService.getAllHotelWithPagination(pageNo,pageSize,sortField,sortDirection));
    }


    @PostMapping("/")
    public  ResponseEntity<HotelDetailsResponseDto> createHotel(@Valid @RequestBody HotelRequestDto hotelRequestDto){

        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.createHotel(hotelRequestDto));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Long id)
    {

        hotelService.deleteByHotelId(id);

        return ResponseEntity.noContent().build();

    }





    @PutMapping("/{id}")
    public ResponseEntity<HotelDetailsResponseDto> updateHotel(
                                                  @PathVariable Long id,
                                                  @Valid @RequestBody HotelRequestDto hotelRequestDto
                                                  )

    {
        log.info("Received request to update hotel with id: {}",id);

        HotelDetailsResponseDto  updatedHotel=hotelService.updateHotel(hotelRequestDto,id);

        log.info("Returning updated hotel with id: {}",id);

        return ResponseEntity.ok(updatedHotel);
    }




}
