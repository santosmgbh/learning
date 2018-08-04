package br.com.almir.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.almir.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByNome(String nome);

	User findByLogin(String login);

}
