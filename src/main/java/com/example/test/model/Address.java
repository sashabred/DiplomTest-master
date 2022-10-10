package com.example.test.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = AUTO)

    private Long id;
    private UUID guid;
    private String Country = "Россия";
    private String City;
    private String Street;

    public Address( String country, String city, String street) {
        Country = country;
        City = city;
        Street = street;
    }

    public Address( String city, String street) {

        City = city;
        Street = street;
    }

    @OneToMany(cascade = {CascadeType.ALL},orphanRemoval = true,fetch = FetchType.EAGER,mappedBy="address")
    private List<House> house = new ArrayList<>();

   public String getFullAddress() {
       return Country + " г." +City + " ул." +Street;
   }

    public String getShortAddress() {
        return "г."+City + " ул." +Street;
    }
}