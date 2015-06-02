package br.edu.ufcg.embedded.projectmanager.data;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ufcg.embedded.projectmanager.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
