package com.example.moodapp

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

//import com.example.lunchtray.R
import com.example.moodapp.screens.MoodTracker

import com.example.moodapp.ui.theme.Purple40
import java.util.Calendar
import java.util.Date


@Composable
fun CalenderScreen() {

    Column(modifier = Modifier.fillMaxSize()) {
        val date = remember { mutableStateOf("Choose the date") }
        Calender(date)

        if (date.value == "Choose the date") {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Please Choose The Date First")
            }
        } else {
            MoodTracker(date = date.value)
        }
    }
}

@Composable
private fun Calender(date: MutableState<String>) {


    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year: Int = calendar.get(Calendar.YEAR)
    val month: Int = calendar.get(Calendar.MONTH)
    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()


    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            date.value = "$day-${month + 1}-$year"
        }, year, month, day
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
            .padding(all = 10.dp)
            .border(2.dp, Purple40)
            .clickable {
                datePickerDialog.show()
            }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (lable, iconView) = createRefs()
            Text(
                text= date.value,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(lable) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(iconView.start)
                        width = Dimension.fillToConstraints
                    }
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.calender),
                contentDescription ="calender icon",
                modifier = Modifier
                    .size(40.dp, 40.dp)
                    .constrainAs(iconView) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                tint = LocalContentColor.current
            )
        }
    }
}