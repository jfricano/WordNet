public class Outcast {
  private final WordNet wordnet;

  // constructor takes a WordNet object
  public Outcast(WordNet wordnet) {
    this.wordnet = wordnet;
  }

  // given an array of WordNet nouns, return an outcast
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

  // see test client below
  public static void main(String[] args) {

  }
}
