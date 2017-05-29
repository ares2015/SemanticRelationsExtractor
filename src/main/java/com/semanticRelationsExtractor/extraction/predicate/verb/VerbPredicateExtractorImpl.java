package com.semanticRelationsExtractor.extraction.predicate.verb;

import com.semanticRelationsExtractor.cache.SemanticExtractionFilterCache;
import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;
import com.semanticRelationsExtractor.extraction.sequence.SequenceExtractor;

import java.util.List;
import java.util.logging.Logger;

import static com.semanticRelationsExtractor.cache.SemanticExtractionFilterCache.negativeVerbPredicateTags;

/**
 * Created by Oliver on 2/17/2017.
 */
public class VerbPredicateExtractorImpl implements VerbPredicateExtractor {

    private final static Logger LOGGER = Logger.getLogger(VerbPredicateExtractorImpl.class.getName());

    private SequenceExtractor sequenceExtractor;

    public VerbPredicateExtractorImpl(SequenceExtractor sequenceExtractor) {
        this.sequenceExtractor = sequenceExtractor;
    }

    @Override
    public void extract(SemanticExtractionData semanticExtractionData, SemanticPreprocessingData semanticPreprocessingData) {
        List<String> tokensList = semanticPreprocessingData.getTokensList();
        List<String> tagsList = semanticPreprocessingData.getTagsList();
        String atomicPredicate = "";
        String extendedPredicate = "";
        boolean isNegativeVerbPredicate = false;
        int verbIndex = semanticPreprocessingData.getVerbIndex();
        int modalVerbIndex = semanticPreprocessingData.getModalVerbIndex();
        int haveBeenSequenceStartIndex = semanticPreprocessingData.getHaveBeenSequenceStartIndex();
        int haveVerbEdSequenceStartIndex = semanticPreprocessingData.getHaveVerbEdSequenceStartIndex();
        int doVerbSequenceStartIndex = semanticPreprocessingData.getDoVerbSequenceStartIndex();
        if (modalVerbIndex > -1) {
            atomicPredicate = sequenceExtractor.extract(tokensList, tagsList, modalVerbIndex, SemanticExtractionFilterCache.modalVerbSequenceAtomicAllowedTags);
            extendedPredicate = sequenceExtractor.extract(tokensList, tagsList, modalVerbIndex, SemanticExtractionFilterCache.modalVerbSequenceExtendedAllowedTags);
            semanticExtractionData.setAtomicVerbPredicate(atomicPredicate);
            semanticExtractionData.setExtendedVerbPredicate(extendedPredicate);
            isNegativeVerbPredicate = isNegativeVerbPredicate(modalVerbIndex, tagsList);
            semanticExtractionData.setNegativeVerbPredicate(isNegativeVerbPredicate);
        } else if (haveBeenSequenceStartIndex > -1) {
            atomicPredicate = sequenceExtractor.extract(tokensList, tagsList, haveBeenSequenceStartIndex, SemanticExtractionFilterCache.haveBeenSequenceAtomicAllowedTags);
            extendedPredicate = sequenceExtractor.extract(tokensList, tagsList, haveBeenSequenceStartIndex, SemanticExtractionFilterCache.haveBeenSequenceExtendedAllowedTags);
            semanticExtractionData.setAtomicVerbPredicate(atomicPredicate);
            semanticExtractionData.setExtendedVerbPredicate(extendedPredicate);
            isNegativeVerbPredicate = isNegativeVerbPredicate(haveBeenSequenceStartIndex, tagsList);
            semanticExtractionData.setNegativeVerbPredicate(isNegativeVerbPredicate);
        } else if (haveVerbEdSequenceStartIndex > -1) {
            atomicPredicate = sequenceExtractor.extract(tokensList, tagsList, haveVerbEdSequenceStartIndex, SemanticExtractionFilterCache.haveVerbEdSequenceAtomicAllowedTags);
            extendedPredicate = sequenceExtractor.extract(tokensList, tagsList, haveVerbEdSequenceStartIndex, SemanticExtractionFilterCache.haveVerbEdSequenceExtendedAllowedTags);
            semanticExtractionData.setAtomicVerbPredicate(atomicPredicate);
            semanticExtractionData.setExtendedVerbPredicate(extendedPredicate);
            isNegativeVerbPredicate = isNegativeVerbPredicate(haveVerbEdSequenceStartIndex, tagsList);
            semanticExtractionData.setNegativeVerbPredicate(isNegativeVerbPredicate);
        } else if (doVerbSequenceStartIndex > -1) {
            atomicPredicate = sequenceExtractor.extract(tokensList, tagsList, doVerbSequenceStartIndex, SemanticExtractionFilterCache.doVerbSequenceAtomicAllowedTags);
            extendedPredicate = sequenceExtractor.extract(tokensList, tagsList, doVerbSequenceStartIndex, SemanticExtractionFilterCache.doVerbSequenceExtendedAllowedTags);
            semanticExtractionData.setAtomicVerbPredicate(atomicPredicate);
            semanticExtractionData.setExtendedVerbPredicate(extendedPredicate);
            isNegativeVerbPredicate = isNegativeVerbPredicate(doVerbSequenceStartIndex, tagsList);
            semanticExtractionData.setNegativeVerbPredicate(isNegativeVerbPredicate);
        } else {
            atomicPredicate = tokensList.get(verbIndex);
            extendedPredicate = sequenceExtractor.extract(tokensList, tagsList, verbIndex, SemanticExtractionFilterCache.simpleVerbSequenceExtendedAllowedTags);
            semanticExtractionData.setAtomicVerbPredicate(atomicPredicate);
            semanticExtractionData.setExtendedVerbPredicate(extendedPredicate);
            isNegativeVerbPredicate = isNegativeVerbPredicate(verbIndex, tagsList);
            semanticExtractionData.setNegativeVerbPredicate(isNegativeVerbPredicate);

        }
        LOGGER.info("Extended verb predicate: " + extendedPredicate);
        LOGGER.info("Atomic verb predicate: " + atomicPredicate);
        LOGGER.info("Is negative verb predicate: " + isNegativeVerbPredicate);
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


}