package ru.suleymanovtat

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import ru.suleymanovtat.entity.TodoDraft
import ru.suleymanovtat.repository.MySQLTodoRepository
import ru.suleymanovtat.repository.TodoRepository
import ru.suleymanovtat.repository.TodoRepositoryImpl

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }


    routing {

//        val todoRepository: TodoRepository = MySQLTodoRepository()
        val todoRepository: TodoRepository = TodoRepositoryImpl()

        get("/") {
            call.respondText("Hello Suleymnaovtat")
        }

        get("/todos") {
            call.respond(todoRepository.getAllToDos())
        }

        get("/todos/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Id no found")
                return@get
            }
            val todo = todoRepository.getToDo(id)
            if (todo == null) {
                call.respond(HttpStatusCode.NotFound, "No found Object")
            } else {
                call.respond(todo)
            }
        }

        post("/todo") {
            val toDoDraft = call.receive<TodoDraft>()
            val todo = todoRepository.addTodoDraft(toDoDraft)
            call.respond(todo)
        }
        put("/todos/{id}") {
            val toDoDraft = call.receive<TodoDraft>()
            val id = call.parameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Id no found")
                return@put
            }
            val isUpdated = todoRepository.updateTodo(id, toDoDraft)
            if (isUpdated) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound, "Found no todo with the id $id")
            }
        }
        delete("/todos/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Id no found")
                return@delete
            }
            val isRemoted = todoRepository.removeTodo(id)
            if(isRemoted){
                call.respond(HttpStatusCode.OK)
            }else{
                call.respond(HttpStatusCode.NotFound, "Found no todo with the id $id")
            }
        }
    }
}

