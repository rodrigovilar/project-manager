package br.edu.ufcg.embedded.projectmanager.rest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import br.edu.ufcg.embedded.projectmanager.domain.Project;
import br.edu.ufcg.embedded.projectmanager.generic.RestUtil;

@XmlRootElement
public class ProjectRest implements Serializable {

	private static final long serialVersionUID = 4431345413823505552L;

	private Integer id;
	private String fullName;
	private String shortName;
	private String fullDescription;
	private String shortDescription;

	public ProjectRest() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String name) {
		this.fullName = name;
	}

	public String getFullDescription() {
		return fullDescription;
	}

	public void setFullDescription(String description) {
		this.fullDescription = description;
	}	

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public static ProjectRest fromCore(Project project) {
		return RestUtil.convert(project, new ProjectRest());
	}

	public Project toCore() {
		return RestUtil.convert(this, new Project());
	}

	@Override
	public String toString() {
		return "ProjectRest [id=" + id + ", full_name=" + fullName 
				+ ", short_name=" + shortName
				+ ", full_description=" + fullDescription 
				+ ", short_description=" + shortDescription
				+ "]";
	}

}
