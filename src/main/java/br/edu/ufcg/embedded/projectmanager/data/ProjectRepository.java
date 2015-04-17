package br.edu.ufcg.embedded.projectmanager.data;


import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ufcg.embedded.projectmanager.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
