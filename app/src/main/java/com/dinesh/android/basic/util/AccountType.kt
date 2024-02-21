package com.dinesh.android.basic.util


enum class AccountType(val displayText: String, val apiValue: String) {
    CHECKING("Checking", "checking"),
    SAVINGS("Savings", "savings"),
    CREDIT_CARD("Credit Card", "credit card"),
    INVESTMENT("Investment", "investment"),
    LOAN("Loan", "loan"),
    MORTGAGE("Mortgage", "mortgage"),
    OTHER("Other", "other"),
}