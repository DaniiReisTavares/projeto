package com.minhas.tarefas.gerenciador_tarefas.minhasTarefas.controller;

import com.minhas.tarefas.gerenciador_tarefas.minhasTarefas.model.Tarefalmodel;
import com.minhas.tarefas.gerenciador_tarefas.minhasTarefas.reposity.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaCiontroller {

    @Autowired
    private TarefaRepository tarefaRepository;

    // GET: Recuperar todas as tarefas
    @GetMapping
    public ResponseEntity<List<Tarefalmodel>> getAllTarefas() {
        List<Tarefalmodel> tarefas = tarefaRepository.findAll();
        return new ResponseEntity<>(tarefas, HttpStatus.OK);
    }

    // GET: Recuperar uma tarefa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarefalmodel> getTarefaById(@PathVariable Long id) {
        Optional<Tarefalmodel> tarefa = tarefaRepository.findById(id);
        return tarefa.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST: Adicionar uma nova tarefa
    @PostMapping
    public ResponseEntity<Tarefalmodel> createTarefa(@RequestBody Tarefalmodel tarefa) {
        Tarefalmodel novaTarefa = tarefaRepository.save(tarefa);
        return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);
    }

    // PUT: Atualizar uma tarefa existente
    @PutMapping("/{id}")
    public ResponseEntity<Tarefalmodel> updateTarefa(@PathVariable Long id, @RequestBody Tarefalmodel tarefaDetails) {
        Optional<Tarefalmodel> optionalTarefa = tarefaRepository.findById(id);

        if (optionalTarefa.isPresent()) {
            Tarefalmodel tarefa = optionalTarefa.get();
            tarefa.setTitulo(tarefaDetails.getTitulo());
            tarefa.setDescricao(tarefaDetails.getDescricao());
            tarefa.setConcluida(tarefaDetails.isConcluida());
            Tarefalmodel tarefaAtualizada = tarefaRepository.save(tarefa);
            return new ResponseEntity<>(tarefaAtualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE: Excluir uma tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTarefa(@PathVariable Long id) {
        try {
            tarefaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}