package br.edu.ufcg.embedded.projectmanager.rest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import br.edu.ufcg.embedded.projectmanager.domain.Project;
import br.edu.ufcg.embedded.projectmanager.generic.RestUtil;

@XmlRootElement
public class ProjectRest implements Serializable {

	private static final long serialVersionUID = 4431345413823505552L;

	private Integer id;
	private String name;
	private String description;

	public ProjectRest() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static ProjectRest fromCore(Project project) {
		return RestUtil.convert(project, new ProjectRest());
	}

	public Project toCore() {
		return RestUtil.convert(this, new Project());
	}

	@Override
	public String toString() {
		return "ProjectRest [id=" + id + ", name=" + name + ", description="
				+ description + "]";
	}

}
