package com.example.test.repository;

import com.example.test.model.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FlatRepo extends JpaRepository<Flat, Long> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM flat WHERE flat_id IN(?1)")
    void deleteFlatList(List<Long> flatId);
}
