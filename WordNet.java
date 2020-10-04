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

  // constructor takes the name of the two input files
  /**
   * constructs a new WordNet object using file input
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
  }

  /**
   * returns all nouns in the WordNet object in arbitrary order
   * @return all nouns in the WordNet object in arbitrary order
   */
  public Iterable<String> nouns() {
    Bag<String> bag = new Bag<>();
    for (String w : nouns.keys()) bag.add(w);
    return bag;
  }

  /**
   * is the word a WordNet noun?
   * @param word  the word to be queried
   * @return      is the word present in the WordNet object?
   */
  public boolean isNoun(String word) {
    // use a binary search for the word
    return nouns.contains(word);
  }

  // distance between nounA and nounB (defined below)
  public int distance(String nounA, String nounB) {
    return 0;
  }

  // a synset (second field of synsets.txt) 
  //  that is the common ancestor of nounA and nounB
  //  in a shortest ancestral path (defined below)
  public String sap(String nounA, String nounB) {
    return "";
  }

  // do unit testing of this class
  public static void main(String[] args) {
    WordNet a = new WordNet(args[0], args[1]);
    for (String str : a.nouns()) StdOut.println(str);
  }
}
