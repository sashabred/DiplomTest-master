package com.example.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meter {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long meterId;

    private MeterType meterType;
    private Number volume;
    private String countType;

    @ManyToOne(optional=false, cascade = CascadeType.ALL)
    @JoinColumn(name="flatId",  nullable=false)
    private Flat flat;
}
