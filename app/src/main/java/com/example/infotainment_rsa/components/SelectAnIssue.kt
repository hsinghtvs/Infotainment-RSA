package com.example.infotainment_rsa.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.infotainment_rsa.R
import com.example.infotainment_rsa.executed
import com.example.infotainment_rsa.listOfIssues
import com.example.infotainment_rsa.listOfIssuesImage
import com.example.infotainment_rsa.listOfProcessExcuted
import com.example.infotainment_rsa.processIndex
import com.example.infotainment_rsa.processStart
import com.example.infotainment_rsa.selectedIndex

@Composable
fun SelectAnIssue(
    modifier: Modifier,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = modifier,
    ) {

        val callGradient = Brush.verticalGradient(
            listOf(
                Color(44, 44, 44, 100),
                Color(236, 254, 238, 6)
            )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f),
                text = "Select an issue or call us at ",
                style = TextStyle(
                    color = Color.White,
                )
            )
            Spacer(modifier = Modifier.size(4.dp))
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .background(
                        brush = callGradient, shape = RoundedCornerShape(
                            topStart = 30.dp, topEnd = 30.dp, bottomEnd = 30.dp, bottomStart = 30.dp
                        )

                    )
                    .border(
                        width = 1.dp, color = Color(0xFF3C4042), shape = RoundedCornerShape(
                            topStart = 30.dp, topEnd = 30.dp, bottomEnd = 30.dp, bottomStart = 30.dp
                        )
                    ), contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = painterResource(id = R.drawable.call), contentDescription = "", tint = Color.White)
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = "1800056743", style = TextStyle(
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.metropolis))
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(10.dp))
        LazyRow() {
            itemsIndexed(listOfIssues) { index, issue ->
                issueBox(index, issue){
                    onClick(it)
                }
            }
        }
    }
}

@Composable
private fun issueBox(index: Int, issue: String, onClick: (Int) -> Unit) {
    var openDialog by remember { mutableStateOf(false) }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            confirmButton = {
                Button(onClick = {
                    selectedIndex = index
                    executed = false
                    processIndex = 0
                    processStart = false
                    listOfProcessExcuted.clear()
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
            if (processIndex == 0) {
                selectedIndex = index
                executed = false
                processIndex = 0
                listOfProcessExcuted.clear()
            } else {
                openDialog = true
            }
        }
        .padding(horizontal = 10.dp, vertical = 5.dp)
        .background(color = Color(0xFF1D3354), shape = RoundedCornerShape(10.dp))
        .border(
            width = 1.dp, color = if (selectedIndex == index) {
                Color(0xFF1F57E7)
            } else {
                Color.Transparent
            }, shape = RoundedCornerShape(10.dp)
        )) {
        Spacer(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.TopEnd)
                .size(15.dp)
                .background(color = Color(0xFF0B1112), shape = CircleShape)
        )
        Column(
            modifier = Modifier.padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(70.dp),
                painter = painterResource(id = listOfIssuesImage[index]),
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