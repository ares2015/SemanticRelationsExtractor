package com.semanticRelationsExtractor.extraction.predicate;

import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;

import java.util.List;
import java.util.logging.Logger;

import static com.semanticRelationsExtractor.cache.SemanticExtractionFilterCache.extendedVerbPredicateExtractionAllowedTags;
import static com.semanticRelationsExtractor.cache.SemanticExtractionFilterCache.negativeVerbPredicateTags;

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
        int haveVerbEdSequenceStartIndex = semanticPreprocessingData.getHaveVerbEdSequenceStartIndex();
        int doVerbSequenceStartIndex = semanticPreprocessingData.getDoVerbSequenceStartIndex();
        int haveBeenSequenceEndIndex = semanticPreprocessingData.getHaveBeenSequenceEndIndex();
        int haveVerbEdSequenceEndIndex = semanticPreprocessingData.getHaveVerbEdSequenceEndIndex();
        int doVerbSequenceEndIndex = semanticPreprocessingData.getDoVerbSequenceEndIndex();
        if (modalVerbIndex > -1) {
            String extendedVerbPredicate = extractExtendedVerbPredicate(tokensList, tagsList, modalVerbIndex);
            semanticExtractionData.setExtendedVerbPredicate(extendedVerbPredicate);
            semanticExtractionData.setAtomicVerbPredicate(extendedVerbPredicate);
            boolean isNegativeVerbPredicate = isNegativeVerbPredicate(modalVerbIndex, tagsList);
            semanticExtractionData.setNegativeVerbPredicate(isNegativeVerbPredicate);
            LOGGER.info("Extended verb predicate: " + extendedVerbPredicate);
            LOGGER.info("Atomic verb predicate: " + extendedVerbPredicate);
            LOGGER.info("Is negative verb predicate: " + isNegativeVerbPredicate);
        } else if (haveBeenSequenceStartIndex > -1 || haveVerbEdSequenceStartIndex > -1 || doVerbSequenceStartIndex > -1) {
            int sequenceStartIndex = getSequenceStartIndex(haveBeenSequenceStartIndex, haveVerbEdSequenceStartIndex, doVerbSequenceStartIndex);
            int sequenceEndIndex = getSequenceEndIndex(haveBeenSequenceEndIndex, haveVerbEdSequenceEndIndex, doVerbSequenceEndIndex);
            String extendedVerbPredicate = extractSequenceVerbPredicate(tokensList, tagsList, sequenceStartIndex, sequenceEndIndex);
            semanticExtractionData.setExtendedVerbPredicate(extendedVerbPredicate);
            String atomicVerbPredicate = tokensList.get(sequenceStartIndex) + " " + tokensList.get(sequenceStartIndex + 1);
            semanticExtractionData.setAtomicVerbPredicate(atomicVerbPredicate);
            boolean isNegativeVerbPredicate = isNegativeVerbPredicate(haveBeenSequenceStartIndex, tagsList);
            semanticExtractionData.setNegativeVerbPredicate(isNegativeVerbPredicate);
            LOGGER.info("Extended verb predicate: " + extendedVerbPredicate);
            LOGGER.info("Atomic verb predicate: " + atomicVerbPredicate);
            LOGGER.info("Is negative verb predicate: " + isNegativeVerbPredicate);
        } else {
            String atomicVerbPredicate = tokensList.get(verbIndex);
            semanticExtractionData.setAtomicVerbPredicate(atomicVerbPredicate);
            String extendedVerbPredicate = extractExtendedVerbPredicate(tokensList, tagsList, verbIndex);
            semanticExtractionData.setExtendedVerbPredicate(extendedVerbPredicate);
            boolean isNegativeVerbPredicate = isNegativeVerbPredicate(verbIndex, tagsList);
            semanticExtractionData.setNegativeVerbPredicate(isNegativeVerbPredicate);
            LOGGER.info("Atomic verb predicate: " + atomicVerbPredicate);
            LOGGER.info("Extended verb predicate: " + extendedVerbPredicate);
            LOGGER.info("Is negative verb predicate: " + isNegativeVerbPredicate);
        }
    }

    private String extractExtendedVerbPredicate(List<String> tokensList, List<String> tagsList, int extractionStartIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = extractionStartIndex; i < tokensList.size(); i++) {
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

    private String extractSequenceVerbPredicate(List<String> tokensList, List<String> tagsList, int sequenceStartIndex, int sequenceEndIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = sequenceStartIndex; i <= sequenceEndIndex; i++) {
            String tag = tagsList.get(i);
//            if (sequenceVerbPredicateExtractionAllowedTags.contains(tag)) {
            stringBuilder.append(tokensList.get(i));
            stringBuilder.append(" ");
//            } else {
//                break;
//            }
        }
        return stringBuilder.toString();
    }

    private boolean isNegativeVerbPredicate(int searchStartIndex, List<String> tagsList) {
        if (searchStartIndex == -1) {
            return false;
        } else {
            for (int i = searchStartIndex; i < tagsList.size(); i++) {
                String tag = tagsList.get(i);
                if (negativeVerbPredicateTags.contains(tag)) {
                    return true;
                }
            }
            return false;
        }
    }

    private int getSequenceStartIndex(int haveBeenStartIndex, int haveVerbEdSequenceStartIndex, int doVerbStartIndex) {
        if (haveBeenStartIndex > -1) {
            return haveBeenStartIndex;
        } else if (haveVerbEdSequenceStartIndex > -1) {
            return haveVerbEdSequenceStartIndex;
        } else {
            return doVerbStartIndex;
        }
    }

    private int getSequenceEndIndex(int haveBeenEndIndex, int haveVerbEdSequenceEndIndex, int doVerbEndIndex) {
        if (haveBeenEndIndex > -1) {
            return haveBeenEndIndex;
        } else if (haveVerbEdSequenceEndIndex > -1) {
            return haveVerbEdSequenceEndIndex;
        } else {
            return doVerbEndIndex;
        }
    }

}