// MqttClientManager.kt
import android.util.Log
import com.example.songprojectv_1.ui.song.MqttClientWrapper
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions

class MqttClientManager() {
    private val brokerUri = "tcp://io.adafruit.com:1883"
    var clientId = MqttClient.generateClientId()

    private val mqttClientWrapper = MqttClientWrapper(brokerUri, clientId)
    private val username = "meriyum1"
    private val aioKey = "aio_pYoa00cNTE2Z5o0fcny6jXf27oeq"
    fun connect() {
        val connectOptions = MqttConnectOptions().apply {
            userName = username
            password = aioKey.toCharArray()
            isCleanSession = true
        }

        mqttClientWrapper.connect(connectOptions)
        Log.d("MQTT", "connection success")
        mqttClientWrapper.subscribe("meriyum1/feeds/playsong", 1) { success ->
            if (success) {
                // Subscription successful
                println("Subscription successful")
            } else {
                // Subscription failed
                println("Subscription failed")
            }
        }



    }

    fun disconnect() {
        mqttClientWrapper.disconnect()
    }



    fun publish(topic: String, message: String, qos: Int) {
        mqttClientWrapper.publish(topic, message, qos)
        Log.d("MQTT", "publish success")

    }

}
