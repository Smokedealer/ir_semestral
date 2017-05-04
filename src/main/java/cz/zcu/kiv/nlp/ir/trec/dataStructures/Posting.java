package cz.zcu.kiv.nlp.ir.trec.dataStructures;

import java.io.Serializable;

/**
 * Created by msip on 5/4/17.
 */
public class Posting implements Comparable<Posting>, Serializable {

    private int documentId;

    public Posting(int documentId) {
        this.documentId = documentId;
    }

    public int getDocumentId() {
        return documentId;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Posting)){
            return false;
        }

        Posting other = (Posting) o;
        return documentId == other.documentId;
    }

    @Override
    public int hashCode() {
        return documentId * 31;
    }

    public int compareTo(Posting posting) {
        return Integer.compare(documentId, posting.documentId);
    }
}
