
import createTaskElement from "../components/TaskCard.js";
import ListTask from "../components/ListTask.js";

const onAddTask = (e, container, title, description, dueDate) => {
    const element = {
        "id": 0,
        "title": title,
        "description": description,
        "dueDate": dueDate,
        "priority": "High"
    }
    fetch("http://localhost:3000/tasks", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(element)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Server response was not ok");
        }
        return response.json();
    })
    .then(data => {
        const newTask = createTaskElement(data)
        container.appendChild(newTask)
        console.log(data)
    })
    .catch(error => {
        console.error("Error creating task:", error);
        alert("Error creating task: " + error.message);
    });
}

const BoardTask = (tasks) => {
    const comp = document.createElement("div");
    comp.className = "board-task";
    const h1 = document.createElement("h1");
    h1.textContent = "Task Manager";

    const input = document.createElement("input");
    input.type = "text";
    input.id = "task-name"
    input.placeholder = "Nueva tarea...";

    const description = document.createElement("input");
    description.type = "text";
    description.id = "task-description"
    description.placeholder = "Descripcion...";

    const dueDate = document.createElement("input");
    dueDate.type = "date";
    dueDate.id = "task-due-date"
    dueDate.placeholder = "Due Date...";
    
    const lists = document.createElement("div");
    lists.className = "group-lists";

    const button = document.createElement("button");
    button.textContent = "Guardar";
    
    tasks.forEach(task => {
        const listElement = ListTask(task);
        lists.appendChild(listElement);
    });
    
    button.onclick = (e) => onAddTask(e, lists.firstElementChild, input.value, description.value, dueDate.value)
    
    comp.appendChild(input)
    comp.appendChild(description)
    comp.appendChild(dueDate)
    comp.appendChild(button)
    comp.appendChild(lists)

    return comp;
}



export default BoardTask;