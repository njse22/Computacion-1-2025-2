# Guía para Implementar Persistencia con JDBC en PostgreSQL

## Prerrequisitos

1. **PostgreSQL Instalado**: Asegúrate de tener una instancia de PostgreSQL en ejecución para esta guía se ha utilizado la versión `16` de postgresql.

2. **Base de Datos Creada**: Crea una base de datos para este proyecto. Ej: `task_manager_db`.

3. **Dependencia de JDBC**: El archivo `build.gradle` ya debería incluir el driver de PostgreSQL. Si no, agrégalo:
   
   ```groovy
   // build.gradle
   dependencies {
       implementation 'org.postgresql:postgresql:42.7.8'
       // ... otras dependencias
   }
   ```

4. **Variables de Entorno**: Configura las variables de entorno para la conexión a la base de datos. El `ConnectionManager` las leerá automáticamente.
   
   - `url`: `jdbc:postgresql://localhost:5432/task_manager_db`
   - `user`: Tu usuario de PostgreSQL.
   - `password`: Tu contraseña de PostgreSQL.
   
   A continuación se detallan dos formas de configurar estas variables.
   
   ### Opción 1: Variables de Entorno del Sistema (Ubuntu/Linux)
   
   Puedes configurar las variables para que estén disponibles en tu sesión de terminal.
   
   **Para la sesión actual:**
   Abre una terminal y ejecuta los siguientes comandos. Estos cambios se perderán cuando cierres la terminal.
   
   ```bash
   export url="jdbc:postgresql://localhost:5432/task_manager_db"
   export user="task_user"
   export password="password"
   ```
   
   **De forma permanente:**
   Para que las variables persistan entre reinicios, agrégalas al final de tu archivo de configuración de shell, como `~/.bashrc` o `~/.profile`.
   
   1. Abre el archivo con un editor de texto:
      
      ```bash
      vim ~/.bashrc
      ```
   
   2. Agrega las siguientes líneas al final del archivo:
      
      ```bash
      export url="jdbc:postgresql://localhost:5432/task_manager_db"
      export user="user"
      export password="password"
      ```
   
   3. Guarda el archivo.
   
   4. Aplica los cambios ejecutando `source ~/.bashrc` o reiniciando la terminal.

## Paso 1: Configurar la Base de Datos y el Usuario

Desde una terminal de Linux, puedes crear el usuario, la base de datos y sus tablas de la siguiente manera.

**1. Acceder a PostgreSQL como superusuario:**

```bash
psql -U postgres 
```

**2. Crear un nuevo usuario (Opcional pero recomendado):**

Es una buena práctica de seguridad crear un usuario específico para tu aplicación. Reemplaza `task_user` y `password` con los valores que usarás en tus variables de entorno.

```sql
CREATE USER task_user WITH ENCRYPTED PASSWORD 'password';
```

**3. Crear la base de datos:**

Dentro de la consola de `psql`, ejecuta el siguiente comando SQL. Cambia `task_manager_db` si deseas otro nombre.

```sql
CREATE DATABASE task_manager_db;
```

**4. Otorgar privilegios al usuario:**

Concede todos los permisos sobre la nueva base de datos al usuario que acabas de crear.

```sql
GRANT ALL PRIVILEGES ON DATABASE task_manager_db TO task_user;
```

**5. Conectarse a la nueva base de datos:**

```sql
\c task_manager_db
```

**6. Ejecutar el script de creación de tablas:**

Ahora, ejecuta las siguientes sentencias SQL para crear las tablas necesarias.

```sql
-- Tabla para los estados de las tareas (To Do, In Progress, Done)
CREATE TABLE task_stage (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT
);

-- Tabla para las tareas
CREATE TABLE task (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    due_date VARCHAR(50),
    priority VARCHAR(50),
    stage_id INTEGER NOT NULL,
    CONSTRAINT fk_stage
        FOREIGN KEY(stage_id) 
        REFERENCES task_stage(id)
);

-- Insertar los estados iniciales
INSERT INTO task_stage (name, description) VALUES
('To Do', 'Tasks to be done'),
('In Progress', 'Tasks in progress'),
('Done', 'Completed tasks');
```

**7. Otorgar Permisos sobre las Tablas al Usuario:**

Por defecto, el nuevo usuario no tiene permiso para leer o escribir en las tablas. Debes concederle estos privilegios explícitamente.

```sql
-- Conceder todos los permisos sobre las tablas
GRANT ALL PRIVILEGES ON TABLE task TO task_user;
GRANT ALL PRIVILEGES ON TABLE task_stage TO task_user;

-- Conceder permisos sobre las secuencias para la generación de IDs
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO task_user;
```

**Importante:** Después de ejecutar estos comandos, asegúrate de que las variables de entorno (`user` y `password`) coincidan con el usuario que creaste.

## Paso 2: Configurar `TaskServices` para Usar `TaskDaoDB`

Para activar la persistencia en la base de datos, debemos cambiar la implementación del DAO que utiliza `TaskServices`.

Modifica el constructor de `managerServer/src/main/java/services/TaskServices.java`:

```java
// services/TaskServices.java

import daos.TaskDaoDB; // Importar TaskDaoDB
import daos.StageDaoDB; // Importar el nuevo StageDaoDB

public class TaskServices {

    private Dao<Task,Integer> repository;
    private Dao<TaskStage,Integer> repositoryStage;

    public TaskServices(){
        // Cambiar de TaskMemDao a TaskDaoDB
        repository = new TaskDaoDB(); 
        // Cambiar de StageDao a un nuevo StageDaoDB que crearemos
        repositoryStage = new StageDaoDB(); 
    }

    // ... resto de los métodos
}
```

