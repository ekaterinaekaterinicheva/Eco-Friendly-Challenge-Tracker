package com.example.ecofriendlychallengeapp.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.ecofriendlychallengeapp.ui.reusable.BasicDesign
import com.example.ecofriendlychallengeapp.viewmodel.ChallengeViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: ChallengeViewModel) {

    // Observing LiveData from the ViewModel
    val currentChallenge by viewModel.currentChallenge.observeAsState("You haven't set a goal yet")
    val loggedDays by viewModel.loggedDays.observeAsState(0)
    val missedDays by viewModel.missedDays.observeAsState(0)
    val passedDays by viewModel.passedDays.observeAsState(0)
    val totalGoalDays by viewModel.totalGoalDays.observeAsState(0)
    val daysLeft by viewModel.daysLeft.observeAsState(0)

    BasicDesign {
        // --- Home ---
        Text("Home", style = MaterialTheme.typography.headlineSmall)
        HorizontalDivider(thickness = 2.dp)
        Spacer(modifier = Modifier.height(35.dp))

        // --- Current Goal ---
        Text("Current Goal", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(25.dp))

        // --- Current Challenge Name ---
        Text("$currentChallenge", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(30.dp))

        // --- Progress Bar for a Current Challenge ---

        val progressValue by animateFloatAsState(
            targetValue = if (totalGoalDays > 0)
                (passedDays.toFloat() / totalGoalDays).coerceIn(0f, 1f)
            else 0f,
            animationSpec = tween(durationMillis = 600)
        )

        @Composable
        fun ProgressBar(progressValue: Float) {

            val trackColor = MaterialTheme.colorScheme.surfaceVariant
            val fillColor = MaterialTheme.colorScheme.primary

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(trackColor) // Track color
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val width = size.width * progressValue

                    drawRect(
                        color = fillColor,
                        size = Size(width, size.height)
                    )
                }
            }
        }
        ProgressBar(progressValue)

        Spacer(modifier = Modifier.height(30.dp))

        // --- Performance Summaries ---
        Text("Days left to achieve your goal: $daysLeft", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(15.dp))
        Text("Days logged as success: $loggedDays", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(15.dp))
        Text("Days you missed: $missedDays", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(30.dp))

        // Evaluating the user’s progress for a current challenge and returning a message
        val motivationalMessage = MutableLiveData<String>()

        motivationalMessage.value = when {
            // Goal achievement message:
            progressValue == 1f -> "Congratulations on achieving your goal!"
            // Halfway through message:
            progressValue >= 0.5 -> "You’re halfway through! Keep it up!"
            // Usual message:
            else -> "You're doing great! Carry on!"
        }

        // --- Delete Current Goal Button ---
        val message by viewModel.message.observeAsState()
        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { viewModel.deleteEntry() },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Delete Current Goal", style = MaterialTheme.typography.titleLarge)
                }
            }
            LaunchedEffect(message) {
                message?.let {
                    SnackbarHostState().showSnackbar(it)
                    viewModel.clearMessage()
                }
            }
        }
    }
}