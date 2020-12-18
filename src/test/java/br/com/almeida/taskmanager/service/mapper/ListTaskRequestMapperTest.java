package br.com.almeida.taskmanager.service.mapper;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.almeida.taskmanager.model.request.ListTaskRequest;
import br.com.almeida.taskmanager.persitence.entity.ListTask;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@SpringBootTest
public class ListTaskRequestMapperTest {

	@Autowired
	private ListTaskRequestMapper mapper;
	
	@BeforeAll
	public static void setup() {
		FixtureFactoryLoader.loadTemplates("br.com.almeida.taskmanager.fixture");
	}
	
	@Test
	public void shouldCreateListTask() {
		ListTaskRequest listTaskRequest = Fixture.from(ListTaskRequest.class).gimme("valid");
		assertThat(listTaskRequest, notNullValue());
		assertThat(listTaskRequest.getTitle(), notNullValue());
		
		ListTask listTask = mapper.map(listTaskRequest);
		
		assertThat(listTask, notNullValue());
		assertThat(listTask.getTitle(), notNullValue());
	}
}
