package io.openrod.openrod.category.impl;

import io.openrod.openrod.app.AppService;
import io.openrod.openrod.category.CategoryDTO;
import io.openrod.openrod.category.CategoryInfoDTO;
import io.openrod.openrod.category.CategoryService;
import io.openrod.openrod.category.events.CategoryCreatedEvent;
import io.openrod.openrod.category.events.CategoryDeletedEvent;
import io.openrod.openrod.memory.MemoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private final AppService appService;
    private final MemoryService memoryService;

    private final ApplicationEventPublisher publisher;

    @Autowired
    public CategoryServiceImpl(
        final CategoryRepository categoryRepository,
        final CategoryMapper categoryMapper,
        final AppService appService,
        final MemoryService memoryService,
        final ApplicationEventPublisher publisher
    ) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;

        this.appService = appService;
        this.memoryService = memoryService;

        this.publisher = publisher;
    }

    @Override
    public List<CategoryInfoDTO> getCategories() {
        return this.categoryRepository.findAll()
            .stream()
            .map(this.categoryMapper::toDTO)
            .map(category ->
                    new CategoryInfoDTO(
                        category,
                        this.appService.getAppsByCategoryId(category.getId()).size(),
                        this.memoryService.getMemoriesByCategoryId(category.getId()).size()
                    )
            )
            .toList();
    }

    @Override
    public CategoryDTO getCategory(UUID id) {
        return this.categoryRepository.findById(id)
                .map(this.categoryMapper::toDTO)
                .orElseThrow(() -> new HttpServerErrorException(HttpStatusCode.valueOf(400), "Category not found"));
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO category) {
        var entity = this.categoryMapper.toEntity(category);

        var createdDTO = this.categoryMapper.toDTO(
            this.categoryRepository.save(entity)
        );

        this.publisher.publishEvent(new CategoryCreatedEvent(createdDTO));

        return createdDTO;
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO category) {
        var entity = this.categoryRepository.findById(category.getId())
                .orElseThrow(() -> new HttpServerErrorException(HttpStatusCode.valueOf(400), "Category not found"));

        this.categoryMapper.mapEntity(category, entity);

        return this.categoryMapper.toDTO(
            this.categoryRepository.save(entity)
        );
    }

    @Override
    public void deleteCategory(UUID id) {
        this.categoryRepository.deleteById(id);
        this.publisher.publishEvent(new CategoryDeletedEvent(id));
    }
}
