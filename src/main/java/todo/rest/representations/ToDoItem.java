package todo.rest.representations;


import java.util.ArrayList;

public class ToDoItem {
    
    private Integer id;
    private String name;
    private String description;
     
    public ToDoItem() {        
                
    }
    
    public ToDoItem(String name, String description) {        
        this.name = name;
        this.description = description;        
    }
 
    public Integer getId() {
        return id;
    }
 
    public void setId(Integer id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String firstName) {
        this.name = firstName;
    }
 
    
    public String getDescription() {
        return description;
    }
 
    public void setEmail(String desc) {
        this.description = desc;
    }
    @Override
    public String toString() {
        return "Task [id=" + id + ", Name=" + name + ", description="
                + description + "]";
    }
}