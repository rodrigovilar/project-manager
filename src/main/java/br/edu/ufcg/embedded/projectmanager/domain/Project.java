package br.edu.ufcg.embedded.projectmanager.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import br.edu.ufcg.embedded.projectmanager.generic.Identifiable;

@Entity
public class Project implements Identifiable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	@NotBlank
	@Length(max = 40)
	private String fullName;
	
	@NotNull
	@NotBlank
	@Length(max = 30)
	private String shortName;

	@NotNull
	@NotBlank
	private String fullDescription;
	
	@NotNull
	@NotBlank
	private String shortDescription;
	
	@NotNull
	@NotBlank
	private String projectVisibility;

	@NotNull
	@NotBlank
	private String type;

	public Project() {
		super();
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
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

	public String getProjectVisibility() {
		return projectVisibility;
	}

	public void setProjectVisibility(String projectVisibility) {
		this.projectVisibility = projectVisibility;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ProjectRest [id=" + id + ", full_name=" + fullName 
				+ ", short_name=" + shortName
				+ ", full_description=" + fullDescription 
				+ ", short_description=" + shortDescription
				+ ", projectVisibility=" + projectVisibility
				+ "]";
	}
}
