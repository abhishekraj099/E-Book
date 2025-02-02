package com.example.ebook.Common


sealed class ResultState<out T>{
    data class Success<out T>(val data: T): ResultState<T>()
    data class Error<T>(val exception: Throwable): ResultState<T>()
    object Loading : ResultState<Nothing>()
}


data class BookModel(
    val bookUrl: String = "",
    val bookName: String = "",
    val category: String = "",
    val imageUrl: String = "",
)

data class BookCategoryModel(
    val name: String = "",
    val imageUrl: String = ""
)