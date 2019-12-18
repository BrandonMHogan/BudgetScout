package com.brandonhogan.budgetscout.budget

interface HelloRepository {
    fun giveHello(): String
}

class HelloRepositoryImpl : HelloRepository {
    override fun giveHello() = "Hello Koin. Did this work?"
}