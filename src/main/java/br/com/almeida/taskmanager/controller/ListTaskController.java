package br.com.almeida.taskmanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.almeida.taskmanager.model.request.ListTaskRequest;
import br.com.almeida.taskmanager.model.response.ListTaskResponse;
import br.com.almeida.taskmanager.service.ListTaskService;

@RestController
@RequestMapping(value = "/v1/list-task")
public class ListTaskController {

	@Autowired
	private ListTaskService listTaskService;

	@PostMapping
	public ResponseEntity<ListTaskResponse> create(@RequestBody @Valid ListTaskRequest listTaskRequest) {

		return ResponseEntity.ok(listTaskService.create(listTaskRequest));
	}

}
