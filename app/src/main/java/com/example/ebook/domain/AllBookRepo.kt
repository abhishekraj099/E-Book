package com.example.ebook.domain

import com.example.ebook.Common.BookCategoryModel
import com.example.ebook.Common.BookModel
import com.example.ebook.Common.ResultState
import kotlinx.coroutines.flow.Flow


// This is interface because we store get the full data in the data layer then filter it out in the domain layer then again access it in the data layer or view model. So the functions here will be interface only.
interface AllBookRepo {
    suspend fun getAllBooks(): Flow<ResultState<List<BookModel>>>
    suspend fun getAllCategory(): Flow<ResultState<List<BookCategoryModel>>>
    suspend fun getAllBooksByCategory(category: String): Flow<ResultState<List<BookModel>>>

    suspend fun addBook(bookUrl: String, bookName: String, category: String): Flow<ResultState<Unit>>
}