package com.frizzer.swing.logic.repository;

import com.frizzer.swing.domain.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
    @Transactional
    void removePriorityByName(String name);
}
