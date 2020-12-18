package br.com.almeida.taskmanager.persitence.entity;

import java.util.function.Function;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.almeida.taskmanager.model.common.TaskStatus;

@Entity
@Table(name = "task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "status", length = 10, nullable = false)
	@Enumerated(value = EnumType.STRING)
	private TaskStatus status;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "list_id", referencedColumnName = "id")
	private ListTask listTask;

	public <R> R map(Function<Task, R> func) {
		return func.apply(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public ListTask getListTask() {
		return listTask;
	}

	public void setListTask(ListTask listTask) {
		this.listTask = listTask;
	}

}
