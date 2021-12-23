package com.dh.usuarios.models.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 20)
    private String username;

    @Column(length = 60)
    private String password;
    private Boolean enabled;

    private String nombre;
    private String apellido;

    @Column(unique = true, length = 100)
    private String email;

    private Integer intentos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name ="usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"), uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id", "role_id"})})
    private List<Role> roles;

    private static final long serialVersionUID = 1L;
}
