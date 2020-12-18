package br.com.almeida.taskmanager.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.almeida.taskmanager.model.request.TaskRequest;
import br.com.almeida.taskmanager.model.response.TaskResponse;
import br.com.almeida.taskmanager.service.TaskService;

@RestController
@RequestMapping(value = "/v1/task")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@PostMapping("/{listTask}")
	public ResponseEntity<TaskResponse> create(@PathVariable("listTask") Long listTask,
			@RequestBody @Valid TaskRequest taskRequest) {

		return ResponseEntity.ok(taskService.create(listTask, taskRequest));
	}

	@DeleteMapping("/{listTask}/{task}")
	public ResponseEntity<Void> delete(@PathVariable("listTask") Long listTask, @PathVariable("task") Long task) {

		if (taskService.delete(listTask, task)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.badRequest().build();
	}

	@PutMapping("/{listTask}/{task}")
	public ResponseEntity<Optional<TaskResponse>> updateStatus(@PathVariable("listTask") Long listTask,
			@PathVariable("task") Long task) {

		return ResponseEntity.ok(taskService.updateStatus(listTask, task));
	}
}
