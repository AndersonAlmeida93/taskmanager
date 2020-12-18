package br.com.almeida.taskmanager.persitence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.almeida.taskmanager.persitence.entity.ListTask;

@Repository
public interface ListTaskRepository extends JpaRepository<ListTask, Long>{

}
