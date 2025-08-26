package com.frizzer.swing.repository;

import com.frizzer.swing.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByIdBetween(Long firstId, Long lastId);
}
