package cz.zcu.kiv.nlp.ir.trec.logic;

import cz.zcu.kiv.nlp.ir.trec.dataStructures.IndexDictionary;
import cz.zcu.kiv.nlp.ir.trec.dataStructures.PostingsList;
import org.apache.lucene.analysis.cz.CzechAnalyzer;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.precedence.PrecedenceQueryParser;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;


import java.util.*;

/**
 * Created by msip on 5/4/17.
 */




public class QParser {

    private PrecedenceQueryParser parser;

    public QParser() {
        this.parser = new PrecedenceQueryParser(new CzechAnalyzer());
        this.parser.setDefaultOperator(StandardQueryConfigHandler.Operator.OR);
    }

    public Query parseQuery(String query) {
        try {
            org.apache.lucene.search.Query q =  parser.parse(query, "");
            Query node = new Query();
            createQTree(q, node);
            return node;
        } catch (QueryNodeException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createQTree(org.apache.lucene.search.Query q, Query root) {
        BooleanQuery booleanQuery = (BooleanQuery) q;
        for(BooleanClause clause : booleanQuery.clauses()) {
            Query child = new Query();
            child.setText(clause.getQuery().toString());
            root.addChild(clause.getOccur(), child);
            if(clause.getQuery().getClass() == BooleanQuery.class) {
                createQTree(clause.getQuery(), child);
            }else if(clause.getQuery().getClass() == TermQuery.class){
               child.setTerm(true);
            }
        }
    }




}
