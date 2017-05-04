package cz.zcu.kiv.nlp.ir.trec;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by msip on 5/4/17.
 */
public class Tokenizer {

    private static Logger LOGGER = Logger.getLogger(Tokenizer.class);

    private Analyzer analyzer;

    public Tokenizer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    public List<String> tokenizeText(String text) {
        // todo check if removes stop words
        List<String> tokens = new ArrayList<String>();

        try {
            TokenStream tokenStream = analyzer.tokenStream(null, new StringReader(text));
            tokenStream.reset();
            while (tokenStream.incrementToken()){
                tokens.add(tokenStream.getAttribute(CharTermAttribute.class).toString());
            }
            tokenStream.end();
            tokenStream.close();
        } catch (IOException e) {
            LOGGER.error("Unexpected exception occured while tokenizing text!");
            e.printStackTrace();
        }


        return tokens;
    }
}
