package com.epam.esm.services;

import java.util.List;

import com.epam.esm.dtos.TagDTO;
import com.epam.esm.response.entities.BaseResponse;

public interface TagService {

	public List<TagDTO> getAllTags();

	public TagDTO getTagById(int id);

	public BaseResponse saveTag(TagDTO tag);

	public BaseResponse deleteTag(int id);

	public boolean isTagExist(String tagName);

	public int getIdByTagName(String tagName);
}
