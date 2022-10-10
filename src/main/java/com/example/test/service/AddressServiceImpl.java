package com.example.test.service;

import com.example.test.model.Address;
import com.example.test.repository.AddressRepo;
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
public class AddressServiceImpl implements DataService{
    private final AddressRepo addressRepo;

    @Override
    public Object save(Object o) {
        return addressRepo.save((Address) o);
    }

    @Override
    public List getAll() {

            return addressRepo.findAll();
    }

    @Override
    public Object getById(long id) {
        Optional<Address> optionalAddr = addressRepo.findById(id);
        Address address= null;
        if (optionalAddr.isPresent()) {
            address = optionalAddr.get();
        } else {
            throw new RuntimeException("House not found for id : " + id);
        }
        return address;
    }

    @Override
    public void deleteById(long id) {
            this.addressRepo.deleteById(id);
    }
}
