package com.semanticRelationsExtractor;

import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;
import com.semanticRelationsExtractor.preprocessing.SemanticPreprocessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oliver on 5/15/2017.
 */
public class SemanticAnalyserImpl implements SemanticAnalyser {

    private SemanticPreprocessor semanticPreprocessor;

    private SemanticExtractor semanticExtractor;

    private SemanticsWriter semanticsWriter;

    private List<TrainingDataRow> trainingDataRowList;

    private List<SemanticExtractionData> semanticExtractionDataList = new ArrayList<>();

    public SemanticAnalyserImpl(SemanticPreprocessor semanticPreprocessor, SemanticExtractor semanticExtractor,
                                SemanticsWriter semanticsWriter, List<TrainingDataRow> trainingDataRowList) {
        this.semanticPreprocessor = semanticPreprocessor;
        this.semanticExtractor = semanticExtractor;
        this.semanticsWriter = semanticsWriter;
        this.trainingDataRowList = trainingDataRowList;
    }

    @Override
    public void run() {
        analyse();
    }

    @Override
    public void analyse() {
        for (TrainingDataRow trainingDataRow : trainingDataRowList) {
            if (trainingDataRow.containsSubSentences()) {
                for (int i = 0; i <= trainingDataRow.getTokensMultiList().size() - 1; i++) {
                    List<String> tokensList = trainingDataRow.getTokensMultiList().get(i);
                    List<String> tagsList = trainingDataRow.getTagsMultiList().get(i);
                    analyseSentence(tokensList, tagsList);
                }
            } else {
                List<String> tagsList = trainingDataRow.getTagsList();
                List<String> tokensList = trainingDataRow.getTokensList();
                analyseSentence(tokensList, tagsList);
            }
        }
        semanticsWriter.write(semanticExtractionDataList);
    }

    private void analyseSentence(List<String> tokensList, List<String> tagsList) {
        SemanticPreprocessingData semanticPreprocessingData = semanticPreprocessor.preprocess(tokensList, tagsList);
        if (semanticPreprocessingData.canGoToExtraction()) {
            SemanticExtractionData semanticExtractionData = semanticExtractor.extract(semanticPreprocessingData);
            semanticExtractionDataList.add(semanticExtractionData);
        }
    }
}
