package com.epam.esm.daos;

import java.util.List;

import com.epam.esm.entities.Tag;

public interface TagDAO {

	public List<Tag> getAllTags();

	public Tag getTagById(int id);

	public int getIdByTagName(String name);

	public int saveTag(Tag tag);

	public int deleteTag(int id);

	public boolean isTagExist(String tagName);

	public boolean isTagExist(int id);

}
