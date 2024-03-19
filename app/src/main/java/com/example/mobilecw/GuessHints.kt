//import androidx.compose.material.Button
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilecw.RandomFlag
import com.example.mobilecw.R.drawable.ar
import com.example.mobilecw.R.drawable.at
import com.example.mobilecw.R.drawable.au
//import com.example.mobilecw.RandomFlag
import org.json.JSONArray
import java.io.IOException
import java.util.*


@SuppressLint("RememberReturnType")
@Composable
fun CountryGuessingHintsGame() {
    var countryName by remember { mutableStateOf("") }
    var guessedChars by remember { mutableStateOf("") }
    var isCorrect by remember { mutableStateOf(false) }
    var guessResult by remember { mutableStateOf("") }

    val randomFlag = remember { getRandomFlag() }

    // Load country name corresponding to the flag
    LaunchedEffect(RandomFlag()) {
        val also = getCountryName(RandomFlag()).also { countryName = it }
        guessedChars = countryName.replace(Regex("[A-Za-z]"), "-")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(id = RandomFlag()),
            contentDescription = "Country Flag",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Guess the country by typing one character at a time:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = guessedChars,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        var charGuess by remember { mutableStateOf("") }
        TextField(
            value = charGuess,
            onValueChange = { charGuess = it },
            label = { Text("Enter a character") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (charGuess.length == 1) {
                    if (countryName.contains(charGuess, ignoreCase = true)) {
                        guessedChars = guessedChars.replace(
                            charGuess.toLowerCase(Locale.ROOT),
                            charGuess,
                            ignoreCase = true
                        )
                        isCorrect = !guessedChars.contains('-')
                        guessResult = if (isCorrect) "CORRECT!" else "Correct character!"
                    } else {
                        guessResult = "WRONG! Try again."
                    }
                } else {
                    guessResult = "Please enter a single character."
                }
                charGuess = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Submit")
        }

        // Display result message
        if (guessResult.isNotEmpty()) {
            val textColor = if (isCorrect) Color.Green else Color.Red
            Text(
                text = guessResult,
                color = textColor,
                modifier = Modifier.padding(top = 15.dp),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

private fun getRandomFlag() {
    // Generate a random flag ID
    val images = intArrayOf(au, at, ar)
    val rand = Random()
    imageView.setImageResource(images[rand.nextInt(images.size)])
}

@Composable
private fun getCountryName(flagResId: Unit): String {
    // Get country name based on flag resource ID
    val flagFileName = "flag_${flagResId.toString().takeLast(6).dropLast(4)}"
    val context = LocalContext.current
    val jsonString = try {
        context.assets.open("countries.json").bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        ""
    }
    val jsonArray = JSONArray(jsonString)
    for (i in 0 until jsonArray.length()) {
        val jsonObject = jsonArray.getJSONObject(i)
        if (jsonObject.getString("alpha_2") == flagFileName) {
            return jsonObject.getString("name")
        }
    }
    return ""
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CountryGuessingHintsGame()
}
