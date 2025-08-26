package com.frizzer.swing.repository;

import com.frizzer.swing.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByIdBetween(Long firstId, Long lastId);
}
