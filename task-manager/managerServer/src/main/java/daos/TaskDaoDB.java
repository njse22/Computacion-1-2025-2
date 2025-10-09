package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DBConfig.ConnectionManager;
import model.Task;
import model.TaskStage;

public class TaskDaoDB implements Dao<Task, Integer>{

    @Override
    public List<Task> findAll() {
        try {
            List<Task> tasks = new ArrayList<>();
            Connection conn = ConnectionManager.getInstance().getConnection();
            String query = "SELECT * FROM task";

            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Task t = new Task();
                t.setId(result.getInt("id"));
                t.setTitle(result.getString("title"));
                t.setDescription(result.getString("description"));
                int stageID = result.getInt("stage_id");
                TaskStage stage = new TaskStage();
                stage.setId(stageID);

                tasks.add(t);
            }

            conn.close();

            return tasks;
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Task finById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'finById'");
    }

    @Override
    public Task update(Task oldEntity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Task entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void save(Task entity) {
	String query = "INSERT INTO task (title, description, due_date, priority, stage_id) VALUES (?, ?, ?, ?, ?)"; 

	try(Connection conn = ConnectionManager.
		getInstance().getConnection()){

	    PreparedStatement statement = conn.
		prepareStatement(query, 
			Statement.RETURN_GENERATED_KEYS);

	    statement.setString(1, entity.getTitle());
	    statement.setString(2, entity.getDescription());
	    statement.setString(3, entity.getDueDate());
	    statement.setString(4, entity.getPriority());
	    statement.setInt(5, entity.getStage().getId());

	    statement.executeUpdate(); 

	    try(ResultSet generatedKeys = 
		    statement.getGeneratedKeys()) {
		if (generatedKeys.next()) {
		    entity.setId(generatedKeys.getInt(1));
		}
	    } 

	}catch(Exception e) {

	}
    }
    
}
