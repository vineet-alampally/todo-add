package todo.rest.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import todo.rest.representations.Task;
import todo.rest.representations.ToDoItem;

public class TodoRepository {

	public int Insert(Task item) throws Exception {
		ToDoItem task = item.getMainTask();
		ArrayList<ToDoItem> subTasks = item.getSubTasks();
		String sql = String.format("INSERT INTO TODOS (parentid,name, description) values (%d,'%s', '%s')", 0,
				task.getName(), task.getDescription());
		Connection con = CreateConnection();
		String generatedColumns[] = { "id" };
		PreparedStatement stmt = con.prepareStatement(sql, generatedColumns);		
		int i = stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
		
		if(!rs.first())
			return -1;
		
		int insertedId = rs.getInt("id");
		for (ToDoItem sub : subTasks) {
			AddSubTask(insertedId, sub);
		}

		return insertedId;
	}

	public ArrayList<Task> GetAll() throws Exception {
		HashMap<Integer, Task> allTasks = new HashMap<Integer, Task>();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		String sql = String.format("select * from todos where parentid = 0");
		Connection con = CreateConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next())
		{
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String description = rs.getString("description");
			ToDoItem item = new ToDoItem(name, description);
			item.setId(id);
			ids.add(id);
			Task parent = new Task(item, new ArrayList<ToDoItem>());
			allTasks.put(id, parent);
		}
		
		
		for(int i : ids)
		{
			sql = String.format("select * from todos where parentid = %d", i);
			Task tsk = allTasks.get(i);
			ResultSet rs1 = stmt.executeQuery(sql);
			while(rs1.next())
			{
				int id = rs1.getInt("id");
				String name = rs1.getString("name");
				String description = rs1.getString("description");
				ToDoItem item = new ToDoItem(name, description);
				item.setId(id);
				tsk.AddSubTask(item);
			}
		}
		
		return new ArrayList<Task>(allTasks.values());
	}
	
	public boolean Update(Task item) throws Exception {
		ToDoItem task = item.getMainTask();
		ArrayList<ToDoItem> subTasks = item.getSubTasks();
		String sql = String.format("UPDATE TODOS SET name = %s, description = %s where id = %d" ,
				task.getName(), task.getDescription(), item.getMainTask().getId());
		Connection con = CreateConnection();
		Statement stmt = con.createStatement();		
		int i = stmt.executeUpdate(sql);
				
		for (ToDoItem sub : subTasks) {
			UpdateSubTask(sub);
		}

		return true;
	}

	public Task Get(int id) throws Exception {
		Task item = null;
		String sql = String.format("SELECT * FROM TODOS WHERE ID = %d", id);
		Connection con = CreateConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if (!rs.first()) {
			return item;
		}
		String name = rs.getString("name");
		String description = rs.getString("description");
		ToDoItem parent = new ToDoItem(name, description);
		parent.setId(id);
		item = new Task(parent, new ArrayList<ToDoItem>());
		sql = String.format("SELECT * FROM TODOS WHERE PARENTID = %d", id);		
		ResultSet rs1 = stmt.executeQuery(sql);
		while(rs1.next()) {
			name = rs1.getString("name");
			description = rs1.getString("description");
			int childid = rs1.getInt("id");
			ToDoItem child = new ToDoItem(name, description);
			child.setId(childid);
			item.AddSubTask(child);
		}
		return item;
	}

	public void AddSubTask(int id, ToDoItem sub) throws Exception {
		String sql = String.format("INSERT INTO TODOS (parentId, name, description) values (%d, '%s', '%s')", id,
				sub.getName(), sub.getDescription());
		Connection con = CreateConnection();
		Statement stmt = con.createStatement();
		stmt.executeUpdate(sql);
	}
	
	public void UpdateSubTask(ToDoItem sub) throws Exception {
		String sql = String.format("UPDATE TODOS set name = %s, description = %s WHERE id = %d",
				sub.getName(), sub.getDescription(), sub.getId());
		Connection con = CreateConnection();
		Statement stmt = con.createStatement();
		stmt.executeUpdate(sql);
	}

	public boolean Remove(int id) throws Exception {
		String sql = String.format("DELETE FROM TODOS WHERE ID = %d", id);
		Connection con = CreateConnection();
		Statement stmt = con.createStatement();
		int i = stmt.executeUpdate(sql);
		return i == 0 ? false : true;
	}

	private Connection CreateConnection() throws Exception {

		Class.forName("org.h2.Driver").newInstance();
		Connection con = DriverManager.getConnection("jdbc:h2:" + "./Database/my", "root", "mypassword");
		return con;
	}
}
