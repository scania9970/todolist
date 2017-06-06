package kr.or.connect.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.todo.domain.Tasks;
import kr.or.connect.todo.persistence.TodoDao;

@Service
public class TodoService {
	private TodoDao dao;

	@Autowired
	public TodoService(TodoDao dao) {
		this.dao = dao;
	}

	public List<Tasks> get_All() {
		System.out.println(dao.select_All().size());
		return dao.select_All();
	}

	public Tasks create(Tasks tasks) {
		Integer id = dao.insert(tasks);
		return dao.selectById(id);
	}

	public void delete(Integer id) {
		dao.deleteById(id);
	}

	public void update(Tasks tasks) {
		int completedValue = tasks.getCompleted();
		if (completedValue == 1) {
			dao.update_1(tasks);
		} else {
			dao.update_0(tasks);
		}

	}

	public List<Tasks> filter(String hash) {

		return dao.selectByCompleted(hash);
	}

	public void deleteCompleted(String[] deleteList) {
		for (int i = 0; i < deleteList.length; i++) {
			dao.deleteById(Integer.parseInt(deleteList[i]));

		}

	}
}
