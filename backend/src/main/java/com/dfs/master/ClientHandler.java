package com.dfs.master;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final HealthMonitor healthMonitor;

    public ClientHandler(Socket clientSocket, HealthMonitor healthMonitor) {
        this.clientSocket = clientSocket;
        this.healthMonitor = healthMonitor;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String message = in.readLine();
            
            // Expecting message format: "HEARTBEAT 9001"
            if (message != null && message.startsWith("HEARTBEAT")) {
                String[] parts = message.split(" ");
                if (parts.length == 2) {
                    String dataNodePort = parts[1]; 
                    
                    // Combine IP and Port to create a routable address (e.g., "localhost:9001")
                    String nodeAddress = "localhost:" + dataNodePort;
                    
                    System.out.println("Heartbeat received from: " + nodeAddress);
                    healthMonitor.updateHeartbeat(nodeAddress);
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading heartbeat: " + e.getMessage());
        } finally {
            try { clientSocket.close(); } catch (Exception e) {}
        }
    }
}