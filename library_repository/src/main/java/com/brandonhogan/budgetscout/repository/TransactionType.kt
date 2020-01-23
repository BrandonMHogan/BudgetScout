package com.brandonhogan.budgetscout.repository

enum class TransactionType(val value: String) {
    Transfer("transfer"),
    Income("income"),
    Expense("expense")
}