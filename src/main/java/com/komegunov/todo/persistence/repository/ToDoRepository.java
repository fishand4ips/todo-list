package com.komegunov.todo.persistence.repository;

import com.komegunov.todo.persistence.entity.ToDo;
import com.komegunov.todo.persistence.entity.User;
import com.komegunov.todo.repr.TodoRepr;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Long> {

    List<TodoRepr> findToDoByUser_Username(String username);

    List<TodoRepr> findToDoByUser(User user);
}
