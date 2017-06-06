package kr.or.connect.todo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.todo.domain.Tasks;
import kr.or.connect.todo.service.TodoService;

@RestController
public class TodoController {
	TodoService service;

	@Autowired
	public TodoController(TodoService service) {
		this.service = service;

	}

	@GetMapping("/tasks")
	List<Tasks> list() {
		return service.get_All();
	}

	@PostMapping("/insert")
	@ResponseStatus(HttpStatus.CREATED)
	Tasks create(@RequestBody Tasks tasks) {
		System.out.println(tasks.getDate());
		return service.create(tasks);

	}

	@DeleteMapping("/task/{id}")
	void delete(@PathVariable Integer id) {

		service.delete(id);
	}

	@PostMapping("/completed")
	void updateCompleted(@RequestBody Tasks tasks) {
		service.update(tasks);

	}

	@GetMapping("/filter/{hash}")
	List<Tasks> filter(@PathVariable String hash) {

		return service.filter(hash);
	}

	@PostMapping("/deleteCompleted")
	void deletedCompletedTasks(@RequestBody String[] deleteList) {
		service.deleteCompleted(deleteList);
	}

}
