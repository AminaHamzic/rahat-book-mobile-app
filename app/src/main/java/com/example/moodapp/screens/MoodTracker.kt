package com.example.moodapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
/*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField*/
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.moodapp.R
//import androidx.constraintlayout.widget.ConstraintLayout

//import com.example.lunchtray.R
import com.example.moodapp.data.Event
import com.example.moodapp.data.Mood
import com.example.moodapp.database.DBHelper
import com.example.moodapp.ui.theme.Purple40

@Composable
fun MoodTracker(date: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        val dbHelper = DBHelper(LocalContext.current)
        var dialogState by remember { mutableStateOf(false) }
        val eventList = dbHelper.fetchEventsFromDate(date)

        if (eventList.isEmpty()) {
            EmptyEvent(
                openDialog = { dialogState = true }
            )
        } else {
            ShowList(eventList, openDialog = { dialogState = true })
        }

        if (dialogState) {
            AddEvent(
                date = date,
                dbHelper = dbHelper,
                closeDialog = { dialogState = false }
            )
        }
    }
}
    @Composable
    fun ShowList(list: List<Event>, openDialog: () -> Unit) {
        Column(modifier = Modifier.fillMaxSize()) {

            Image(

                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
                    .size(60.dp, 60.dp)
                    .clickable { openDialog.invoke() },
                painter = painterResource(id = R.drawable.add_icon_free_vector),
                contentDescription = "add event"
            )


            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn {
                    items(list) {
                        EventItem(it)
                    }
                }
            }
        }

    }

    private fun getImageVectorFromMood(mood: Mood): Int {
        return when(mood) {
            Mood.HAPPY -> R.drawable.happy
            Mood.SAD -> R.drawable.sad
            Mood.ANGRY -> R.drawable.angry
        }
    }

    @Composable
    fun EventItem(event: Event) {

        val imageVector = ImageVector.vectorResource(id = getImageVectorFromMood(event.mood))

        Card(modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .border(3.dp, Purple40)
        ) {

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val (lable, iconView) = createRefs()
                Text(
                    text= event.name,
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
                    imageVector = imageVector,
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

    @Composable
    fun EmptyEvent( openDialog: () -> Unit ) {

        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "List is empty Please add the Event")

                Image(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp)
                        .size(60.dp, 60.dp)
                        .clickable { openDialog.invoke() },
                    painter = painterResource(id = R.drawable.add_icon_free_vector),
                    contentDescription = "add event" )
            }
        }
    }

    @Composable
    fun AddEvent(
        date: String,
        dbHelper: DBHelper,
        closeDialog: () -> Unit
    ) {
        val moodList: List<Mood> = listOf(Mood.HAPPY, Mood.SAD, Mood.ANGRY)
        var eventName by remember { mutableStateOf("") }
        val selectedMood = remember { mutableStateOf(-1) }

        Dialog(
            onDismissRequest = { closeDialog.invoke() },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false
            )
        ) {

            Surface() {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    TextField(value = eventName, onValueChange = { eventName = it })

                    Spacer(modifier = Modifier.padding(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        for (i in moodList.indices) {
                            if (selectedMood.value == i) {
                                Icon(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .clickable { selectedMood.value = i },
                                    imageVector =  ImageVector.vectorResource(id = getImageVectorFromMood(
                                        moodList[i]
                                    )),
                                    contentDescription ="calender icon",
                                    tint = Color.Green
                                )
                            } else {
                                Icon(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .clickable { selectedMood.value = i },
                                    imageVector =  ImageVector.vectorResource(id = getImageVectorFromMood(
                                        moodList[i]
                                    )),
                                    contentDescription ="calender icon",
                                    tint = LocalContentColor.current
                                )
                            }
                        }
                    }

                    Column(modifier = Modifier.fillMaxWidth()) {

                        Button(
                            onClick = {
                                dbHelper.addEvent(
                                    date = date,
                                    event = Event(eventName, Mood.values()[selectedMood.value])
                                )
                                closeDialog.invoke()
                            },
                            colors = ButtonDefaults.buttonColors(Color.Magenta)
                        ) {
                            Text(text = "ADD")
                        }


                        Button(onClick = { closeDialog.invoke() },
                            colors = ButtonDefaults.buttonColors(Color.Magenta),
                        ) {
                            Text(text = "CANCEL")
                        }

                    }

                }
            }
        }
    }

/*
@Composable
@Preview
fun HomeScreenPreview() {
    MoodTracker()
}*/