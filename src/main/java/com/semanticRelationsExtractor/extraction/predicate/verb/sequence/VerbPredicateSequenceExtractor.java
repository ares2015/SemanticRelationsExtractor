package com.semanticRelationsExtractor.extraction.predicate.verb.sequence;

import java.util.List;
import java.util.Set;

/**
 * Created by Oliver on 5/29/2017.
 */
public interface VerbPredicateSequenceExtractor {

    String extract(List<String> tokensList, List<String> tagsList, int sequenceStartIndex, Set<String> allowedTags);

}
