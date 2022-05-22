package com.epam.esm.daos;

import java.util.List;

public interface GiftCertificateAndTagDAO {

	public List<Integer> findAllTagIdsByGiftCertificateId(int giftCertificateId);

	public List<Integer> findAllGiftCertificateIdsByTagId(int tagId);

	public int deleteAllByGiftCertificateId(int id);

	public int deleteAllByTagId(int id);

	public int saveAssosiation(int savedGiftCertificateId, int savedTagId);

	public boolean checkAssosiation(int giftCertificateId, int tagId);

}
