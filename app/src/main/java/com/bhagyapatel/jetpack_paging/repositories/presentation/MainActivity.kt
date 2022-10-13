package com.bhagyapatel.jetpack_paging.repositories.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bhagyapatel.jetpack_paging.repositories.RepositoriesPagingSource
import com.bhagyapatel.jetpack_paging.repositories.data.Remote.Repository
import com.bhagyapatel.jetpack_paging.repositories.presentation.dataList.RepositoriesScreen
import com.bhagyapatel.jetpack_paging.repositories.presentation.dataList.RepositoriesViewModel
import com.bhagyapatel.jetpack_paging.ui.theme.JetpackPagingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackPagingTheme {
                val viewModel : RepositoriesViewModel = viewModel()
                val reposFlow = viewModel.repositories
                val lazyRepoItems : LazyPagingItems<Repository>
                    = reposFlow.collectAsLazyPagingItems()
                RepositoriesScreen(lazyRepoItems)
            }
        }
    }
}
