package br.com.almeida.taskmanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.almeida.taskmanager.model.request.ListTaskRequest;
import br.com.almeida.taskmanager.model.response.ListTaskResponse;
import br.com.almeida.taskmanager.persitence.entity.ListTask;
import br.com.almeida.taskmanager.persitence.repository.ListTaskRepository;
import br.com.almeida.taskmanager.service.mapper.Mapper;

@SpringBootTest
public class ListTaskServiceImplTest {

	@Autowired
	private ListTaskServiceImpl listTaskService;
	
	@MockBean
	private ListTaskRepository repository;
	
	@MockBean
	private Mapper<ListTaskRequest, ListTask> listTaskRequestMapper;
	
	@MockBean
	private Mapper<ListTask, ListTaskResponse> listTaskResponseMapper;
	
	@Test
	public void shouldCreateListTask() {
		var response = getListTaskResponse();
		var request = getListTaskRequest();
		var listTask = getListTask();
		
		when(listTaskRequestMapper.map(request)).thenReturn(listTask);
		when(repository.save(listTask)).thenReturn(listTask);
		when(listTaskResponseMapper.map(listTask)).thenReturn(response);
		
		var listTaskResponse = listTaskService.create(request);
		checkListTaskResponse(response, listTaskResponse);
		
		verify(listTaskRequestMapper).map(eq(request));
		verify(repository,never()).findById(listTask.getId());
		verify(repository).save(listTask);
		verify(listTaskResponseMapper).map(listTask);
	}
	
	@Test
	public void shouldThrowExceptionWhenRequestIsNullCreate() {
		var ex = assertThrows(IllegalArgumentException.class, () -> listTaskService.create(null));
		assertNotNull(ex);
		assertEquals("taskRequest must not be null", ex.getMessage());
		
		verify(listTaskRequestMapper,never()).map(any());
		verify(repository,never()).findById(anyLong());
		verify(repository,never()).save(any());
		verify(listTaskResponseMapper,never()).map(any());
	}
	
	private void checkListTaskResponse(ListTaskResponse expected, ListTaskResponse actual) {
		assertNotNull(actual);
		assertEquals(expected, actual);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getTitle(), actual.getTitle());
	}
	
	private ListTaskRequest getListTaskRequest() {
		var request = new ListTaskRequest();
		request.setTitle("Test list-task");
		return request;
	}
	
	private ListTaskResponse getListTaskResponse() {
		var response = new ListTaskResponse();
		response.setTitle("Test list-task");
		return response;
	}
	
	private ListTask getListTask() {
		var listTask = new ListTask();
		listTask.setId(1L);
		listTask.setTitle("Test list-task");
		return listTask;
	}
}
