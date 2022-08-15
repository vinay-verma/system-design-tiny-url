package com.zemoso.systemdesignbootcamp.keygenerator.service.impl;

import com.zemoso.systemdesignbootcamp.keygenerator.entity.Key;
import com.zemoso.systemdesignbootcamp.keygenerator.generators.KeyGenerator;
import com.zemoso.systemdesignbootcamp.keygenerator.generators.TwitterSnowflakeKeyGenerator;
import com.zemoso.systemdesignbootcamp.keygenerator.repository.KeyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KeyGeneratorServiceImplTest {

    @Mock
    private KeyRepository keyRepository;

    private KeyGeneratorServiceImpl keyGeneratorService;

    private ExecutorService executorService;

    @BeforeEach
    public void setUp() {
        executorService = Executors.newFixedThreadPool(5);
        KeyGenerator keyGenerator = new TwitterSnowflakeKeyGenerator(TwitterSnowflakeKeyGenerator.TwitterSnowflakeKeyGeneratorConfig
                .builder()
                .withNodeId(1001)
                .build());
        keyGeneratorService = new KeyGeneratorServiceImpl(keyGenerator, keyRepository, executorService);
    }

    @AfterEach
    public void cleanUp() {
        executorService.shutdown();
    }

    @Test
    public void shouldReplenishIfNoUnusedKeyAvailable() throws ExecutionException, InterruptedException {
        ReflectionTestUtils.setField(keyGeneratorService, "keyReplenishThreshold", 200);
        when(keyRepository.countByUsedFalse()).thenReturn(0L);
        when(keyRepository.save(any(Key.class))).thenAnswer(answer -> answer.getArgument(0));

        CompletableFuture<Collection<String>> future = keyGeneratorService.replenishMoreIfRequired();
        Collection<String> generatedKeys = future.get();

        assertNotNull(generatedKeys);
        assertEquals(200, generatedKeys.size());
        verify(keyRepository, times(200)).save(any(Key.class));
    }

}
