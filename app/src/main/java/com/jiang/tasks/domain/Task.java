package com.jiang.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @Column(name = "status", nullable = false)
    private String status;

}
