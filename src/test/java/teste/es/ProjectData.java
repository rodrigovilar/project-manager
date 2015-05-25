package teste.es;

import java.util.LinkedList;
import java.util.List;

import br.edu.ufcg.embedded.projectmanager.domain.Project;
import br.edu.ufcg.embedded.projectmanager.rest.ProjectRest;


public class ProjectData {

	public static Project buildProject(Integer id, String name, String description) {
		Project project = new Project();
		project.setId(id);
		project.setFullName(name);
		project.setFullDescription(description);
		return project;
	}

	public static String projectToJson(Project project) {
		return TestUtil.toJson(ProjectRest.fromCore(project));
	}

	public static Project project1() {
		return buildProject(null, "Scrum", "Project Details!");
	}

	public static Project project2() {
		return buildProject(null, "XP", "Extreme!");
	}

	public static Project project3() {
		return buildProject(null, "RUP", "Rational!");
	}

	public static Project project4() {
		return buildProject(null, "Espiral", "Espiral Project!");
	}

	public static Project project5() {
		return buildProject(null, "Cascata", "Cascata Project!");
	}

	public static ProjectRest projectRest1() {
		return ProjectRest.fromCore(project1());
	}

	public static ProjectRest projectRest2() {
		return ProjectRest.fromCore(project2());
	}

	public static ProjectRest projectRest3() {
		return ProjectRest.fromCore(project3());
	}

	public static String projectJson1() {
		ProjectRest project = projectRest1();
		project.setId(13);
		return TestUtil.toJson(project);
	}

	public static List<Project> projects() {
		List<Project> projects = new LinkedList<Project>();
		Project t1 = project1();
		t1.setId(1);
		projects.add(t1);
		Project t2 = project2();
		t2.setId(2);
		projects.add(t2);
		Project t3 = project3();
		t3.setId(3);
		projects.add(t3);
		return projects;
	}

}
