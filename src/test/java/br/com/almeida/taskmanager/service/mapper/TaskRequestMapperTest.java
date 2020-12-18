package br.com.almeida.taskmanager.service.mapper;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.almeida.taskmanager.model.request.TaskRequest;
import br.com.almeida.taskmanager.persitence.entity.Task;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@SpringBootTest
public class TaskRequestMapperTest {

	@Autowired
	private TaskRequestMapper mapper;
	
	@BeforeAll
	public static void setup() {
		FixtureFactoryLoader.loadTemplates("br.com.almeida.taskmanager.fixture");
	}
	
	@Test
	public void shouldCreateTask() {
		TaskRequest taskRequest = Fixture.from(TaskRequest.class).gimme("valid");
		assertThat(taskRequest, notNullValue());
		assertThat(taskRequest.getDescription(), notNullValue());
		
		Task task = mapper.map(taskRequest);
		
		assertThat(task, notNullValue());
		assertThat(task.getDescription(), notNullValue());
	}
}
