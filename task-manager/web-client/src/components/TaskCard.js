

function createTaskElement(task) {
    const li = document.createElement("div");
    li.className = "task-item";
    li.draggable = true; 

    const title = document.createElement("span");
    title.textContent = `${task.title}`; 

    const description = document.createElement("span");
    description.textContent = ` - ${task.description}`;

    const createdAt = document.createElement("span");
    createdAt.textContent = ` (Due: ${task.dueDate})`;

    const delBtn = document.createElement("button");
    delBtn.textContent = "Delete";
    delBtn.onclick = () => {
        li.remove();
    };

    li.appendChild(title);
    li.appendChild(description);
    li.appendChild(createdAt);

    li.appendChild(delBtn);

    li.addEventListener("dragstart", (e) => {
        e.dataTransfer.setData("text/plain", task.id); // o JSON.stringify(task)
        li.classList.add("dragging");
    });

    li.addEventListener("dragend", () => {
        li.classList.remove("dragging");
    });

    li.addEventListener("dragover", (e) => {
        e.preventDefault(); 
        li.classList.add("drag-over-sibling");
    });

    
    li.addEventListener("dragleave", () => {
        li.classList.remove("drag-over-sibling");
    });
    

    return li;
}

export default createTaskElement;