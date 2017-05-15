package com.semanticRelationsExtractor;

import com.postagger.main.PosTagger;
import com.postagger.main.PosTaggerImpl;

/**
 * Created by Oliver on 5/14/2017.
 */
public class Extractor {

    public static void main(String[] args) throws InterruptedException {
        PosTagger posTagger = new PosTaggerImpl();
        posTagger.tag("the Serbian frontier in Macedonia was left practically unguarded");
        posTagger.tag("David Beckham was football superstar");

    }
}
