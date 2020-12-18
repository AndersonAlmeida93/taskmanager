package br.com.almeida.taskmanager.service;

import static org.springframework.util.Assert.notNull;
import static org.springframework.util.Assert.state;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.almeida.taskmanager.exception.NotFoundException;
import br.com.almeida.taskmanager.model.common.TaskStatus;
import br.com.almeida.taskmanager.model.request.TaskRequest;
import br.com.almeida.taskmanager.model.response.TaskResponse;
import br.com.almeida.taskmanager.persitence.entity.Task;
import br.com.almeida.taskmanager.persitence.repository.ListTaskRepository;
import br.com.almeida.taskmanager.persitence.repository.TaskRepository;
import br.com.almeida.taskmanager.service.mapper.Mapper;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private Mapper<TaskRequest, Task> taskRequestMapper;

	@Autowired
	private Mapper<Task, TaskResponse> taskResponseMapper;

	@Override
	public TaskResponse create(Long listTask, TaskRequest taskRequest) {

		notNull(taskRequest, "taskRequest must not be null");
		notNull(listTask, "invalid listTask id");
		state(listTask > 0, "Invalid listTask id, must be greater zero");

		LOGGER.info("Create a Task from List {}", listTask);

		try {

			taskRequest.setListTask(listTask);

			Task task = this.taskRequestMapper.map(taskRequest);

			task.setStatus(TaskStatus.OPEN);
			return taskRepository.save(task).map(this.taskResponseMapper::map);

		} catch (Exception ex) {
			LOGGER.warn("Error to create a task from list {}", listTask, ex);
			throw new IllegalArgumentException("Error to create a task from list");
		}

	}

	@Override
	public boolean delete(Long listTask, Long task) {

		notNull(listTask, "invalid listTask id");
		state(listTask > 0, "Invalid listTask id, must be greater zero");
		notNull(task, "invalid Task id");
		state(task > 0, "Invalid Task id, must be greater zero");

		Optional<Task> tasks = taskRepository.findById(task);
		if (!tasks.isPresent()) {
			throw new NotFoundException("Task not exists");
		}
		LOGGER.info("Delete a Task {} from List {}", task, listTask);
		try {
			taskRepository.deleteById(task);
			return true;
		} catch (Exception ex) {
			LOGGER.warn("Error to delete task id {}", task, ex);
		}
		return false;
	}

	@Override
	public Optional<TaskResponse> updateStatus(Long listTask, Long task) {

		notNull(listTask, "invalid listTask id");
		state(listTask > 0, "Invalid listTask id, must be greater zero");
		notNull(task, "invalid Task id");
		state(task > 0, "Invalid Task id, must be greater zero");

		Optional<Task> tasks = taskRepository.findById(task);
		if (!tasks.isPresent()) {
			throw new NotFoundException("Task not exists");
		}

		LOGGER.info("Update status task {} from List {}", task, listTask);

		return setTaskStatus(task, TaskStatus.CLOSE);
	}

	private Optional<TaskResponse> setTaskStatus(Long id, TaskStatus taskStatus) {

		Optional<TaskResponse> optional = taskRepository.findById(id).map(it -> {
			it.setStatus(taskStatus);
			return taskRepository.save(it).map(this.taskResponseMapper::map);
		});

		return optional;
	}

}
