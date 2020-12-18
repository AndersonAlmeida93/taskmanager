package br.com.almeida.taskmanager.service.mapper;

import org.springframework.stereotype.Component;

import br.com.almeida.taskmanager.model.response.ListTaskResponse;
import br.com.almeida.taskmanager.persitence.entity.ListTask;

@Component
public class ListTaskResponseMapper implements Mapper<ListTask, ListTaskResponse>{

	@Override
	public ListTaskResponse map(ListTask input) {
		if (input == null) {
			return null;
		}
		
		ListTaskResponse response = new ListTaskResponse();
		response.setId(input.getId());
		response.setTitle(input.getTitle());
		
		return response;
	}

}
