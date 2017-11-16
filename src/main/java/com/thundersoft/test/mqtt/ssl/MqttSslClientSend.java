package com.thundersoft.test.mqtt.ssl;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MqttSslClientSend {

    //客户端RPC
    private static String rpcTopic = "v1/devices/me/rpc/request/" + new Random().nextInt(100);

    //从服务器请求属性值
    private static String sendTopic = "v1/devices/me/attributes/request/" + new Random().nextInt(100);

    public static void timeSend(MqttClient client) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                send(client);
            }
        };
        Timer timer = new Timer();
        //延迟执行
        long delay = 0;
        //执行间隔
        long intevalPeriod = 500 * 1000;
        //调度要在一段时间内运行的任务
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);
    }

    private static void send(MqttClient mqttClient) {
        try {
            //请求属性消息体格式：'{"clientKeys":"attribute1,attribute2", "sharedKeys":"shared1,shared2"}'
            //clientKeys：客户端属性
            //sharedKeys：共享属性
            String msg = "{\"clientKeys\":\"active,attr_f1,attr_n1,1attr_s1,A_S_1,humidity\"}";
            mqttClient.publish(sendTopic, msg.getBytes(), 0, true);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
