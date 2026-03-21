package com.example.mathgameapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MathGameApp()
        }
    }
}

data class Question(
    val number1: Int,
    val number2: Int,
    val answer: Int
)

sealed class Screen {
    object Start : Screen()
    object Question : Screen()
    object Result : Screen()
}

@Composable
fun MathGameApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Start) }

    var questionCount by remember { mutableStateOf(0) }
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var correctCount by remember { mutableStateOf(0) }
    var wrongCount by remember { mutableStateOf(0) }
    var questions by remember { mutableStateOf(listOf<Question>()) }

    fun generateQuestions(count: Int): List<Question> {
        return List(count) {
            val n1 = Random.nextInt(1, 21)
            val n2 = Random.nextInt(1, 21)
            Question(n1, n2, n1 + n2)
        }
    }

    fun startGame(totalQuestions: Int) {
        questionCount = totalQuestions
        currentQuestionIndex = 0
        correctCount = 0
        wrongCount = 0
        questions = generateQuestions(totalQuestions)
        currentScreen = Screen.Question
    }

    fun cancelGame() {
        currentScreen = Screen.Start
        questionCount = 0
        currentQuestionIndex = 0
        correctCount = 0
        wrongCount = 0
        questions = emptyList()
    }

    fun submitAnswer(userAnswer: String) {
        val currentQuestion = questions[currentQuestionIndex]
        val answerInt = userAnswer.toIntOrNull()

        if (answerInt == currentQuestion.answer) {
            correctCount++
        } else {
            wrongCount++
        }

        if (currentQuestionIndex < questionCount - 1) {
            currentQuestionIndex++
        } else {
            currentScreen = Screen.Result
        }
    }

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE3F2FD),
            Color(0xFFBBDEFB),
            Color(0xFF90CAF9)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
    ) {
        Scaffold(
            containerColor = Color.Transparent
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (currentScreen) {
                    is Screen.Start -> {
                        StartScreen(
                            onStartGame = { count ->
                                startGame(count)
                            }
                        )
                    }

                    is Screen.Question -> {
                        QuestionScreen(
                            question = questions[currentQuestionIndex],
                            questionNumber = currentQuestionIndex + 1,
                            totalQuestions = questionCount,
                            correctCount = correctCount,
                            wrongCount = wrongCount,
                            onNext = { answer ->
                                submitAnswer(answer)
                            },
                            onCancel = {
                                cancelGame()
                            }
                        )
                    }

                    is Screen.Result -> {
                        ResultScreen(
                            correctCount = correctCount,
                            wrongCount = wrongCount,
                            totalQuestions = questionCount,
                            onPlayAgain = {
                                currentScreen = Screen.Start
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StartScreen(
    onStartGame: (Int) -> Unit
) {
    var input by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Math Addition Game",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0D47A1)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Enter how many questions you want",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = input,
            onValueChange = {
                input = it
                errorMessage = ""
            },
            label = { Text("Number of Questions") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val count = input.toIntOrNull()
                if (count == null || count <= 0) {
                    errorMessage = "Please enter a valid number greater than 0"
                } else {
                    onStartGame(count)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1565C0)
            )
        ) {
            Text("Start Game", fontSize = 18.sp)
        }
    }
}

@Composable
fun QuestionScreen(
    question: Question,
    questionNumber: Int,
    totalQuestions: Int,
    correctCount: Int,
    wrongCount: Int,
    onNext: (String) -> Unit,
    onCancel: () -> Unit
) {
    var answerInput by remember(questionNumber) { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ScoreBox(title = "Correct", value = correctCount.toString())
            ScoreBox(title = "Wrong", value = wrongCount.toString())
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Question $questionNumber of $totalQuestions",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0D47A1)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${question.number1} + ${question.number2} = ?",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = answerInput,
                    onValueChange = { answerInput = it },
                    label = { Text("Your Answer") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                onNext(answerInput)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2E7D32)
            )
        ) {
            Text("Next", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onCancel,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFC62828)
            )
        ) {
            Text("Cancel", fontSize = 18.sp)
        }
    }
}

@Composable
fun ScoreBox(title: String, value: String) {
    Card(
        modifier = Modifier.width(140.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1565C0)
            )
        }
    }
}

@Composable
fun ResultScreen(
    correctCount: Int,
    wrongCount: Int,
    totalQuestions: Int,
    onPlayAgain: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Game Result",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0D47A1)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Total Questions: $totalQuestions",
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Correct Answers: $correctCount",
                    fontSize = 20.sp,
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Wrong Answers: $wrongCount",
                    fontSize = 20.sp,
                    color = Color(0xFFC62828),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onPlayAgain,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1565C0)
            )
        ) {
            Text("Play Again", fontSize = 18.sp)
        }
    }
}