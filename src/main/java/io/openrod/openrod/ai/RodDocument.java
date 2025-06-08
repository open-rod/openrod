package io.openrod.openrod.ai;

import io.openrod.openrod.tag.TagDTO;

import java.util.List;

public record RodDocument(String content, List<TagDTO> tags, Double score) { }
