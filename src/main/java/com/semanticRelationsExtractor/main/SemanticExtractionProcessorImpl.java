package com.semanticRelationsExtractor.main;

import com.postagger.main.PosTagger;
import com.postagger.main.PosTaggerImpl;
import com.semanticRelationsExtractor.database.DatabaseAccessor;
import com.semanticRelationsExtractor.extraction.SemanticRelationsExtractor;
import com.semanticRelationsExtractor.factories.InputDataFactory;
import com.semanticRelationsExtractor.preprocessing.CapitalizedTokensPreprocessor;
import com.semanticRelationsExtractor.preprocessing.SemanticPreprocessor;
import com.semanticRelationsExtractor.tokens.Tokenizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.*;
import java.util.logging.Logger;


/**
 * Created by Oliver on 5/15/2017.
 */
public class SemanticExtractionProcessorImpl implements SemanticExtractionProcessor {

    private InputDataFactory inputDataFactory;

    private PosTagger posTagger = new PosTaggerImpl();

    private CapitalizedTokensPreprocessor capitalizedTokensPreprocessor;

    private SemanticPreprocessor semanticPreprocessor;

    private SemanticRelationsExtractor semanticRelationsExtractor;

    private DatabaseAccessor databaseAccessor;

    private Tokenizer tokenizer;

    private String executor1Path = "C:\\Users\\Oliver\\Documents\\NlpTrainingData\\SemanticExtraction\\WikipediaSemanticExtractionData1.txt";

    private String executor2Path = "C:\\Users\\Oliver\\Documents\\NlpTrainingData\\SemanticExtraction\\WikipediaSemanticExtractionData2.txt";

    private String executor3Path = "C:\\Users\\Oliver\\Documents\\NlpTrainingData\\SemanticExtraction\\WikipediaSemanticExtractionData3.txt";

    private String executor4Path = "C:\\Users\\Oliver\\Documents\\NlpTrainingData\\SemanticExtraction\\WikipediaSemanticExtractionData4.txt";

    private final static Logger LOGGER = Logger.getLogger(SemanticExtractionProcessorImpl.class.getName());

    public SemanticExtractionProcessorImpl(InputDataFactory inputDataFactory,
                                           CapitalizedTokensPreprocessor capitalizedTokensPreprocessor,
                                           SemanticPreprocessor semanticPreprocessor, SemanticRelationsExtractor semanticRelationsExtractor,
                                           DatabaseAccessor databaseAccessor, Tokenizer tokenizer) {
        this.inputDataFactory = inputDataFactory;
        this.capitalizedTokensPreprocessor = capitalizedTokensPreprocessor;
        this.semanticPreprocessor = semanticPreprocessor;
        this.semanticRelationsExtractor = semanticRelationsExtractor;
        this.databaseAccessor = databaseAccessor;
        this.tokenizer = tokenizer;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, FileNotFoundException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_beans.xml");
        SemanticExtractionProcessor semanticExtractionProcessor = (SemanticExtractionProcessor) context.getBean("semanticExtractionProcessor");
        semanticExtractionProcessor.process();
    }


    @Override
    public void process() throws InterruptedException, ExecutionException, FileNotFoundException {
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(4);

//        SemanticExtractionExecutor semanticExtractionExecutor1 = new SemanticExtractionExecutorImpl(inputDataFactory, capitalizedTokensPreprocessor,
//                posTagger, semanticPreprocessor, semanticRelationsExtractor, databaseAccessor, tokenizer, executor1Path);
//        semanticExtractionExecutor1.execute();
//
//        SemanticExtractionExecutor semanticExtractionExecutor2 = new SemanticExtractionExecutorImpl(inputDataFactory, capitalizedTokensPreprocessor,
//                posTagger, semanticPreprocessor, semanticRelationsExtractor, databaseAccessor, tokenizer, executor2Path);
//        semanticExtractionExecutor2.execute();
//
//        SemanticExtractionExecutor semanticExtractionExecutor3 = new SemanticExtractionExecutorImpl(inputDataFactory, capitalizedTokensPreprocessor,
//                posTagger, semanticPreprocessor, semanticRelationsExtractor, databaseAccessor, tokenizer, executor3Path);
//        semanticExtractionExecutor3.execute();
//
//        SemanticExtractionExecutor semanticExtractionExecutor4 = new SemanticExtractionExecutorImpl(inputDataFactory, capitalizedTokensPreprocessor,
//                posTagger, semanticPreprocessor, semanticRelationsExtractor, databaseAccessor, tokenizer, executor4Path);
//        semanticExtractionExecutor4.execute();

        Callable semanticExtractionExecutor1 = new SemanticExtractionExecutorImpl(inputDataFactory, capitalizedTokensPreprocessor,
                posTagger, semanticPreprocessor, semanticRelationsExtractor, databaseAccessor, tokenizer, executor1Path);

        Callable semanticExtractionExecutor2 = new SemanticExtractionExecutorImpl(inputDataFactory, capitalizedTokensPreprocessor,
                posTagger, semanticPreprocessor, semanticRelationsExtractor, databaseAccessor, tokenizer, executor2Path);

        Callable semanticExtractionExecutor3 = new SemanticExtractionExecutorImpl(inputDataFactory, capitalizedTokensPreprocessor,
                posTagger, semanticPreprocessor, semanticRelationsExtractor, databaseAccessor, tokenizer, executor3Path);

        Callable semanticExtractionExecutor4 = new SemanticExtractionExecutorImpl(inputDataFactory, capitalizedTokensPreprocessor,
                posTagger, semanticPreprocessor, semanticRelationsExtractor, databaseAccessor, tokenizer, executor4Path);

        Future future1 = executor.submit(semanticExtractionExecutor1);
        Future future2 = executor.submit(semanticExtractionExecutor2);
        Future future3 = executor.submit(semanticExtractionExecutor3);
        Future future4 = executor.submit(semanticExtractionExecutor4);

        boolean areDataProcessed = false;
        while (!areDataProcessed) {
            areDataProcessed = !future1.isDone() && !future2.isDone() && !future3.isDone() && !future4.isDone();
        }

        if (areDataProcessed) {
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            int numberOfProcessedSentences = (Integer) future1.get() + (Integer) future2.get() + (Integer) future3.get() + (Integer) future4.get();
            LOGGER.info(numberOfProcessedSentences + " sentences were processed " + "in " + (elapsedTime / 1000) / 60 + " minutes and "
                    + (elapsedTime / 1000) % 60 + " seconds");

            PrintWriter pw1 = new PrintWriter(executor1Path);
            pw1.close();
            PrintWriter pw2 = new PrintWriter(executor2Path);
            pw2.close();
            PrintWriter pw3 = new PrintWriter(executor3Path);
            pw3.close();
            PrintWriter pw4 = new PrintWriter(executor4Path);
            pw4.close();
        }
    }

}