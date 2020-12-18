package br.com.almeida.taskmanager.fixture;

import br.com.almeida.taskmanager.model.common.TaskStatus;
import br.com.almeida.taskmanager.model.request.TaskRequest;
import br.com.almeida.taskmanager.model.response.TaskResponse;
import br.com.almeida.taskmanager.persitence.entity.ListTask;
import br.com.almeida.taskmanager.persitence.entity.Task;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class TaskTemplateLoader implements TemplateLoader{

	@Override
	public void load() {

		Fixture.of(TaskRequest.class).addTemplate("valid", new Rule() {{
			add("description", name());
		}});
		
		Fixture.of(Task.class).addTemplate("valid", new Rule() {{
			add("description", name());
			add("status", TaskStatus.OPEN);
			add("listTask", createObject());
		}});
		
		Fixture.of(TaskResponse.class).addTemplate("valid", new Rule() {{
			add("id", random(Long.class, range(1L, 200L)));
			add("description", name());
			add("status", TaskStatus.OPEN);
		}});
	}
	
	private ListTask createObject() {
		
		ListTask listTask = new ListTask();
		
		listTask.setId(1L);
		listTask.setTitle("test");
		
		return listTask;
	}

}
