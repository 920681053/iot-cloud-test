package com.thundersoft.test.mqtt.nio;

import java.nio.IntBuffer;

public class TestBuffer {

    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        buffer.put(1);
        buffer.put(2);
        buffer.put(3);
        for (int i = 0; i < buffer.limit(); i++) {
            System.err.print(buffer.get(i) + "\t");
        }
    }
}
