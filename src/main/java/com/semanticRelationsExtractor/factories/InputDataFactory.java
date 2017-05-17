package com.semanticRelationsExtractor.factories;

import com.semanticRelationsExtractor.data.InputData;

import java.util.List;

/**
 * Created by oled on 5/16/2017.
 */
public interface InputDataFactory {

    InputData create(String sentence, List<List<String>> tagSequencesMultiList);

}
