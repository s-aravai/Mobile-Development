package com.example.bromode.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SetRow {
    var weight by mutableStateOf("")
    var reps by mutableStateOf("")
    var done by mutableStateOf(false)
}

class ExerciseState(val name: String) {
    val sets = mutableStateListOf(SetRow(), SetRow(), SetRow())
}

class WorkoutViewModel : ViewModel() {

    val exercises = mutableStateListOf<ExerciseState>()

    fun addExercise(name: String) {
        val clean = name.trim()
        if (clean.isBlank()) return
        exercises.add(ExerciseState(clean))
    }

    fun removeExercise(index: Int) {
        if (index in exercises.indices) exercises.removeAt(index)
    }

    fun addSet(exerciseIndex: Int) {
        if (exerciseIndex !in exercises.indices) return
        exercises[exerciseIndex].sets.add(SetRow())
    }

    fun clearWorkout() = exercises.clear()
}