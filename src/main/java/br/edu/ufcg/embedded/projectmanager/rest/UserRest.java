package br.edu.ufcg.embedded.projectmanager.rest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import br.edu.ufcg.embedded.projectmanager.domain.User;
import br.edu.ufcg.embedded.projectmanager.generic.RestUtil;

@XmlRootElement
public class UserRest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1834155787506326465L;
	
	private Integer id;
	private String name;
	private String userName;
	private String email;
	private String password;
	
	
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public static UserRest fromCore(User user) {
		return RestUtil.convert(user, new UserRest());
	}
	
	public User toCore() {
		return RestUtil.convert(this, new User());
	}

	@Override
	public String toString() {
		return "UserRest [id=" + id 
				+ ", name=" + name 
				+ ", userName=" + userName
				+ ", email=" + email
				+ ", password=" + password
				+ "]";
	}

}
