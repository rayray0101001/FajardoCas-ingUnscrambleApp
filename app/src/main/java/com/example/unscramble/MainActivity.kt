package com.example.unscramble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unscramble.ui.theme.UnscrambleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnscrambleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GameScreen()
                }
            }
        }
    }
}

@Composable
fun GameScreen() {
    val words = remember {
        listOf(
            "CAT" to "TAC"
        )
    }

    var currentWordIndex by remember { mutableIntStateOf(0) }
    var userAnswer by remember { mutableStateOf("") }
    var score by remember { mutableIntStateOf(0) }

    val isGameOver = currentWordIndex >= words.size
    val currentWordPair = if (!isGameOver) words[currentWordIndex] else null

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!isGameOver) {
            Text(
                text = "UNSCRAMBLE",
                fontSize = 30.sp
            )
            Text(
                text = currentWordPair?.second ?: "",
                fontSize = 40.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Unscramble the word!"
            )
            OutlinedTextField(
                value = userAnswer,
                onValueChange = { userAnswer = it },
                label = {
                    Text("Enter your answer")
                },
                singleLine = true,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Button(
                onClick = {
                    if (userAnswer.trim().equals(currentWordPair?.first, ignoreCase = true)) {
                        score++
                        currentWordIndex++
                    }
                    userAnswer = ""
                }
            ) {
                Text("SUBMIT")
            }
        } else {
            Text(
                text = "Congratulations!",
                fontSize = 30.sp
            )
            Text(
                text = "You've finished the level.",
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Button(
                onClick = {
                    score = 0
                    currentWordIndex = 0
                    userAnswer = ""
                }
            ) {
                Text("Play Again")
            }
        }

        Text(
            text = "Score: $score",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    UnscrambleTheme(dynamicColor = false) {
        Surface {
            GameScreen()
        }
    }
}
