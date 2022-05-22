package com.epam.esm.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.esm.entities.Tag;

public class TagRowMapper implements RowMapper<Tag> {

	@Override
	public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tag tag = new Tag();
		tag.setId(rs.getInt("id"));
		tag.setName(rs.getString("name"));
		return tag;
	}

}
