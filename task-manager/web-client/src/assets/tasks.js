const tasks =[
    {
        "id": 1,
        "name": "To Do",
        "description": "Tasks that need to be done",
        "tasks": [
            {
                "id": 1,
                "title": "Buy groceries",
                "description": "Milk, Bread, Eggs, Butter",
                "dueDate": "2023-10-01",
                "priority": "High"
            },
            {
                "id": 2,
                "title": "Read a book",
                "description": "Finish reading 'The Great Gatsby'",
                "dueDate": "2023-10-05",
                "priority": "Medium"
            }
        ]
    },
    {
        "id": 2,
        "name": "In Progress",
        "description": "Tasks that are currently being worked on",
        "tasks": [
            {
                "id": 3,
                "title": "Develop feature X",
                "description": "Implement the new feature for the app",
                "dueDate": "2023-10-10",
                "priority": "High"
            }
        ]
    },
    {
        "id": 3,
        "name": "Completed",
        "description": "Tasks that have been completed",
        "tasks": [
            {
                "id": 4,
                "title": "Morning workout",
                "description": "30 minutes of cardio and strength training",
                "dueDate": "2023-09-30",
                "priority": "Low"
            }
        ]
    }
]

export default tasks;
