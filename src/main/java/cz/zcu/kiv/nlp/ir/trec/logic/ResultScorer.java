package cz.zcu.kiv.nlp.ir.trec.logic;

import cz.zcu.kiv.nlp.ir.trec.data.Result;
import cz.zcu.kiv.nlp.ir.trec.data.ResultImpl;
import cz.zcu.kiv.nlp.ir.trec.dataStructures.IndexDictionary;
import cz.zcu.kiv.nlp.ir.trec.dataStructures.Posting;
import cz.zcu.kiv.nlp.ir.trec.dataStructures.PostingsList;

import java.util.*;

/**
 * Created by msip on 5/7/17.
 */
public class ResultScorer {

    private IndexDictionary indexDictionary;

    public ResultScorer(IndexDictionary indexDictionary) {
        this.indexDictionary = indexDictionary;
    }

    List<Result> scoreResults(List<String> queryTerms, PostingsList postingsList){
        Map<String, Integer> uniqueQueries = new HashMap<String, Integer>();
        for(String term : queryTerms) {
            Integer count = uniqueQueries.get(term);
            if(count == null) {
                uniqueQueries.put(term, 1);
            }else{
                uniqueQueries.put(term, count+1);
            }
        }

        List<Result> results = new ArrayList<Result>(postingsList.getPostings().size());

        for (Posting p : postingsList.getPostings()) {
            double qSize = 0, dSize = 0, score = 0;
            for(String queryTerm : uniqueQueries.keySet()) {
                final double termQueryWeight = termQueryWeights(uniqueQueries, queryTerm);
                final double termDocWeight = termDocumentWeight(queryTerm, p.getDocumentId());
                score += (termDocWeight * termQueryWeight);
                qSize += (termQueryWeight * termQueryWeight);
                dSize += (termDocWeight * termDocWeight);
            }
            score /= (Math.sqrt(qSize) * Math.sqrt(dSize));

            ResultImpl result = new ResultImpl();
            result.setDocumentID(indexDictionary.getStringId(p.getDocumentId()));
            result.setScore((float) score);

            results.add(result);
        }

        Collections.sort(results, (result, t1) -> Float.compare(t1.getScore(), result.getScore()));

        // add rank
        for(int i = 0; i < results.size(); i++) {
            ((ResultImpl)results.get(i)).setRank(i+1);
        }

        return results;
    }


    double termQueryWeights(Map<String, Integer> uniqueQueryTerms, String term) {

        PostingsList termPostings = indexDictionary.getPostings(term);
        if(termPostings == null) {
            return 0f;
        }

        final int documentFrequency = termPostings.getTerm().getDocFreq();
        final double idf = Math.log(indexDictionary.getSize() / (double) documentFrequency);

        final int termQueryFrequency = uniqueQueryTerms.get(term);

        double result = (Math.log((double) termQueryFrequency) + 1) * idf;

        return result;
    }

    double termDocumentWeight(String term, int documentId) {

        PostingsList termPostings = indexDictionary.getPostings(term);
        if(termPostings == null) {
            return 0f;
        }

        Posting docPosting = termPostings.getDocumentPosting(documentId);
        if(docPosting == null) {
            return 0f;
        }

        final int documentFrequency = termPostings.getTerm().getDocFreq();
        final double idf = Math.log(indexDictionary.getSize() / (double) documentFrequency);

        final int termDocumentFrequency = docPosting.getTermDocFrequency();

        double result = (Math.log((double) termDocumentFrequency) + 1) * idf;

        return result;
    }
}
