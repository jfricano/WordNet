import java.util.ArrayList;

import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {
  // hash table to hold nouns; key is word, value is ID
  // array of bag<> objects to hold IDs and associated synsets, ID = array index
  // digraph to hold relationships
  LinearProbingHashST<String, Bag<Integer>> nouns;
  ArrayList<String> synsetIdx;
  Digraph wordNet;
  // SAP sap;

  /**
   * constructs a new WordNet object using file input
   * 
   * @param synsets   name of file containing synsets as CSVs 
   *                  (<integer id>, <synset (words separated by whitespace)>, 
   *                   <gloss (not used here)>)
   * @param hypernyms name of file containing hypernym connections as CSVs
   *                  (hyponym, hypernym)
   */
  public WordNet(String synsets, String hypernyms) {
    In in;                    // file to be read in
    String[] line, synset;    // line read in, synset read from line
    int id;                   // synset id
    Bag<Integer> value;       // value to be associated with noun (as key)

    nouns = new LinearProbingHashST<>();
    synsetIdx = new ArrayList<>();
    in = new In(synsets);
    while (!in.isEmpty()) {
      line = in.readLine().split(",");
      synset = line[1].split(" ");
      id = Integer.parseInt(line[0]);
      // add the synset to the synset index
      synsetIdx.add(id, line[1]);
      // add each word of the synset to the word list
      // and associate the value with the word
      // use bag to capture all duplicate entries of the word
      for (int i = 0; i < synset.length; i++) {
        if (!isNoun(synset[i])) value = new Bag<>();
        else                    value = nouns.get(synset[i]);
        value.add(id);
        nouns.put(synset[i], value);
      }
    }
    // trim the arrayList to size
    synsetIdx.trimToSize();

    // add edges to digraph (from file input)
    wordNet = new Digraph(synsetIdx.size());
    in = new In(hypernyms);
    while (!in.isEmpty()) {
      line = in.readLine().split(",");
      for (int i = 1; i < line.length; i++) 
        wordNet.addEdge(Integer.parseInt(line[0]), Integer.parseInt(line[i]));
    }

    // instantiate the sap object
    // SAP sap = new SAP(wordNet);
  }

  /**
   * returns all nouns in the WordNet object in arbitrary order
   * 
   * @return all nouns in the WordNet object in arbitrary order
   */
  public Iterable<String> nouns() {
    Bag<String> bag = new Bag<>();
    for (String w : nouns.keys()) bag.add(w);
    return bag;
  }

  /**
   * is the word a WordNet noun?
   * 
   * @param word  the word to be queried
   * @return      is the word present in the WordNet object?
   */
  public boolean isNoun(String word) {
    // use a binary search for the word
    return nouns.contains(word);
  }

  // NEED TO DO SOME WORK HERE
  // NOT ENTIRELY CLEAR EXTENT TO WHICH ARE DIFFERENT THAN SAP METHODS
  /**
   * distance between nounA and nounB
   * 
   * @param nounA noun to be queried
   * @param nounB noun to be queried
   * @return      distance between nounA and nounB
   */
  // WILL THIS BE THE SAME AS LENGTH??
  public int distance(String nounA, String nounB) {
    // should I instantiate an sap object as a class field?
    // or just call it here??
    // or maybe i'm doing this wrong altogeher
    // same for sap below
    return new SAP(wordNet).length(nouns.get(nounA), nouns.get(nounB));
  }
  
  /**
   * closest common ancestor of nounA and nounB
   * 
   * @param   nounA noun to be queried
   * @param   nounB noun to be queried
   * @return  closest ancestor of nounA and nounB,
   *          as a whitespace-separated synset of nouns in a String
   */
  // WILL USE SAP.ancestor(), BUT WILL NOT NECC. BE THE SAME B/C WANT THE SHORTEST HERE
  public String sap(String nounA, String nounB) {
    return synsetIdx.get(new SAP(wordNet).ancestor(nouns.get(nounA), nouns.get(nounB)));
  }

  // do unit testing of this class
  /**
   * unit testing of this class
   * 
   * @param args filenames from which to instantiate the WordNet object
   */
  public static void main(String[] args) {
    // test synset constructors and nouns() method
    WordNet a = new WordNet(args[0], args[1]);
    for (String str : a.nouns()) {
      StdOut.println(str);
      Bag<Integer> ids = a.nouns.get(str);
      for (int id : ids) StdOut.print(id + "  ");
      StdOut.println();
    }

    StdOut.println();

    // test digraph constructor
    StdOut.println(a.wordNet.toString());
  }
}
