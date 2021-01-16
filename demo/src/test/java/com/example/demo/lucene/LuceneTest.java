package com.example.demo.lucene;


import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

public class LuceneTest {

    private IndexWriter indexWriter;
    private StandardAnalyzer analyzer;
    private Directory index;

    @Before
    public void prepare() throws IOException {
        analyzer = new StandardAnalyzer();

        // 1. create the index
        index = new SimpleFSDirectory(Paths.get("/Users/Seraph/Downloads/lucene_test"));

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setUseCompoundFile(false);

        indexWriter = new IndexWriter(index, config);
    }

    @Test
    public void testBlockTree() throws IOException, ParseException {
//        addTextField("test0", "abc-d0");
//        addTextField("test0", "abc-e0"); //TODO 以-分割和_在pushTerm时不一样？
//        addTextField("test0", "abc-f0");

        for (int i=0; i<25; i++) {
            addTextField("test0", "abc_d" + i);
        }

        addTextField("test0", "abc_e0");
        addTextField("test0", "abc_f0");
        addTextField("test0", "aaa_d0");

        indexWriter.flush();

        testQuery();

        indexWriter.flush();
        indexWriter.commit();
//        indexWriter.close();
    }

    @Test
    public void testInsert() throws IOException, ParseException {
        String querystr = "have";
        String queryField = "test0";


        addTextField("test0", "superman has more strength than batman batman and superman superman");
        addTextField("test0", "batman cannot fly");
        addTextField("test0", "superman can fly");


        indexWriter.commit();


        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);


        search(searcher, queryField, querystr);



        addTextField("test0", "superman can fly 2");


        indexWriter.flush();

        reader = DirectoryReader.open(index);
        searcher = new IndexSearcher(reader);


        search(searcher, queryField, querystr);

        indexWriter.commit();

        reader = DirectoryReader.open(index);
        searcher = new IndexSearcher(reader);

        search(searcher, queryField, querystr);

        indexWriter.flush();
        indexWriter.commit();
//        indexWriter.close();
    }

    private void search(IndexSearcher searcher, String queryField, String querystr) throws ParseException, IOException {
        Query q = new QueryParser(queryField, analyzer).parse(querystr);
        int hitsPerPage = 20;


        TopDocs docs = searcher.search(q, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;

        System.out.println("Found " + hits.length + " hits.");
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get(queryField) + "\t" + d.get("title"));
        }

    }


    @Test
    public void testDelete() throws IOException, ParseException {

        Document doc = new Document();
        doc.add(new StringField("key", "idxxx", Field.Store.YES));
        doc.add(new TextField("value", "hahaha haha", Field.Store.YES));

        indexWriter.addDocument(doc);


        doc = new Document();
        doc.add(new StringField("key", "id", Field.Store.YES));
        doc.add(new TextField("value", "hahaha haha", Field.Store.YES));

        indexWriter.addDocument(doc);


        indexWriter.deleteDocuments(new Term("key", "id"));


        indexWriter.flush();
        indexWriter.commit();

        String querystr = "id";
        Query q = new QueryParser("key", analyzer).parse(querystr);
        int hitsPerPage = 20;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);



        TopDocs docs = searcher.search(q, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;

        System.out.println("Found " + hits.length + " hits.");
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("key") + "\t" + d.get("value"));
        }


        indexWriter.deleteDocuments(new Term("key", "id"));

        docs = searcher.search(q, hitsPerPage);
        hits = docs.scoreDocs;

        System.out.println("Found " + hits.length + " hits.");
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("key") + "\t" + d.get("value"));
        }


        indexWriter.flush();
        indexWriter.commit();


        //open ntr
        reader = DirectoryReader.open(indexWriter);
        //reader = DirectoryReader.open(indexWriter, true, true);
        searcher = new IndexSearcher(reader);


        docs = searcher.search(q, hitsPerPage);
        hits = docs.scoreDocs;

        System.out.println("Found " + hits.length + " hits.");
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("key") + "\t" + d.get("value"));
        }

        reader.close();
    }



    public void addTextField(String fieldName, String value) throws IOException {
        Document doc = new Document();
        doc.add(new TextField(fieldName, value, Field.Store.YES));
        indexWriter.addDocument(doc);
    }

    @Test
    public void testQuery() throws ParseException, IOException {
        String querystr = "mao";
        Query q = new QueryParser("title", analyzer).parse(querystr);

        // 3. search
        int hitsPerPage = 20;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(q, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;

        // 4. display results
        System.out.println("Found " + hits.length + " hits.");
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
        }

        // reader can only be closed when there
        // is no need to access the documents any more.
        reader.close();
    }


    private void addDoc(String title, String isbn) throws IOException {
        Document doc = new Document();

        doc.add(new TextField("title", title, Field.Store.YES));

        // use a string field for isbn because we don't want it tokenized
        doc.add(new StringField("isbn", isbn, Field.Store.YES));

        indexWriter.addDocument(doc);
    }

    public static void main(String[] args) throws Exception{
// 0. Specify the analyzer for tokenizing text.
        //    The same analyzer should be used for indexing and searching
        StandardAnalyzer analyzer = new StandardAnalyzer();


        // 1. create the index
        Directory index = new SimpleFSDirectory(Paths.get("C:\\Users\\sup\\Desktop\\tmp"));

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter w = new IndexWriter(index, config);
        addDoc(w, "1", "AAA Lucene in Action", "193398817");
        addDoc(w, "2", "AAA Lucene for 2", "55320055Z");
        addDoc(w, "1", "AAA Lucene for Dummies", "55320055Z");
//        addDoc(w, "AAA Managing Gigabytes", "55063554A");
//        addDoc(w, "AAA The Art of Computer Science", "9900333X");
//        addDoc(w, "AAA The Art to Science", "123456465");
//        addDoc(w, "AAA The Art to Science", "123456465");
        w.close();

        // 2. query
        String querystr = args.length > 0 ? args[0] : "Lucene";

        // the "title" arg specifies the default field to use
        // when no field is explicitly specified in the query.
        Query q = new QueryParser("title", analyzer).parse(querystr);

        // 3. search
        int hitsPerPage = 20;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(q, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;

        // 4. display results
        System.out.println("Found " + hits.length + " hits.");
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
        }

        // reader can only be closed when there
        // is no need to access the documents any more.
        reader.close();
    }

    private static void addDoc(IndexWriter w, String id, String title, String isbn) throws IOException {
        Document doc = new Document();

        doc.add(new StringField("_doc_id", id, Field.Store.YES));

        doc.add(new TextField("title", title, Field.Store.YES));

        // use a string field for isbn because we don't want it tokenized
        doc.add(new StringField("isbn", isbn, Field.Store.YES));

        w.addDocument(doc);
//        long seq = w.updateDocument(new Term("_doc_id", id), doc); //先查再删和直接用update哪个快？
//        System.out.println(seq);
        w.commit();
    }

}

