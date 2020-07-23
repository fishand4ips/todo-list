package com.komegunov.todo.service;

import com.komegunov.todo.persistence.entity.ToDo;
import com.komegunov.todo.persistence.repository.ToDoRepository;
import com.komegunov.todo.persistence.repository.UserRepository;
import com.komegunov.todo.repr.TodoRepr;
import com.komegunov.todo.security.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ToDoService {

    private final ToDoRepository toDoRepository;
    private final UserRepository userRepository;

    @Autowired
    public ToDoService(ToDoRepository toDoRepository, UserRepository userRepository) {
        this.toDoRepository = toDoRepository;
        this.userRepository = userRepository;
    }

    public Optional<TodoRepr> findById(Long id) {
        return toDoRepository.findById(id)
                .map(TodoRepr::new);
    }

    public List<TodoRepr> findToDoByUserId(Long userId) {
        return toDoRepository.findToDoByUserId(userId);
    }

    public void save(TodoRepr toDoRepr) {
        Utils.getCurrentUser()
                .flatMap(userRepository::getUserByUsername)
                .ifPresent(user -> {
                    ToDo toDo = new ToDo();
                    toDo.setId(toDoRepr.getId());
                    toDo.setDescription(toDoRepr.getDescription());
                    toDo.setTargetDate(toDoRepr.getTargetDate());
                    toDo.setUser(user);
                    toDoRepository.save(toDo);
                });
    }

    public void delete(Long id) {
        toDoRepository.findById(id).
                ifPresent(toDoRepository::delete);
    }
}
