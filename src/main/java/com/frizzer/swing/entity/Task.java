package com.frizzer.swing.entity;


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
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String description;
    LocalDate date;
    @ManyToOne
    @JoinColumn(name = "priority_id", nullable = false)
    Priority priority;

    public String[] toRow() {
        return new String[]{String.valueOf(this.id), this.description, this.date.toString(), this.priority.getName()};
    }

}
