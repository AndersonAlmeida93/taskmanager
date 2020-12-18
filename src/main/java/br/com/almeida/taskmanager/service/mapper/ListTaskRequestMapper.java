package br.com.almeida.taskmanager.service.mapper;

import org.springframework.stereotype.Component;

import br.com.almeida.taskmanager.model.request.ListTaskRequest;
import br.com.almeida.taskmanager.persitence.entity.ListTask;

@Component
public class ListTaskRequestMapper implements Mapper<ListTaskRequest, ListTask>{

	@Override
	public ListTask map(ListTaskRequest input) {

		if (input == null) {
			return null;
		}
		
		ListTask result = new ListTask();
		result.setTitle(input.getTitle());
		
		return result;
	}

}
