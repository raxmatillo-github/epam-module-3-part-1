package com.epam.esm.dao.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.epam.esm.daos.GiftCertificateAndTagDAO;

@Repository
public class GiftCertificateAndTagDAOImpl implements GiftCertificateAndTagDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Integer> findAllTagIdsByGiftCertificateId(int giftCertificateId) {
		String sql = "select tag_id from gift_certificate_and_tag where gift_certificate_id=?";
		List<Integer> tags = jdbcTemplate.queryForList(sql, Integer.class, giftCertificateId);
		return tags;
	}

	@Override
	public List<Integer> findAllGiftCertificateIdsByTagId(int tagId) {
		String sql = "select gift_certificate_id from gift_certificate_and_tag where tag_id=?";
		List<Integer> giftCertificateIds = jdbcTemplate.queryForList(sql, Integer.class, tagId);
		return giftCertificateIds;
	}

	@Override
	public int deleteAllByGiftCertificateId(int id) {
		String sql = "delete from gift_certificate_and_tag where gift_certificate_id=?";
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public int deleteAllByTagId(int id) {
		String sql = "delete from gift_certificate_and_tag where tag_id=?";
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public int saveAssosiation(int giftCertificateId, int tagId) {
		String sql = "insert into gift_certificate_and_tag values(?,?)";
		return jdbcTemplate.update(sql, giftCertificateId, tagId);
	}

	@Override
	public boolean checkAssosiation(int giftCertificateId, int tagId) {
		String sql = "SELECT EXISTS(SELECT FROM gift_certificate_and_tag WHERE gift_certificate_id = ? and tag_id=?)";
		boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, giftCertificateId, tagId);
		return result;
	}

}
