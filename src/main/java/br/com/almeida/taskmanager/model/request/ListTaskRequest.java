package br.com.almeida.taskmanager.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ListTaskRequest {

	@NotBlank
	@Size(max = 55)
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
