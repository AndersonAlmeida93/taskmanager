package br.com.almeida.taskmanager.service;

import br.com.almeida.taskmanager.model.request.ListTaskRequest;
import br.com.almeida.taskmanager.model.response.ListTaskResponse;

public interface ListTaskService {
	
	ListTaskResponse create (ListTaskRequest listTaskRequest);

}
