package com.epam.esm.service.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.esm.daos.GiftCertificateAndTagDAO;
import com.epam.esm.daos.TagDAO;
import com.epam.esm.dtos.TagDTO;
import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.AlreadyExistException;
import com.epam.esm.exceptions.FieldRequiredException;
import com.epam.esm.exceptions.GenericException;
import com.epam.esm.exceptions.NotFoundException;
import com.epam.esm.response.entities.BaseResponse;
import com.epam.esm.response.entities.BaseResponseBody;
import com.epam.esm.services.TagService;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagDAO tagDao;

	@Autowired
	private GiftCertificateAndTagDAO gcatDao;

	@Override
	public List<TagDTO> getAllTags() {
		List<TagDTO> result = new ArrayList<>();
		List<Tag> tags = tagDao.getAllTags();
		for (Tag tag : tags) {
			TagDTO tagDto = populateTagDto(tag);
			result.add(tagDto);
		}
		return result;
	}

	@Override
	public TagDTO getTagById(int id) {
		if (!tagDao.isTagExist(id)) {
			throw new NotFoundException("Tag with id: " + id + " is not found to get");
		}
		Tag tag = tagDao.getTagById(id);
		return populateTagDto(tag);
	}

	private TagDTO populateTagDto(Tag tag) {
		TagDTO tagDto = new TagDTO();
		tagDto.setId(tag.getId());
		tagDto.setName(tag.getName());
		return tagDto;
	}

	@Override
	public BaseResponse saveTag(TagDTO tagDto) {
		if (tagDto.getName() == null)
			throw new FieldRequiredException("Tag name is required in saving");

		if (isTagExist(tagDto.getName()))
			throw new AlreadyExistException("Tag name is already exist");
		Tag tag = new Tag();
		tag.setName(tagDto.getName());
		int result = tagDao.saveTag(tag);
		if (result == 0)
			throw new GenericException("Error occured during saving new Tag.");
		BaseResponseBody responseBody = new BaseResponseBody("'" + tag.getName() + "' is saved successfully", 9999);
		BaseResponse responseEntity = new BaseResponse(200, responseBody);
		return responseEntity;
	}

	@Override
	public BaseResponse deleteTag(int id) {
		if (!tagDao.isTagExist(id)) {
			throw new NotFoundException("Tag with id: " + id + " is not found to delete");
		}
		gcatDao.deleteAllByTagId(id);
		int result = tagDao.deleteTag(id);
		if (result == 0)
			throw new GenericException("Error occured during deletion Tag.");
		BaseResponseBody responseBody = new BaseResponseBody("Tag with id: " + id + " is deleted successfully", 9999);
		BaseResponse responseEntity = new BaseResponse(200, responseBody);
		return responseEntity;
	}

	@Override
	public boolean isTagExist(String tagName) {
		return tagDao.isTagExist(tagName);
	}

	@Override
	public int getIdByTagName(String tagName) {
		if (tagName == null)
			throw new FieldRequiredException("Tag name is required");
		return tagDao.getIdByTagName(tagName);
	}

}
