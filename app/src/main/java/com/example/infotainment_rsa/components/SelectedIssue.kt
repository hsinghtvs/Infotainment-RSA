package com.example.infotainment_rsa.components

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.infotainment_rsa.R
import com.example.infotainment_rsa.viewmodel.MainViewModel
import java.util.Calendar

@Composable
fun selectedIssue(
    modifier: Modifier,
    viewModel: MainViewModel
) {

    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val callGradient = Brush.verticalGradient(
        listOf(
            Color(0xFF255AF5).copy(alpha = 1f),
            Color(0xFF255AF5).copy(alpha = 1f),
            Color(0xFF255AF5).copy(alpha = 0.8f),
            Color(0xFF255AF5).copy(alpha = 0.7f),
        )
    )

    val transparentGradient = Brush.verticalGradient(
        listOf(
            Color.Transparent,
            Color.Transparent
        )
    )
    Column(modifier = modifier.padding(bottom = 10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(10.dp), text = "Selected Issue", style = TextStyle(
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.manrope_extrabold))
                )
            )
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        viewModel.selectedIndex = -1
                    },
                text = "Change Issue",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    color = Color(0xFF5DE9FF),
                    fontFamily = FontFamily(Font(R.font.manrope_medium))
                )
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .background(color = Color(0xFF1D3354), shape = RoundedCornerShape(10.dp))
                .border(
                    width = 1.dp, color = Color(0xFF1F57E7), shape = RoundedCornerShape(10.dp)
                )
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = viewModel.listOfIssuesImage[viewModel.selectedIndex]),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    modifier = Modifier.weight(0.8f),
                    text = viewModel.listOfIssues[viewModel.selectedIndex],
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.manrope_bold))
                    )
                )
                Row(
                    modifier = Modifier.weight(1f)
                ) {
                    var buttonStroke = Brush.linearGradient(
                        listOf(
                            Color(0xFFFFFFFF).copy(alpha = 1f),
                            Color(0xFFFFFFFF).copy(alpha = 0f),
                            Color(0xFFFFFFFF).copy(alpha = 0f),
                            Color(0xFFFFFFFF).copy(alpha = 1f)
                        )
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .background(
                                color = Color(0xFFFFFFFF).copy(alpha = 0.3f),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .border(1.dp, brush = buttonStroke, shape = RoundedCornerShape(20.dp))
                            .background(
                                brush = if (!viewModel.executed) callGradient else transparentGradient,
                                shape = RoundedCornerShape(
                                    topStart = 30.dp,
                                    topEnd = 30.dp,
                                    bottomEnd = 30.dp,
                                    bottomStart = 30.dp
                                )

                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier.padding(5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier.clickable {
                                    if (viewModel.executed == false) {
                                        viewModel.executed = true
                                    } else {
                                        viewModel.executed = false
                                    }
                                    if (viewModel.processStart == false) {
                                        viewModel.processStart = true
                                    } else {
                                        viewModel.processStart = false
                                    }
                                    if (!viewModel.executed) {
                                        viewModel.selectedIndex = viewModel.selectedIndex
                                        viewModel.executed = false
                                        viewModel.processIndex = 0
                                        viewModel.processStart = false
                                        viewModel.listOfProcessExcuted.clear()
                                    }
                                },
                                text = if (!viewModel.executed) "Confirm Request" else "Cancel Request",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily(Font(R.font.manrope_semibold))
                                )
                            )
                        }
                    }
                }
            }
        }
        Column {
            if (!viewModel.executed) { // change to false
                Text(
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                    text = "Your Location",
                    style = TextStyle(
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.manrope_bold))
                    )
                )
                if (!viewModel.areaName.isEmpty()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(
                            modifier = Modifier
                                .padding(10.dp)
                                .size(10.dp)
                                .background(
                                    color = Color(0xFF34A443), shape = RoundedCornerShape(10.dp)
                                )
                        )
                        Text(
                            modifier = Modifier, text = viewModel.areaName, style = TextStyle(
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.manrope_semibold))
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "$hour $minute",
                            style = TextStyle(
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.manrope_semibold))
                            )
                        )
                    }
                } else {
                    Dialog(
                        onDismissRequest = { !viewModel.areaName.isEmpty() },
                        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    text = viewModel.address,
                    style = TextStyle(
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.manrope_medium))
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
            } else {
                Process(modifier = Modifier.weight(1f), viewModel = viewModel)
            }
        }
    }

    BackHandler {
        if (viewModel.selectedIndex != -1) {
            viewModel.issueFromAndroid = "No issues Found"
            viewModel.selectedIndex = -1
        } else {
            viewModel.closeApp = true
        }
    }
}