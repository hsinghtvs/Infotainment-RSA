package com.example.infotainment_rsa.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.infotainment_rsa.MainActivity
import com.example.infotainment_rsa.R
import com.example.infotainment_rsa.viewmodel.MainViewModel


@Composable
fun MapBox(modifier: Modifier, mainActivity: MainActivity,viewModel: MainViewModel) {
    Column(modifier = modifier.padding(bottom = 10.dp)) {
        Text(
            modifier = Modifier.padding(10.dp), text = "Confirm Your Location", style = TextStyle(
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.manrope_medium))
            )
        )
        Spacer(modifier = Modifier.size(10.dp))
        Box(modifier = Modifier) {
            MapComponent(
                mainActivity = mainActivity,
                modifier = Modifier
                    .background(
                        color = Color.Transparent, shape = RoundedCornerShape(10.dp)
                    )
                    .border(
                        width = 0.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = Color.Transparent
                    )
            )
            if (!viewModel.areaName.isEmpty()) {
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                    ) {
                        Row {
                            Column(
                                modifier = Modifier.weight(2f),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Row(
                                ) {
                                    Spacer(
                                        modifier = Modifier
                                            .padding(start = 10.dp, top = 10.dp)
                                            .size(10.dp)
                                            .background(
                                                color = Color(0xFF34A443),
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                    )
                                    Text(
                                        modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                                        text = viewModel.areaName,
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 10.sp
                                        )
                                    )
                                }
                                Text(
                                    modifier = Modifier.padding(
                                        horizontal = 30.dp,
                                        vertical = 10.dp
                                    ),
                                    text = viewModel.address,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 10.sp

                                    )
                                )
                            }
                            if (viewModel.processIndex >= 2) {
                                Column(
                                    modifier = Modifier
                                        .weight(1.4f)
                                        .padding(horizontal = 10.dp),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        modifier = Modifier.padding(
                                            vertical = 10.dp
                                        ),
                                        text = "My Tvs Assistance",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 10.sp

                                        )
                                    )
                                    Text(
                                        modifier = Modifier,
                                        text = buildAnnotatedString {
                                            append("Request ID : ")
                                            withStyle(
                                                style = SpanStyle(
                                                    color = Color.Red,

                                                    )
                                            ) {
                                                append("123456789")
                                            }
                                        },
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 10.sp

                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}