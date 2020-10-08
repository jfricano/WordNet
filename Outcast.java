public class Outcast {
  private final WordNet wordnet;

  /**
   * constructs Outcast object
   * 
   * @param wordnet wordnet object in which search will be conducted
   */
  public Outcast(WordNet wordnet) {
    this.wordnet = wordnet;
  }

  /**
   * returns an outcast given array of WordNet nouns
   * 
   * @param nouns wordnet nouns to be queried
   * @return      outcast, i.e. the least related noun, among nouns in passed array
   */
  public String outcast(String[] nouns) {
    String outkast = "";
    int outkastDist = -1;

    for (int i = 0; i < nouns.length; i++) {
      int nounDist = 0;
      for (int j = 0; j < nouns.length; j++) {
        if (j == i) continue;
        nounDist += wordnet.distance(nouns[i], nouns[j]);
      }
      if (nounDist > outkastDist) {
        outkastDist = nounDist;
        outkast = nouns[i];
      }
    }
    return outkast;
  }

  /**
   * main method not used
   * 
   * @param args
   */
  public static void main(String[] args) {
    // main method not used
  }
}
