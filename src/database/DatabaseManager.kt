package ru.suleymanovtat.database

import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import ru.suleymanovtat.entity.Todo
import ru.suleymanovtat.entity.TodoDraft
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet


class DatabaseManager {

    // config
    private val hostname = "localhost:8889"
    private val databaseName = "ktor_todo"
    private val username = "root"
    private val password = "root"

    // database
    private lateinit var ktormDatabase: Database

    init {
        try {
            val сonnection = DriverManager.getConnection(
                "jdbc:mysql://$hostname/$databaseName?serverTimezone=UTC",
                username,
                password
            )
            сonnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
            ktormDatabase = Database.connect {
                object : Connection by сonnection {
                    override fun close() {
                    }
                }
            }
            println("сonnection ok")
        } catch (e: Exception) {
            print("Error сonnection" + e.message)
        }
    }

    fun getAllTodos(): List<DBTodoEntity> {
        return ktormDatabase.sequenceOf(DBTodoTable).toList()
    }

    fun getTodo(id: Long): DBTodoEntity? {
        return ktormDatabase.sequenceOf(DBTodoTable)
            .firstOrNull { it.id eq id }
    }

    fun addTodo(draft: TodoDraft): Todo {
        val insertedId = ktormDatabase.insertAndGenerateKey(DBTodoTable) {
            set(DBTodoTable.title, draft.title)
            set(DBTodoTable.done, draft.done)
        } as Long
        return Todo(insertedId, draft.title, draft.done)
    }

    fun updateTodo(id: Long, draft: TodoDraft): Boolean {
        val updatedRows = ktormDatabase.update(DBTodoTable) {
            set(DBTodoTable.title, draft.title)
            set(DBTodoTable.done, draft.done)
            where {
                it.id eq id
            }
        }
        return updatedRows > 0
    }

    fun removeTodo(id: Long): Boolean {
        val deletedRows = ktormDatabase.delete(DBTodoTable) {
            it.id eq id
        }
        return deletedRows > 0
    }

}