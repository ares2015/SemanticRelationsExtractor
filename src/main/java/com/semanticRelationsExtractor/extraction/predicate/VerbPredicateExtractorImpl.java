package com.semanticRelationsExtractor.extraction.predicate;

import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;

import java.util.List;
import java.util.logging.Logger;

import static com.semanticRelationsExtractor.cache.SemanticExtractionFilterCache.*;

/**
 * Created by Oliver on 2/17/2017.
 */
public class VerbPredicateExtractorImpl implements VerbPredicateExtractor {

    private final static Logger LOGGER = Logger.getLogger(VerbPredicateExtractorImpl.class.getName());

    @Override
    public void extract(SemanticExtractionData semanticExtractionData, SemanticPreprocessingData semanticPreprocessingData) {
        List<String> tokensList = semanticPreprocessingData.getTokensList();
        List<String> tagsList = semanticPreprocessingData.getTagsList();
        int verbIndex = semanticPreprocessingData.getVerbIndex();
        int modalVerbIndex = semanticPreprocessingData.getModalVerbIndex();
        int haveBeenSequenceStartIndex = semanticPreprocessingData.getHaveBeenSequenceStartIndex();
        if (modalVerbIndex > -1) {
            String extendedVerbPredicate = extractExtendedVerbPredicate(tokensList, tagsList, modalVerbIndex);
            semanticExtractionData.setExtendedVerbPredicate(extendedVerbPredicate);
            semanticExtractionData.setAtomicVerbPredicate(extendedVerbPredicate);
            boolean isNegativeVerbPredicate = isNegativeVerbPredicate(modalVerbIndex, tagsList);
            semanticExtractionData.setNegativeVerbPredicate(isNegativeVerbPredicate);
            LOGGER.info("Extended verb predicate: " + extendedVerbPredicate);
            LOGGER.info("Atomic verb predicate: " + extendedVerbPredicate);
            LOGGER.info("Is negative verb predicate: " + isNegativeVerbPredicate);
        } else if (haveBeenSequenceStartIndex > -1) {
            String extendedVerbPredicate = extractExtendedHaveBeenVerbPredicate(tokensList, tagsList, haveBeenSequenceStartIndex);
            semanticExtractionData.setExtendedVerbPredicate(extendedVerbPredicate);
            String atomicVerbPredicate = tokensList.get(haveBeenSequenceStartIndex) + " " + tokensList.get(haveBeenSequenceStartIndex + 1);
            semanticExtractionData.setAtomicVerbPredicate(atomicVerbPredicate);
            boolean isNegativeVerbPredicate = isNegativeVerbPredicate(haveBeenSequenceStartIndex, tagsList);
            semanticExtractionData.setNegativeVerbPredicate(isNegativeVerbPredicate);
            LOGGER.info("Extended verb predicate: " + extendedVerbPredicate);
            LOGGER.info("Atomic verb predicate: " + atomicVerbPredicate);
            LOGGER.info("Is negative verb predicate: " + isNegativeVerbPredicate);
        } else {
            String atomicVerbPredicate = tokensList.get(verbIndex);
            semanticExtractionData.setAtomicVerbPredicate(atomicVerbPredicate);
            boolean isNegativeVerbPredicate = isNegativeVerbPredicate(verbIndex, tagsList);
            semanticExtractionData.setNegativeVerbPredicate(isNegativeVerbPredicate);
            LOGGER.info("Atomic verb predicate: " + atomicVerbPredicate);
            LOGGER.info("Is negative verb predicate: " + isNegativeVerbPredicate);
        }
    }

    private String extractExtendedVerbPredicate(List<String> tokensList, List<String> tagsList, int modalVerbIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = modalVerbIndex; i < tokensList.size(); i++) {
            String tag = tagsList.get(i);
            if (extendedVerbPredicateExtractionAllowedTags.contains(tag)) {
                stringBuilder.append(tokensList.get(i));
                stringBuilder.append(" ");
            } else {
                break;
            }
        }
        return stringBuilder.toString();
    }

    private String extractExtendedHaveBeenVerbPredicate(List<String> tokensList, List<String> tagsList, int haveBeenSequenceStartIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = haveBeenSequenceStartIndex; i < tokensList.size(); i++) {
            String tag = tagsList.get(i);
            if (extendedHaveBeenVerbPredicateExtractionAllowedTags.contains(tag)) {
                stringBuilder.append(tokensList.get(i));
                stringBuilder.append(" ");
            } else {
                break;
            }
        }
        return stringBuilder.toString();
    }

    private boolean isNegativeVerbPredicate(int searchStartIndex, List<String> tagsList) {
        for (String tag : tagsList) {
            if (negativeVerbPredicateTags.contains(tag)) {
                return true;
            }
        }
        return false;
    }

}