package com.example.bromode.ui.screens.tracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bromode.viewmodel.WorkoutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseTrackerScreen(
    navController: NavController,
    vm: WorkoutViewModel
) {
    val bg = Brush.verticalGradient(listOf(Color(0xFF0B0B0F), Color(0xFF0F172A)))
    val neon = Color(0xFFA3FF00)
    val card = Color.White.copy(alpha = 0.06f)

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Tracker",
                        color = Color.White,
                        fontWeight = FontWeight.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    TextButton(onClick = {
                        vm.clearWorkout()
                        navController.popBackStack()
                    }) {
                        Text(
                            text = "Reset",
                            color = neon,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(bg)
                .padding(innerPadding)
                .padding(18.dp)
        ) {

            if (vm.exercises.isEmpty()) {
                Text(
                    text = "No workout loaded. Go back and add exercises.",
                    color = Color.White.copy(alpha = 0.7f)
                )
                return@Column
            }

            vm.exercises.forEachIndexed { exIndex, ex ->

                Card(
                    colors = CardDefaults.cardColors(containerColor = card),
                    shape = RoundedCornerShape(22.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = ex.name,
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Header row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("SET", color = Color.White.copy(0.6f), fontSize = 12.sp, modifier = Modifier.width(34.dp))
                            Text("WEIGHT", color = Color.White.copy(0.6f), fontSize = 12.sp, modifier = Modifier.width(100.dp))
                            Text("REPS", color = Color.White.copy(0.6f), fontSize = 12.sp, modifier = Modifier.width(100.dp))
                            Text("DONE", color = Color.White.copy(0.6f), fontSize = 12.sp)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        ex.sets.forEachIndexed { setIndex, setRow ->

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text(
                                    text = "${setIndex + 1}",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.width(34.dp)
                                )

                                OutlinedTextField(
                                    value = setRow.weight,
                                    onValueChange = { setRow.weight = it },
                                    singleLine = true,
                                    placeholder = { Text("kg", color = Color.White.copy(0.4f)) },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = neon,
                                        unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White,
                                        cursorColor = neon
                                    ),
                                    shape = RoundedCornerShape(14.dp),
                                    modifier = Modifier.width(100.dp)
                                )

                                OutlinedTextField(
                                    value = setRow.reps,
                                    onValueChange = { setRow.reps = it },
                                    singleLine = true,
                                    placeholder = { Text("reps", color = Color.White.copy(0.4f)) },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = neon,
                                        unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White,
                                        cursorColor = neon
                                    ),
                                    shape = RoundedCornerShape(14.dp),
                                    modifier = Modifier.width(100.dp)
                                )

                                IconButton(onClick = { setRow.done = !setRow.done }) {
                                    Icon(
                                        imageVector = if (setRow.done) Icons.Filled.CheckCircle else Icons.Filled.RadioButtonUnchecked,
                                        contentDescription = "Done",
                                        tint = if (setRow.done) neon else Color.White.copy(alpha = 0.55f)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedButton(
                            onClick = { vm.addSet(exIndex) },
                            shape = RoundedCornerShape(18.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = neon),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "Add set", tint = neon)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("ADD SET", fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = neon, contentColor = Color.Black),
                shape = RoundedCornerShape(22.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
            ) {
                Text("FINISH WORKOUT", fontWeight = FontWeight.Black, letterSpacing = 1.2.sp)
            }
        }
    }
}