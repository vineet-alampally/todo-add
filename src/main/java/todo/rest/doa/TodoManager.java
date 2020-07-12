package todo.rest.doa;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import todo.rest.repository.TodoRepository;
import todo.rest.representations.Task;
import todo.rest.representations.ToDoItem;
 
public class TodoManager {
     
    public ArrayList<Task> getTodos() throws Exception{
    	TodoRepository repo = new TodoRepository();
    	return repo.GetAll();
    }
     
    public Task getTodo(Integer id){
    	TodoRepository repo = new TodoRepository();
        try {
			return repo.Get(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    
    public int addTodo(Task todo) throws Exception{
    	TodoRepository repo = new TodoRepository();
    	return repo.Insert(todo);
    }
     
    public void updateTodo(Integer id, Task item) throws Exception{
    	TodoRepository repo = new TodoRepository();
    	repo.Update(item);
    }
    
    public boolean addSubTask(Integer id, ToDoItem sub){
    	TodoRepository repo = new TodoRepository();
    	try {    		
			
    		repo.AddSubTask(id, sub);
			return true;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
     
    public boolean removeTodo(Integer id){
    	TodoRepository repo = new TodoRepository();
    	try {
			return repo.Remove(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }        
}