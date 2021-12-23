package com.dh.services;

import com.dh.models.entity.Usuario;

public interface IUsuarioService {

    public Usuario findByUsername(String username);

    public Usuario update(Usuario usuario, Long id);
}
