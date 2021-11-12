package com.formacionbdi.springboot.app.usuarios.repositories;

import com.formaciobdi.springboot.app.usuarios.commons.models.Usuario;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;


//@Repository
@RepositoryRestResource(path = "usuarios") //CRUD AUTOMATICO????
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {

    @RestResource(path = "buscar-username")
    Usuario findByUsername(@Param("username") String username);

    @Query("select u from Usuario u where u.username=?1")
    Usuario obtenerPorUsername(String username);

}
