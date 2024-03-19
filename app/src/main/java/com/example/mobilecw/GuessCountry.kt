import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.mobilecw.FList
import com.example.mobilecw.GuessFlag
//----
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import android.content.Context


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuessCountry()
        }
    }
}

@Composable
fun GuessCountry(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // Get the list of countries and a random flag ID
    val countryList = FList.map.values.toList()
    val randomFlagId = remember {
        FList.map.keys.random()
    }

    // Initialize states
    val correctFlag by remember { mutableStateOf( "") }
    var selection: String by remember { mutableStateOf("") }
    var result: String by remember { mutableStateOf("") }
    var resultColor: Color by remember { mutableStateOf(Color.Yellow) }
    var buttonFunction: String by remember { mutableStateOf("Submit") }

    Column(
        horizontalAlignment= Alignment.CenterHorizontally,
        verticalArrangement= Arrangement.Center,
        modifier= Modifier.fillMaxSize()
    ) {
        Text(
            text= correctFlag,
            fontSize= 35.dp,
            color= Color.Blue
        )

        Image(
            painter= painterResource(id = randomFlagId),
            contentDescription= "Country",
            modifier= Modifier
                .padding(bottom = 10.dp)
                .size(350.dp)
        )

        LazyColumn(
            modifier = modifier
                .padding(bottom = 40.dp)
                .size(350.dp, 200.dp)
        ) {
            itemsIndexed(countryList) { _, country ->
                val bgColor = if (country == selection) Color.LightGray
                else
                    Color.Transparent
                var color = (if (country == selection) Color.Black
                else
                    Color.White).also {
                    Text(
                        text= country,
                        modifier= Modifier
                            .padding(10.dp)
                            .clickable { selection = country }
                            .size(350.dp, 70.dp)
                            .background(bgColor),
                        fontSize= 30.sp,
                        color= it
                    )
                }
            }
        }

        Button(
            onClick = {
                if (selection.isEmpty()) {
                    result = "Select a country"
                } else {
                    if (buttonFunction == "Submit") {
                        if (selection == correctFlag) { result = "CORRECT!"
                            resultColor = Color.Green
                            buttonFunction = "Next"
                        } else {
                            result = "WRONG!"
                            resultColor = Color.Red
                            buttonFunction = "Next"
                        }
                    } else {
                        // Restart the game
                        @Composable
                        fun ButtonWithLogic(
                            buttonFunction: String,
                            selection: String,
                            correctFlag: String,
                            result: MutableState<String>,
                            resultColor: MutableState<Color>,
                            context: Context
                        ) {
                            Button(
                                onClick = {
                                    if (selection.isEmpty()) {
                                        result.value = "Select a country"
                                    } else {
                                        if (buttonFunction == "Submit") {
                                            if (selection == correctFlag) {
                                                result.value = "CORRECT!"
                                                resultColor.value = Color.Green
                                            } else {
                                                result.value = "WRONG!"
                                                resultColor.value = Color.Red         }
                                        } else {
                                            // Restart the game
                                            val intent = Intent(context, GuessFlag()::class.java)
                                            ContextCompat.startActivity(context, intent, null)
                                        }
                                    }
                                }
                            ) {
                                Text(
                                    text = buttonFunction,
                                    fontSize = 30.sp,
                                )
                            }
                        }


                    }

                }
            }
        ) {
            Text(
                text = buttonFunction,
                fontSize = 30.sp,
            )
        }

        Text(
            text = result,
            fontSize = 30.sp,
            color = resultColor,
        )
    }
}

fun Text(text: String, fontSize: Dp, color: Color) {
    TODO("Not yet implemented")
}
