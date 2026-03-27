package com.dfs.master;

import com.dfs.shared.models.FileMetadata;
import java.util.concurrent.ConcurrentHashMap;

public class NamespaceMap {
    
    // A thread-safe map. Key = filename, Value = The File's Receipt
    private final ConcurrentHashMap<String, FileMetadata> map;

    public NamespaceMap() {
        this.map = new ConcurrentHashMap<>();
    }

    // THIS IS THE MISSING METHOD!
    public void put(String filename, FileMetadata metadata) {
        map.put(filename, metadata);
    }

    public FileMetadata get(String filename) {
        return map.get(filename);
    }

    public boolean contains(String filename) {
        return map.containsKey(filename);
    }
}