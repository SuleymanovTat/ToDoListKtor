package ru.suleymanovtat.repository

import ru.suleymanovtat.entity.Todo
import ru.suleymanovtat.entity.TodoDraft

interface TodoRepository {

    fun getAllToDos(): List<Todo>
    fun getToDo(id: Long): Todo?
    fun addTodoDraft(draft: TodoDraft): Todo
    fun removeTodo(id: Long): Boolean
    fun updateTodo(id: Long, draft: TodoDraft): Boolean
}