package br.com.almeida.taskmanager.model.response;

import br.com.almeida.taskmanager.model.common.TaskStatus;

public class TaskResponse {

	private Long id;

	private String description;

	private TaskStatus status;

	private Long listTask;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public Long getListTask() {
		return listTask;
	}

	public void setListTask(Long listTask) {
		this.listTask = listTask;
	}

}
