package io.openrod.openrod.tag;

public class TagInfoDO {

    private final TagDTO tag;
    private final int numberOfApps;
    private final int numberOfMemories;

    public TagInfoDO(
        final TagDTO tag,
        final int numberOfApps,
        final int numberOfMemories
    ) {
        this.tag = tag;
        this.numberOfApps = numberOfApps;
        this.numberOfMemories = numberOfMemories;
    }

    public TagDTO getTag() {
        return this.tag;
    }

    public int getNumberOfApps() {
        return this.numberOfApps;
    }

    public int getNumberOfMemories() {
        return this.numberOfMemories;
    }
}
