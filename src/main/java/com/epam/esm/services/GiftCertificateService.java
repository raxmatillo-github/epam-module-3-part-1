package com.epam.esm.services;

import java.util.List;

import com.epam.esm.dtos.GiftCertificateDTO;
import com.epam.esm.response.entities.BaseResponse;

public interface GiftCertificateService {

	public List<GiftCertificateDTO> getAllGiftCertificates();

	public GiftCertificateDTO getGiftCertificateById(int id);

	public List<GiftCertificateDTO> getAllGiftCertificatesByTagName(String tagName);

	public List<GiftCertificateDTO> getAllGiftCertificatesBySearching(String value);

	public List<GiftCertificateDTO> getAllSortedGiftCertificates(String orderBy, String type);

	public BaseResponse saveGiftCertificate(GiftCertificateDTO giftCertificate);

	public BaseResponse updateGiftCertificate(int giftCertificateId, GiftCertificateDTO giftCertificate);

	public BaseResponse deleteGiftCertificate(int id);

}
