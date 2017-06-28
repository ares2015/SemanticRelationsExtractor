package com.semanticRelationsExtractor.main;

import com.postagger.main.PosTagger;
import com.semanticRelationsExtractor.data.InputData;
import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;
import com.semanticRelationsExtractor.database.DatabaseInserter;
import com.semanticRelationsExtractor.extraction.SemanticRelationsExtractor;
import com.semanticRelationsExtractor.factories.InputDataFactory;
import com.semanticRelationsExtractor.preprocessing.CapitalizedTokensPreprocessor;
import com.semanticRelationsExtractor.preprocessing.SemanticPreprocessor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * Created by Oliver on 6/27/2017.
 */
public class SemanticExtractionExecutorImpl implements SemanticExtractionExecutor, Callable {

    private final static Logger LOGGER = Logger.getLogger(SemanticExtractionProcessorImpl.class.getName());

    private InputDataFactory inputDataFactory;

    private CapitalizedTokensPreprocessor capitalizedTokensPreprocessor;

    private PosTagger posTagger;

    private SemanticPreprocessor semanticPreprocessor;

    private SemanticRelationsExtractor semanticRelationsExtractor;

    private DatabaseInserter databaseInserter;

    private String path;

    private Integer countSemanticallyProcessedSentences = 0;

    public SemanticExtractionExecutorImpl(InputDataFactory inputDataFactory,
                                          CapitalizedTokensPreprocessor capitalizedTokensPreprocessor, PosTagger posTagger,
                                          SemanticPreprocessor semanticPreprocessor, SemanticRelationsExtractor semanticRelationsExtractor,
                                          DatabaseInserter databaseInserter, String path) {
        this.inputDataFactory = inputDataFactory;
        this.capitalizedTokensPreprocessor = capitalizedTokensPreprocessor;
        this.posTagger = posTagger;
        this.semanticPreprocessor = semanticPreprocessor;
        this.semanticRelationsExtractor = semanticRelationsExtractor;
        this.databaseInserter = databaseInserter;
        this.path = path;
    }

    @Override
    public Object call() throws Exception {
        execute();
        return countSemanticallyProcessedSentences;
    }

    @Override
    public void execute() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            int nrOfProcessedSentences = 0;
            String inputDataString = br.readLine();
            while (inputDataString != null) {
                try {
                    nrOfProcessedSentences++;
                    String[] split = inputDataString.split("#");
                    String sentence = split[0];
                    String object = split[1];
                    System.out.println("Processing sentence: " + sentence);
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
                    System.out.println(nrOfProcessedSentences + " were sentences read and processed");
                    inputDataString = br.readLine();
                } catch (Exception e) {
//                    System.out.println(e.toString());
                    inputDataString = br.readLine();
                    continue;
                }
            }
            System.out.println(countSemanticallyProcessedSentences + " sentences were semantically processed.");
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
