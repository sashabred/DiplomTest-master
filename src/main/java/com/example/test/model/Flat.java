package com.example.test.model;

import com.example.test.authorization.model.Role;
import com.example.test.authorization.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flat {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long flatId;
    private String flatNum;
    private String type;


    @ManyToOne(optional=false, cascade = CascadeType.ALL)
    @JoinColumn(name="HouseId",  nullable=false)
    private House house;

    @OneToMany(cascade = {CascadeType.ALL},orphanRemoval = true,fetch = FetchType.EAGER,mappedBy="flat")
    private List<Meter> meters = new ArrayList<>();

    public Flat(String flatNum, String type, House house) {
        this.flatNum = flatNum;
        this.type = type;
        this.house = house;
    }

    public String getFlatAddress() {

        return house.getAddress()+flatNum;
    }


}
