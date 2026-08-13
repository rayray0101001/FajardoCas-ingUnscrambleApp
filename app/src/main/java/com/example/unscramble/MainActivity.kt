package com.example.unscramble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
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
        listOf("CAT", "DOG", "BOOK")
    }

    var currentWordIndex by remember { mutableIntStateOf(0) }
    var userAnswer by remember { mutableStateOf("") }
    var score by remember { mutableIntStateOf(0) }

    // Phase 5: Create state for the scrambled word and initialize it
    var scrambledWord by remember {
        mutableStateOf(words[0].shuffled().joinToString(""))
    }

    val isGameOver = currentWordIndex >= words.size

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!isGameOver) {
            val correctAnswer = words[currentWordIndex]

            Text(
                text = "UNSCRAMBLE",
                fontSize = 30.sp
            )
            // Phase 5: Display the scrambled word
            Text(
                text = scrambledWord,
                fontSize = 40.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Unscramble the word!"
            )
            OutlinedTextField(
                value = userAnswer,
                onValueChange = { userAnswer = it },
                placeholder = {
                    Text("Enter your answer")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Button(
                onClick = {
                    if (userAnswer.trim().equals(correctAnswer, ignoreCase = true)) {
                        score++
                        currentWordIndex++
                        
                        // Phase 5: Change the scrambled word after a correct answer
                        if (currentWordIndex < words.size) {
                            scrambledWord = words[currentWordIndex].shuffled().joinToString("")
                        }
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
                    // Phase 5: Reset scrambled word for restart
                    scrambledWord = words[0].shuffled().joinToString("")
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
