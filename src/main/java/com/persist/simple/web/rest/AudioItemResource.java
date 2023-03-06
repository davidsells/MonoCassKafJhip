package com.persist.simple.web.rest;

import com.persist.simple.domain.AudioItem;
import com.persist.simple.repository.AudioItemRepository;
import com.persist.simple.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.persist.simple.domain.AudioItem}.
 */
@RestController
@RequestMapping("/api")
public class AudioItemResource {

    private final Logger log = LoggerFactory.getLogger(AudioItemResource.class);

    private static final String ENTITY_NAME = "audioItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AudioItemRepository audioItemRepository;

    public AudioItemResource(AudioItemRepository audioItemRepository) {
        this.audioItemRepository = audioItemRepository;
    }

    /**
     * {@code POST  /audio-items} : Create a new audioItem.
     *
     * @param audioItem the audioItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new audioItem, or with status {@code 400 (Bad Request)} if the audioItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/audio-items")
    public ResponseEntity<AudioItem> createAudioItem(@RequestBody AudioItem audioItem) throws URISyntaxException {
        log.debug("REST request to save AudioItem : {}", audioItem);
        if (audioItem.getId() != null) {
            throw new BadRequestAlertException("A new audioItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        audioItem.setId(UUID.randomUUID());
        AudioItem result = audioItemRepository.save(audioItem);
        return ResponseEntity
            .created(new URI("/api/audio-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /audio-items/:id} : Updates an existing audioItem.
     *
     * @param id the id of the audioItem to save.
     * @param audioItem the audioItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated audioItem,
     * or with status {@code 400 (Bad Request)} if the audioItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the audioItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/audio-items/{id}")
    public ResponseEntity<AudioItem> updateAudioItem(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody AudioItem audioItem
    ) throws URISyntaxException {
        log.debug("REST request to update AudioItem : {}, {}", id, audioItem);
        if (audioItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, audioItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!audioItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AudioItem result = audioItemRepository.save(audioItem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, audioItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /audio-items/:id} : Partial updates given fields of an existing audioItem, field will ignore if it is null
     *
     * @param id the id of the audioItem to save.
     * @param audioItem the audioItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated audioItem,
     * or with status {@code 400 (Bad Request)} if the audioItem is not valid,
     * or with status {@code 404 (Not Found)} if the audioItem is not found,
     * or with status {@code 500 (Internal Server Error)} if the audioItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/audio-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AudioItem> partialUpdateAudioItem(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody AudioItem audioItem
    ) throws URISyntaxException {
        log.debug("REST request to partial update AudioItem partially : {}, {}", id, audioItem);
        if (audioItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, audioItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!audioItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AudioItem> result = audioItemRepository
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

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, audioItem.getId().toString())
        );
    }

    /**
     * {@code GET  /audio-items} : get all the audioItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of audioItems in body.
     */
    @GetMapping("/audio-items")
    public List<AudioItem> getAllAudioItems() {
        log.debug("REST request to get all AudioItems");
        return audioItemRepository.findAll();
    }

    /**
     * {@code GET  /audio-items/:id} : get the "id" audioItem.
     *
     * @param id the id of the audioItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the audioItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/audio-items/{id}")
    public ResponseEntity<AudioItem> getAudioItem(@PathVariable UUID id) {
        log.debug("REST request to get AudioItem : {}", id);
        Optional<AudioItem> audioItem = audioItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(audioItem);
    }

    /**
     * {@code DELETE  /audio-items/:id} : delete the "id" audioItem.
     *
     * @param id the id of the audioItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/audio-items/{id}")
    public ResponseEntity<Void> deleteAudioItem(@PathVariable UUID id) {
        log.debug("REST request to delete AudioItem : {}", id);
        audioItemRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