## Paso 3: Crear `StageDaoDB` para los Estados

El `StageDao` actual funciona en memoria. Necesitamos una versión que consulte la tabla `task_stage` de la base de datos.

Crea el archivo `managerServer/src/main/java/daos/StageDaoDB.java`:

```java
// daos/StageDaoDB.java
package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DBConfig.ConnectionManager;
import model.TaskStage;

public class StageDaoDB implements Dao<TaskStage, Integer> {

    @Override
    public List<TaskStage> findAll() {
        List<TaskStage> stages = new ArrayList<>();
        String query = "SELECT * FROM task_stage ORDER BY id";
        try (Connection conn = ConnectionManager.getInstance().getConnection();
             Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                TaskStage stage = new TaskStage();
                stage.setId(result.getInt("id"));
                stage.setName(result.getString("name"));
                stage.setDescription(result.getString("description"));
                stages.add(stage);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar todos los stages", e);
        }
        return stages;
    }

    @Override
    public TaskStage findById(Integer id) {
        TaskStage stage = null;
        String query = "SELECT * FROM task_stage WHERE id = ?";
        try (Connection conn = ConnectionManager.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    stage = new TaskStage();
                    stage.setId(result.getInt("id"));
                    stage.setName(result.getString("name"));
                    stage.setDescription(result.getString("description"));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el stage con id " + id, e);
        }
        return stage;
    }

    // Los métodos update, delete y save no son necesarios para los estados
    // ya que se gestionan directamente en la BD, pero deben implementarse.

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
```

## Paso 4: Completar la Implementación de `TaskDaoDB`

El archivo `TaskDaoDB.java` está incompleto. Reemplaza su contenido con la siguiente implementación que incluye todos los métodos CRUD.

```java
// daos/TaskDaoDB.java
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

public class TaskDaoDB implements Dao<Task, Integer> {

    private Dao<TaskStage, Integer> stageDao;

    public TaskDaoDB(){
        this.stageDao = new StageDaoDB(); 
    }

    @Override
    public void save(Task entity) {
        String query = "INSERT INTO task (title, description, due_date, priority, stage_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getDescription());
            statement.setString(3, entity.getDueDate());
            statement.setString(4, entity.getPriority());
            statement.setInt(5, entity.getStage().getId());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la tarea", e);
        }
    }

    @Override
    public Task findById(Integer id) {
        Task task = null;
        String query = "SELECT * FROM task WHERE id = ?";
        try (Connection conn = ConnectionManager.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    task = mapResultSetToTask(result);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar la tarea con id " + id, e);
        }
        return task;
    }

    @Override
    public Task update(Task entity) {
        String query = "UPDATE task SET title = ?, description = ?, due_date = ?, priority = ?, stage_id = ? WHERE id = ?";
        try (Connection conn = ConnectionManager.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getDescription());
            statement.setString(3, entity.getDueDate());
            statement.setString(4, entity.getPriority());
            statement.setInt(5, entity.getStage().getId());
            statement.setInt(6, entity.getId());

            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la tarea", e);
        }
        return entity;
    }

    @Override
    public void delete(Task entity) {
        String query = "DELETE FROM task WHERE id = ?";
        try (Connection conn = ConnectionManager.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, entity.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la tarea", e);
        }
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM task ORDER BY id";
        try (Connection conn = ConnectionManager.getInstance().getConnection();
             Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                tasks.add(mapResultSetToTask(result));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar todas las tareas", e);
        }
        return tasks;
    }

    private Task mapResultSetToTask(ResultSet result) throws Exception {
        Task task = new Task();
        task.setId(result.getInt("id"));
        task.setTitle(result.getString("title"));
        task.setDescription(result.getString("description"));
        task.setDueDate(result.getString("due_date"));
        task.setPriority(result.getString("priority"));

        int stageId = result.getInt("stage_id");
        TaskStage stage = stageDao.findById(stageId);
        task.setStage(stage);

        return task;
    }
}
```

## Paso 5: Ajustar la Lógica de `TaskServices`

La lógica para obtener todas las tareas y agruparlas por estado debe cambiar, ya que ahora los datos vienen de la base de datos.

Reemplaza el método `getTask` en `managerServer/src/main/java/services/TaskServices.java` por una versión más eficiente que evita el bucle anidado:

```java
// services/TaskServices.java
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public List<TaskStage> getTask(){
    // 1. Obtener todos los estados y crear un mapa para acceso rápido
    List<TaskStage> stages = repositoryStage.findAll();
    Map<Integer, TaskStage> stageMap = new HashMap<>();
    for (TaskStage stage : stages) {
        stageMap.put(stage.getId(), stage);
    }

    // 2. Obtener todas las tareas de la base de datos
    List<Task> allTasks = repository.findAll();

    // 3. Asignar cada tarea a su estado correspondiente usando el mapa
    for (Task task : allTasks) {
        if (task.getStage() != null) {
            TaskStage correspondingStage = stageMap.get(task.getStage().getId());
            if (correspondingStage != null) {
                correspondingStage.getTasks().add(task);
            }
        }
    }

    return stages;
}
```

El método `changeStage` también necesita una pequeña corrección para asegurar que la entidad que se actualiza es la correcta.

```java
// services/TaskServices.java

public Task changeStage(int taskId, int stageId){
    Task t = repository.findById(taskId);
    if (t != null) {
        TaskStage newStage = repositoryStage.findById(stageId);
        if (newStage != null) {
            t.setStage(newStage);
            repository.update(t);
        }
    }
    return t;
}
```
