package cz.zcu.kiv.nlp.ir.trec.dataStructures;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by msip on 5/4/17.
 */
public class IndexDictionary implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(IndexDictionary.class);

    private static IndexDictionary INSTANCE;

    private Map<String, Integer> stringIntIdMap;
    private Map<String, PostingsList> dictionary;

    public IndexDictionary() {
        IndexDictionary.INSTANCE = this;
        this.stringIntIdMap = new HashMap<>();
        this.dictionary = new HashMap<>();
    }

    public static IndexDictionary getInstance() {
        return IndexDictionary.INSTANCE;
    }

    public void add(final Map<String, Integer> termFrequencyMap, final String documentId) {

        int id;

        if(stringIntIdMap.containsKey(documentId)){
            id = stringIntIdMap.get(documentId);
        }else {
            id = stringIntIdMap.size();
            stringIntIdMap.put(documentId, id);
        }

        for(Map.Entry<String, Integer> termFrequencyPair : termFrequencyMap.entrySet()) {
            add(termFrequencyPair, id);
        }
    }

    public PostingsList getPostings(final String term) {
        return this.dictionary.get(term);
    }

    public void add(final Map.Entry<String, Integer> termFrequencyPair, final int documentId){

        PostingsList postingsList = this.dictionary.get(termFrequencyPair.getKey());
        if(postingsList == null){
            postingsList = new PostingsList();
            this.dictionary.put(termFrequencyPair.getKey(), postingsList);
        }
        postingsList.addPosting(new Posting(documentId, termFrequencyPair.getValue()));
    }

    public static boolean save(final String fileName, final IndexDictionary dictionary) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(dictionary);
            out.close();
            fileOut.close();
            LOGGER.info("Serialized data is saved in: " + fileName);
            return true;
        } catch (IOException i) {
            LOGGER.error("Failed to serialize data. Error: " + i.getMessage());
            i.printStackTrace();
            return false;
        }
    }

    public static IndexDictionary load(final String fileName) {
        final Object object;
        try {
            final ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
            object = objectInputStream.readObject();
            objectInputStream.close();
            LOGGER.info("Serialized data loaded from: " + fileName);
            return (IndexDictionary) object;
        } catch (Exception ex) {
            LOGGER.error("Failed to deserialize data. Error: " + ex.getMessage());
            return null;
        }
    }
}
