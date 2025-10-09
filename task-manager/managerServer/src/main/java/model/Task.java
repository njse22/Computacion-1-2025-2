package model;

public class Task {
    private int id;
    private String title;
    private String description;
    private String dueDate;
    private String priority;
    private TaskStage stage;

    public Task(){

    }

    public String getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setTitle(String name) {
        this.title = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStage getStage() {
        return stage;
    }

    public void setStage(TaskStage stage) {
        this.stage = stage;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return id +" - " +title;
    }

}
