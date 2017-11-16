package com.thundersoft.test.mqtt.ssl;

import com.google.common.io.Resources;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import javax.net.ssl.*;
import java.security.KeyStore;

public class MqttSslClient {

    private static final String MQTT_URL = "ssl://127.0.0.1:8883";
    //服务端RPC
    private static final String RPC_REQUEST_TOPIC = "v1/devices/me/rpc/request/+";
    //客户端RPC
    private static final String RPC_RESPONSE_TOPIC = "v1/devices/me/rpc/response/+";
    //从服务器请求属性值
    private static final String ATTRIBUTES_RESPONSE_TOPIC = "v1/devices/me/attributes/response/+";
    //从服务器订阅属性更新
    private static final String ATTRIBUTES_TOPIC = "v1/devices/me/attributes";

    private static final String clientId = "MQTT_SSL_JAVA_CLIENT";
    private static final String keyStoreFile = "mqttclient.jks";
    private static final String JKS = "JKS";
    private static final String TLS = "TLS";
    private static final String CLIENT_KEYSTORE_PASSWORD = "iotcloud";
    private static final String CLIENT_KEY_PASSWORD = "iotcloud";

    public static void main(String[] args) {

        try {

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            KeyStore trustStore = KeyStore.getInstance(JKS);
            trustStore.load(Resources.getResource(keyStoreFile).openStream(), CLIENT_KEYSTORE_PASSWORD.toCharArray());
            tmf.init(trustStore);
            KeyStore ks = KeyStore.getInstance(JKS);

            ks.load(Resources.getResource(keyStoreFile).openStream(), CLIENT_KEYSTORE_PASSWORD.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, CLIENT_KEY_PASSWORD.toCharArray());

            KeyManager[] km = kmf.getKeyManagers();
            TrustManager[] tm = tmf.getTrustManagers();
            SSLContext sslContext = SSLContext.getInstance(TLS);
            sslContext.init(km, tm, null);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setSocketFactory(sslContext.getSocketFactory());
            MqttClient client = new MqttClient(MQTT_URL, clientId);
            client.setCallback(new MqttSslClientCallback(client));
            client.connect(options);

            client.subscribe(RPC_REQUEST_TOPIC, 1);
            client.subscribe(RPC_RESPONSE_TOPIC, 1);
            client.subscribe(ATTRIBUTES_RESPONSE_TOPIC, 1);
            client.subscribe(ATTRIBUTES_TOPIC, 0);
            MqttSslClientSend.timeSend(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
