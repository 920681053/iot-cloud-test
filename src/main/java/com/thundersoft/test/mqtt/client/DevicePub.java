package com.thundersoft.test.mqtt.client;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class DevicePub {

    private String topic = "v1/devices/me/rpc/response";

    public void pub(MqttClient mqttClient, String subTopic, MqttMessage message) {
        try {
            topic = topic + subTopic.substring("v1/devices/me/rpc/request/".length() - 1);
            System.err.println(topic);
            //String msg = "{\"method\":\"setGpioStatus\",\"params\":{\"pin\":7,\"enabled\":true}}";
            mqttClient.publish(topic, message.getPayload(), 1, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
