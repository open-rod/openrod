package io.openrod.openrod.tag;

import java.util.List;
import java.util.UUID;

public interface TagService {

    List<TagInfoDO> getTags();
    TagDTO getTag(final UUID id);
    TagDTO createTag(final TagDTO tag);
    TagDTO updateTag(final TagDTO tag);
    void deleteTag(final UUID id);

}
