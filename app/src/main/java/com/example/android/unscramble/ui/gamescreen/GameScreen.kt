package com.example.android.unscramble.ui.gamescreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.unscramble.data.Player
import com.example.android.unscramble.ui.GameViewModel
import com.example.android.unscramble.ui.theme.UnscrambleTheme


@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel()
) {

    val gameUiState by gameViewModel.uiState.collectAsState()
    var name by remember{ mutableStateOf("") }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        GameStatus(
            wordCount = gameUiState.currentWordCount,
            score = gameUiState.score
        )
        GameLayout(
            currentScrambledWord = gameUiState.currentScrambledWord,
            userGuess = gameViewModel.userGuess,
            onUserGuessChanged = { gameViewModel.updateUserGuess(it) },
            onKeyboardDone = { gameViewModel.checkUserGuess() },
            isGuessWrong = gameUiState.isGuessedWordWrong,
        )

        Controls(
            onSkipClick = {gameViewModel.skipWord()},
            onSubmitClick = {gameViewModel.checkUserGuess()}
        )

        if (gameUiState.isGameOver) {
            FinalScoreDialog(
                score = gameUiState.score,
                onPlayAgain = {
                    gameViewModel.resetGame()
                    gameViewModel.addNewPlayer(name, gameUiState.score)
                              },
                guessedWords = gameViewModel.guessedWords,
                name = name,
                onNameChanged = {name = it}
            )
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //gameViewModel.allPlayers.collectAsState(initial = PlayersList()


    }
}
@Composable
private fun PlayersList(
    playersList: List<Player>,
    modifier: Modifier = Modifier) {
    LazyColumn {
        items(playersList){ player ->
            PlayerCard(player)
        }
    }
}

@Composable
fun PlayerCard(
    player: Player,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.padding(8.dp),
        elevation = 4.dp
    ){
        Row() {
            Text(text = player.name)
            Text(text = "${ player.score }")
        }
    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    UnscrambleTheme {
        GameScreen()
    }
}