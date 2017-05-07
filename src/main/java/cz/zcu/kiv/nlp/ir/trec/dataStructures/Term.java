package cz.zcu.kiv.nlp.ir.trec.dataStructures;

/**
 * Created by msip on 5/7/17.
 */
public class Term {

    protected int docFreq;
    protected String text;

    public Term(String text){
        this.text = text;
        this.docFreq = 0;
    }

    public int getDocFreq() { return docFreq; }
    public void setDocFreq(int docFreq) { this.docFreq = docFreq; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Term term = (Term) o;

        return text.equals(term.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }
}
