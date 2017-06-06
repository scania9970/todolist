package kr.or.connect.todo.persistence;

public class TodoSqls {
	static final String DELETE_BY_ID = "DELETE FROM todo WHERE id= :id";
	static final String SELECT_All = "SELECT * FROM todo";
	static final String SELECT_BY_ID = "SELECT * FROM todo WHERE id= :id";
	static final String UPDATE_1 = "UPDATE todo SET completed=1 where id= :id";
	static final String UPDATE_0 = "UPDATE todo SET completed=0 where id= :id";
	static final String SELECT_BY_COMPLETED = "SELECT * FROM todo WHERE completed = :completed";
}
