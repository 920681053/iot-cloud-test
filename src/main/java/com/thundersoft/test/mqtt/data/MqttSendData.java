package com.thundersoft.test.mqtt.data;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MqttSendData {

    public static final String HOST = "tcp://192.168.7.206:8883";
    public static final String TOPIC = "v1/devices/me/telemetry";
    private static final String clientid = "client124";
    private MqttClient client;
    private MqttConnectOptions options = new MqttConnectOptions();
    private static String deviceToken1 = "A1_TEST_TOKEN";
    private static String deviceToken2 = "A2_TEST_TOKEN";
    private static String deviceToken3 = "A3_TEST_TOKEN";
    private static String deviceToken4 = "A4_TEST_TOKEN";
    private static String deviceToken5 = "A5_TEST_TOKEN";
    private static String deviceToken6 = "A6_TEST_TOKEN";
    private String passWord = "";
    Random random = new Random();

    private void pubMsg(String username) {
        try {
            // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(HOST, clientid, new MemoryPersistence());
            // MQTT的连接设置
            options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            // 设置连接的用户名
            options.setUserName(username);
            // 设置连接的密码
            options.setPassword(passWord.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);
            // 设置回调
            //client.setCallback(new PushCallback());
            client.connect(options);
            int humidity = random.nextInt(50) + 20;
            int temperature = random.nextInt(25) + 10;
            int co = random.nextInt(50) + 10;
            int co2 = random.nextInt(25) + 10;
            int pm2_5 = random.nextInt(50) + 10;
            if ("A6_TEST_TOKEN".equals(username)) {
                String msg = "{\"inDoor\":" + random.nextInt(3) + ",\"outDoor\":" + random.nextInt(3) + "}";
                client.publish(TOPIC, msg.getBytes(), 1, true);
            } else {
                String msg = "{\"humidity\":" + humidity + "%,\"temperature\":" + temperature + "℃," +
                        "\"co\":" + co + "ppm,\"co2\":" + co2 + "ppm,\"pm2.5\":" + pm2_5 + "μgm3}";
                client.publish(TOPIC, msg.getBytes(), 1, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws MqttException {
        MqttSendData mqttData = new MqttSendData();
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(15);
        scheduledThreadPool.scheduleAtFixedRate(new Thread(() -> {
            System.out.println("1" + Thread.currentThread().getName());
            mqttData.pubMsg(deviceToken1);
            mqttData.pubMsg(deviceToken2);
            mqttData.pubMsg(deviceToken3);
            mqttData.pubMsg(deviceToken4);
            mqttData.pubMsg(deviceToken5);
            mqttData.pubMsg(deviceToken6);
        }), 0, 2, TimeUnit.SECONDS);
    }
}
