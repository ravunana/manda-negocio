package com.ravunana.manda.service;

import com.ravunana.manda.domain.ContactoEmpresa;
import com.ravunana.manda.repository.ContactoEmpresaRepository;
import com.ravunana.manda.service.dto.ContactoEmpresaDTO;
import com.ravunana.manda.service.mapper.ContactoEmpresaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ContactoEmpresa}.
 */
@Service
@Transactional
public class ContactoEmpresaService {

    private final Logger log = LoggerFactory.getLogger(ContactoEmpresaService.class);

    private final ContactoEmpresaRepository contactoEmpresaRepository;

    private final ContactoEmpresaMapper contactoEmpresaMapper;

    public ContactoEmpresaService(ContactoEmpresaRepository contactoEmpresaRepository, ContactoEmpresaMapper contactoEmpresaMapper) {
        this.contactoEmpresaRepository = contactoEmpresaRepository;
        this.contactoEmpresaMapper = contactoEmpresaMapper;
    }

    /**
     * Save a contactoEmpresa.
     *
     * @param contactoEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public ContactoEmpresaDTO save(ContactoEmpresaDTO contactoEmpresaDTO) {
        log.debug("Request to save ContactoEmpresa : {}", contactoEmpresaDTO);
        ContactoEmpresa contactoEmpresa = contactoEmpresaMapper.toEntity(contactoEmpresaDTO);
        contactoEmpresa = contactoEmpresaRepository.save(contactoEmpresa);
        return contactoEmpresaMapper.toDto(contactoEmpresa);
    }

    /**
     * Get all the contactoEmpresas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactoEmpresaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContactoEmpresas");
        return contactoEmpresaRepository.findAll(pageable)
            .map(contactoEmpresaMapper::toDto);
    }


    /**
     * Get one contactoEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContactoEmpresaDTO> findOne(Long id) {
        log.debug("Request to get ContactoEmpresa : {}", id);
        return contactoEmpresaRepository.findById(id)
            .map(contactoEmpresaMapper::toDto);
    }

    /**
     * Delete the contactoEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ContactoEmpresa : {}", id);
        contactoEmpresaRepository.deleteById(id);
    }
}
