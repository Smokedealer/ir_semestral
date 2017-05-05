package cz.zcu.kiv.nlp.ir.trec.logic;

import cz.zcu.kiv.nlp.ir.trec.dataStructures.IndexDictionary;
import cz.zcu.kiv.nlp.ir.trec.dataStructures.PostingsList;
import org.apache.lucene.search.BooleanClause;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    public PostingsList execute() {
        if(isTerm){
            return IndexDictionary.getInstance().getPostings(this.text);
        }else {
            PostingsList result = null;

            for(Map.Entry<BooleanClause.Occur, Collection<Query>> childOccurence : this.children.entrySet()){
                switch (childOccurence.getKey()) {
                    case MUST:
                        for(Query child : childOccurence.getValue()) {
                            result = PostingsList.andOperation(result, child.execute());
                        }
                        break;
                    case SHOULD:
                        for(Query child : childOccurence.getValue()) {
                            result = PostingsList.orOperation(result, child.execute());
                        }
                        break;
                    case MUST_NOT:
                        break;
                }
            }

            return result;
        }
    }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public boolean isTerm() { return isTerm; }
    public void setTerm(boolean term) { isTerm = term; }

}