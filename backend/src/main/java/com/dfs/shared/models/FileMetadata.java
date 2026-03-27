package com.dfs.shared.models;

import java.util.ArrayList;
import java.util.List;

public class FileMetadata {
    private final String filename;
    private final long fileSize;
    private final List<ChunkInfo> chunks;

    public FileMetadata(String filename, long fileSize) {
        this.filename = filename;
        this.fileSize = fileSize;
        this.chunks = new ArrayList<>();
    }

    public void addChunk(ChunkInfo chunk) {
        this.chunks.add(chunk);
    }

    public String getFilename() { return filename; }
    public long getFileSize() { return fileSize; }
    public List<ChunkInfo> getChunks() { return chunks; }
}