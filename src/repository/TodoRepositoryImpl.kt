package ru.suleymanovtat.repository

import ru.suleymanovtat.entity.Todo
import ru.suleymanovtat.entity.TodoDraft
import java.util.*
import kotlin.random.Random.Default.nextLong

class TodoRepositoryImpl : TodoRepository {

    var todos = arrayListOf<Todo>(
        Todo(1, "Task 1", true),
        Todo(2, "Task 2", true),
        Todo(3, "Task 3", false),
        Todo(4, "Task 4", false),
    )

    override fun getAllToDos(): List<Todo> {
        return todos
    }

    override fun getToDo(id: Long): Todo? {
        return todos.firstOrNull { it.id == id }
    }

    override fun addTodoDraft(draft: TodoDraft): Todo {
        val todo = Todo(
            id = Random().nextLong(),
            title = draft.title,
            done = draft.done
        )
        todos.add(todo)
        return todo
    }

    override fun removeTodo(id: Long): Boolean {
        return todos.removeIf { it.id == id }
    }

    override fun updateTodo(id: Long, draft: TodoDraft): Boolean {
        val todo = todos.firstOrNull { it.id == id } ?: return false
        todo.title = draft.title
        todo.done = draft.done
        return true
    }
}