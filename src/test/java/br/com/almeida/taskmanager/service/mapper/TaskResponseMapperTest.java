package br.com.almeida.taskmanager.service.mapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.almeida.taskmanager.model.response.TaskResponse;
import br.com.almeida.taskmanager.persitence.entity.Task;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class TaskResponseMapperTest {

	@Autowired
	private TaskResponseMapper mapper;
	
	@BeforeAll
	public static void setup() {
		FixtureFactoryLoader.loadTemplates("br.com.almeida.taskmanager.fixture");
	}
	
	@Test
	public void shouldCreateTask() {
		Task task = Fixture.from(Task.class).gimme("valid");
		assertThat(task, notNullValue());
		assertThat(task.getDescription(), notNullValue());
		assertThat(task.getStatus(), notNullValue());
		assertThat(task.getListTask(), notNullValue());
		
		TaskResponse response = mapper.map(task);
		
		assertThat(response, notNullValue());
		assertThat(response.getDescription(), notNullValue());
		assertThat(response.getStatus(), notNullValue());
		assertThat(response.getListTask(), notNullValue());
	}
}
