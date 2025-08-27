package com.frizzer.swing.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, unique = true)
    String title;
    @Column(nullable = false)
    String description;
    @Column(nullable = false)
    LocalDate date;
    @ManyToOne
    @JoinColumn(name = "priority_id", nullable = false)
    Priority priority;

    public String[] toRow() {
        return new String[]{this.title, this.description, this.date.toString(), this.priority.getName()};
    }

}
