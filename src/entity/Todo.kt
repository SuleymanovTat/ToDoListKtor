package ru.suleymanovtat.entity

data class Todo(
    val id: Long,
    var title: String? = null,
    var done: Boolean? = null
)