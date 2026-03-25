package com.dfs.shared.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileMetadata {
    private final String fileName;
    private final long fileSizeBytes;
    // The ordered list of chunks that make up this entire file
    private final List<ChunkInfo> chunks;

    public FileMetadata(String fileName, long fileSizeBytes, List<ChunkInfo> chunks) {
        this.fileName = fileName;
        this.fileSizeBytes = fileSizeBytes;
        
        // Defensive copying to ensure strict immutability
        this.chunks = Collections.unmodifiableList(new ArrayList<>(chunks));
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSizeBytes() {
        return fileSizeBytes;
    }

    public List<ChunkInfo> getChunks() {
        return chunks;
    }
}