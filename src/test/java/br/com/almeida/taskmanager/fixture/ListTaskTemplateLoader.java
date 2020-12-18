package br.com.almeida.taskmanager.fixture;

import br.com.almeida.taskmanager.model.request.ListTaskRequest;
import br.com.almeida.taskmanager.model.response.ListTaskResponse;
import br.com.almeida.taskmanager.persitence.entity.ListTask;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class ListTaskTemplateLoader implements TemplateLoader {

	@Override
	public void load() {

		Fixture.of(ListTaskRequest.class).addTemplate("valid", new Rule() {{
			add("title", name());
		}});

		Fixture.of(ListTask.class).addTemplate("valid", new Rule() {{
			add("title", name());
		}});

		Fixture.of(ListTaskResponse.class).addTemplate("valid", new Rule() {{
			add("id", random(Long.class, range(1L, 200L)));
			add("title", name());
		}});
	}

}
