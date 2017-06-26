package com.semanticRelationsExtractor.main;

import com.postagger.main.PosTagger;
import com.postagger.main.PosTaggerImpl;
import com.semanticRelationsExtractor.data.InputData;
import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;
import com.semanticRelationsExtractor.database.DatabaseInserter;
import com.semanticRelationsExtractor.extraction.SemanticRelationsExtractor;
import com.semanticRelationsExtractor.factories.InputDataFactory;
import com.semanticRelationsExtractor.preprocessing.CapitalizedTokensPreprocessor;
import com.semanticRelationsExtractor.preprocessing.SemanticPreprocessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;


/**
 * Created by Oliver on 5/15/2017.
 */
public class SemanticExtractionProcessorImpl implements SemanticExtractionProcessor {

    private InputDataFactory inputDataFactory;

    private CapitalizedTokensPreprocessor capitalizedTokensPreprocessor;

    private PosTagger posTagger;

    private SemanticPreprocessor semanticPreprocessor;

    private SemanticRelationsExtractor semanticRelationsExtractor;

    private DatabaseInserter databaseInserter;

    private int countSemanticallyProcessedSentences = 0;

    private final static Logger LOGGER = Logger.getLogger(SemanticExtractionProcessorImpl.class.getName());

    public SemanticExtractionProcessorImpl(InputDataFactory inputDataFactory,
                                           CapitalizedTokensPreprocessor capitalizedTokensPreprocessor,
                                           SemanticPreprocessor semanticPreprocessor, SemanticRelationsExtractor semanticRelationsExtractor,
                                           DatabaseInserter databaseInserter) {
        this.inputDataFactory = inputDataFactory;
        this.capitalizedTokensPreprocessor = capitalizedTokensPreprocessor;
        this.posTagger = new PosTaggerImpl();
        this.semanticPreprocessor = semanticPreprocessor;
        this.semanticRelationsExtractor = semanticRelationsExtractor;
        this.databaseInserter = databaseInserter;
    }

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_beans.xml");
        SemanticExtractionProcessor semanticExtractionProcessor = (SemanticExtractionProcessor) context.getBean("semanticExtractionProcessor");
        semanticExtractionProcessor.process();
    }


    @Override
    public void process() throws InterruptedException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("C:\\Users\\Oliver\\Documents\\NlpTrainingData\\SemanticExtraction\\WikipediaSemanticExtractionData.txt"));
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String inputDataString = br.readLine();
            while (inputDataString != null) {
                try {
                    String[] split = inputDataString.split("#");
                    String sentence = split[0];
                    String object = split[1];
                    LOGGER.info("Processing sentence: " + sentence);
                    List<List<String>> tagSequencesMultiList = posTagger.tag(sentence);
                    InputData inputData = inputDataFactory.create(sentence, tagSequencesMultiList);
                    capitalizedTokensPreprocessor.process(inputData);
                    if (inputData.containsSubSentences()) {
                        for (int i = 0; i <= inputData.getTokensMultiList().size() - 1; i++) {
                            List<String> tokensList = inputData.getTokensMultiList().get(i);
                            List<String> tagsList = inputData.getTagsMultiList().get(i);
                            processSentence(tokensList, tagsList, sentence, object);
                        }
                    } else {
                        List<String> tagsList = inputData.getTagsList();
                        List<String> tokensList = inputData.getTokensList();
                        processSentence(tokensList, tagsList, sentence, object);
                    }
                    inputDataString = br.readLine();
                } catch (Exception e) {
                    continue;
                }
            }
            LOGGER.info(countSemanticallyProcessedSentences + " sentences were semantically processed.");
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void processSentence(List<String> tokensList, List<String> tagsList, String sentence, String object) {
        SemanticPreprocessingData semanticPreprocessingData = semanticPreprocessor.preprocess(tokensList, tagsList);
        if (semanticPreprocessingData.canGoToExtraction()) {
            countSemanticallyProcessedSentences++;
            SemanticExtractionData semanticExtractionData = semanticRelationsExtractor.extract(semanticPreprocessingData);
            semanticExtractionData.setSentence(sentence);
            semanticExtractionData.setObject(object);
            databaseInserter.insertSemanticData(semanticExtractionData);
        }
    }
}
