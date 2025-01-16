package com.example.infotainment_rsa.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.infotainment_rsa.R
import com.example.infotainment_rsa.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueBox(index: Int, issue: String,viewModel: MainViewModel) {
    var openDialog by remember { mutableStateOf(false) }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            confirmButton = {
                Button(onClick = {
                    viewModel.selectedIndex = index
                    viewModel.executed = false
                    viewModel.processIndex = 0
                    viewModel.processStart = false
                    viewModel.listOfProcessExcuted.clear()
                    openDialog = false
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog = false }) {
                    Text("Dismiss")
                }
            },
            icon = {
                Icon(imageVector = Icons.Default.Warning, contentDescription = "Warning Icon")
            },
            title = {
                Text(text = "Cant Select", color = Color.Black)
            },
            text = {
                Text(
                    text = "Once of the issue is already selected, Cancel That Before you select This",
                    color = Color.DarkGray
                )
            },
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            containerColor = Color.White,
            iconContentColor = Color.Red,
            titleContentColor = Color.Black,
            textContentColor = Color.DarkGray,
            tonalElevation = 8.dp,
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
        )
    }

    Box(modifier = Modifier
        .size(150.dp)
        .fillMaxWidth()
        .clickable {
            if (viewModel.processIndex == 0) {
                viewModel.selectedIndex = index
                viewModel.executed = false
                viewModel.processIndex = 0
                viewModel.listOfProcessExcuted.clear()
            } else {
//                openDialog = true
            }
        }
        .padding(horizontal = 10.dp, vertical = 5.dp)
        .background(color = Color(0xFF1D3354), shape = RoundedCornerShape(10.dp))
        .border(
            width = 1.dp, color = if (viewModel.selectedIndex == index) {
                Color(0xFF3DED4F)
            } else {
                Color.Transparent
            }, shape = RoundedCornerShape(10.dp)
        )) {
        if (viewModel.selectedIndex == index) {
            Image(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.TopEnd)
                    .size(15.dp),
                painter = painterResource(id = R.drawable.circle_tick),
                contentDescription = ""
            )

        } else {
            Spacer(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.TopEnd)
                    .size(15.dp)
                    .background(color = Color(0xFF0B1112), shape = CircleShape)
            )
        }
        Column(
            modifier = Modifier.padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(70.dp),
                painter = painterResource(id = viewModel.listOfIssuesImage[index]),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .fillMaxWidth(),
                text = issue, style = TextStyle(
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}