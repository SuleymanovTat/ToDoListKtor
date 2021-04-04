package ru.suleymanovtat.repository

import ru.suleymanovtat.database.DatabaseManager
import ru.suleymanovtat.entity.Todo
import ru.suleymanovtat.entity.TodoDraft

class MySQLTodoRepository : TodoRepository {

    private val database = DatabaseManager()

    override fun getAllToDos(): List<Todo> {
        return database.getAllTodos()
            .map { Todo(it.id, it.title, it.done) }
    }

    override fun getToDo(id: Long): Todo? {
        return database.getTodo(id)
            ?.let { Todo(it.id, it.title, it.done) }
    }

    override fun addTodoDraft(draft: TodoDraft): Todo {
        return database.addTodo(draft)
    }

    override fun removeTodo(id: Long): Boolean {
        return database.removeTodo(id)
    }

    override fun updateTodo(id: Long, draft: TodoDraft): Boolean {
        return database.updateTodo(id, draft)
    }
}