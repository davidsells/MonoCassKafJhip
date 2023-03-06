package com.persist.simple.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.persist.simple.IntegrationTest;
import com.persist.simple.domain.AudioItem;
import com.persist.simple.repository.AudioItemRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link AudioItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AudioItemResourceIT {

    private static final String DEFAULT_SLUG = "AAAAAAAAAA";
    private static final String UPDATED_SLUG = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_PRICE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_DATEOFADD = "AAAAAAAAAA";
    private static final String UPDATED_DATEOFADD = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/audio-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private AudioItemRepository audioItemRepository;

    @Autowired
    private MockMvc restAudioItemMockMvc;

    private AudioItem audioItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AudioItem createEntity() {
        AudioItem audioItem = new AudioItem()
            .slug(DEFAULT_SLUG)
            .transaction(DEFAULT_TRANSACTION)
            .itemDescription(DEFAULT_ITEM_DESCRIPTION)
            .categoryName(DEFAULT_CATEGORY_NAME)
            .price(DEFAULT_PRICE)
            .location(DEFAULT_LOCATION)
            .dateofadd(DEFAULT_DATEOFADD)
            .link(DEFAULT_LINK)
            .image(DEFAULT_IMAGE);
        return audioItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AudioItem createUpdatedEntity() {
        AudioItem audioItem = new AudioItem()
            .slug(UPDATED_SLUG)
            .transaction(UPDATED_TRANSACTION)
            .itemDescription(UPDATED_ITEM_DESCRIPTION)
            .categoryName(UPDATED_CATEGORY_NAME)
            .price(UPDATED_PRICE)
            .location(UPDATED_LOCATION)
            .dateofadd(UPDATED_DATEOFADD)
            .link(UPDATED_LINK)
            .image(UPDATED_IMAGE);
        return audioItem;
    }

    @BeforeEach
    public void initTest() {
        audioItemRepository.deleteAll();
        audioItem = createEntity();
    }

    @Test
    void createAudioItem() throws Exception {
        int databaseSizeBeforeCreate = audioItemRepository.findAll().size();
        // Create the AudioItem
        restAudioItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(audioItem)))
            .andExpect(status().isCreated());

        // Validate the AudioItem in the database
        List<AudioItem> audioItemList = audioItemRepository.findAll();
        assertThat(audioItemList).hasSize(databaseSizeBeforeCreate + 1);
        AudioItem testAudioItem = audioItemList.get(audioItemList.size() - 1);
        assertThat(testAudioItem.getSlug()).isEqualTo(DEFAULT_SLUG);
        assertThat(testAudioItem.getTransaction()).isEqualTo(DEFAULT_TRANSACTION);
        assertThat(testAudioItem.getItemDescription()).isEqualTo(DEFAULT_ITEM_DESCRIPTION);
        assertThat(testAudioItem.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testAudioItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testAudioItem.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testAudioItem.getDateofadd()).isEqualTo(DEFAULT_DATEOFADD);
        assertThat(testAudioItem.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testAudioItem.getImage()).isEqualTo(DEFAULT_IMAGE);
    }

    @Test
    void createAudioItemWithExistingId() throws Exception {
        // Create the AudioItem with an existing ID
        audioItem.setId(UUID.randomUUID());

        int databaseSizeBeforeCreate = audioItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAudioItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(audioItem)))
            .andExpect(status().isBadRequest());

        // Validate the AudioItem in the database
        List<AudioItem> audioItemList = audioItemRepository.findAll();
        assertThat(audioItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllAudioItems() throws Exception {
        // Initialize the database
        audioItem.setId(UUID.randomUUID());
        audioItemRepository.save(audioItem);

        // Get all the audioItemList
        restAudioItemMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(audioItem.getId().toString())))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG)))
            .andExpect(jsonPath("$.[*].transaction").value(hasItem(DEFAULT_TRANSACTION)))
            .andExpect(jsonPath("$.[*].itemDescription").value(hasItem(DEFAULT_ITEM_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].dateofadd").value(hasItem(DEFAULT_DATEOFADD)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)));
    }

    @Test
    void getAudioItem() throws Exception {
        // Initialize the database
        audioItem.setId(UUID.randomUUID());
        audioItemRepository.save(audioItem);

        // Get the audioItem
        restAudioItemMockMvc
            .perform(get(ENTITY_API_URL_ID, audioItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(audioItem.getId().toString()))
            .andExpect(jsonPath("$.slug").value(DEFAULT_SLUG))
            .andExpect(jsonPath("$.transaction").value(DEFAULT_TRANSACTION))
            .andExpect(jsonPath("$.itemDescription").value(DEFAULT_ITEM_DESCRIPTION))
            .andExpect(jsonPath("$.categoryName").value(DEFAULT_CATEGORY_NAME))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.dateofadd").value(DEFAULT_DATEOFADD))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE));
    }

    @Test
    void getNonExistingAudioItem() throws Exception {
        // Get the audioItem
        restAudioItemMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingAudioItem() throws Exception {
        // Initialize the database
        audioItem.setId(UUID.randomUUID());
        audioItemRepository.save(audioItem);

        int databaseSizeBeforeUpdate = audioItemRepository.findAll().size();

        // Update the audioItem
        AudioItem updatedAudioItem = audioItemRepository.findById(audioItem.getId()).get();
        updatedAudioItem
            .slug(UPDATED_SLUG)
            .transaction(UPDATED_TRANSACTION)
            .itemDescription(UPDATED_ITEM_DESCRIPTION)
            .categoryName(UPDATED_CATEGORY_NAME)
            .price(UPDATED_PRICE)
            .location(UPDATED_LOCATION)
            .dateofadd(UPDATED_DATEOFADD)
            .link(UPDATED_LINK)
            .image(UPDATED_IMAGE);

        restAudioItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAudioItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAudioItem))
            )
            .andExpect(status().isOk());

        // Validate the AudioItem in the database
        List<AudioItem> audioItemList = audioItemRepository.findAll();
        assertThat(audioItemList).hasSize(databaseSizeBeforeUpdate);
        AudioItem testAudioItem = audioItemList.get(audioItemList.size() - 1);
        assertThat(testAudioItem.getSlug()).isEqualTo(UPDATED_SLUG);
        assertThat(testAudioItem.getTransaction()).isEqualTo(UPDATED_TRANSACTION);
        assertThat(testAudioItem.getItemDescription()).isEqualTo(UPDATED_ITEM_DESCRIPTION);
        assertThat(testAudioItem.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testAudioItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testAudioItem.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testAudioItem.getDateofadd()).isEqualTo(UPDATED_DATEOFADD);
        assertThat(testAudioItem.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testAudioItem.getImage()).isEqualTo(UPDATED_IMAGE);
    }

    @Test
    void putNonExistingAudioItem() throws Exception {
        int databaseSizeBeforeUpdate = audioItemRepository.findAll().size();
        audioItem.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAudioItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, audioItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(audioItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the AudioItem in the database
        List<AudioItem> audioItemList = audioItemRepository.findAll();
        assertThat(audioItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAudioItem() throws Exception {
        int databaseSizeBeforeUpdate = audioItemRepository.findAll().size();
        audioItem.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAudioItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(audioItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the AudioItem in the database
        List<AudioItem> audioItemList = audioItemRepository.findAll();
        assertThat(audioItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAudioItem() throws Exception {
        int databaseSizeBeforeUpdate = audioItemRepository.findAll().size();
        audioItem.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAudioItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(audioItem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AudioItem in the database
        List<AudioItem> audioItemList = audioItemRepository.findAll();
        assertThat(audioItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAudioItemWithPatch() throws Exception {
        // Initialize the database
        audioItem.setId(UUID.randomUUID());
        audioItemRepository.save(audioItem);

        int databaseSizeBeforeUpdate = audioItemRepository.findAll().size();

        // Update the audioItem using partial update
        AudioItem partialUpdatedAudioItem = new AudioItem();
        partialUpdatedAudioItem.setId(audioItem.getId());

        partialUpdatedAudioItem.price(UPDATED_PRICE);

        restAudioItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAudioItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAudioItem))
            )
            .andExpect(status().isOk());

        // Validate the AudioItem in the database
        List<AudioItem> audioItemList = audioItemRepository.findAll();
        assertThat(audioItemList).hasSize(databaseSizeBeforeUpdate);
        AudioItem testAudioItem = audioItemList.get(audioItemList.size() - 1);
        assertThat(testAudioItem.getSlug()).isEqualTo(DEFAULT_SLUG);
        assertThat(testAudioItem.getTransaction()).isEqualTo(DEFAULT_TRANSACTION);
        assertThat(testAudioItem.getItemDescription()).isEqualTo(DEFAULT_ITEM_DESCRIPTION);
        assertThat(testAudioItem.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testAudioItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testAudioItem.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testAudioItem.getDateofadd()).isEqualTo(DEFAULT_DATEOFADD);
        assertThat(testAudioItem.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testAudioItem.getImage()).isEqualTo(DEFAULT_IMAGE);
    }

    @Test
    void fullUpdateAudioItemWithPatch() throws Exception {
        // Initialize the database
        audioItem.setId(UUID.randomUUID());
        audioItemRepository.save(audioItem);

        int databaseSizeBeforeUpdate = audioItemRepository.findAll().size();

        // Update the audioItem using partial update
        AudioItem partialUpdatedAudioItem = new AudioItem();
        partialUpdatedAudioItem.setId(audioItem.getId());

        partialUpdatedAudioItem
            .slug(UPDATED_SLUG)
            .transaction(UPDATED_TRANSACTION)
            .itemDescription(UPDATED_ITEM_DESCRIPTION)
            .categoryName(UPDATED_CATEGORY_NAME)
            .price(UPDATED_PRICE)
            .location(UPDATED_LOCATION)
            .dateofadd(UPDATED_DATEOFADD)
            .link(UPDATED_LINK)
            .image(UPDATED_IMAGE);

        restAudioItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAudioItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAudioItem))
            )
            .andExpect(status().isOk());

        // Validate the AudioItem in the database
        List<AudioItem> audioItemList = audioItemRepository.findAll();
        assertThat(audioItemList).hasSize(databaseSizeBeforeUpdate);
        AudioItem testAudioItem = audioItemList.get(audioItemList.size() - 1);
        assertThat(testAudioItem.getSlug()).isEqualTo(UPDATED_SLUG);
        assertThat(testAudioItem.getTransaction()).isEqualTo(UPDATED_TRANSACTION);
        assertThat(testAudioItem.getItemDescription()).isEqualTo(UPDATED_ITEM_DESCRIPTION);
        assertThat(testAudioItem.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testAudioItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testAudioItem.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testAudioItem.getDateofadd()).isEqualTo(UPDATED_DATEOFADD);
        assertThat(testAudioItem.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testAudioItem.getImage()).isEqualTo(UPDATED_IMAGE);
    }

    @Test
    void patchNonExistingAudioItem() throws Exception {
        int databaseSizeBeforeUpdate = audioItemRepository.findAll().size();
        audioItem.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAudioItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, audioItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(audioItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the AudioItem in the database
        List<AudioItem> audioItemList = audioItemRepository.findAll();
        assertThat(audioItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAudioItem() throws Exception {
        int databaseSizeBeforeUpdate = audioItemRepository.findAll().size();
        audioItem.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAudioItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(audioItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the AudioItem in the database
        List<AudioItem> audioItemList = audioItemRepository.findAll();
        assertThat(audioItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAudioItem() throws Exception {
        int databaseSizeBeforeUpdate = audioItemRepository.findAll().size();
        audioItem.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAudioItemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(audioItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AudioItem in the database
        List<AudioItem> audioItemList = audioItemRepository.findAll();
        assertThat(audioItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAudioItem() throws Exception {
        // Initialize the database
        audioItem.setId(UUID.randomUUID());
        audioItemRepository.save(audioItem);

        int databaseSizeBeforeDelete = audioItemRepository.findAll().size();

        // Delete the audioItem
        restAudioItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, audioItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AudioItem> audioItemList = audioItemRepository.findAll();
        assertThat(audioItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
