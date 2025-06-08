package io.openrod.openrod.memory;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface MemoryService {

    List<MemoryDTO> getMemories();
    Page<MemoryDTO> searchMemories(MemorySearchCriteria searchCriteria);
    MemoryDTO getMemory(UUID id);
    MemoryDTO createMemory(MemoryDTO memory);
    MemoryDTO updateMemory(MemoryDTO memory);
    void deleteMemory(UUID id);

    List<MemoryDTO> getMemoriesByCategories(List<UUID> list);

    List<MemoryDTO> getMemoriesByCategoryId(final UUID categoryId);
    List<MemoryDTO> getMemoriesByTagId(final UUID tagId);

}
