package com.multi.todoappwithexpress.features.tasks.presentation.tasks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.multi.todoappwithexpress.features.tasks.presentation.tasks.components.TaskItem

@Composable
fun TasksScreen(
    viewModel: TasksViewModel = hiltViewModel(),
    onNavigateToDetails: (Int) -> Unit
) {
    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = state.value.tasks) {
        viewModel.getTasks()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Todos")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigateToDetails(-1)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->

        if (state.value.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        LazyColumn(contentPadding = innerPadding) {
            items(state.value.tasks) {
                TaskItem(
                    task = it,
                    onNavigateToDetails = onNavigateToDetails,
                    onDeleteClick = {
                        viewModel.onEvent(TasksEvent.DeleteNote(it.id))
                    }
                )
            }
        }
    }
}