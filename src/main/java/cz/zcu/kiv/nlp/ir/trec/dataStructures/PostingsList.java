package cz.zcu.kiv.nlp.ir.trec.dataStructures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by msip on 5/4/17.
 */
public class PostingsList implements Serializable{

    private List<Posting> postingsTree;

    public PostingsList() {
        this.postingsTree = new ArrayList<>();
    }

    public void addItem(final int documentId) {
        for(Posting p : postingsTree) {
            if(p.getDocumentId() == documentId){
                return;
            }else if(p.getDocumentId() > documentId) {
                break;
            }
        }

        postingsTree.add(new Posting(documentId));
    }

}
