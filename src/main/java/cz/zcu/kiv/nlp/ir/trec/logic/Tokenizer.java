package cz.zcu.kiv.nlp.ir.trec.logic;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by msip on 5/4/17.
 */
public class Tokenizer {

    private static Logger LOGGER = Logger.getLogger(Tokenizer.class);

    private Analyzer analyzer;

    public Tokenizer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    public Map<String, Integer> getTerms(String text) {

        // todo check if removes stop words
        Map<String, Integer> terms = new HashMap<>();

        try {
            TokenStream tokenStream = analyzer.tokenStream(null, new StringReader(text));
            tokenStream.reset();
            while (tokenStream.incrementToken()){
                String term = tokenStream.getAttribute(CharTermAttribute.class).toString();
                Integer termCount = terms.get(term);
                if(termCount == null) {
                    terms.put(term, 1);
                }else{
                    terms.put(term, termCount + 1);
                }
            }
            tokenStream.end();
            tokenStream.close();
        } catch (IOException e) {
            LOGGER.error("Unexpected exception occured while tokenizing text!");
            e.printStackTrace();
        }


        return terms;
    }
}
