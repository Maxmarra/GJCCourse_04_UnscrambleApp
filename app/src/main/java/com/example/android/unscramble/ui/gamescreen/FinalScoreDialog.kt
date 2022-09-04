package com.example.android.unscramble.ui.gamescreen

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.android.unscramble.R

/*
 * Creates and shows an AlertDialog with final score.
 */
@Composable
fun FinalScoreDialog(
    name: String,
    onNameChanged: (String)->Unit,
    score: Int,
    guessedWords: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as Activity)



    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
        },

        title = { Text(stringResource(R.string.congratulations)) },
        text = {
           Column() {

               Text(stringResource(R.string.you_scored, score))
               Text(stringResource(R.string.guessedWords, guessedWords))
               OutlinedTextField(
                   value = name,
                   onValueChange = onNameChanged
               )

           }
               },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text = stringResource(R.string.exit))
            }
        },
        confirmButton = {
            TextButton(
                onClick = onPlayAgain
            ) {
                Text(text = stringResource(R.string.play_again))
            }
        }
    )
}