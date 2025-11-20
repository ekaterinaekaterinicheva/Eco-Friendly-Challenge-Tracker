package com.example.ecofriendlychallengeapp.ui.setGoal

import androidx.compose.foundation.layout.Arrangement
import com.example.ecofriendlychallengeapp.viewmodel.ChallengeViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ecofriendlychallengeapp.ui.reusable.BasicDesign
import java.time.LocalDate

@Composable
fun SetGoalScreen(navController: NavController, viewModel: ChallengeViewModel) {

    // Challenge labels for the radio buttons:
    val challengeOptions = listOf(
        "Taking a 5-minute shower",
        "Turning off taps while brushing teeth",
        "Taking public transport",
        "Turning off lights when leaving a room",
        "Unplug electronics if not in use",
        "Using a reusable cup / bottle"
    )

    // Local UI state variables
    var selectedChallenge by remember { mutableStateOf<String?>(null) } // for a selected radio button
    var durationInput by remember { mutableStateOf("") } // for a typed duration input
    val startDate = remember { LocalDate.now() }

    // Observing from the ViewModel
    val currentChallenge by viewModel.currentChallenge.observeAsState()
    val goalActive = !currentChallenge.isNullOrEmpty()

    BasicDesign {
        // --- Select Your Goal ---
        Text("Goals", style = MaterialTheme.typography.headlineSmall)
        HorizontalDivider(thickness = 2.dp)
        Spacer(modifier = Modifier.height(35.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.Start, // left-align by default
            verticalArrangement = Arrangement.Top // default
        ) {
            if (!goalActive) {
            // --- Select Your Challenge ---
            Text(
                "Select your challenge:",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(20.dp))

            // --- A List of Challenges (Radio Buttons) ---
            // selectableGroup() is used for accessibility with screen readers
            Column(modifier = Modifier.selectableGroup()) {
                challengeOptions.forEach { option ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .selectable( // makes the Row act as a single selectable item
                                selected = option == selectedChallenge,
                                onClick = { selectedChallenge = option },
                                // role is used for accessibility to inform that an element is a radio button)
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Creating the RadioButton composable
                        RadioButton(
                            selected = (option == selectedChallenge),
                            onClick = null
                        )
                        Text(
                            text = option,
                            modifier = Modifier.padding(start = 10.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))

            // --- Text Field for Duration Input ---
                TextField(
                    value = durationInput,
                    // onValueChange updates the current value of the text when the user types
                    onValueChange = { durationInput = it },
                    label = { Text("How many days do you want to challenge yourself?") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(15.dp))

                // --- Save Button ---
                Button(
                    onClick = {
                        // Converting the user's input to int
                        val duration = durationInput.toIntOrNull()
                            ?: 7  // Applying a fallback if input is invalid
                        if (selectedChallenge != null) {
                            // Updating the ViewModel on Save
                            viewModel.currentChallenge.postValue(selectedChallenge)
                            // Passing the start date and goal duration to the ViewModel
                            viewModel.setGoal(startDate = startDate, durationDays = duration)
                            // Clearing the input after saving
                            durationInput = ""
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .width(100.dp)
                        .height(50.dp)
                ) {
                    Text("Save", style = MaterialTheme.typography.titleLarge)
                }
            // Notification
            } else {
                Text(
                    text = "You are already in the process of achieving a goal: \"$currentChallenge\".",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "To start another goal, delete this goal first (see Home screen).",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}