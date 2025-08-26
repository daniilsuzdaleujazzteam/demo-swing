package com.frizzer.swing.logic.repository;

import com.frizzer.swing.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByIdBetween(Long firstId, Long lastId);
}
