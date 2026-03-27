package com.dfs.shared.models;

import java.util.ArrayList;
import java.util.List;

public class ChunkInfo {
    private final String chunkId;
    private final int sequenceNumber; // Which piece of the file is this? (0, 1, 2...)
    private final List<String> nodeLocations; // Where is it saved? e.g., ["localhost:9001"]

    public ChunkInfo(String chunkId, int sequenceNumber) {
        this.chunkId = chunkId;
        this.sequenceNumber = sequenceNumber;
        this.nodeLocations = new ArrayList<>();
    }

    public void addNodeLocation(String nodeAddress) {
        this.nodeLocations.add(nodeAddress);
    }

    public String getChunkId() { return chunkId; }
    public int getSequenceNumber() { return sequenceNumber; }
    public List<String> getNodeLocations() { return nodeLocations; }
}