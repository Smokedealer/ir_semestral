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

    public void addPosting(final Posting posting) {
        postingsTree.add(posting);
    }

}
