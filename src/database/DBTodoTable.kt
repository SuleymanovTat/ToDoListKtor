package ru.suleymanovtat.database

import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.long
import org.ktorm.schema.varchar
import org.ktorm.entity.Entity

object DBTodoTable: Table<DBTodoEntity>("Todo") {

    val id = long("id").primaryKey().bindTo { it.id }
    val title = varchar("title").bindTo { it.title }
    val done = boolean("done").bindTo { it.done }
}

interface DBTodoEntity: Entity<DBTodoEntity> {

    companion object : Entity.Factory<DBTodoEntity>()

    val id: Long
    val title: String
    val done: Boolean

}