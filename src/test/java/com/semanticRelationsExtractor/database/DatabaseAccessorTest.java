package com.semanticRelationsExtractor.database;

import com.semanticRelationsExtractor.data.SemanticExtractionData;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Oliver on 7/4/2017.
 */
public class DatabaseAccessorTest {

    private ApplicationContext context = new ClassPathXmlApplicationContext("spring_beans.xml");

    private DatabaseAccessor databaseAccessor = (DatabaseAccessor) context.getBean("databaseAccessor");

    @Test
    public void testExistsSemanticDataInDatabase() {
        SemanticExtractionData semanticExtractionData = new SemanticExtractionData();
        semanticExtractionData.setAtomicSubject("dog");
        semanticExtractionData.setAtomicVerbPredicate("is");
        semanticExtractionData.setAtomicNounPredicate("animal");
        databaseAccessor.existsSemanticDataInDatabase(semanticExtractionData);
    }
}
