package com.multi.todoappwithexpress.navigation

sealed class Screen(val route: String) {
    object TasksScreen : Screen(route = "tasks_screen")
    object TaskDetailsScreen : Screen(route = "task_details_screen")
}
