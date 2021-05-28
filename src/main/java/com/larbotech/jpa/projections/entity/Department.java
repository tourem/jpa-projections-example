package com.larbotech.jpa.projections.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "department")
@Getter
@Setter
public class Department implements Serializable {

    private static final long serialVersionUID = 796133818701506050L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", length = 50)
    private String name;
}
