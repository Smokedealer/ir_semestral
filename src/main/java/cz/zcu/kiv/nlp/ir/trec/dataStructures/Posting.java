package cz.zcu.kiv.nlp.ir.trec.dataStructures;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by msip on 5/4/17.
 */

class PostingComparator implements Comparator<Posting> {

    @Override
    public int compare(Posting first, Posting second) {
        return Integer.compare(first.getDocumentId(), second.getDocumentId());
    }
}

public class Posting implements Serializable {

    private int documentId;
    private int getTermDocFrequency;

    public Posting(int documentId, int getTermDocFrequency) {
        this.documentId = documentId;
        this.getTermDocFrequency = getTermDocFrequency;
    }

    public int getDocumentId() {
        return documentId;
    }

    public int getTermDocFrequency() { return getTermDocFrequency; }
}
