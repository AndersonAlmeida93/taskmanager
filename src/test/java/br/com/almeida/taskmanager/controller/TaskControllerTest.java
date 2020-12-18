package br.com.almeida.taskmanager.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.almeida.taskmanager.model.common.TaskStatus;
import br.com.almeida.taskmanager.model.request.TaskRequest;
import br.com.almeida.taskmanager.persitence.entity.ListTask;
import br.com.almeida.taskmanager.persitence.repository.ListTaskRepository;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = BEFORE_TEST_METHOD,scripts = "classpath:clear_tables.sql")
public class TaskControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ListTaskRepository listTaskRepository;
	
	private long id;
	
	@BeforeAll
	public static void setup() {
		FixtureFactoryLoader.loadTemplates("br.com.almeida.taskmanager.fixture");
	}
	
	@BeforeEach
	private void createFakeListTask() {
		ListTask listTask = Fixture.from(ListTask.class).gimme("valid");
		ListTask lisTaskId = listTaskRepository.save(listTask);
		id = lisTaskId.getId();
	}
	
	public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	@Test
	@Transactional
	public void createTest() throws Exception {
		
		TaskRequest request = Fixture.from(TaskRequest.class).gimme("valid");
		request.setListTask(id);
		assertThat(id).isNotNull();
		
		mvc.perform(
				post("/v1/task/{listTask}",id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id", greaterThan(0)))
				.andExpect(jsonPath("$.description", is(request.getDescription())))
				.andExpect(jsonPath("$.status", is(TaskStatus.OPEN.name())))
				.andExpect(jsonPath("$.listTask",greaterThan(0)));
	}
	
	@Test
	@Transactional
	public void deleteTest() throws Exception {
		
		TaskRequest request = Fixture.from(TaskRequest.class).gimme("valid");
		request.setListTask(id);
		assertThat(id).isNotNull();
		
		MvcResult response = mvc.perform(
				post("/v1/task/{listTask}",id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id", greaterThan(0)))
				.andExpect(jsonPath("$.description", is(request.getDescription())))
				.andExpect(jsonPath("$.status", is(TaskStatus.OPEN.name())))
				.andExpect(jsonPath("$.listTask",greaterThan(0)))
				.andReturn();
		
		assertThat(request.getListTask()).isNotNull();
		
		JSONObject jsonObject = new JSONObject(response.getResponse().getContentAsString());
		
		long idResponse = jsonObject.getInt("id");
		
		mvc.perform(
				delete("/v1/task/{listTask}/{task}", id, idResponse))
				.andExpect(status().isOk());
	}
	
	@Test
	@Transactional
	public void updateStatusTest() throws Exception {
		
		TaskRequest request = Fixture.from(TaskRequest.class).gimme("valid");
		request.setListTask(id);
		assertThat(id).isNotNull();
		
		MvcResult response = mvc.perform(
				post("/v1/task/{listTask}",id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id", greaterThan(0)))
				.andExpect(jsonPath("$.description", is(request.getDescription())))
				.andExpect(jsonPath("$.status", is(TaskStatus.OPEN.name())))
				.andExpect(jsonPath("$.listTask",greaterThan(0)))
				.andReturn();
		
		assertThat(request.getListTask()).isNotNull();
		
		JSONObject jsonObject = new JSONObject(response.getResponse().getContentAsString());
		
		long idResponse = jsonObject.getInt("id");
		
			mvc.perform(
				put("/v1/task/{listTask}/{task}",id,idResponse)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id", greaterThan(0)))
				.andExpect(jsonPath("$.description", is(request.getDescription())))
				.andExpect(jsonPath("$.status", is(TaskStatus.CLOSE.name())))
				.andExpect(jsonPath("$.listTask",greaterThan(0)));
	}
	
}
