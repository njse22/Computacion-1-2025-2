import BoardTask from "./pages/BoardTask.js";

const getTasks = () => {
    return fetch("http://localhost:3000/tasks")
    .then(response => {
        if (!response.ok) {
            throw new Error("Server response was not ok");
        }
        return response.json();
    })
    .then(data => {
        return data;
    })
    .catch(error => {
        console.error("Error fetching tasks:", error);
        alert("Error fetching tasks: " + error.message);
        return [];
    });
}

function renderApp() {
    const app = document.getElementById("app");
    const h1 = document.createElement("h1");
    h1.textContent = "Task Manager";
    app.appendChild(h1);

    getTasks().then(tasks => {
        const board = BoardTask(tasks);
        app.appendChild(board);
    });
}

renderApp();
