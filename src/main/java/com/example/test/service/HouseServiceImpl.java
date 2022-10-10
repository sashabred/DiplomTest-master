package com.example.test.service;

import com.example.test.model.Address;
import com.example.test.model.House;
import com.example.test.repository.HouseRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


    @Service
    @RequiredArgsConstructor
    @Transactional
    @Slf4j
    public class HouseServiceImpl implements DataService {

        private final HouseRepo houseRepo;


        @Override
        public Object save(Object o) {

            return houseRepo.save((House) o);
        }


        public void saveHome(Address address, String num) {
            House addH = new House(address, num);
            houseRepo.save(addH);
        }

        public House update(House house) {
            if (house.getId() == null) {
                house = houseRepo.save(house);

                return house;
            } else {
                Optional<House> sOptional = houseRepo.findById(house.getId());
                if (sOptional != null) {
                    House house2 = sOptional.get();
                    house2.setCity(house.getCity());
                    house2.setStreet(house.getStreet());
                    house2.setNum(house.getNum());
                    house2 = houseRepo.save(house2);
                    return house2;
                } else {
                    house = houseRepo.save(house);
                    return house;
                }
            }
        }


        @Override
        public List<House> getAll() {
            return houseRepo.findAll();
        }

        @Override
        public Object getById(long id) {
            Optional<House> optionalHouse = houseRepo.findById(id);
            House house= null;
            if (optionalHouse.isPresent()) {
                house = optionalHouse.get();
            } else {
                throw new RuntimeException("House not found for id : " + id);
            }
            return house;
        }

        @Override
        public void deleteById(long id) {
            this.houseRepo.deleteById(id);
        }
    }
