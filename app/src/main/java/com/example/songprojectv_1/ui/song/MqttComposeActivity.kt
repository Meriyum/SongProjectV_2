package com.example.songprojectv_1.ui.song// com.example.songprojectv_1.ui.song.MqttComposeActivity.kt
//import com.example.songprojectv_1.ui.theme.SongProjectV1Theme
import MqttClientManager
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.songprojectv_1.R
import com.example.songprojectv_1.ui.navigation.NavigationDestination
import com.example.songprojectv_1.ui.theme.SongProjectV_1Theme
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttException


object SongMqttDestination : NavigationDestination {
    override val route = "mqtt_details"
    override val titleRes = R.string.song_detail_title
    const val songIdArg = "track_id"
    val routeWithArgs = "$route/{$songIdArg}"
}


// In your com.example.songprojectv_1.ui.song.MqttComposeActivity
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MqttComposeActivity(
    navigateToMqttEntry: (Int) -> Unit,
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController,


    ) {

    val trackId = remember {
        val navBackStackEntry = navController.currentBackStackEntry
        navBackStackEntry?.arguments?.getInt(SongMqttDestination.songIdArg) ?: 0

    }
    println("trackId: $trackId")


    var messageToSend by remember { mutableStateOf(trackId.toString()) }
    val mqttClientManager = remember { MqttClientManager() }
    var receivedMessage = MqttClientWrapper.MessageUtils.receivedMessage.value
    var messageAsString: String = receivedMessage.toString()
    Log.d("MQTT", "message arrived on topic1 " + messageAsString)


    DisposableEffect(Unit) {

        mqttClientManager.connect()

        onDispose {
            mqttClientManager.disconnect()
        }
    }

    SongProjectV_1Theme {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(
                value = messageToSend,
                onValueChange = { messageToSend = it },
                label = { Text("Track IDs") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,

                    )
            )

            Button(
                onClick = {

                    try {
                        mqttClientManager.publish("meriyum1/feeds/lyric", messageToSend, 1)


                    } catch (e: MqttException) {
                        // Handle MQTT exception
                        e.printStackTrace()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text("Publish Message")
            }
            TextField(
                value = messageAsString,
                onValueChange = { messageAsString = it },
                label = { Text("value received from io.adafruit") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,

                    )
            )
        }
    }
}
