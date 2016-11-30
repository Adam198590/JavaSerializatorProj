package test_client_server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

import api.ByteBufferSerializableObject;
import impl.gen.packet.LoginBody;
import util.SocketChannelUtil;

public class Server {

    private static SocketChannelUtil socketChannelUtil = new SocketChannelUtil();

    public static void main(String[] args) {
        try {
            Selector selector = SelectorProvider.provider().openSelector();
            ServerSocketChannel socketServer = ServerSocketChannel.open();                          // selectable channel for stream-oriented listening sockets

            socketServer.configureBlocking(false);                                                  // Adjusts this channel's blocking mode.

            socketServer.bind(new InetSocketAddress("localhost", 1111));

            socketServer.register(selector, socketServer.validOps(), null);                         // selector registering

            // Keep server running
            log("i'm a server and i'm waiting for new connection and buffer select...");
            while (selector.select() > -1) {
                Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();   // get keys with events

                while (selectionKeyIterator.hasNext()) {
                    SelectionKey key = selectionKeyIterator.next();

                    if (key.isValid()) {
                        try {
                            if (key.isAcceptable()) {                       // Is this key channel ready to accept a connection
                                accept(key);
                            } else if (key.isReadable()) {                  // Is this key channel ready for reading
                                SocketChannel socketClient = (SocketChannel) key.channel();

                                ByteBufferSerializableObject loginPacket = socketChannelUtil.readPacket(socketClient);

                                System.out.println(((LoginBody) loginPacket).userData.uid);
                                System.out.println(((LoginBody) loginPacket).userData.name);

                                close(key);
                            } else if (key.isWritable()) {
                                throw new IllegalStateException();
                            }
                        } catch (Exception e) {
                            log(e.getMessage());
                            close(key);
                        }
                    }

                    selectionKeyIterator.remove();
                }
            }
        } catch (Exception e) {
            log(e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    /**
     * Accept connection, register key with event(OP_READ)
     * @param key key with event
     * @throws IOException
     */
    private static void accept(SelectionKey key) throws IOException {
        SocketChannel newChannel = ((ServerSocketChannel) key.channel()).accept();
        newChannel.configureBlocking(false);
        newChannel.register(key.selector(), SelectionKey.OP_READ);

        log("Connection Accepted: " + newChannel.getLocalAddress() + "\n");
    }

    private static void close(SelectionKey key) throws IOException {
        key.cancel();
        key.channel().close();
    }

    static void log(String str) {
        System.out.println(str);
    }
}
