package com.bhagyapatel.jetpack_paging.repositories.presentation.dataList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.bhagyapatel.jetpack_paging.repositories.data.Remote.Repository
import com.bhagyapatel.jetpack_paging.ui.theme.JetpackPagingTheme

@Composable
fun RepositoriesScreen(repos: LazyPagingItems<Repository>) {
    LazyColumn(
        contentPadding = PaddingValues(
            horizontal = 8.dp,
            vertical = 8.dp
        )
    ) {
        itemsIndexed(repos) { index, repo ->
            if (repo != null)
                RepositoryItem(index = index, item = repo)
        }

//  Have used itemsIndexed() instead of items():: This is because, since our app will support
//  pagination, we want to paint the index of the element displayed on the screen to better
//  understand where we are at right now. To get the index of the composable item visible on the
//  screen, the itemsIndexed() function provides us with this information out of the box.

        val refreshLoadState = repos.loadState.refresh
        val appendLoadState = repos.loadState.append

        when {
            refreshLoadState is LoadState.Loading -> {
                item {
                    LoadingItem(
                        Modifier.fillParentMaxSize()
                    )
                }
            }
            refreshLoadState is LoadState.Error -> {
                val error = refreshLoadState.error
                item {
                    ErrorItem(
                        message = error.localizedMessage ?: "",
                        modifier = Modifier.fillParentMaxSize(),
                        onClick = { repos.retry() }
                    )
                }
            }
        }

        when {
            appendLoadState is LoadState.Loading -> {
                item {
                    LoadingItem(Modifier.fillMaxWidth())
                }
            }

            appendLoadState is LoadState.Error -> {
                val error = appendLoadState.error
                item {
                    ErrorItem(
                        message = error.localizedMessage ?: "",
                        onClick = { repos.retry() }
                    )
                }
            }
        }

    }
}

@Composable
fun ErrorItem(message: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.h6,
            maxLines = 2,
            color = Color.Red
        )

        Button(onClick = onClick, modifier = Modifier.padding(8.dp)) {
            Text(text = "Try Again")
        }
    }
}

@Composable
fun LoadingItem(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun RepositoryItem(index: Int, item: Repository) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .height(120.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = index.toString(),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .weight(0.2f)
                    .padding(8.dp)
            )

            Column(modifier = Modifier.weight(0.8f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.h6
                )

                Text(
                    text = item.description, style =
                    MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
            }
        }
    }
}


@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun DefaultPreview() {
    JetpackPagingTheme {

    }
}
