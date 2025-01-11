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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.example.infotainment_rsa.viewmodel.MainViewModel

@Composable
fun SelectAnIssue(
    modifier: Modifier,
    viewModel: MainViewModel,
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
        Text(
            modifier = Modifier
                .padding(10.dp),
            text = "We have identified an issue with your vehicle. Please Request Help for assistance.",
            style = TextStyle(
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.manrope_extrabold))
            )
        )
        Spacer(modifier = Modifier.size(10.dp))
        Box(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.error_bg),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .align(Alignment.Center)
                    .padding(horizontal = 10.dp)
            ) {
                Image(
                    modifier = Modifier.align(Alignment.Center),
                    painter = painterResource(id = R.drawable.button_error_background),
                    contentDescription = ""
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if(viewModel.issueFromAndroid != "No issues Found") {
                        Image(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.braekdown),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(10.dp),
                        text = viewModel.issueFromAndroid,
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.manrope_extrabold))
                        )
                    )
                    var buttonGradient = Brush.verticalGradient(
                        listOf(
                            Color(0xFF18348E),
                            Color(0xFF2A64E1),
                        )
                    )
                    var buttonStroke = Brush.linearGradient(
                        listOf(
                            Color(0xFFFFFFFF).copy(alpha = 1f),
                            Color(0xFFFFFFFF).copy(alpha = 0f),
                            Color(0xFFFFFFFF).copy(alpha = 0f),
                            Color(0xFFFFFFFF).copy(alpha = 1f)
                        )
                    )
                    if (viewModel.issueFromAndroid != "No issues Found") {
                        Text(
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable {
                                    viewModel.intentIssue = viewModel.issueFromAndroid
                                    if (!viewModel.intentIssue.isEmpty() && !viewModel.intenissueAdded) {
                                        viewModel.listOfIssues.add(viewModel.intentIssue)
                                        viewModel.listOfIssuesImage.add(R.drawable.battery__1_)
                                        viewModel.selectedIndex = viewModel.listOfIssues.size - 1
                                        viewModel.intenissueAdded = true
                                    }
                                }
                                .background(
                                    color = Color(0xFFFFFFFF).copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .border(
                                    1.dp,
                                    brush = buttonStroke,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .background(
                                    brush = buttonGradient,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            text = "Request Help",
                            style = TextStyle(
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.manrope_bold))
                            )
                        )
                    }
                }
            }
        }
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
                    fontFamily = FontFamily(Font(R.font.manrope_semibold))
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
                    Icon(
                        painter = painterResource(id = R.drawable.call),
                        contentDescription = "",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = "1800056743", style = TextStyle(
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.manrope_semibold))
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(10.dp))
        LazyRow() {
            itemsIndexed(viewModel.listOfIssues) { index, issue ->
                issueBox(index, issue, viewModel) {
                    onClick(it)
                }
            }
        }
    }
}

@Composable
private fun issueBox(index: Int, issue: String, viewModel: MainViewModel, onClick: (Int) -> Unit) {
//    var openDialog by remember { mutableStateOf(false) }

    if (viewModel.openDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.openDialog = false },
            confirmButton = {
                Button(onClick = {
                    viewModel.selectedIndex = index
                    viewModel.executed = false
                    viewModel.processIndex = 0
                    viewModel.processStart = false
                    viewModel.listOfProcessExcuted.clear()
                    viewModel.openDialog = false
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.openDialog = false }) {
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
                viewModel.openDialog = true
            }
        }
        .padding(horizontal = 10.dp, vertical = 5.dp)
        .background(color = Color(0xFF1D3354), shape = RoundedCornerShape(10.dp))
        .border(
            width = 1.dp, color = if (viewModel.selectedIndex == index) {
                Color(0xFF1F57E7)
            } else {
                Color.Transparent
            }, shape = RoundedCornerShape(10.dp)
        )) {
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
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.manrope_bold))
                )
            )
        }
    }
}