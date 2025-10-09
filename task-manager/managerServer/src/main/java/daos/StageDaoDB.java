package daos;

import model.TaskStage;

import java.util.List;

import org.w3c.dom.xpath.XPathResult;

import DBConfig.ConnectionManager;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class StageDaoDB implements Dao<TaskStage, Integer> {

    public StageDaoDB() {

    }


    @Override
    public List<TaskStage> findAll(){
	List<TaskStage> stages = new ArrayList<>(); 

	String query = "SELECT * FROM task_stage"; 
	try(Connection conn = ConnectionManager.
		getInstance().getConnection()){

	    Statement statement = conn.createStatement(); 
	    ResultSet result = statement.executeQuery(query);
	    System.out.println(result);

	    while (result.next()) {
		TaskStage stage = new TaskStage(); 
		stage.setId(result.getInt("id"));
		stage.setName(result.getString("name"));
		stage.setDescription(
			result.getString("description"));

		stages.add(stage); 
	    }

	}
	catch(Exception e) {

	}

	return stages; 

    }

    @Override
    public TaskStage findById(Integer id){
	TaskStage stage = null; 
	String query = "SELECT * FROM task_stage WHERE id == ?";

	try(Connection conn = ConnectionManager.
		getInstance().getConnection()){
	    PreparedStatement statement = 
		conn.prepareStatement(query);

	    statement.setInt(1, id);

	    try(ResultSet result = statement.executeQuery()){
		if (result.next()) {
		    stage = new TaskStage(); 
		    stage.setId(result.getInt("id"));
		    stage.setName(result.getString("name"));
		    stage.setDescription(
			    result.getString("description"));
			
		}
	    }

	}
	catch (Exception e) {
		
	}

	return stage;
    }




    @Override
    public TaskStage update(TaskStage newEntity) {
        throw new UnsupportedOperationException("La actualización de stages no está permitida.");
    }

    @Override
    public void delete(TaskStage entity) {
        throw new UnsupportedOperationException("La eliminación de stages no está permitida.");
    }

    @Override
    public void save(TaskStage entity) {
        throw new UnsupportedOperationException("La creación de nuevos stages no está permitida.");
    }
}
