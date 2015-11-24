package au.com.iglooit.espower.esadapter.core.analyzer;

/**
 * Created by nicholaszhu on 29/10/2015.
 */
public interface IQueryAnalyzer {
    // list of keywords, split by space
    String getKeywords(String queryContent);
}
