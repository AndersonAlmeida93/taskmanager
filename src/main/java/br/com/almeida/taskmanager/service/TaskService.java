package br.com.almeida.taskmanager.service;

import java.util.Optional;

import br.com.almeida.taskmanager.model.request.TaskRequest;
import br.com.almeida.taskmanager.model.response.TaskResponse;
import br.com.almeida.taskmanager.persitence.entity.Task;

public interface TaskService {
	
	TaskResponse create (Long listTask, TaskRequest taskRequest);
	
	boolean delete (Long listTask, Long task); 

	Optional<TaskResponse> updateStatus(Long listTask,Long task);
}
