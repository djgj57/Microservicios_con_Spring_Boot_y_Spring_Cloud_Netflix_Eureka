package com.dh.models.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 30)
    private String nombre;

    private static final long serialVersionUID = 1L;
}