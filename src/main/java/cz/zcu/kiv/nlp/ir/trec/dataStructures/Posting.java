package cz.zcu.kiv.nlp.ir.trec.dataStructures;

import java.io.Serializable;

/**
 * Created by msip on 5/4/17.
 */
public class Posting implements Comparable<Posting>, Serializable {

    private int documentId;
    private int termFrequency;
    private Term term;          // posting hold reference to Term for sake of
                                // faster rank calculation

    public Posting(int documentId, int termFrequency) {
        this.documentId = documentId;
        this.termFrequency = termFrequency;
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

    public int getTermFrequency() { return termFrequency; }
    public Term getTerm() { return term; }
    public void setTerm(Term term) { this.term = term; }

    @Override
    public int hashCode() {
        return documentId * 31;
    }
    public int compareTo(Posting posting) {
        return Integer.compare(documentId, posting.documentId);
    }
}
