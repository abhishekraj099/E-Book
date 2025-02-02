package com.example.ebook.Presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.ebook.Common.BookModel
import com.example.ebook.Viewmodel.ViewModel
import com.example.ebook.navigation.NavigationItem
import com.example.ebook.ui.theme.AppColors

@Composable
fun AllBooksScreen(viewModel: ViewModel = hiltViewModel(), navController: NavController) {
    val res = viewModel.state.value

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(NavigationItem.AddBookScreen) },
                containerColor = AppColors.PrimaryColor,
                contentColor = AppColors.OnPrimaryColor
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Book")
            }
        }
    ) { innerPadding ->
        innerPadding
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.SurfaceColor)
                .padding()
        ) {
            when {
                res.isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
//                        CircularProgressIndicator(
//                            color = PrimaryColor
//                        )
                        ShimmerLoading()
                    }
                }

                res.error.isNotEmpty() -> {
                    ErrorMessage(error = res.error)
                }

                res.items.isNotEmpty() -> {
                    BookList(books = res.items, navController = navController)
                }
            }
        }
    }

}

@Composable
fun BookList(books: List<BookModel>, navController: NavController) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = 300.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalItemSpacing = 16.dp,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(books) { book ->
            BookItem(
                book = book,
                onBookClick = {
                    navController.navigate(
                        NavigationItem.ShowPdfScreen(
                            url = book.bookUrl,
                            bookName = book.bookName
                        )
                    )
                },

                )
        }
    }
}

@Composable
fun BookItem(book: BookModel, onBookClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable(onClick = onBookClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.SurfaceColor)
        ) {
            BookCover(
                imageUrl = book.imageUrl,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.4f)
            )
            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = book.bookName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = AppColors.TextPrimaryColor,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = book.category,
                        fontSize = 16.sp,
                        color = AppColors.TextSecondaryColor
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Reading Progress",
                        fontSize = 14.sp,
                        color = AppColors.TextSecondaryColor
                    )
                    LinearProgressIndicator(
                        progress = { 0.3f }, // Replace with actual reading progress
                        modifier = Modifier.fillMaxWidth(),
                        color = AppColors.AccentColor1,
                        trackColor = Color.LightGray,
                    )
                }
            }
        }
    }
}

@Composable
fun BookCover(imageUrl: String?, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl ?: "https://m.media-amazon.com/images/I/31RW8HQ31WL._SY445_SX342_.jpg")
                .crossfade(true)
                .build(),
            contentDescription = "Book cover",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = AppColors.AccentColor1)
                    }
                }
                is AsyncImagePainter.State.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Book,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }
                else -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }
        // Add a subtle gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    )
                )
        )
    }
}



@Composable
fun ErrorMessage(error: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = error,
            color = AppColors.ErrorColor,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}