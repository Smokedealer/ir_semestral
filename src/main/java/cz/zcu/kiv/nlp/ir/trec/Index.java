package cz.zcu.kiv.nlp.ir.trec;

import cz.zcu.kiv.nlp.ir.trec.data.Document;
import cz.zcu.kiv.nlp.ir.trec.data.Result;
import cz.zcu.kiv.nlp.ir.trec.dataStructures.IndexDictionary;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.cz.CzechAnalyzer;

import java.util.List;

/**
 * @author msip
 */

public class Index implements Indexer, Searcher {

    private static final Logger LOGGER = LogManager.getLogger(Index.class);

    private static final String DICTIONARY_NAME = "./indexDictionary.ser";

    private Tokenizer tokenizer;
    private IndexDictionary dictionary;

    public Index() {
        this.tokenizer = new Tokenizer(new CzechAnalyzer());
        this.dictionary = new IndexDictionary();
    }


    public void index(final List<Document> documents) {
        LOGGER.info("Creating new IndexDictionary");
        for(Document doc : documents) {
            addToIndex(doc);
        }
        saveDictionary();
    }

    public boolean loadDictionary() {
        IndexDictionary dictionary = IndexDictionary.load(DICTIONARY_NAME);
        if(dictionary != null) {
            LOGGER.info("IndexDictionary successfully loaded");
            this.dictionary = dictionary;
            return true;
        }else {
            return false;
        }
    }

    public boolean saveDictionary() {
        LOGGER.info("Saving IndexDictionary");
        return IndexDictionary.save(DICTIONARY_NAME, this.dictionary);
    }



    public void addToIndex(final Document document) {
        List terms = tokenizer.tokenizeText(document.getText());
        dictionary.add(terms, document.getId());
    }

    public List<Result> search(String query) {
        //  todo implement
        return null;
    }
}
