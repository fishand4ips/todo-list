package com.komegunov.todo.service;

import com.komegunov.todo.persistence.entity.ToDo;
import com.komegunov.todo.persistence.entity.User;
import com.komegunov.todo.persistence.repository.ToDoRepository;
import com.komegunov.todo.persistence.repository.UserRepository;
import com.komegunov.todo.repr.TodoRepr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.komegunov.todo.service.UserService.getCurrentUser;

@Service
@Transactional
public class ToDoService {

    private ToDoRepository toDoRepository;
    private UserRepository userRepository;

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

    public void save(TodoRepr todoRepr) {
        Optional<String> currentUser = getCurrentUser();
        if (currentUser.isPresent()) {
            Optional<User> optUser = userRepository.getUserByUsername(currentUser.get());
            if (optUser.isPresent()) {
                ToDo toDo = new ToDo();
                toDo.setId(todoRepr.getId());
                toDo.setDescription(todoRepr.getDescription());
                toDo.setTargetDate(todoRepr.getTargetDate());
                toDo.setUser(optUser.get());
                toDoRepository.save(toDo);
            }
        }
    }

    public void delete(Long id) {
        toDoRepository.findById(id).
                ifPresent(toDo -> toDoRepository.delete(toDo));
    }
}
