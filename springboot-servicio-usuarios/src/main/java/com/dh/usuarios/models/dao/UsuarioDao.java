package com.dh.usuarios.models.dao;

import com.dh.usuarios.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "usuarios")
public interface UsuarioDao extends JpaRepository<Usuario, Long> {

//    public Usuario findByUsernameAndEmail(String username, String email);


//        Ejemplo para utilizarlo: http://localhost:8090/api/usuarios/usuarios/search/buscar-username?username=andres
        @RestResource(path = "buscar-username")
        public Usuario findByUsername(@Param("username") String username);

//        Ejemplo para utilizarlo: http://localhost:8090/api/usuarios/usuarios/search/obtenerPorUserName?username=andres
        @Query(value = "select u from Usuario u where u.username = ?1", nativeQuery = false)
        public Usuario obtenerPorUserName(String username);

}
