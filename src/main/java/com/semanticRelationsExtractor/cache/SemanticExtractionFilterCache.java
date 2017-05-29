package com.semanticRelationsExtractor.cache;

import com.semanticRelationsExtractor.tags.Tags;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Oliver on 11/2/2016.
 */
public final class SemanticExtractionFilterCache {

    public static final Set<String> semanticExtractionAllowedTags = new HashSet<>();

    public static final Set<String> sequenceVerbPredicateExtractionAllowedTags = new HashSet<>();

    public static final Set<String> extendedVerbPredicateExtractionAllowedTags = new HashSet<>();

    public static final Set<String> subjectNounPredicateExtractionAllowedTags = new HashSet<>();

    public static final Set<String> negativeVerbPredicateTags = new HashSet<>();


    public static final Set<String> wordsToFilterCache = new HashSet<>();


    static {
        semanticExtractionAllowedTags.add(Tags.ADJECTIVE);
        semanticExtractionAllowedTags.add(Tags.ADVERB);
        semanticExtractionAllowedTags.add(Tags.DETERMINER);
        semanticExtractionAllowedTags.add(Tags.DO);
        semanticExtractionAllowedTags.add(Tags.DO_NOT);
        semanticExtractionAllowedTags.add(Tags.CONJUNCTION);
        semanticExtractionAllowedTags.add(Tags.IS_ARE);
        semanticExtractionAllowedTags.add(Tags.IS_ARE_NOT);
        semanticExtractionAllowedTags.add(Tags.HAVE);
        semanticExtractionAllowedTags.add(Tags.HAVE_NOT);
        semanticExtractionAllowedTags.add(Tags.MODAL_VERB);
        semanticExtractionAllowedTags.add(Tags.MODAL_VERB_NOT);
        semanticExtractionAllowedTags.add(Tags.NOUN);
        semanticExtractionAllowedTags.add(Tags.NOT);
        semanticExtractionAllowedTags.add(Tags.NUMBER);
        semanticExtractionAllowedTags.add(Tags.PREPOSITION);
        semanticExtractionAllowedTags.add(Tags.PRONOUN_POSSESIVE);
        semanticExtractionAllowedTags.add(Tags.QUANTIFIER);
        semanticExtractionAllowedTags.add(Tags.TO);
        semanticExtractionAllowedTags.add(Tags.VERB);
        semanticExtractionAllowedTags.add(Tags.VERB_ING);
        semanticExtractionAllowedTags.add(Tags.VERB_ED);

        extendedVerbPredicateExtractionAllowedTags.add(Tags.HAVE);
        extendedVerbPredicateExtractionAllowedTags.add(Tags.HAVE_NOT);
        extendedVerbPredicateExtractionAllowedTags.add(Tags.VERB);
        extendedVerbPredicateExtractionAllowedTags.add(Tags.VERB_ED);
        extendedVerbPredicateExtractionAllowedTags.add(Tags.MODAL_VERB);
        extendedVerbPredicateExtractionAllowedTags.add(Tags.MODAL_VERB_NOT);
        extendedVerbPredicateExtractionAllowedTags.add(Tags.IS_ARE);
        extendedVerbPredicateExtractionAllowedTags.add(Tags.IS_ARE_NOT);
        extendedVerbPredicateExtractionAllowedTags.add(Tags.NOT);
        extendedVerbPredicateExtractionAllowedTags.add(Tags.ADVERB);

        sequenceVerbPredicateExtractionAllowedTags.add(Tags.DO);
        sequenceVerbPredicateExtractionAllowedTags.add(Tags.DO_NOT);
        sequenceVerbPredicateExtractionAllowedTags.add(Tags.HAVE);
        sequenceVerbPredicateExtractionAllowedTags.add(Tags.HAVE_NOT);
        sequenceVerbPredicateExtractionAllowedTags.add(Tags.IS_ARE);
        sequenceVerbPredicateExtractionAllowedTags.add(Tags.IS_ARE_NOT);
        sequenceVerbPredicateExtractionAllowedTags.add(Tags.NOT);
        sequenceVerbPredicateExtractionAllowedTags.add(Tags.ADJECTIVE);
        sequenceVerbPredicateExtractionAllowedTags.add(Tags.VERB);
        sequenceVerbPredicateExtractionAllowedTags.add(Tags.VERB_ED);
        sequenceVerbPredicateExtractionAllowedTags.add(Tags.ADVERB);

        subjectNounPredicateExtractionAllowedTags.add(Tags.ADJECTIVE);
        subjectNounPredicateExtractionAllowedTags.add(Tags.CONJUNCTION);
        subjectNounPredicateExtractionAllowedTags.add(Tags.NOUN);
        subjectNounPredicateExtractionAllowedTags.add(Tags.NUMBER);
        subjectNounPredicateExtractionAllowedTags.add(Tags.PREPOSITION);
        subjectNounPredicateExtractionAllowedTags.add(Tags.QUANTIFIER);
        subjectNounPredicateExtractionAllowedTags.add(Tags.TO);
        subjectNounPredicateExtractionAllowedTags.add(Tags.VERB_ING);
        subjectNounPredicateExtractionAllowedTags.add(Tags.VERB_ED);

        negativeVerbPredicateTags.add(Tags.NOT);
        negativeVerbPredicateTags.add(Tags.DO_NOT);
        negativeVerbPredicateTags.add(Tags.HAVE_NOT);
        negativeVerbPredicateTags.add(Tags.MODAL_VERB_NOT);
        negativeVerbPredicateTags.add(Tags.IS_ARE_NOT);

        wordsToFilterCache.add("the");
        wordsToFilterCache.add("a");
        wordsToFilterCache.add("an");
        wordsToFilterCache.add("The");
        wordsToFilterCache.add("A");
        wordsToFilterCache.add("An");
    }

}
