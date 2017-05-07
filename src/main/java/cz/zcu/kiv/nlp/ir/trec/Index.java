package cz.zcu.kiv.nlp.ir.trec;

import cz.zcu.kiv.nlp.ir.trec.data.Document;
import cz.zcu.kiv.nlp.ir.trec.data.Result;
import cz.zcu.kiv.nlp.ir.trec.dataStructures.IndexDictionary;
import cz.zcu.kiv.nlp.ir.trec.dataStructures.PostingsList;
import cz.zcu.kiv.nlp.ir.trec.logic.QParser;
import cz.zcu.kiv.nlp.ir.trec.logic.Query;
import cz.zcu.kiv.nlp.ir.trec.logic.Tokenizer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.cz.CzechAnalyzer;

import java.util.List;
import java.util.Map;

/**
 * @author msip
 */

public class Index implements Indexer, Searcher {

    private static final Logger LOGGER = LogManager.getLogger(Index.class);

    private static final String DICTIONARY_NAME = "./indexDictionary.ser";

    private QParser parser;
    private Tokenizer tokenizer;
    private IndexDictionary dictionary;

    public Index() {
        this.parser = new QParser();
        this.tokenizer = new Tokenizer(new CzechAnalyzer());
        this.dictionary = new IndexDictionary();
    }


    public void index(final List<Document> documents) {
        LOGGER.info("Creating new IndexDictionary");
        long startTime = System.currentTimeMillis();
        for(Document doc : documents) {
            addToIndex(doc);
        }
        long endTime = System.currentTimeMillis();
        LOGGER.info("Index finished after: " + (endTime- startTime)/1000 + " seconds");

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
        Map<String, Integer> termFrequencyMap = tokenizer.getTerms(document.getText());
        dictionary.add(termFrequencyMap, document.getId());
    }

    public List<Result> search(String query) {
        Query q = parser.parseQuery(query);
        return q.execute(dictionary);
    }
}
