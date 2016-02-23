package indexRetriever;

import java.io.File;
import java.util.Date;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Retriever {
	
	@Parameter(names={"-index", "-i"},required = true)
  private String indexDirPath;
  @Parameter(names={"-query", "-q"},required = true)
  private String searchString;
	
	public static void main(String[] args) throws Exception {
	
		Retriever retriever = new Retriever();
		new JCommander(retriever, args);
		retriever.start();
		
	}
	
	public void start() throws Exception{
	
		File indexDir = new File(indexDirPath);
		String q = searchString;
		if (!indexDir.exists() || !indexDir.isDirectory()) {
			throw new Exception(indexDir +" does not exist or is not a directory.");
		}
		search(indexDir, q);
		}
	
		public static void search(File indexDir, String q)throws Exception {
		Directory fsDir = FSDirectory.open(indexDir.toPath());
		IndexSearcher is = new IndexSearcher(DirectoryReader.open(fsDir));
		QueryParser parser = new QueryParser("body", new StandardAnalyzer());
		Query query = parser.parse(q);
		
		long start = new Date().getTime();
		TopDocs hits = is.search(query, 10);
		long end = new Date().getTime();
		System.err.println("Found " + hits.totalHits +
        " document(s) (in " + (end - start) +
        " milliseconds) that matched query '" +
        q + "':");
		
		
		for (int i = 0; i < hits.scoreDocs.length; i++) {
			ScoreDoc scoreDoc = hits.scoreDocs[i];
      org.apache.lucene.document.Document doc = is.doc(scoreDoc.doc);
      System.out.println(doc.get("title"));
		}
	}
}

