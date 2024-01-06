package com.example.songprojectv_1.ui.song// com.example.songprojectv_1.ui.song.MqttClientWrapper.kt
import com.example.songprojectv_1.ui.song.MqttClientWrapper.MessageUtils.receivedMessage
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

class MqttClientWrapper(private val brokerUri: String, private val clientId: String) {
    private val mqttClient = MqttClient(brokerUri, clientId, MemoryPersistence())
  var messageReceivedCallback: ((String) -> Unit)? = null

    fun connect(connectOptions: MqttConnectOptions) {
        mqttClient.connect(connectOptions)
    }
    object MessageUtils {val receivedMessage: MutableState<String> = mutableStateOf("")}
    fun disconnect() {
        mqttClient.disconnect()
    }

fun subscribe(topic: String, qos: Int, callback: (Boolean) -> Unit) {
    mqttClient.subscribe(topic, qos
    ) { topic, message ->
        // Handle received message here if needed
        var receivedMsg=message?.toString()
        messageReceivedCallback?.invoke(receivedMsg ?: "")
        if (receivedMsg != null) {
            receivedMessage.value = receivedMsg
            Log.d("MQTT", "message arrived1 on topic "+topic+ "message:"+receivedMsg)
        }
        Log.d("MQTT", "message arrived on topic "+topic+ "message:"+receivedMsg)
    }


}


    fun publish(topic: String, message: String, qos: Int) {
        val mqttMessage = MqttMessage(message.toByteArray())
        mqttMessage.qos = qos
        mqttClient.publish(topic, mqttMessage)
    }
}
