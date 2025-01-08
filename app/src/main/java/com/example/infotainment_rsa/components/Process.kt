package com.example.infotainment_rsa.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.infotainment_rsa.R
import com.example.infotainment_rsa.viewmodel.MainViewModel

@Composable
fun Process(modifier: Modifier, viewModel: MainViewModel) {
    val context = LocalContext.current
    var boxHeight by remember {
        mutableIntStateOf(0)
    }
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1f))
        LazyColumn(
        ) {
            itemsIndexed(viewModel.listOfServiceTimeKey) { index, item ->
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        val (box, spacer1, spacer2) = createRefs()
                        if (index != 0) {
                            Canvas(modifier = Modifier
                                .constrainAs(spacer1) {
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(box.top)
                                    top.linkTo(parent.top)
                                }
                                .height(((boxHeight / 2) + 21).dp)
                                .width(1.dp)) {
                                if (viewModel.listOfProcessExcuted.contains(index) || viewModel.processIndex == 3) {
                                    drawLine(
                                        color = Color(
                                            0xFF34A443
                                        ),
                                        start = Offset(0f, 0f),
                                        end = Offset(0f, size.height),
                                        strokeWidth = 1f
                                    )
                                } else {
                                    drawLine(
                                        color = Color.LightGray,
                                        start = Offset(0f, 0f),
                                        end = Offset(0f, size.height),
                                        pathEffect = PathEffect.dashPathEffect(
                                            floatArrayOf(10f, 10f),
                                            0f
                                        )
                                    )
                                }
                            }
                        }
                        if (viewModel.listOfProcessExcuted.contains(index)) {
                            Image(
                                modifier = Modifier
                                    .constrainAs(box) {
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                        bottom.linkTo(parent.bottom)
                                        top.linkTo(parent.top)
                                    }
                                    .size(10.dp)
                                    .background(
                                        color = Color.LightGray,
                                        shape = RoundedCornerShape(10.dp)
                                    ),
                                painter = painterResource(id = R.drawable.circle_tick),
                                contentDescription = "")
                        } else if (viewModel.processIndex == index) {
                            Image(
                                modifier = Modifier
                                    .constrainAs(box) {
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                        bottom.linkTo(parent.bottom)
                                        top.linkTo(parent.top)
                                    }
                                    .size(10.dp)
                                    .background(
                                        color = Color.LightGray,
                                        shape = RoundedCornerShape(10.dp)
                                    ),
                                painter = painterResource(id = R.drawable.processing),
                                contentDescription = "")
                        } else {
                            Spacer(modifier = Modifier
                                .constrainAs(box) {
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom)
                                    top.linkTo(parent.top)
                                }
                                .size(10.dp)
                                .background(
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(10.dp)
                                ))
                        }
                        if (index != 3) {
                            Canvas(
                                modifier = Modifier
                                    .constrainAs(spacer2) {
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                        top.linkTo(parent.top)
                                        top.linkTo(box.bottom)
                                    }
                                    .fillMaxHeight()
                                    .height(((boxHeight / 2) + 20).dp)
                                    .width(1.dp)
                            ) {

                                if (viewModel.listOfProcessExcuted.contains(index)) {
                                    drawLine(
                                        color = if (viewModel.listOfProcessExcuted.contains(index - 1) || (viewModel.processIndex > -1 && index == 0) || viewModel.processIndex == 3) Color(
                                            0xFF34A443
                                        ) else Color.LightGray,
                                        start = Offset(0f, 0f),
                                        end = Offset(0f, size.height),
                                        strokeWidth = 1f
                                    )
                                } else {
                                    drawLine(
                                        color = Color.LightGray,
                                        start = Offset(0f, 0f),
                                        end = Offset(0f, size.height),
                                        pathEffect = PathEffect.dashPathEffect(
                                            floatArrayOf(10f, 10f),
                                            0f
                                        )
                                    )
                                }
                            }
                        }
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                        }
                        .padding(horizontal = 20.dp, vertical = 5.dp)
                        .background(
                            color = Color(0xFF1D3354), shape = RoundedCornerShape(10.dp)
                        )
                        .border(
                            width = 1.dp, color = if (viewModel.processIndex == index) {
                                Color(0xFF23AD13)
                            } else {
                                Color.Transparent
                            }, shape = RoundedCornerShape(10.dp)
                        )
                        .padding(10.dp)
                        .onGloballyPositioned {
                            boxHeight = (it.size.height / 2) - 10
                        }
                    ) {
                        Column(
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                text = item,
                                style = TextStyle(
                                    color = Color.White,
                                )
                            )
                            Text(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                text = viewModel.listOfServiceTimeValue[index],
                                maxLines = 1,
                                style = TextStyle(
                                    color = Color.White,
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}