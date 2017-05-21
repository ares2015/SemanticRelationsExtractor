package com.semanticRelationsExtractor.extraction.predicate;

import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;
import com.semanticRelationsExtractor.tags.Tags;

import java.util.List;
import java.util.logging.Logger;

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
        if (modalVerbIndex > -1) {
            String extendedVerbPredicate = extractExtendedVerbPredicate(tokensList, tagsList, modalVerbIndex);
            semanticExtractionData.setExtendedVerbPredicate(extendedVerbPredicate);
            semanticExtractionData.setAtomicVerbPredicate(extendedVerbPredicate);
            LOGGER.info("Extended verb predicate: " + extendedVerbPredicate);
            LOGGER.info("Atomic verb predicate: " + extendedVerbPredicate);
        } else {
            String atomicVerbPredicate = tokensList.get(verbIndex);
            semanticExtractionData.setAtomicVerbPredicate(atomicVerbPredicate);
            LOGGER.info("Atomic verb predicate: " + atomicVerbPredicate);

        }
    }

    private String extractExtendedVerbPredicate(List<String> tokensList, List<String> encodedTagsList, int modalVerbIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = modalVerbIndex; i < tokensList.size(); i++) {
            if (Tags.VERB.equals(encodedTagsList.get(i)) || Tags.VERB_ED.equals(encodedTagsList.get(i)) || Tags.MODAL_VERB.equals(encodedTagsList.get(i))
                    || Tags.IS_ARE.equals(encodedTagsList.get(i)) || Tags.ADVERB.equals(encodedTagsList.get(i))) {
                stringBuilder.append(tokensList.get(i));
                stringBuilder.append(" ");
            } else {
                break;
            }
        }
        return stringBuilder.toString();
    }

}