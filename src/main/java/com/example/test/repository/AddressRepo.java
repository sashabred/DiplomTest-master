package com.example.test.repository;

import com.example.test.authorization.model.User;
import com.example.test.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
    Optional<Address> findById(Long id);
}
