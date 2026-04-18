package com.sohel.hotel.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="room")
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private Long id;

  @Column(name="room_number")
  private String roomNumber;

  @Column(name="type")
  private String type;

  @Column(name="price")
  private Double price;


  @ManyToOne
  @JoinColumn(name="hotel_id")
  private  Hotel hotel;






}
