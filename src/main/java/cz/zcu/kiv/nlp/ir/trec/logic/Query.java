package cz.zcu.kiv.nlp.ir.trec.logic;

import cz.zcu.kiv.nlp.ir.trec.data.Result;
import cz.zcu.kiv.nlp.ir.trec.dataStructures.IndexDictionary;
import cz.zcu.kiv.nlp.ir.trec.dataStructures.Posting;
import cz.zcu.kiv.nlp.ir.trec.dataStructures.PostingsList;
import org.apache.lucene.search.BooleanClause;

import java.util.*;

public class Query {

    private Map<BooleanClause.Occur, Collection<Query>> children;
    private String text;

    private boolean isTerm;

    public Query() {
        this.children = new HashMap<>();
        // this order must be preserved!!
        this.children.put(BooleanClause.Occur.MUST, new ArrayList<Query>());
        this.children.put(BooleanClause.Occur.SHOULD, new ArrayList<Query>());
        this.children.put(BooleanClause.Occur.MUST_NOT, new ArrayList<Query>());
    }


    public void addChild(BooleanClause.Occur occur, Query child) {
        this.children.get(occur).add(child);
    }

    public List<Result> execute(final IndexDictionary dictionary) {
        ResultScorer scorer = new ResultScorer(dictionary); // create scorer instance

        PostingsList postings = fetchPostings(dictionary);  // fetch all doc postings corresponding to this query
        List<String> terms  = new ArrayList<>();
        getTerms(terms);                                    // get all query terms

        return scorer.scoreResults(terms, postings);        // return scored results
    }

    public PostingsList fetchPostings(final IndexDictionary dictionary) {
        if(isTerm){
            return IndexDictionary.getInstance().getPostings(this.text);
        }else {
            PostingsList result = null;

            for(Map.Entry<BooleanClause.Occur, Collection<Query>> childOccurence : this.children.entrySet()){
                switch (childOccurence.getKey()) {
                    case MUST:
                        for(Query child : childOccurence.getValue()) {
                            result = PostingsList.andOperation(result, child.fetchPostings(dictionary));
                        }
                        break;
                    case SHOULD:
                        for(Query child : childOccurence.getValue()) {
                            result = PostingsList.orOperation(result, child.fetchPostings(dictionary));
                        }
                        break;
                    case MUST_NOT:
                        break;
                }
            }

            return result;
        }
    }

    public void getTerms(List<String> terms){
        if(isTerm) {
            terms.add(this.text);
        } else {
            for(Map.Entry<BooleanClause.Occur, Collection<Query>> childOccurence : this.children.entrySet()) {
                for(Query child : childOccurence.getValue()) {
                    child.getTerms(terms);
                }
            }
        }
    }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public boolean isTerm() { return isTerm; }
    public void setTerm(boolean term) { isTerm = term; }

}