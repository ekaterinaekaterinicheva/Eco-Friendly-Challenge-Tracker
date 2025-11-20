package com.example.ecofriendlychallengeapp.ui.progressLogScreen

import com.example.ecofriendlychallengeapp.viewmodel.ChallengeViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ecofriendlychallengeapp.ui.reusable.BasicDesign

@Composable
fun LogProgressScreen(navController: NavController, viewModel: ChallengeViewModel) {

    // Observing the LiveData and representing their values via observeAsState
    val currentChallenge by viewModel.currentChallenge.observeAsState("")
    val message by viewModel.message.observeAsState()

    BasicDesign {

        // --- Log Progress ---
        Text("Log Progress", style = MaterialTheme.typography.headlineSmall)
        HorizontalDivider(thickness = 2.dp)
        Spacer(modifier = Modifier.height(25.dp))

        // Centering content both vertically and horizontally via Box
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // --- Showing current challenge ---
                if (!currentChallenge.isNullOrEmpty()) {
                    Text("Your current challenge:",
                        style = MaterialTheme.typography.titleLarge)
                } else {
                    Text("You haven’t set a challenge yet.")
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "$currentChallenge",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(25.dp))
                // --- Question to the user ---
                Text(
                    text = "Did you manage to complete this challenge today?",
                    style = MaterialTheme.typography.titleLarge
                )

                // --- Yes / No Buttons ---
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { viewModel.insertEntry(success = true) },
                        modifier = Modifier
                            .width(100.dp)
                            .height(50.dp)
                    ) {
                        Text("Yes", style = MaterialTheme.typography.titleLarge)
                    }

                    Button(
                        onClick = { viewModel.insertEntry(success = false) },
                        modifier = Modifier
                            .width(100.dp)
                            .height(50.dp)
                    ) {
                        Text("No", style = MaterialTheme.typography.titleLarge)
                    }
                }

                // Snackbar
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(message) {
                    message?.takeIf { it.isNotEmpty() }?.let {
                        snackbarHostState.showSnackbar(it)
                        // Clearing so that the next message triggers correctly
                        viewModel.clearMessage()
                    }
                }
                SnackbarHost(hostState = snackbarHostState)
            }
        }
    }
}