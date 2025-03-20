package com.wisnu.kurniawan.composetodolist.home.widgets

import android.os.Bundle
import android.util.Log
import java.util.regex.Pattern
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier

//@AndroidEntryPoint
class AssistantActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold { contentPadding ->
                Row {}
            }
        }

        val data = intent.data

        if (data != null && data.scheme == "your_data_scheme") {
            val action = data.host

            Log.d("LOG_TAG---", "AssistantActivity-onCreate#26: $action")

//            if (action == "addTask" || action == "updateTask") {
//                val query = data.getQueryParameter("q") ?: ""
//
//                val (task, listName, newTask) = extractDataFromQuery(query)
//
//                // Handle task addition or update based on the action
//                if (action == "addTask") {
//                    // Add task to the list
////                    todoList.addTask(task, listName)
//                } else if (action == "updateTask") {
//                    // Update the task in the list
////                    todoList.updateTask(task, listName, newTask)
//                }
//            }
        }
    }

    private fun extractDataFromQuery(query: String): Triple<String, String, String> {
        val taskPattern = Pattern.compile(".*add (.*) to (.*)")
        val updatePattern = Pattern.compile(".*update (.*) in (.*) to (.*)")

        val taskMatcher = taskPattern.matcher(query)
        val updateMatcher = updatePattern.matcher(query)

        return if (taskMatcher.matches()) {
            Triple(taskMatcher.group(1)!!, taskMatcher.group(2)!!, "")
        } else if (updateMatcher.matches()) {
            Triple(updateMatcher.group(1)!!, updateMatcher.group(2)!!, updateMatcher.group(3)!!)
        } else {
            Triple("", "", "") // Handle invalid queries
        }
    }

    // Replace with your actual TodoList implementation
//    private val todoList = TodoList()
}
