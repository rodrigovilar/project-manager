package br.edu.ufcg.embedded.projectmanager.generic.event;

public class ResponseEvent {

	protected boolean entityFound = true;
	protected boolean operationCompleted = true;

	public boolean isEntityFound() {
		return entityFound;
	}

	public void setEntityFound(boolean entityFound) {
		this.entityFound = entityFound;
	}

	public boolean isOperationCompleted() {
		return operationCompleted;
	}

	public void setOperationCompleted(boolean operationCompleted) {
		this.operationCompleted = operationCompleted;
	}

}
