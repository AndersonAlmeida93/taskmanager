package br.com.almeida.taskmanager.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.almeida.taskmanager.model.request.TaskRequest;
import br.com.almeida.taskmanager.persitence.entity.ListTask;
import br.com.almeida.taskmanager.persitence.entity.Task;
import br.com.almeida.taskmanager.persitence.repository.ListTaskRepository;

@Component
public class TaskRequestMapper implements Mapper<TaskRequest, Task>{
	
	@Autowired
	private ListTaskRepository listTaskRepository;

	@Override
	public Task map(TaskRequest input) {
		if(input == null) {
			return null;
		}
		
		ListTask listTask = input.getListTask() == null ? null : listTaskRepository.findById(input.getListTask())
				.orElseThrow(() -> new IllegalArgumentException("invalid List Task id"));
		
		Task result = new Task();
		result.setDescription(input.getDescription());
		result.setListTask(listTask);
		
		return result;
	}

}
