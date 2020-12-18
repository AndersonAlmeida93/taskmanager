package br.com.almeida.taskmanager.service;

import static org.springframework.util.Assert.notNull;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.almeida.taskmanager.model.request.ListTaskRequest;
import br.com.almeida.taskmanager.model.response.ListTaskResponse;
import br.com.almeida.taskmanager.persitence.entity.ListTask;
import br.com.almeida.taskmanager.persitence.repository.ListTaskRepository;
import br.com.almeida.taskmanager.service.mapper.Mapper;

@Service
@Transactional
public class ListTaskServiceImpl implements ListTaskService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ListTaskService.class);

	@Autowired
	private ListTaskRepository listTaskRepository;

	@Autowired
	private Mapper<ListTaskRequest, ListTask> listTaskRequestMapper;

	@Autowired
	private Mapper<ListTask, ListTaskResponse> listTaskResponseMapper;

	@Override
	public ListTaskResponse create(ListTaskRequest listTaskRequest) {

		notNull(listTaskRequest, "taskRequest must not be null");

		LOGGER.info("create a List Task");

		ListTask listTask = this.listTaskRequestMapper.map(listTaskRequest);

		return listTaskRepository.save(listTask).map(this.listTaskResponseMapper::map);
	}

}
