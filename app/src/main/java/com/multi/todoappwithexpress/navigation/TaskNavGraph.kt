package com.multi.todoappwithexpress.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.multi.todoappwithexpress.features.tasks.presentation.task_detail.TaskDetailsScreen
import com.multi.todoappwithexpress.features.tasks.presentation.tasks.TasksScreen

@Composable
fun TaskNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.TasksScreen.route
    ) {
        composable(
            route = Screen.TasksScreen.route
        ) {
            TasksScreen(
                onNavigateToDetails = { taskId ->
                    navController.navigate(
                        route = "${Screen.TaskDetailsScreen.route}?taskId=$taskId"
                    )
                }
            )
        }

        composable(
            route = "${Screen.TaskDetailsScreen.route}?taskId={taskId}",
            arguments = listOf(navArgument("taskId") {
                type = NavType.IntType
                defaultValue = -1
            })
        ) {
            TaskDetailsScreen(
                onNavigateToTasks = {
                    navController.popBackStack()
                }
            )
        }
    }
}