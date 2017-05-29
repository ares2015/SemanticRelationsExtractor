package com.semanticRelationsExtractor.extraction.sequence;

import java.util.List;
import java.util.Set;

/**
 * Created by Oliver on 5/29/2017.
 */
public interface SequenceExtractor {

    String extract(List<String> tokensList, List<String> tagsList, int sequenceStartIndex, Set<String> allowedTags);

}
