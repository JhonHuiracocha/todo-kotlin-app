package com.multi.todoappwithexpress.features.tasks.presentation.task_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TaskDetailsScreen(
    viewModel: TaskDetailsViewModel = hiltViewModel(),
    onNavigateToTasks: () -> Unit
) {
    val state = viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Details")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onNavigateToTasks()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(TaskDetailsEvent.DeleteTaskById)
                            onNavigateToTasks()
                        },
                        enabled = viewModel.taskId != -1
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
            )
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                OutlinedTextField(
                    value = state.value.title,
                    onValueChange = { title ->
                        viewModel.onEvent(TaskDetailsEvent.EnteredTitle(title))
                    },
                    label = {
                        Text(text = "Title")
                    },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                OutlinedTextField(
                    value = state.value.description,
                    onValueChange = { description ->
                        viewModel.onEvent(TaskDetailsEvent.EnteredDescription(description))
                    },
                    label = {
                        Text(text = "Content")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModel.onEvent(TaskDetailsEvent.Submit)
                        onNavigateToTasks()
                    }
                ) {
                    Text(text = if (viewModel.taskId == -1) "Create" else "Update")
                }
            }
        }
    }
}