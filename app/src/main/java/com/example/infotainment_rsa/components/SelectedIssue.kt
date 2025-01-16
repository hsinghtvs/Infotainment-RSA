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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
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

    Column(modifier = modifier.padding(bottom = 10.dp)) {
        if (viewModel.issueFromAndroid == "No issues Found" && viewModel.intentIssue.isEmpty() && !viewModel.executed) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "Selected Issue",
                    style = TextStyle(
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
        }
        if (viewModel.executed) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                    text = buildAnnotatedString {
                        append("Request ID : ")
                        withStyle(
                            style = SpanStyle(
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.manrope_regular))
                            )
                        ) {
                            append("123456789")
                        }
                    },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.manrope_bold))
                    )
                )
            }
        }
        if (!viewModel.intentIssue.isEmpty() && !viewModel.executed) {
            Spacer(modifier = Modifier.size(42.dp))
        }
        if (!viewModel.executed && viewModel.issueFromAndroid != "No issues Found") {
            Spacer(modifier = Modifier.size(42.dp))
        }
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
                    painter = painterResource(
                        id =
                        if (viewModel.issueFromAndroid != "No issues Found") {
                            viewModel.issueAndroidImage
                        } else {
                            viewModel.listOfIssuesImage[viewModel.selectedIndex]
                        }
                    ),
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
                        textAlign = TextAlign.Start,
                        fontFamily = FontFamily(Font(R.font.manrope_bold))
                    )
                )
                Row(
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .clickable {
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
                            }
                            .fillMaxWidth()
                            .padding(10.dp)
                            .background(
                                color = Color(0xFFFFFFFF).copy(alpha = 0.3f),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .border(1.dp, brush = viewModel.buttonStroke, shape = RoundedCornerShape(20.dp))
                            .background(
                                brush = if (!viewModel.executed) viewModel.buttonGradient else viewModel.transparentGradient,
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
                                .padding(horizontal = 10.dp)
                                .size(10.dp)
                                .background(
                                    color = Color(0xFF34A443), shape = RoundedCornerShape(10.dp)
                                )
                        )
                        Text(
                            modifier = Modifier, text = viewModel.areaName, style = TextStyle(
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.manrope_bold))
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            text = "$hour : $minute",
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
                Text(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    text = viewModel.address,
                    style = TextStyle(
                        color = Color.LightGray,
                        fontFamily = FontFamily(Font(R.font.manrope_medium))
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
            } else {
                Process(modifier = Modifier, viewModel = viewModel)
            }
        }
    }

    BackHandler {
        if (viewModel.selectedIndex != -1 && viewModel.intentIssue.isEmpty()) {
            viewModel.issueFromAndroid = "No issues Found"
            viewModel.selectedIndex = -1
            viewModel.executed = false
            viewModel.processIndex = 0
            viewModel.processStart = false
            viewModel.listOfProcessExcuted.clear()
        } else {
            viewModel.closeApp = true
        }
    }
}