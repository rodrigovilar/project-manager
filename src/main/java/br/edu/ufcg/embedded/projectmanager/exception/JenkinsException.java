package br.edu.ufcg.embedded.projectmanager.exception;

public class JenkinsException extends EventException {
	
	private static final long serialVersionUID = -3409662248119550890L;

	public JenkinsException(String mensagem){
		super(mensagem);
	}
}