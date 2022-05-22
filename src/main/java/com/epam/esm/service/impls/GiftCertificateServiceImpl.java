package com.epam.esm.service.impls;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.esm.daos.GiftCertificateAndTagDAO;
import com.epam.esm.daos.GiftCertificateDAO;
import com.epam.esm.dtos.GiftCertificateDTO;
import com.epam.esm.dtos.TagDTO;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.exceptions.AlreadyExistException;
import com.epam.esm.exceptions.FieldRequiredException;
import com.epam.esm.exceptions.GenericException;
import com.epam.esm.exceptions.NotFoundException;
import com.epam.esm.response.entities.BaseResponse;
import com.epam.esm.response.entities.BaseResponseBody;
import com.epam.esm.services.GiftCertificateService;
import com.epam.esm.services.TagService;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

	private GiftCertificateDAO giftCertificateDao;

	@Autowired
	private GiftCertificateAndTagDAO giftCertificateAndTagDao;

	@Autowired
	private TagService tagService;

	@Autowired
	public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDao) {
		this.giftCertificateDao = giftCertificateDao;
	}

	@Override
	public List<GiftCertificateDTO> getAllGiftCertificates() {

		List<GiftCertificateDTO> result = new ArrayList<>();

		List<GiftCertificate> giftCertificates = giftCertificateDao.getAllGiftCertificates();
		for (GiftCertificate giftCertificate : giftCertificates) {
			GiftCertificateDTO populatedGiftCertificateDTO = populateGiftCertificateDTO(giftCertificate);
			result.add(populatedGiftCertificateDTO);
		}
		return result;
	}

	@Override
	public GiftCertificateDTO getGiftCertificateById(int id) {
		boolean isExist = giftCertificateDao.isExistByCertificateId(id);
		if (!isExist) {
			throw new NotFoundException("Gift Certificate with id: " + id + " is not found to get.");
		}
		GiftCertificate giftCertificate = giftCertificateDao.getGiftCertificateById(id);
		GiftCertificateDTO populatedGiftCertificateDTO = populateGiftCertificateDTO(giftCertificate);
		return populatedGiftCertificateDTO;
	}

	@Override
	public List<GiftCertificateDTO> getAllGiftCertificatesByTagName(String tagName) {
		if (tagName == null) {
			throw new GenericException("Tag name is required");
		}
		List<GiftCertificateDTO> result = new ArrayList<>();
		int tagId = tagService.getIdByTagName(tagName);
		List<Integer> giftCertificateIds = giftCertificateAndTagDao.findAllGiftCertificateIdsByTagId(tagId);
		for (int giftCertificateId : giftCertificateIds) {
			GiftCertificate giftCertificate = giftCertificateDao.getGiftCertificateById(giftCertificateId);
			GiftCertificateDTO giftCertificateDTO = populateGiftCertificateDTO(giftCertificate);
			result.add(giftCertificateDTO);
		}
		return result;
	}

	@Override
	public List<GiftCertificateDTO> getAllSortedGiftCertificates(String orderBy, String type) {
		if (orderBy == null)
			orderBy = "name";
		if (type == null)
			type = "asc";
		List<GiftCertificateDTO> result = new ArrayList<>();
		List<GiftCertificate> sortedGiftCertificates = giftCertificateDao.getAllSortedGiftCertificates(orderBy, type);
		for (GiftCertificate giftCertificate : sortedGiftCertificates) {
			GiftCertificateDTO populatedGiftCertificateDTO = populateGiftCertificateDTO(giftCertificate);
			result.add(populatedGiftCertificateDTO);
		}
		return result;
	}

	@Override
	public List<GiftCertificateDTO> getAllGiftCertificatesBySearching(String value) {
		if (value == null) {
			throw new GenericException("Value is required to search");
		}
		List<GiftCertificateDTO> result = new ArrayList<>();
		List<GiftCertificate> allGiftCertificates = giftCertificateDao.getAllGiftCertificatesBySearching(value);
		for (GiftCertificate giftCertificate : allGiftCertificates) {
			GiftCertificateDTO giftCertificateDTO = populateGiftCertificateDTO(giftCertificate);
			result.add(giftCertificateDTO);
		}
		return result;
	}

	public GiftCertificateDTO populateGiftCertificateDTO(GiftCertificate giftCertificate) {
		GiftCertificateDTO result = new GiftCertificateDTO();
		result.setId(giftCertificate.getId());
		result.setName(giftCertificate.getName());
		result.setDescription(giftCertificate.getDescription());
		result.setPrice(giftCertificate.getPrice());
		result.setDuration(giftCertificate.getDuration());
		result.setCreateDate(giftCertificate.getCreateDate());
		result.setLastUpdateDate(giftCertificate.getLastUpdateDate());

		List<TagDTO> tags = getGiftCertificateTags(giftCertificate.getId());
		result.setTags(tags);
		return result;
	}

	public List<TagDTO> getGiftCertificateTags(int giftCertificateId) {
		List<TagDTO> tags = new ArrayList<>();
		List<Integer> tagIds = giftCertificateAndTagDao.findAllTagIdsByGiftCertificateId(giftCertificateId);
		for (Integer tagId : tagIds) {
			TagDTO tag = tagService.getTagById(tagId);
			tags.add(tag);
		}
		return tags;
	}

	@Override
	public BaseResponse saveGiftCertificate(GiftCertificateDTO giftCertificateDto) {

		String giftCertificateName = giftCertificateDto.getName();
		if (giftCertificateName == null)
			throw new FieldRequiredException("Name field is required");
		boolean isExist = giftCertificateDao.isExistByCertificateName(giftCertificateName);
		if (isExist)
			throw new AlreadyExistException("'" + giftCertificateName + "' is already exist");

		GiftCertificate giftCertificate = new GiftCertificate();
		giftCertificate.setName(giftCertificateName);
		giftCertificate.setDescription(giftCertificateDto.getDescription());
		giftCertificate.setPrice(giftCertificateDto.getPrice());
		giftCertificate.setDuration(giftCertificateDto.getDuration());
		giftCertificate.setCreateDate(getTime());
		giftCertificate.setLastUpdateDate(getTime());

		int result = giftCertificateDao.saveGiftCertificate(giftCertificate);
		if (result == 0)
			throw new GenericException("Error happened during saving new Gift Certificate");
		int savedGiftCertificateId = giftCertificateDao.getLastSavedGiftCertificateId();

		List<TagDTO> tags = giftCertificateDto.getTags();
		if (tags != null) {
			List<Integer> savedTagIds = getShouldBeSavedTagIds(savedGiftCertificateId, tags);
			for (int savedTagId : savedTagIds) {
				giftCertificateAndTagDao.saveAssosiation(savedGiftCertificateId, savedTagId);
			}
		}
		BaseResponseBody responseBody = new BaseResponseBody("'" + giftCertificateName + "' is saved successfully",
				9999);
		BaseResponse responseEntity = new BaseResponse(200, responseBody);
		return responseEntity;
	}

	private LocalDateTime getTime() {
		return LocalDateTime.now();
	}

	private List<Integer> getShouldBeSavedTagIds(int giftCertificateId, List<TagDTO> tags) {
		List<Integer> shouldBeSavedTagIds = new ArrayList<>();
		for (TagDTO tag : tags) {
			boolean isExist = tagService.isTagExist(tag.getName());
			if (!isExist)
				tagService.saveTag(tag);
			int tagId = tagService.getIdByTagName(tag.getName());
			boolean checkAssosiation = giftCertificateAndTagDao.checkAssosiation(giftCertificateId, tagId);
			if (!checkAssosiation)
				shouldBeSavedTagIds.add(tagId);
		}
		return shouldBeSavedTagIds;
	}

	@Override
	public BaseResponse updateGiftCertificate(int giftCertificateId, GiftCertificateDTO giftCertificateDto) {
		boolean isExist = giftCertificateDao.isExistByCertificateId(giftCertificateId);
		if (!isExist) {
			throw new NotFoundException("Gift Certificate with id: " + giftCertificateId + " is not found to update.");
		}
		ArrayList<Object> fields = new ArrayList<>();
		String sql = "update gift_certificate set ";
		int sqlLength = sql.length();
		if (giftCertificateDto.getName() != null) {
			sql += "name = ?";
			fields.add(giftCertificateDto.getName());
		}
		if (giftCertificateDto.getDescription() != null) {
			if (sql.length() != sqlLength)
				sql += ", ";
			sql += "description = ?";
			fields.add(giftCertificateDto.getDescription());
		}
		if (giftCertificateDto.getPrice() != 0) {
			if (sql.length() != sqlLength)
				sql += ", ";
			sql += "price = ?";
			fields.add(giftCertificateDto.getPrice());
		}
		if (giftCertificateDto.getDuration() != 0) {
			if (sql.length() != sqlLength)
				sql += ", ";
			sql += "duration = ?";
			fields.add(giftCertificateDto.getDuration());
		}
		if (sql.length() == sqlLength && giftCertificateDto.getTags() == null) {
			throw new GenericException("No field is added to update");
		}
		if (sql.length() != sqlLength) {
			sql += ", ";
		}
		sql += " last_update_date = ? where id=?";
		fields.add(getTime());
		fields.add(giftCertificateId);
		Object[] params = fields.toArray();
		int result = giftCertificateDao.updateGiftCertificate(sql, params);
		if (result == 0)
			throw new GenericException("Error during updating");

		if (giftCertificateDto.getTags() != null && giftCertificateDto.getTags().size() != 0) {
			List<Integer> tagIds = getShouldBeSavedTagIds(giftCertificateId, giftCertificateDto.getTags());
			for (Integer tagId : tagIds) {
				giftCertificateAndTagDao.saveAssosiation(giftCertificateId, tagId);
			}
		}
		BaseResponseBody responseBody = new BaseResponseBody("Updated successfully", 9999);
		BaseResponse responseEntity = new BaseResponse(200, responseBody);
		return responseEntity;

	}

	@Override
	public BaseResponse deleteGiftCertificate(int id) {
		boolean isExist = giftCertificateDao.isExistByCertificateId(id);
		if (!isExist) {
			throw new NotFoundException("Gift Certificate with id: " + id + " is not found to delete.");
		}
		int result = giftCertificateDao.deleteGiftCertificate(id);
		if (result == 0)
			throw new GenericException("Error during deletion");
		giftCertificateAndTagDao.deleteAllByGiftCertificateId(id);
		BaseResponseBody responseBody = new BaseResponseBody("Deleted successfully", 9999);
		BaseResponse responseEntity = new BaseResponse(200, responseBody);
		return responseEntity;
	}

}
