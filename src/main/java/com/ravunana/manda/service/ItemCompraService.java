package com.ravunana.manda.service;

import com.ravunana.manda.domain.ItemCompra;
import com.ravunana.manda.domain.Produto;
import com.ravunana.manda.repository.FluxoDocumentoRepository;
import com.ravunana.manda.repository.ItemCompraRepository;
import com.ravunana.manda.repository.ProdutoRepository;
import com.ravunana.manda.service.dto.ItemCompraDTO;
import com.ravunana.manda.service.mapper.ItemCompraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ItemCompra}.
 */
@Service
@Transactional
public class ItemCompraService {

    private final Logger log = LoggerFactory.getLogger(ItemCompraService.class);

    private final ItemCompraRepository itemCompraRepository;

    private final ItemCompraMapper itemCompraMapper;

    private List<ItemCompraDTO> items = new ArrayList<>();

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FluxoDocumentoRepository fluxoDocumentoRepository;

    @Autowired
    private ProdutoService produtoService;


    public ItemCompraService(ItemCompraRepository itemCompraRepository, ItemCompraMapper itemCompraMapper) {
        this.itemCompraRepository = itemCompraRepository;
        this.itemCompraMapper = itemCompraMapper;
    }

    /**
     * Save a itemCompra.
     *
     * @param itemCompraDTO the entity to save.
     * @return the persisted entity.
     */
    public ItemCompraDTO save(ItemCompraDTO itemCompraDTO) {
        log.debug("Request to save ItemCompra : {}", itemCompraDTO);
        ItemCompra itemCompra = itemCompraMapper.toEntity(itemCompraDTO);
        itemCompra = itemCompraRepository.save(itemCompra);
        Boolean isAumentaEstoque = fluxoDocumentoRepository.findById( itemCompraDTO.getStatusId() ).get().isAumentaEstoque();

        if ( isAumentaEstoque == true ) {
        produtoService.aumentarQuantidadeEstoque(itemCompraDTO.getProdutoId(), itemCompraDTO.getQuantidade());
        }
        return itemCompraMapper.toDto(itemCompra);
    }

    /**
     * Get all the itemCompras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemCompraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemCompras");
        return itemCompraRepository.findAll(pageable)
            .map(itemCompraMapper::toDto);
    }


    /**
     * Get one itemCompra by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemCompraDTO> findOne(Long id) {
        log.debug("Request to get ItemCompra : {}", id);
        return itemCompraRepository.findById(id)
            .map(itemCompraMapper::toDto);
    }

    /**
     * Delete the itemCompra by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemCompra : {}", id);
        itemCompraRepository.deleteById(id);
    }

    public ItemCompraDTO addItem(ItemCompraDTO item) {
        Produto produto = produtoRepository.findById( item.getProdutoId() ).get();
        item.setProdutoNome( produto.getNome() );
        Boolean result = items.add(item);
        if ( result )
            return item;
        else
            return new ItemCompraDTO();
    }

    public ItemCompraDTO deleteItem(int index) {
        return items.remove(index);
    }

    public List<ItemCompraDTO> getItems() {
        return items;
    }

    public void cleanItems() {
        items.clear();
    }
}
