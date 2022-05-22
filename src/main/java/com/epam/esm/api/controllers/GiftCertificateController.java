package com.epam.esm.api.controllers;

import com.epam.esm.response.entities.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dtos.GiftCertificateDTO;
import com.epam.esm.services.GiftCertificateService;

import java.util.List;

@RestController
@RequestMapping("api/certificates")
public class GiftCertificateController {

    @Autowired
    private GiftCertificateService giftCertificateService;

    @GetMapping
    public ResponseEntity<List<GiftCertificateDTO>> getGiftCertificates() {
        List<GiftCertificateDTO> giftCertificates = giftCertificateService.getAllGiftCertificates();
        return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
    }

    @GetMapping("/{giftCertificateId}")
    public ResponseEntity<GiftCertificateDTO> getGiftCertificate(@PathVariable int giftCertificateId) {
        GiftCertificateDTO giftCertificateDto = giftCertificateService.getGiftCertificateById(giftCertificateId);
        return new ResponseEntity<>(giftCertificateDto, HttpStatus.OK);
    }

    @GetMapping("/tag")
    public ResponseEntity<List<GiftCertificateDTO>> getAllGiftCertificatesByTagName(@RequestParam String name) {
        List<GiftCertificateDTO> giftCertificatesDto = giftCertificateService.getAllGiftCertificatesByTagName(name);
        return new ResponseEntity<>(giftCertificatesDto, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<GiftCertificateDTO>> getGiftCertificatesBySearching(@RequestParam(required = false) String value) {
        List<GiftCertificateDTO> giftCertificatesDto = giftCertificateService.getAllGiftCertificatesBySearching(value);
        return new ResponseEntity<>(giftCertificatesDto, HttpStatus.OK);
    }

    @GetMapping("/sort")
    public ResponseEntity<List<GiftCertificateDTO>> getGiftCertificatesBySorting(@RequestParam(required = false) String orderBy,
                                                                                 @RequestParam(required = false) String type) {
        List<GiftCertificateDTO> giftCertificates = giftCertificateService.getAllSortedGiftCertificates(orderBy, type);
        return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BaseResponse> saveGiftCertificate(@RequestBody GiftCertificateDTO request) {
        BaseResponse responseEntity = giftCertificateService.saveGiftCertificate(request);
        return new ResponseEntity<>(responseEntity, HttpStatus.CREATED);
    }

    @PutMapping("/{giftCertificateId}")
    public ResponseEntity<BaseResponse> updateGiftCertificate(@PathVariable int giftCertificateId,
                                                              @RequestBody GiftCertificateDTO giftCertificateDto) {
        BaseResponse myResponseEntity = giftCertificateService.updateGiftCertificate(giftCertificateId,
                giftCertificateDto);
        return new ResponseEntity<>(myResponseEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{giftCertificateId}")
    public ResponseEntity<BaseResponse> deleteGiftCertificate(@PathVariable int giftCertificateId) {
        BaseResponse myResponseEntity = giftCertificateService.deleteGiftCertificate(giftCertificateId);
        return new ResponseEntity<>(myResponseEntity, HttpStatus.OK);
    }

}
