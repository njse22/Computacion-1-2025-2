package daos;

import java.sql.Connection;
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
            Connection conn = ConnectionManager
            .getInstance(
).getConnection();
            String query = "Select * from task";

            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Task t = new Task();
                //TODO completar mapeo de las entidades
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }
    
}
