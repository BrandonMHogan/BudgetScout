package com.brandonhogan.budgetscout.repository

enum class TransactionType(val value: String) {
    Transfer("transfer"),
    New("new"),
    Edit("edit"),
    Delete("delete")
}