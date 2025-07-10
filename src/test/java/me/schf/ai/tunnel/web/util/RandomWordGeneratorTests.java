package me.schf.ai.tunnel.web.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class RandomWordGeneratorTests {
	
	@Test
	void test_randomStartingPoints_hasSize5() {
		List<String> randomWords = RandomWordGenerator.randomStartingPoints();
		assertThat(randomWords).hasSize(5);
	}
	
	@RepeatedTest(100)
    void test_randomStartingPoints_isRandom() {
        List<String> randomWords = RandomWordGenerator.randomStartingPoints();

        Set<String> uniqueWords = new HashSet<>(randomWords);
        assertThat(uniqueWords.size()).isEqualTo(randomWords.size());
    }

}
