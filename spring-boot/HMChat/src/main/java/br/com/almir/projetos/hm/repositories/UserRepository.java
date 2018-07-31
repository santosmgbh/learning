package br.com.almir.projetos.hm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.almir.projetos.hm.model.User;

public interface UserRepository  extends JpaRepository<User, Long>  {

	User findByUsername(String username);

	boolean existsByUsername(String username);

	         
       
}