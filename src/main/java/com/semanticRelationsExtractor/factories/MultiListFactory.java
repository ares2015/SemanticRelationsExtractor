package com.semanticRelationsExtractor.factories;

import java.util.List;

/**
 * Created by Oliver on 5/17/2017.
 */
public interface MultiListFactory {

    List<List<String>> create(List<String> items);

}
