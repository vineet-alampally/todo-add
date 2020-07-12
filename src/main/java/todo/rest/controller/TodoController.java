package todo.rest.controller;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import todo.rest.representations.Task;
import todo.rest.representations.ToDoItem;
import todo.rest.doa.*;

@Path("/todoapp")
@Produces(MediaType.APPLICATION_JSON)
public class TodoController {

	private TodoManager mgr;
    public TodoController() {
    	mgr = new TodoManager();
    }
 
    @GET
    @Path("/get/{id}")
    public Response get(@PathParam("id") Integer id) {
    	try 
    	{
    		Task resp =mgr.getTodo(id); 
    		return Response.ok(resp).build();
    	}
    	catch(Exception ex)
    	{
    		return Response.serverError().build();
    	}    	
    }
 
    @GET
    @Path("/getall")
    public Response getAll() {
    	try 
    	{
    		return Response.ok(mgr.getTodos()).build();
    	}
    	catch(Exception ex)
    	{
    		return Response.serverError().build();
    	}
        
    }
 
    @POST
    @Path("/create")
    public Response create(Task item) throws Exception{
    	
    	try 
    	{
    		
    		return Response.ok(mgr.addTodo(item)).build();
    	}
    	catch(Exception ex)
    	{
    		return Response.serverError().build();
    	}
    }
    
    @POST
    @Path("/addSubTask/{id}")
    public Response addSubTask(@PathParam("id") Integer id,ToDoItem item) throws Exception{
    	
    	try 
    	{    		
    		return Response.ok(mgr.addSubTask(id, item)).build();
    	}
    	catch(Exception ex)
    	{
    		return Response.serverError().build();
    	}
    }
 
    @PUT
    @Path("/modify/{id}")
    public Response modify(@PathParam("id") Integer id, Task item) {
    	try 
    	{    		
    		mgr.updateTodo(id, item);
    		return Response.ok(true).build();
    	}
    	catch(Exception ex)
    	{
    		return Response.serverError().build();
    	}
    }
     
    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Integer id) {
    	try 
    	{    		
    		return Response.ok(mgr.removeTodo(id)).build();
    	}
    	catch(Exception ex)
    	{
    		return Response.serverError().build();
    	}
    }
}
