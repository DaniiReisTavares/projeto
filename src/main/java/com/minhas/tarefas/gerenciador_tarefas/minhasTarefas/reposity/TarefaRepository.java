package com.minhas.tarefas.gerenciador_tarefas.minhasTarefas.reposity;

import com.minhas.tarefas.gerenciador_tarefas.minhasTarefas.model.Tarefalmodel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefalmodel, Long> {
}