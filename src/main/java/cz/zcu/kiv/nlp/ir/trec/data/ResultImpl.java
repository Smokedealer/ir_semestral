package cz.zcu.kiv.nlp.ir.trec.data;

/**
 * Created by Tigi on 8.1.2015.
 */
public class ResultImpl extends AbstractResult implements Comparable<ResultImpl> {
    @Override
    public int compareTo(ResultImpl result) {
        return Float.compare(this.score, result.score);
    }
}
