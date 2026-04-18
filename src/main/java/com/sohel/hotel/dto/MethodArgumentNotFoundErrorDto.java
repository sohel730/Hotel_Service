package com.sohel.hotel.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MethodArgumentNotFoundErrorDto {



    private int status;
    private String error;
    private Map<String, String> validationErrors = new HashMap<>();
    private String path;
    private LocalDateTime timestamp;







}
