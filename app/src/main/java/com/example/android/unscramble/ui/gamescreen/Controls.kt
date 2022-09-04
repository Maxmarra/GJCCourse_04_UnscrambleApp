package com.example.android.unscramble.ui.gamescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.android.unscramble.R

@Composable
fun Controls(modifier: Modifier = Modifier,
             onSkipClick:()-> Unit,
             onSubmitClick:()-> Unit,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        OutlinedButton(
            onClick = onSkipClick,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text(stringResource(R.string.skip))
        }

        Button(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 8.dp),
            onClick = onSubmitClick
        ) {
            Text(stringResource(R.string.submit))
        }
    }

}