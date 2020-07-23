package com.komegunov.todo.controllers;

import com.komegunov.todo.repr.TodoRepr;
import com.komegunov.todo.service.ToDoService;
import com.komegunov.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TodoController {

    private ToDoService toDoService;
    private UserService userService;

    @Autowired
    public TodoController(ToDoService toDoService, UserService userService) {
        this.toDoService = toDoService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        List<TodoRepr> todos = toDoService.findToDoByUserId(userService.getCurrentUserId().orElseThrow(ResourceNotFoundException::new));
        model.addAttribute("todos", todos);
        return "index";
    }

    @GetMapping("/todo/{id}")
    public String todoPage(@PathVariable("id") Long id, Model model) {
        final TodoRepr todoRepr = toDoService.findById(id).orElseThrow(ResourceNotFoundException::new);
        model.addAttribute("todo", todoRepr);
        return "todo";
    }

    @GetMapping("/todo/create")
    public String createToDoPage(Model model) {
        model.addAttribute("todo", new TodoRepr());
        return "todo";
    }

    @PostMapping("/todo/create")
    public String createToDoPost(@ModelAttribute("todo") TodoRepr todoRepr) {
        toDoService.save(todoRepr);
        return "redirect:/";
    }

    @GetMapping("/todo/delete/{id}")
    public String deleteToDo(Model model, @PathVariable Long id) {
        toDoService.delete(id);
        return "redirect:/";
    }


}
