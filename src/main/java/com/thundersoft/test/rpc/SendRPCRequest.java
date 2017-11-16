package com.thundersoft.test.rpc;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendRPCRequest {
    //主机IP
    private static final String HOST = "http://127.0.0.1:8080";
    //RPC单向指令接口
    private static final String RPC_ONEWAY = "/api/plugins/rpc/oneway/";
    //设备ID
    private static final String DEVICE_ID = "7efba430-ca98-11e7-aca8-0fd3c824709d";
    //RPC请求URL
    private static final String RPC_REQUEST_URL = HOST + RPC_ONEWAY + DEVICE_ID;

    /**
     * 发送指定RPC请求
     *
     * @param command
     * @return
     */
    public static String sendPost(String command) {
        PrintWriter out = null;
        BufferedInputStream in = null;
        String result = "";
        try {
            URL realUrl = new URL(RPC_REQUEST_URL);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:56.0) Gecko/20100101 Firefox/56.0");
            connection.setRequestProperty("Accept", "application/json, text/plain, */*");
            connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.setRequestProperty("X-Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuZW9saXhAdGh1bmRlcnNvZnQuY29tIiwic2NvcGVzIjpbIlRFTkFOVF9BRE1JTiJdLCJ1c2VySWQiOiJmNjlhMjMwMC04ZTFhLTExZTctODhkMi1hZjY2ZDVhOWFmZDciLCJmaXJzdE5hbWUiOiJuZW9saXgiLCJsYXN0TmFtZSI6IiIsImVuYWJsZWQiOnRydWUsImlzUHVibGljIjpmYWxzZSwidGVuYW50SWQiOiIzODJhNmFjMC04ZTE5LTExZTctODhkMi1hZjY2ZDVhOWFmZDciLCJjdXN0b21lcklkIjoiMTM4MTQwMDAtMWRkMi0xMWIyLTgwODAtODA4MDgwODA4MDgwIiwiaXNzIjoidGhpbmdzYm9hcmQuaW8iLCJpYXQiOjE1MTA4MTI0ODEsImV4cCI6MTUxOTgxMjQ4MX0.Sy2u3owV296AEXSgu4qmLIlILumhnro_pYh2DHcetkl8LnzHJjJjKUw6zGslm5kVWVApI8mEDR01T8vWvB5AdA");
            connection.setRequestProperty("connection", "Keep-Alive");
            // 是否可以发送数据到服务器
            connection.setDoOutput(true);
            // 设置是否读服务端
            connection.setDoInput(true);
            // 设置是否缓存
            connection.setUseCaches(false);
            // 设置响应超时
            connection.setConnectTimeout(15000);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(connection.getOutputStream());
            // 发送请求参数
            out.print(command);
            // flush输出流的缓冲
            out.flush();
            if (connection.getResponseCode() == 200) {
                in = new BufferedInputStream(connection.getInputStream());
                byte[] buffer = new byte[10240];
                int flag = 0;
                while ((flag = in.read(buffer)) != -1) {
                    result += new String(buffer, 0, flag);
                }
            } else {
                System.err.println(connection.getResponseCode());
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
