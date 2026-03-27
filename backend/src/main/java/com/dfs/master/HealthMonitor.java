package com.dfs.master;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HealthMonitor implements Runnable {
    
    // Maps a Data Node's ID to the timestamp of its last heartbeat
    private final ConcurrentHashMap<String, Long> activeNodes;
    
    // If a node is silent for 15 seconds, consider it dead
    private final long TIMEOUT_THRESHOLD_MS = 15000; 

    public HealthMonitor() {
        this.activeNodes = new ConcurrentHashMap<>();
    }

    public void updateHeartbeat(String nodeId) {
        activeNodes.put(nodeId, System.currentTimeMillis());
    }

    /**
     * Returns a list of all currently active Data Nodes.
     * The File Splitter will use this to know where to send the file chunks.
     */
    public List<String> getActiveNodes() {
        // We return a brand new ArrayList containing the keys. 
        // This is a "Defensive Copy" to ensure thread safety.
        return new ArrayList<>(activeNodes.keySet());
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Pause the thread for 5 seconds before checking again
                Thread.sleep(5000); 
                
                long currentTime = System.currentTimeMillis();
                
                // Sweep through the map to find expired timestamps
                for (Map.Entry<String, Long> entry : activeNodes.entrySet()) {
                    if (currentTime - entry.getValue() > TIMEOUT_THRESHOLD_MS) {
                        String deadNodeId = entry.getKey();
                        System.out.println("CRITICAL: Data Node " + deadNodeId + " is unresponsive.");
                        handleNodeFailure(deadNodeId);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Health Monitor shutting down...");
                break;
            }
        }
    }

    private void handleNodeFailure(String deadNodeId) {
        activeNodes.remove(deadNodeId);
        // Future logic: Command surviving nodes to duplicate chunks that were on this node
    }
}