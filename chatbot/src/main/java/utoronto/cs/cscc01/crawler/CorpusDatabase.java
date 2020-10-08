package utoronto.cs.cscc01.crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CorpusDatabase implements Iterable<HashMap<String, String>> {
  
  List<String> questions = new ArrayList<String> ();
  List<String> answers = new ArrayList<String> ();
  int index = 0;
  
  @Override
  public Iterator<HashMap<String, String>> iterator() {
    // TODO Auto-generated method stub
    return new corpusIterator(this);
  }
  
  private class corpusIterator implements Iterator<HashMap<String, String>> {
    
    corpusIterator (CorpusDatabase obj) { 
      // initialize cursor 
      //System.out.println(index);
      CorpusReader read = new CorpusReader();
      read.corpusCrawlingFP("upload/corpus.txt", questions, answers);
  } 
    
    @Override
    public boolean hasNext() {
      // TODO Auto-generated method stub
      return !(index == questions.size());
    }

    @Override
    public HashMap<String, String> next() {
      // TODO Auto-generated method stub
      //Store the question and answer in a HashMap
      HashMap<String, String> qaSet = new HashMap<String, String>();
      if(!(index == questions.size())) {
        qaSet.put(questions.get(index), answers.get(index));
        //System.out.println(index);
        index += 1;
      }
      return qaSet;
    }
    
  }

}
