package kr.or.connect.todo.persistence;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.todo.domain.Tasks;

@Repository
public class TodoDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;

	public TodoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("todo").usingGeneratedKeyColumns("id");
	}

	public List<Tasks> select_All() {

		RowMapper<Tasks> rowMapper = BeanPropertyRowMapper.newInstance(Tasks.class);
		return jdbc.query(TodoSqls.SELECT_All, rowMapper);

	}

	public Integer insert(Tasks tasks) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(tasks);
		return insertAction.executeAndReturnKey(params).intValue();
	}

	public Tasks selectById(Integer id) {
		RowMapper<Tasks> rowMapper = BeanPropertyRowMapper.newInstance(Tasks.class);
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbc.queryForObject(TodoSqls.SELECT_BY_ID, params, rowMapper);
	}

	public void deleteById(Integer id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		jdbc.update(TodoSqls.DELETE_BY_ID, params);
	}

	public void update_1(Tasks tasks) {
		Map<String, ?> params = Collections.singletonMap("id", tasks.getId());
		jdbc.update(TodoSqls.UPDATE_1, params);

	}

	public void update_0(Tasks tasks) {
		Map<String, ?> params = Collections.singletonMap("id", tasks.getId());
		jdbc.update(TodoSqls.UPDATE_0, params);
	}

	public List<Tasks> selectByCompleted(String hash) {

		RowMapper<Tasks> rowMapper = BeanPropertyRowMapper.newInstance(Tasks.class);

		if (hash.equals("all")) {

			return jdbc.query(TodoSqls.SELECT_All, rowMapper);

		} else if (hash.equals("active")) {

			Map<String, Object> params = new HashMap<>();
			params.put("completed", 0);
			return jdbc.query(TodoSqls.SELECT_BY_COMPLETED, params, rowMapper);

		} else {

			Map<String, Object> params = new HashMap<>();
			params.put("completed", 1);
			return jdbc.query(TodoSqls.SELECT_BY_COMPLETED, params, rowMapper);

		}

	}

}
