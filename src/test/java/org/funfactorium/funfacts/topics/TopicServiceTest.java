package org.funfactorium.funfacts.topics;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TopicServiceTest {

    private static TopicService testService;
    private static TopicRepository mockRepository;
    private static List<Topic> mockTopicList;

    @BeforeAll
    public static void setUp() {
        mockRepository = mock(TopicRepository.class);
        mockTopicList = new ArrayList<>();
        Topic mockTopic1 = mock(Topic.class);
        Topic mockTopic2 = mock(Topic.class);
        mockTopicList.add(mockTopic1);
        mockTopicList.add(mockTopic2);
        testService = new TopicService(mockRepository);
    }

    @Test
    public void testFindAllWorksCorrectly() {
        when(mockRepository.findAll()).thenReturn(mockTopicList);
        List<Topic> testList = testService.allTopics();
        assertEquals(2, testList.size());
    }

    @Test
    public void testFindAllCanReturnEmptyList() {
        when(mockRepository.findAll()).thenReturn(new ArrayList<>());
        List<Topic> testList = testService.allTopics();
        assertEquals(0, testList.size());
    }

}