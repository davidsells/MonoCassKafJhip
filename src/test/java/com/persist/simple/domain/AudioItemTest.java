package com.persist.simple.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.persist.simple.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AudioItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AudioItem.class);
        AudioItem audioItem1 = new AudioItem();
        audioItem1.setId(UUID.randomUUID());
        AudioItem audioItem2 = new AudioItem();
        audioItem2.setId(audioItem1.getId());
        assertThat(audioItem1).isEqualTo(audioItem2);
        audioItem2.setId(UUID.randomUUID());
        assertThat(audioItem1).isNotEqualTo(audioItem2);
        audioItem1.setId(null);
        assertThat(audioItem1).isNotEqualTo(audioItem2);
    }
}
