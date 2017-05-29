package com.semanticRelationsExtractor.extraction.predicate.noun;


import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;
import com.semanticRelationsExtractor.tags.Tags;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Oliver on 2/16/2017.
 */
public class NounPredicateExtractorImpl implements NounPredicateExtractor {

    private final static Logger LOGGER = Logger.getLogger(NounPredicateExtractorImpl.class.getName());


    @Override
    public void extract(SemanticExtractionData semanticExtractionData, SemanticPreprocessingData semanticPreprocessingData) {
        List<String> tokensList = semanticPreprocessingData.getTokensList();
        List<String> tagsList = semanticPreprocessingData.getTagsList();
        int extractionStartIndex = getExtractionStartIndex(semanticPreprocessingData);
        int afterVerbPrepositionIndex = semanticPreprocessingData.getAfterVerbFirstPrepositionIndex();
        if (!semanticPreprocessingData.containsAfterVerbVerbIng()) {
            String atomicNounPredicate = extractAtomicNounPredicate(tokensList, tagsList, extractionStartIndex, afterVerbPrepositionIndex);
            semanticExtractionData.setAtomicNounPredicate(atomicNounPredicate);
            LOGGER.info("Atomic noun predicate: " + atomicNounPredicate);
        }
        String extendedNounPredicate = extractExtendedNounPredicate(tokensList, tagsList, extractionStartIndex);
        semanticExtractionData.setExtendedNounPredicate(extendedNounPredicate);
        LOGGER.info("Extended noun predicate: " + extendedNounPredicate);
    }

    private String extractAtomicNounPredicate(List<String> tokensList, List<String> encodedTagsList,
                                              int extractionStartIndex, int afterVerbPrepositionIndex) {
        int lastNounIndex = getLastNounVerbEdIndex(encodedTagsList, extractionStartIndex, afterVerbPrepositionIndex);
        if (lastNounIndex > -1) {
            return tokensList.get(lastNounIndex);
        } else {
            return "";
        }
    }

    private int getLastNounVerbEdIndex(List<String> encodedTagsList, int extractionStartIndex, int afterVerbPrepositionIndex) {
        int lastNounIndex = -1;
        if (afterVerbPrepositionIndex > -1) {
            for (int i = extractionStartIndex; i <= afterVerbPrepositionIndex; i++) {
                if (Tags.NOUN.equals(encodedTagsList.get(i)) || Tags.VERB_ED.equals(encodedTagsList.get(i))) {
                    lastNounIndex = i;
                }
            }
        } else {
            for (int i = extractionStartIndex + 1; i < encodedTagsList.size(); i++) {
                if (Tags.NOUN.equals(encodedTagsList.get(i))) {
                    lastNounIndex = i;
                }
            }
        }
        return lastNounIndex;
    }

    private String extractExtendedNounPredicate(List<String> tokensList, List<String> tagsList, int verbIndex) {
        int startIndex = verbIndex + 1;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = startIndex; i < tokensList.size(); i++) {
            if (i == tokensList.size() - 1 && (Tags.PREPOSITION.equals(tagsList.get(i)) || Tags.CONJUNCTION.equals(tagsList.get(i)))) {
                break;
            }
            stringBuilder.append(tokensList.get(i));
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    private int getExtractionStartIndex(SemanticPreprocessingData semanticPreprocessingData) {
        if (semanticPreprocessingData.getHaveBeenSequenceEndIndex() > -1) {
            return semanticPreprocessingData.getHaveBeenSequenceEndIndex() - 1;
        } else if (semanticPreprocessingData.getHaveVerbEdSequenceEndIndex() > -1) {
            return semanticPreprocessingData.getHaveVerbEdSequenceEndIndex() - 1;
        } else if (semanticPreprocessingData.getDoVerbSequenceEndIndex() > -1) {
            return semanticPreprocessingData.getDoVerbSequenceEndIndex() - 1;
        } else {
            return semanticPreprocessingData.getVerbIndex();
        }
    }

}
