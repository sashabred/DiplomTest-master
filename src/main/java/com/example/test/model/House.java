package com.example.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class House {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @ManyToOne(optional=false, cascade = CascadeType.ALL)
    @JoinColumn(name="addressID",  nullable=false)
    private Address address;
    private String num;

    private String type;

    private Double square;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getSquare() {
        return square;
    }

    public void setSquare(Double square) {
        this.square = square;
    }

    public House(Address address, String num) {
        this.address = address;
        this.num = num;
    }

    public String getAddress() {
        return address.getFullAddress();
    }
    public String getFullAddress() {
        return address.getFullAddress() + " д."+num;
    }

    public Long getAddressID() {

        return address.getId();
    }


    public House(String city, String street, String num) {
        address.setCity(city);
        address.setStreet(street);
        this.num = num;
    }

    public String getCity() {
        return address.getCity();
    }
    public void setCity(String city) {
        address.setCity(city);
    }

    public String getStreet() {
        return address.getStreet();
    }
    public void setStreet(String street) {
         address.setStreet(street);
    }

    public List<Flat> getFlats() {
        return flats;
    }

    public void setFlats(List<Flat> flats) {
        this.flats = flats;
    }

    @OneToMany(cascade = {CascadeType.ALL},orphanRemoval = true,fetch = FetchType.EAGER,mappedBy="house")
    private List<Flat> flats = new ArrayList<>();
}
