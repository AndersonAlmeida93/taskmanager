package br.com.almeida.taskmanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.almeida.taskmanager.model.common.TaskStatus;
import br.com.almeida.taskmanager.model.request.TaskRequest;
import br.com.almeida.taskmanager.model.response.TaskResponse;
import br.com.almeida.taskmanager.persitence.entity.ListTask;
import br.com.almeida.taskmanager.persitence.entity.Task;
import br.com.almeida.taskmanager.persitence.repository.TaskRepository;
import br.com.almeida.taskmanager.service.mapper.Mapper;

@SpringBootTest
public class TaskServiceImplTest {

	@Autowired
	private TaskServiceImpl taskService;
	
	@MockBean
	private TaskRepository repository;
	
	@MockBean
	private Mapper<TaskRequest, Task> taskRequestMapper;
	
	@MockBean
	private Mapper<Task, TaskResponse> taskResponseMapper;
	
	
	@Test
	public void shouldCreateTask() {
		var response = getTaskResponse();
		var request = getTaskRequest();
		var task = getTask();
		
		when(taskRequestMapper.map(request)).thenReturn(task);
		when(repository.save(task)).thenReturn(task);
		when(taskResponseMapper.map(task)).thenReturn(response);
		
		var taskResponse = taskService.create(task.getListTask().getId(), request);
		checkTaskResponse(response, taskResponse);
		
		verify(taskRequestMapper).map(eq(request));
		verify(repository,never()).findById(task.getId());
		verify(repository).save(eq(task));
		verify(taskResponseMapper).map(eq(task));
	}
	
	@Test
	public void shouldThrowExceptionWhenRequestIsNullCreate() {
		var ex = assertThrows(IllegalArgumentException.class, () -> taskService.create(1L, null));
		assertNotNull(ex);
		assertEquals("taskRequest must not be null", ex.getMessage());
		
		verify(taskRequestMapper,never()).map(any());
		verify(repository,never()).findById(anyLong());
		verify(repository,never()).save(any());
		verify(taskResponseMapper,never()).map(any());
	}
	
	@Test
	public void shouldThrowExceptionWhenListTaskIsNullCreate() {
		var request = getTaskRequest();
		Exception ex = assertThrows(IllegalStateException.class, () -> taskService.create(0L, request));
		assertNotNull(ex);
		assertEquals("Invalid listTask id, must be greater zero", ex.getMessage());
		
		ex = assertThrows(IllegalArgumentException.class, () -> taskService.create(null, request));
		assertNotNull(ex);
		assertEquals("invalid listTask id", ex.getMessage());
		
		verify(taskRequestMapper,never()).map(any());
		verify(repository,never()).findById(anyLong());
		verify(repository,never()).save(any());
		verify(taskResponseMapper,never()).map(any());
	}
	
	@Test
	public void shouldDeleteTask() {
		var listTaskId = 1L;
		var task = getTask();
		
		when(repository.findById(task.getId())).thenReturn(Optional.of(task));
		assertTrue(taskService.delete(listTaskId, task.getId()));
		
		verify(repository).findById(task.getId());
	}
	
	@Test
	public void shouldThrowExceptionWhenInvalidParametersDelete() {
		
		Exception ex = assertThrows(IllegalArgumentException.class, () -> taskService.delete(null, 1L));
		assertNotNull(ex);
		assertEquals("invalid listTask id", ex.getMessage());
		
		ex = assertThrows(IllegalStateException.class, () -> taskService.delete(0L, 1L));
		assertNotNull(ex);
		assertEquals("Invalid listTask id, must be greater zero", ex.getMessage());
		
		ex = assertThrows(IllegalArgumentException.class, () -> taskService.delete(1L, null));
		assertNotNull(ex);
		assertEquals("invalid Task id", ex.getMessage());
		
		ex = assertThrows(IllegalStateException.class, () -> taskService.delete(1L, 0L));
		assertNotNull(ex);
		assertEquals("Invalid Task id, must be greater zero", ex.getMessage());
		
	}
	
	@Test
	public void shouldUpdateStatusTask() {
		
		var listTaskId = 1L;
		var response = getTaskResponse();
		var task = getTask();
		
		when(repository.findById(task.getId())).thenReturn(Optional.of(task));
		when(repository.save(task)).thenReturn(task);
		when(taskResponseMapper.map(task)).thenReturn(response);
		
		var taskResponse = taskService.updateStatus(listTaskId, task.getId());
		assertTrue(taskResponse.isPresent());
		
		verify(repository).save(eq(task));
		verify(taskResponseMapper).map(eq(task));
		
	}
	
	@Test
	public void shouldThrowExceptionWhenInvalidParametersUpdateStatus() {
		
		Exception ex = assertThrows(IllegalArgumentException.class, () -> taskService.updateStatus(null, 1L));
		assertNotNull(ex);
		assertEquals("invalid listTask id", ex.getMessage());
		
		ex = assertThrows(IllegalStateException.class, () -> taskService.updateStatus(0L, 1L));
		assertNotNull(ex);
		assertEquals("Invalid listTask id, must be greater zero", ex.getMessage());
		
		ex = assertThrows(IllegalArgumentException.class, () -> taskService.updateStatus(1L, null));
		assertNotNull(ex);
		assertEquals("invalid Task id", ex.getMessage());
		
		ex = assertThrows(IllegalStateException.class, () -> taskService.updateStatus(1L, 0L));
		assertNotNull(ex);
		assertEquals("Invalid Task id, must be greater zero", ex.getMessage());
	}
	
	
	private void checkTaskResponse(TaskResponse expected, TaskResponse actual) {
		assertNotNull(actual);
		assertEquals(expected, actual);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getStatus().name(), actual.getStatus().name());
		assertEquals(expected.getListTask(), actual.getListTask());
	}
	
	private TaskRequest getTaskRequest() {
		var request = new TaskRequest();
		request.setDescription("Task 01");
		return request;
	}
	
	private TaskResponse getTaskResponse() {
		var response = new TaskResponse();
		response.setDescription("Task 01");
		response.setListTask(1L);
		response.setStatus(TaskStatus.OPEN);
		return response;
	}
	
	private Task getTask() {
		var task = new Task();
		task.setId(1L);
		task.setDescription("Task 01");
		task.setListTask(getListTask());
		task.setStatus(TaskStatus.OPEN);
		return task;
	}
	
	private ListTask getListTask() {
		var listTask = new ListTask();
		listTask.setId(1L);
		listTask.setTitle("Test list-task");
		return listTask;
	}
}
