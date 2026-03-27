package com.dfs.master;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MasterNode {
    
    private final NamespaceMap namespaceMap;
    private final HealthMonitor healthMonitor;
    // The pool of worker threads
    private final ExecutorService networkThreadPool;
    private final int PORT = 9000;

    public MasterNode() {
        this.namespaceMap = new NamespaceMap();
        this.healthMonitor = new HealthMonitor();
        // Create a fixed pool of 10 threads to handle concurrent connections
        this.networkThreadPool = Executors.newFixedThreadPool(10);
    }

    public void start() {
        System.out.println("Booting up Master Node...");

        // 1. Start the Health Monitor in a background thread
        Thread monitorThread = new Thread(healthMonitor);
        // Setting it as a Daemon ensures it runs strictly in the background
        monitorThread.setDaemon(true); 
        monitorThread.start();
        System.out.println("Health Monitor active. Watching for dead process states.");

        // Start the Front Door (HTTP API)
        MasterApiServer apiServer = new MasterApiServer(healthMonitor, namespaceMap);
        apiServer.start();
        
        // 2. Open the network port and listen for connections
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Master Node network listener active on port " + PORT);

            // Infinite loop to keep the server running forever
            while (true) {
                // The program PAUSES on this next line until someone connects
                Socket clientSocket = serverSocket.accept();
                System.out.println("New network connection received!");

                // THE FIX IS HERE: Removed namespaceMap from the constructor
                networkThreadPool.submit(new ClientHandler(clientSocket, healthMonitor));
            }
            
        } catch (IOException e) {
            System.err.println("Critical Network Failure: " + e.getMessage());
        }
    }

    public static void main(String[] args) 
    {
        MasterNode master = new MasterNode();
        master.start();
    }
}