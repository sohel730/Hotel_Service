package com.sohel.hotel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="location")
    private String location;

    @Column(name="rating")
    private Double rating;

    @Column(name="created_at")
    private LocalDateTime createdAt= LocalDateTime.now();


  @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL ,orphanRemoval = true)
  private List<Room> rooms;





}
