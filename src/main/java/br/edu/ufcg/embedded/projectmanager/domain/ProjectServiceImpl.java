package br.edu.ufcg.embedded.projectmanager.domain;


import org.springframework.stereotype.Service;

import br.edu.ufcg.embedded.projectmanager.data.ProjectRepository;
import br.edu.ufcg.embedded.projectmanager.generic.GenericServiceImpl;

@Service
public class ProjectServiceImpl extends
		GenericServiceImpl<Project, ProjectRepository> implements ProjectService {

}
