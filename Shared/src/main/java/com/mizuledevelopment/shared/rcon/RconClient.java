package com.mizuledevelopment.shared.rcon;

import com.mizuledevelopment.shared.objects.ServerModel;
import com.mizuledevelopment.shared.rcon.ex.AuthFailureException;
import com.mizuledevelopment.shared.rcon.ex.RconClientException;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class RconClient implements Closeable {

    private static final Charset PAYLOAD_CHARSET = StandardCharsets.US_ASCII;

    private final SocketChannel socketChannel;
    private final AtomicInteger currentRequestId;

    private RconClient(SocketChannel socketChannel) {
        this.socketChannel = Objects.requireNonNull(socketChannel, "socketChannel");
        this.currentRequestId = new AtomicInteger(1);
    }

    public static RconClient open(ServerModel serverModel, String rconPassword) {
        return open(serverModel.getHost(), serverModel.getRconPort(), rconPassword);
    }

    public static RconClient open(String host, int port, String password) {
        RconClient rconClient = null;

        try {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            rconClient = new RconClient(socketChannel);

            if (password != null)
                rconClient.authenticate(password);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return rconClient;
    }

    private static ByteBuffer toByteBuffer(int requestId, int type, String payload) {
        ByteBuffer buffer = ByteBuffer.allocate((3 * Integer.BYTES) + payload.length() + (2 * Byte.BYTES));
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        buffer.putInt((2 * Integer.BYTES) + payload.length() + (2 * Byte.BYTES));
        buffer.putInt(requestId);
        buffer.putInt(type);
        buffer.put(payload.getBytes(PAYLOAD_CHARSET));
        buffer.put((byte) 0);
        buffer.put((byte) 0);

        buffer.position(0);
        return buffer;
    }

    public String sendCommand(String command) {
        byte[] res = send(2, command).getBytes(StandardCharsets.UTF_8);
        return new String(res, StandardCharsets.UTF_8);
    }

    @Override
    public void close() {
        try {
            socketChannel.close();
        } catch (IOException ex) {
            throw new RconClientException("Failed to close the socket. " + socketChannel.socket().toString(), ex);
        }
    }

    private void authenticate(String password) {
        this.send(3, password);
    }

    private String send(int type, String payload) {
        int requestId = currentRequestId.getAndIncrement();

        ByteBuffer buffer = toByteBuffer(requestId, type, payload);
        try {
            socketChannel.write(buffer);
        } catch (IOException e) {
            throw new RconClientException("Failed to write " + buffer.capacity() + " bytes", e);
        }

        ByteBuffer responseBuffer = readResponse();
        int responseId = responseBuffer.getInt();

        if (responseId == -1)
            throw new AuthFailureException();
        if (responseId != requestId)
            throw new RconClientException("Sent request id " + requestId + " but received " + responseId);

        int responseType = responseBuffer.getInt();

        byte[] bodyBytes = new byte[responseBuffer.remaining()];
        responseBuffer.get(bodyBytes);

        return new String(bodyBytes, PAYLOAD_CHARSET);
    }

    private ByteBuffer readResponse() {
        int size = readData(Integer.BYTES).getInt();
        ByteBuffer dataBuffer = readData(size - (2 * Byte.BYTES));
        ByteBuffer nullsBuffer = readData(2 * Byte.BYTES);

        byte null1 = nullsBuffer.get(0);
        byte null2 = nullsBuffer.get(1);

        if (null1 != 0 || null2 != 0)
            throw new RconClientException("Expected 2 null bytes but received " + null1 + " and " + null2);

        return dataBuffer;
    }

    private ByteBuffer readData(int size) {
        ByteBuffer buffer = ByteBuffer.allocate(size);
        int readCount;
        try {
            readCount = socketChannel.read(buffer);
        } catch (IOException e) {
            throw new RconClientException("Failed to read " + size + " bytes", e);
        }

        if (readCount != size)
            throw new RconClientException("Expected " + size + " bytes but received " + readCount);

        buffer.position(0);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer;
    }

}
