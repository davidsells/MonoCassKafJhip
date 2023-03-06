package com.persist.simple.service;

import com.persist.simple.domain.AudioItem;
import com.persist.simple.repository.AudioItemRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link AudioItem}.
 */
@Service
public class AudioItemService {

    private final Logger log = LoggerFactory.getLogger(AudioItemService.class);

    private final AudioItemRepository audioItemRepository;

    public AudioItemService(AudioItemRepository audioItemRepository) {
        this.audioItemRepository = audioItemRepository;
    }

    /**
     * Save a audioItem.
     *
     * @param audioItem the entity to save.
     * @return the persisted entity.
     */
    public AudioItem save(AudioItem audioItem) {
        log.debug("Request to save AudioItem : {}", audioItem);
        return audioItemRepository.save(audioItem);
    }

    /**
     * Update a audioItem.
     *
     * @param audioItem the entity to save.
     * @return the persisted entity.
     */
    public AudioItem update(AudioItem audioItem) {
        log.debug("Request to update AudioItem : {}", audioItem);
        return audioItemRepository.save(audioItem);
    }

    /**
     * Partially update a audioItem.
     *
     * @param audioItem the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AudioItem> partialUpdate(AudioItem audioItem) {
        log.debug("Request to partially update AudioItem : {}", audioItem);

        return audioItemRepository
            .findById(audioItem.getId())
            .map(existingAudioItem -> {
                if (audioItem.getSlug() != null) {
                    existingAudioItem.setSlug(audioItem.getSlug());
                }
                if (audioItem.getTransaction() != null) {
                    existingAudioItem.setTransaction(audioItem.getTransaction());
                }
                if (audioItem.getItemDescription() != null) {
                    existingAudioItem.setItemDescription(audioItem.getItemDescription());
                }
                if (audioItem.getCategoryName() != null) {
                    existingAudioItem.setCategoryName(audioItem.getCategoryName());
                }
                if (audioItem.getPrice() != null) {
                    existingAudioItem.setPrice(audioItem.getPrice());
                }
                if (audioItem.getLocation() != null) {
                    existingAudioItem.setLocation(audioItem.getLocation());
                }
                if (audioItem.getDateofadd() != null) {
                    existingAudioItem.setDateofadd(audioItem.getDateofadd());
                }
                if (audioItem.getLink() != null) {
                    existingAudioItem.setLink(audioItem.getLink());
                }
                if (audioItem.getImage() != null) {
                    existingAudioItem.setImage(audioItem.getImage());
                }

                return existingAudioItem;
            })
            .map(audioItemRepository::save);
    }

    /**
     * Get all the audioItems.
     *
     * @return the list of entities.
     */
    public List<AudioItem> findAll() {
        log.debug("Request to get all AudioItems");
        return audioItemRepository.findAll();
    }

    /**
     * Get one audioItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<AudioItem> findOne(UUID id) {
        log.debug("Request to get AudioItem : {}", id);
        return audioItemRepository.findById(id);
    }

    /**
     * Delete the audioItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete AudioItem : {}", id);
        audioItemRepository.deleteById(id);
    }
}
