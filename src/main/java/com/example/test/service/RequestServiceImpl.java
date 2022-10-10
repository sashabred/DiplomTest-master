package com.example.test.service;

import com.example.test.model.Request;
import com.example.test.repository.FlatRepo;
import com.example.test.repository.RequestRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RequestServiceImpl  implements DataService{

    private final RequestRepo requestRepo;

    @Override
    public Object save(Object o) {
        return requestRepo.save((Request) o);
    }

    @Override
    public List getAll() {
        return requestRepo.findAll();
    }

    @Override
    public Object getById(long id) {
        return requestRepo.findById(id);
    }

    @Override
    public void deleteById(long id) {
        requestRepo.deleteById(id);
    }

}
