package br.com.almeida.taskmanager.service.mapper;

import org.springframework.stereotype.Component;

import br.com.almeida.taskmanager.model.response.TaskResponse;
import br.com.almeida.taskmanager.persitence.entity.Task;

@Component
public class TaskResponseMapper implements Mapper<Task, TaskResponse> {

	@Override
	public TaskResponse map(Task input) {
		if (input == null) {
			return null;
		}

		TaskResponse response = new TaskResponse();
		response.setId(input.getId());
		response.setDescription(input.getDescription());
		response.setStatus(input.getStatus());
		response.setListTask(input.getListTask().getId());

		return response;
	}

}
