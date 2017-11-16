package com.thundersoft.test.mqtt.client;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Device {

    //private static final String HOST = "tcp://demo.thingsboard.io";
    private static final String HOST = "tcp://127.0.0.1:8883";
    //服务端RPC
    private static final String RPC_REQUEST_TOPIC = "v1/devices/me/rpc/request/+";
    //客户端RPC
    private static final String RPC_RESPONSE_TOPIC = "v1/devices/me/rpc/response/+";
    //从服务器请求属性值
    private static final String ATTRIBUTES_RESPONSE_TOPIC = "v1/devices/me/attributes/response/+";
    //从服务器订阅属性更新
    private static final String ATTRIBUTES_TOPIC = "v1/devices/me/attributes";

    private static final String clientid = "7efba430-ca98-11e7-aca8-0fd3c824709d";
    private static MqttClient mqttClient;
    private static MqttConnectOptions options;
    private static String userName = "pExXMsybrxOkhmgVr7ms";
    private static String passWord = "";

    public static void init() {
        try {
            // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            mqttClient = new MqttClient(HOST, clientid, new MemoryPersistence());
            // MQTT的连接设置
            options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            // 设置连接的用户名
            options.setUserName(userName);
            // 设置连接的密码
            options.setPassword(passWord.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(200);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*200秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(200);
            // 设置回调
            mqttClient.setCallback(new DeviceCallback(mqttClient));
            //设置连接
            mqttClient.connect(options);
            //订阅消息
            mqttClient.subscribe(RPC_REQUEST_TOPIC, 1);
            mqttClient.subscribe(RPC_RESPONSE_TOPIC, 1);
            mqttClient.subscribe(ATTRIBUTES_RESPONSE_TOPIC, 1);
            mqttClient.subscribe(ATTRIBUTES_TOPIC, 1);
            //DeviceSend.timeSend(mqttClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        init();
    }
}
