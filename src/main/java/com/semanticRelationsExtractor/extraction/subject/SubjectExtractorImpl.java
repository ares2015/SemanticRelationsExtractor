package com.semanticRelationsExtractor.extraction.subject;

import com.semanticRelationsExtractor.cache.SemanticExtractionFilterCache;
import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;
import com.semanticRelationsExtractor.tags.Tags;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Oliver on 2/16/2017.
 */
public class SubjectExtractorImpl implements SubjectExtractor {

    private final static Logger LOGGER = Logger.getLogger(SubjectExtractorImpl.class.getName());

    @Override
    public void extract(SemanticExtractionData semanticExtractionData, SemanticPreprocessingData semanticPreprocessingData) {
        List<String> tokensList = semanticPreprocessingData.getTokensList();
        List<String> tagsList = semanticPreprocessingData.getTagsList();
        int extractionEndIndex = getExtractionEndIndex(semanticPreprocessingData);
        if (!semanticPreprocessingData.containsBeforeVerbPreposition()) {
            String atomicSubject = "";
            atomicSubject = extractAtomicSubject(tokensList, tagsList, extractionEndIndex);
            semanticExtractionData.setAtomicSubject(atomicSubject);
            LOGGER.info("Atomic subject: " + atomicSubject);
        }
        if (extractionEndIndex > 1) {
            String extendedSubject = extractExtendedSubject(tokensList, tagsList, extractionEndIndex);
            semanticExtractionData.setExtendedSubject(extendedSubject);
            LOGGER.info("Extended subject: " + extendedSubject);
        }
    }

    private String extractAtomicSubject(List<String> tokensList, List<String> tagsList, int extractionEndIndex) {
        for (int i = extractionEndIndex; i >= 0; i--) {
            if (Tags.NOUN.equals(tagsList.get(i)) || Tags.VERB_ED.equals(tagsList.get(i))) {
                return tokensList.get(i);
            }
        }
        throw new IllegalStateException("There is no subject in the sentence");
    }

    private String extractExtendedSubject(List<String> tokensList, List<String> tagsList, int extractionEndIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < extractionEndIndex; i++) {
            if (SemanticExtractionFilterCache.subjectNounPredicateExtractionAllowedTags.contains(tagsList.get(i))) {
                stringBuilder.append(tokensList.get(i));
                stringBuilder.append(" ");
            } else {
                break;
            }
        }
        return stringBuilder.toString();
    }

    private int getExtractionEndIndex(SemanticPreprocessingData semanticPreprocessingData) {
        if (semanticPreprocessingData.getModalVerbIndex() > -1) {
            return semanticPreprocessingData.getModalVerbIndex();
        } else if (semanticPreprocessingData.getHaveBeenSequenceStartIndex() > -1) {
            return semanticPreprocessingData.getHaveBeenSequenceStartIndex();
        } else {
            return semanticPreprocessingData.getVerbIndex();
        }
    }
}
