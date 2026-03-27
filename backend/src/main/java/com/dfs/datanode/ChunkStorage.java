package com.dfs.datanode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ChunkStorage {

    private final String storageDirectory;

    public ChunkStorage(String nodeId) {
        // Creates a simulated hard drive folder for each node
        // e.g., "storage_sim/node_8001/"
        this.storageDirectory = System.getProperty("user.dir") + "/storage_sim/" + nodeId + "/";
        initializeStorage();
    }

    private void initializeStorage() {
        File dir = new File(storageDirectory);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println("Initialized storage volume at: " + storageDirectory);
            } else {
                System.err.println("CRITICAL: Failed to create storage volume.");
            }
        }
    }

    public boolean saveChunk(String chunkId, byte[] data) {
        File chunkFile = new File(storageDirectory + chunkId + ".chunk");
        
        try (FileOutputStream fos = new FileOutputStream(chunkFile)) {
            fos.write(data);
            System.out.println("Successfully stored chunk: " + chunkId + " (" + data.length + " bytes)");
            return true;
        } catch (IOException e) {
            System.err.println("Disk I/O Error while saving chunk " + chunkId + ": " + e.getMessage());
            return false;
        }
    }
}