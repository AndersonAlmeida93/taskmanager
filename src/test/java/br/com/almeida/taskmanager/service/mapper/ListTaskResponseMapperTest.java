package br.com.almeida.taskmanager.service.mapper;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.almeida.taskmanager.model.response.ListTaskResponse;
import br.com.almeida.taskmanager.persitence.entity.ListTask;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@SpringBootTest
public class ListTaskResponseMapperTest {

	@Autowired
	private ListTaskResponseMapper mapper;
	
	@BeforeAll
	public static void setup() {
		FixtureFactoryLoader.loadTemplates("br.com.almeida.taskmanager.fixture");
	}
	
	@Test
	public void shouldCreateListTask() {
		ListTask listTask = Fixture.from(ListTask.class).gimme("valid");
		assertThat(listTask, notNullValue());
		assertThat(listTask.getTitle(), notNullValue());
		
		ListTaskResponse response = mapper.map(listTask);
		
		assertThat(response, notNullValue());
		assertThat(response.getTitle(), notNullValue());
	}
}
