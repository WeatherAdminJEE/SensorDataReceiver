package imt.org.web.sensordatareceiver.publisher.mqtt;

import imt.org.web.commonmodel.SensorData;
import imt.org.web.sensordatareceiver.publisher.IPublisher;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ResourceBundle;

/**
 * MQTT Publisher class
 */
@Getter
@Setter
public class MQTTPublisher implements IPublisher, MqttCallback {

    private MqttClient client;
    private String brokerUrl;
    private String topic;
    private int qos;
    private boolean cleanSession;

    // Config file
    public static final ResourceBundle CONFIG = ResourceBundle.getBundle("config");

    /**
     * Constructor
     * @param clientId ID of the client to connect MQTT broker
     */
    public MQTTPublisher(String clientId) {
        initMQTTPublisher();

        // Temp directory
        String tmpDir = System.getProperty("java.io.tmpdir");
        MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);

        try {
            // Construct an MQTT blocking mode client
            client = new MqttClient(this.brokerUrl, clientId, dataStore);

            // Set this wrapper as the callback handler
            client.setCallback(this);
        } catch (MqttException e) {
            System.out.println("MQTTPublisher() - Unable to set up client : " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Init MQTTPublisher properties
     */
    private void initMQTTPublisher() {
        String url = CONFIG.getString("MQTTBroker");
        String port = CONFIG.getString("MQTTPort");
        String protocol = "tcp://";
        brokerUrl = protocol + url + ":" + port;
        cleanSession = true;
        qos = 2;
        topic = CONFIG.getString("MQTTTopic");
    }

    /**
     * Publish a message to an MQTT server
     * @param sensorData Sensor data to send to the MQTT server
     */
    @Override
    public void publish(SensorData sensorData) {
        try {
            // Connect client
            System.out.println("Connecting to " + brokerUrl + " with client ID " + client.getClientId());
            client.connect();
            System.out.println("Connected");

            printSentData(sensorData);

            // Create MQTT message
            MqttMessage message = new MqttMessage(serializeSensorData(sensorData));
            message.setQos(qos);

            // Publish message
            System.out.println("MQTT - Publishing to " + brokerUrl + " - topic " + topic);
            client.publish(topic, message);

            // Disconnect client
            client.disconnect();
            System.out.println("Disconnected");
        } catch(MqttException me) {
            System.out.println("publish() - Unable to publish message : " + me.getMessage());
        } catch (IOException ex) {
            System.out.println("publish() - Unable to serialize object : " + ex.getMessage());
        }
    }

    /**
     * Serialize a SensorData object
     * @param sensorData SensorData to serialize
     * @return Serialized SensorData
     * @throws IOException
     */
    public byte[] serializeSensorData(SensorData sensorData) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutput objectOutput;
        objectOutput = new ObjectOutputStream(outputStream);
        objectOutput.writeObject(sensorData);
        objectOutput.flush();
        return outputStream.toByteArray();
    }

    /**
     * System.out sent SensorData
     * @param sensorData SensorData to print
     */
    public void printSentData(SensorData sensorData) {
        System.out.println(
            "idSensor:"+String.valueOf(sensorData.getIdSensor())+"\n"
            +"idCountry:"+sensorData.getIdCountry()+"\n"
            +"idCity:"+sensorData.getIdCity()+"\n"
            +"temperature:"+String.valueOf(sensorData.getMeasure().getTemperature())+"\n"
            +"windSpeed:"+String.valueOf(sensorData.getMeasure().getWindSpeed())+"\n"
            +"pressure:"+String.valueOf(sensorData.getMeasure().getPressure())+"\n"
            +"timestamp:"+sensorData.getDate().toString()
        );
    }

    /**
     * @see MqttCallback#connectionLost(Throwable)
     */
    public void connectionLost(Throwable cause) {
        // Called when the connection to the server has been lost.
        System.out.println("connectionLost() - Connection to " + brokerUrl + " lost : " + cause);
    }

    /**
     * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
     */
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Called when a message has been delivered to the server
        System.out.println("deliveryComplete() - Message has been successfully delivered");
    }

    /**
     * @see MqttCallback#messageArrived(String, MqttMessage)
     */
    public void messageArrived(String topic, MqttMessage message) {
        // Unused - Called when a message arrives from the server
    }
}
