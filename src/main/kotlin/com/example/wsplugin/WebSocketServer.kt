package com.example.wsplugin

import org.java_websocket.server.WebSocketServer
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import java.net.InetSocketAddress
import java.util.concurrent.CopyOnWriteArrayList

class WebSocketServer : WebSocketServer(InetSocketAddress(9095)) {
    private val clients = CopyOnWriteArrayList<WebSocket>()

    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        clients.add(conn)
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        clients.remove(conn)
    }

    override fun onMessage(conn: WebSocket, message: String) {
        // echo or ignore
    }

    override fun onError(conn: WebSocket?, ex: Exception) {
        ex.printStackTrace()
    }

    override fun onStart() {
        println("WebSocket started on port 9095")
    }

    fun broadcastMessage(msg: String) {
        for (client in clients) {
            client.send(msg)
        }
    }
}