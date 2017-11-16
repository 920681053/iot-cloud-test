package com.thundersoft.test.mqtt.ssl;

import org.eclipse.paho.client.mqttv3.MqttClient;

public class MqttSslClientPub {

    private String topic = "v1/devices/me/rpc/response";

    public void pub(MqttClient mqttClient, String subTopic) {
        try {
            topic = topic + subTopic.substring(subTopic.lastIndexOf("/"));
            System.err.println(topic);
            String msg = "{\"method\":\"setGpioStatus\",\"params\":{\"pin\":7,\"enabled\":true}}";
            mqttClient.publish(topic, msg.getBytes(), 1, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
