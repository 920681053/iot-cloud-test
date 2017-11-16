package com.thundersoft.test.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    public static void main(String[] args) {
        //http 遥测数据上传
        //String sendPost = HttpUtils.sendPost("http://192.168.7.47:8080/api/v1/NH3UMaj8UZU0MHAohvJZ/attributes", "{key1:value1,key2:true,key3:3.0,key4:4}");
        //System.out.println(sendPost);

        //http 客户端属性上传
        //HttpUtils.sendPost("http://192.168.7.206:8080/api/v1/NH3UMaj8UZU0MHAohvJZ/attributes", "{\"attribute1\":\"value1\", \"attribute2\":true, \"attribute3\":42.0, \"attribute4\":73}");

        //http 客户端订阅共享属性的更新（只能接收一次响应）
        //HttpUtils.sendGet("http://192.168.7.206:8080/api/v1/NH3UMaj8UZU0MHAohvJZ/attributes/updates", "timeout=20000");

        //http 客户端获取客户端、共享属性
        //clientKeys=attribute1,attribute2&sharedKeys=shared1,shared2
        //HttpUtils.sendGet("http://192.168.7.206:8080/api/v1/NH3UMaj8UZU0MHAohvJZ/attributes", "clientKeys=attribute1,attribute2&sharedKeys=shared1,shared2");

        //http 客户端RPC将POST请求发送到以下URL：
        //String sendPost = HttpUtils.sendPost("http://192.168.7.47:8080/api/v1/NH3UMaj8UZU0MHAohvJZ/rpc", "{\"method\": \"getDevices\", \"params\":{}}");
        //System.out.println(sendPost);

        //http 服务端RPC oneway
        //http://localhost:8080/api/v1/$ACCESS_TOKEN/rpc/1  twoway 响应的URL
        //String sendGet = HttpUtils.sendGet("https://demo.thingsboard.io/api/v1/jD6MqIJXszKPulrkynsm/rpc", "timeout=20000");
        //System.out.println(sendGet);

    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedInputStream in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            if (connection.getResponseCode() == 200) {
                // 定义 BufferedReader输入流来读取URL的响应
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
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Object param) {
        PrintWriter out = null;
        BufferedInputStream in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("Charset", "utf-8");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 固定格式
            //connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //connection.setRequestProperty("Charset", "utf-8");

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
            out.print(param);
            System.err.println(param);
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
