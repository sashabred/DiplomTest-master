package com.example.test.service;

import com.example.test.model.Flat;
import com.example.test.repository.FlatRepo;
import com.example.test.repository.HouseRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FlatServiceImpl implements DataService{

    private final FlatRepo flatRepo;

    @Override
    public Object save(Object o) {
        return flatRepo.save((Flat) o);
    }

    @Override
    public List<Flat> getAll() {
        return flatRepo.findAll();
    }

    @Override
    public Object getById(long id) {
        return flatRepo.findById(id);
    }

    @Override
    public void deleteById(long id) {
        flatRepo.deleteById(id);
    }


    public void deleteFlatList(Long[] flatId) {
        flatRepo.deleteFlatList(Arrays.asList(flatId));
    }
}
