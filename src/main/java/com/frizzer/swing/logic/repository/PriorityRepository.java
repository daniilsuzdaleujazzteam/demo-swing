package com.frizzer.swing.logic.repository;

import com.frizzer.swing.domain.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
    Optional<Priority> findByName(String name);

    @Transactional
    void removePriorityByName(String name);

    List<Priority> findByIdIn(Collection<Long> ids);
}
