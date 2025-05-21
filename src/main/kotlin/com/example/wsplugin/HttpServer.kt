package com.example.wsplugin

import com.sun.net.httpserver.HttpServer
import com.sun.net.httpserver.HttpHandler
import java.io.InputStream
import java.net.InetSocketAddress

class HttpServer(private val wsServer: WebSocketServer) {
    private var server: HttpServer? = null

    fun start() {
        server = HttpServer.create(InetSocketAddress(9093), 0)
        server?.createContext("/") { exchange ->
            if (exchange.requestMethod.equals("POST", true)) {
                val body = exchange.requestBody.readAllBytes().toString(Charsets.UTF_8)
                wsServer.broadcastMessage(body)
                exchange.sendResponseHeaders(200, 0)
            } else {
                exchange.sendResponseHeaders(405, 0)
            }
            exchange.responseBody.close()
        }
        server?.start()
        println("HTTP server started on port 9093")
    }

    fun stop() {
        server?.stop(0)
        println("HTTP server stopped")
    }
}