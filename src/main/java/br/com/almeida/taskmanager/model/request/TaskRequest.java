package br.com.almeida.taskmanager.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TaskRequest {

	@NotBlank
	@Size(max = 255)
	private String description;

	private Long listTask;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getListTask() {
		return listTask;
	}

	public void setListTask(Long listTask) {
		this.listTask = listTask;
	}

}
