package com.example.wsplugin

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

object ServerManager {
    var isRunning = false
    var wsServer: WebSocketServer? = null
    var httpServer: HttpServer? = null
}

class ToggleServerAction : AnAction("PHP GGBB Debug Server") {
    override fun actionPerformed(e: AnActionEvent) {
        if (!ServerManager.isRunning) {
            ServerManager.wsServer = WebSocketServer().apply { start() }
            ServerManager.httpServer = HttpServer(ServerManager.wsServer!!).apply { start() }
            ServerManager.isRunning = true

            Messages.showMessageDialog(
                "Сервер успешно запущен на портах WS:9095 и HTTP:9093",
                "WebSocket Server",
                Messages.getInformationIcon()
            )
        } else {
            ServerManager.wsServer?.stop()
            ServerManager.httpServer?.stop()
            ServerManager.isRunning = false

            Messages.showMessageDialog(
                "Сервер остановлен",
                "WebSocket Server",
                Messages.getWarningIcon()
            )
        }
    }

    private fun notify(content: String, type: NotificationType) {
        Notifications.Bus.notify(Notification("ws-plugin", "WebSocket Plugin", content, type))
    }
}