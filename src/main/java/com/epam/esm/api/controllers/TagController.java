package com.epam.esm.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dtos.TagDTO;
import com.epam.esm.response.entities.BaseResponse;
import com.epam.esm.services.TagService;

@RestController
@RequestMapping("/api/tags")
public class TagController {

	@Autowired
	private TagService tagService;

	@GetMapping
	public ResponseEntity<List<TagDTO>> getTags() {
		List<TagDTO> tags = tagService.getAllTags();
		return new ResponseEntity<>(tags, HttpStatus.OK);
	}

	@GetMapping("/{tagId}")
	public ResponseEntity<TagDTO> getTag(@PathVariable int tagId) {
		TagDTO tagDto = tagService.getTagById(tagId);
		return new ResponseEntity<>(tagDto, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<BaseResponse> saveTag(@RequestBody TagDTO tagDto) {
		BaseResponse response = tagService.saveTag(tagDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{tagId}")
	public ResponseEntity<BaseResponse> deleteTag(@PathVariable int tagId) {
		BaseResponse response = tagService.deleteTag(tagId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
