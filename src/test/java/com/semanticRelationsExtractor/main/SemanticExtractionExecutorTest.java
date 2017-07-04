package com.semanticRelationsExtractor.main;

import com.postagger.main.PosTagger;
import com.postagger.main.PosTaggerImpl;
import com.semanticRelationsExtractor.database.DatabaseAccessor;
import com.semanticRelationsExtractor.extraction.SemanticRelationsExtractor;
import com.semanticRelationsExtractor.factories.InputDataFactory;
import com.semanticRelationsExtractor.preprocessing.CapitalizedTokensPreprocessor;
import com.semanticRelationsExtractor.preprocessing.SemanticPreprocessor;
import com.semanticRelationsExtractor.tokens.Tokenizer;
import com.semanticRelationsExtractor.tokens.TokenizerImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Oliver on 6/28/2017.
 */
public class SemanticExtractionExecutorTest {

    private InputDataFactory inputDataFactory;

    private CapitalizedTokensPreprocessor capitalizedTokensPreprocessor;

    private SemanticPreprocessor semanticPreprocessor;

    private SemanticRelationsExtractor semanticRelationsExtractor;

    private DatabaseAccessor databaseAccessor;

    private Tokenizer tokenizer;

    private SemanticExtractionExecutor semanticExtractionExecutor;

    private PosTagger posTagger = new PosTaggerImpl();

    private String executorPath = "C:\\Users\\Oliver\\Documents\\NlpTrainingData\\SemanticExtraction\\SemanticExtractionTestData.txt";

    @Before
    public void setup() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_beans.xml");
        InputDataFactory inputDataFactory = (InputDataFactory) context.getBean("inputDataFactory");
        CapitalizedTokensPreprocessor capitalizedTokensPreprocessor = (CapitalizedTokensPreprocessor) context.getBean("capitalizedTokensPreprocessor");
        SemanticPreprocessor semanticPreprocessor = (SemanticPreprocessor) context.getBean("semanticPreprocessor");
        SemanticRelationsExtractor semanticRelationsExtractor = (SemanticRelationsExtractor) context.getBean("semanticRelationsExtractor");
        tokenizer = new TokenizerImpl();
        databaseAccessor = Mockito.mock(DatabaseAccessor.class);
        semanticExtractionExecutor = new SemanticExtractionExecutorImpl(inputDataFactory, capitalizedTokensPreprocessor,
                posTagger, semanticPreprocessor, semanticRelationsExtractor, databaseAccessor, tokenizer, executorPath);
    }

    @Test
    public void test1() {
        long startTime = System.currentTimeMillis();
        semanticExtractionExecutor.execute();
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("sentence processed " + "in " + (elapsedTime / 1000) / 60 + " minutes and "
                + (elapsedTime / 1000) % 60 + " seconds");
    }

}
