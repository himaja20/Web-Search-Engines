package indexRetriever;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Date;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import htmlparser.JTidyHTMLHandler;

public class Indexer {

	@Parameter(names={"-index", "-i"},required = true)
  private String indexDirPath;
  @Parameter(names={"-docs", "-d"},required = true)
  private String dataDirPath;

	public static void main(String[] args) throws Exception {
		
		Indexer indexer = new Indexer();
		new JCommander(indexer, args);
		indexer.start();
	}
	
	public void start() throws IOException{
		File indexDir = new File(indexDirPath);
		File dataDir = new File(dataDirPath);
		long start = new Date().getTime();
		int numIndexed = index(indexDir, dataDir);
		long end = new Date().getTime();
		System.out.println("Indexing " + numIndexed + " files took "
		+ (end - start) + " milliseconds");
}
	
	
	// open an index and start file directory traversal
	public static int index(File indexDir, File dataDir)
	throws IOException {
		if (!dataDir.exists() || !dataDir.isDirectory()) {
			throw new IOException(dataDir
			+ " does not exist or is not a directory");
		}
		Path path = indexDir.toPath();
		Directory indexDirectory = 
		         FSDirectory.open(path);

		IndexWriterConfig c = new IndexWriterConfig(new StandardAnalyzer());
		IndexWriter writer = new IndexWriter(indexDirectory,c);
		indexDirectory(writer, dataDir);
		int numIndexed = writer.maxDoc();
		writer.close();
		return numIndexed;
	}
	
	private static void indexDirectory(IndexWriter writer,File dir)throws IOException {
		File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				if (f.isDirectory()) {
					indexDirectory(writer,f);
				} 
				else if (f.getName().endsWith(".html") || f.getName().endsWith(".htm")) {
					indexFile(writer,f);
				}
			}
		}

	//method to actually index a file using Lucene
	private static void indexFile(IndexWriter writer, File f)throws IOException {
		if (f.isHidden() || !f.exists() || !f.canRead()) {
			return;
		}
		System.out.println("Indexing " + f.getCanonicalPath());
		JTidyHTMLHandler parser = new JTidyHTMLHandler();
		Document doct = new Document();
		InputStream is = new FileInputStream(f);
		doct = parser.getDocument(is);
		doct.add(new StringField("filename", f.getCanonicalPath(), Field.Store.YES));
		writer.addDocument(doct);
	} 
}
	


