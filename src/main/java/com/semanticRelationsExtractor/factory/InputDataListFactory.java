package com.semanticRelationsExtractor.factory;

import com.semanticRelationsExtractor.data.InputData;

import java.util.List;

/**
 * Created by oled on 5/16/2017.
 */
public interface InputDataListFactory {

    List<InputData> create(List<String> inputDataAsStringList);

}
