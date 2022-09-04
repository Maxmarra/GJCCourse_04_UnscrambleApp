package com.example.android.unscramble.ui

import android.content.ClipData
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.unscramble.data.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: PlayerRepository
) : ViewModel() {

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        var allPlayers: Flow<List<Player>> = repository.getPlayersRepo()

    private fun insertPlayer(player: Player) {
        viewModelScope.launch {
            repository.insertRepo(player)
        }
    }

    private lateinit var currentWord: String

    // Set of words used in the game
    private var usedWords: MutableSet<String> = mutableSetOf()

    // Game UI state
    private val _uiState = MutableStateFlow(GameUiState())

    // Backing property to avoid state updates from other classes
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    var userGuess by mutableStateOf("")
        private set
    var guessedWords by mutableStateOf(0)
        private set

    init {
        resetGame()
    }

    private fun pickRandomWordAndShuffle(): String {
        // Continue picking up a new random word until you get one that hasn't been used before
        currentWord = allWords.random()
        return if (usedWords.contains(currentWord)) {
            pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            shuffleCurrentWord(currentWord)
        }
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        // Scramble the word
        tempWord.shuffle()
        while (String(tempWord) == word) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    fun resetGame() {
        usedWords.clear()
        guessedWords = 0
        _uiState.value =
            GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
        updateUserGuess("")
    }

    fun updateUserGuess(guessedWord: String) {
        userGuess = guessedWord
    }

    fun checkUserGuess() {

        if (userGuess.trim().lowercase().equals(currentWord, ignoreCase = true)) {
             // User's guess is correct, increase the score
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
            guessedWords++
            // Reset user guess

        } else {
            // Reset user guess
            //чистим неправильное слово
            updateUserGuess("")
            // User's guess is wrong, show an error
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
    }
    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == MAX_NO_OF_WORDS){
            //Last round in the game, update isGameOver to true, don't pick a new word
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else{
            // Normal round in the game
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    currentWordCount = currentState.currentWordCount.inc(),
                    score = updatedScore
                )
            }

            updateUserGuess("")
        }
    }

    fun skipWord() {
        updateGameState(_uiState.value.score)

        //чистим если что-то было написано но все равно нажали "пропустить"
        updateUserGuess("")
    }

    private fun getNewPlayerEntry(
        playerName: String,
        playerScore: Int,
        ): Player {
        return Player(
            name = playerName,
            score = playerScore,
        )
    }

    fun addNewPlayer(
        playerName: String,
        playerScore: Int,
    ) {
        val newPlayer = getNewPlayerEntry(
            playerName, playerScore
        )
        insertPlayer(newPlayer)
    }

    fun isEntryValid(
        playerName: String,
        ): Boolean {

        if (
            playerName.isBlank()
            ) {
            return false
        }
        return true
    }

}