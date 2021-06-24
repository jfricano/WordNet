# WordNet

<ul>
  <li><a target="_blank" href="https://algs4.cs.princeton.edu/code/" >Info and how to install environment</a></li>
  <li><a href="https://algs4.cs.princeton.edu/code/javadoc/" target="_blank" rel="noopener noreferrer">algs4.jar Documentation</a></li>
  <li><a href="https://algs4.cs.princeton.edu/code/algs4.jar">Dowload Princeton's algs4.jar package</a></li>
</ul>

<b>Challenge. </b>
<a href="https://wordnet.princeton.edu/">WordNet</a> is a semantic lexicon for the English language that computational linguists and cognitive scientists use extensively. For example, WordNet was a key component in IBM’s Jeopardy-playing <a href="https://en.wikipedia.org/wiki/Watson_(computer)">Watson</a> computer system. WordNet groups words into sets of synonyms called synsets. For example, { AND circuit, AND gate } is a synset that represent a logical gate that fires only when all of its inputs fire. WordNet also describes semantic relationships between synsets. One such relationship is the is-a relationship, which connects a hyponym (more specific synset) to a hypernym (more general synset). For example, the synset { gate, logic gate } is a hypernym of { AND circuit, AND gate } because an AND gate is a kind of logic gate.

<h2>WordNet.java</h2>
<b>The WordNet digraph.</b> The WordNet object is represented as a directed graph (digraph).  Each vertex v is an integer that represents a synset, and each directed edge v→w represents that w is a hypernym of v. The WordNet digraph is a rooted DAG: it is acyclic and has one vertex—the root—that is an ancestor of every other vertex. However, it is not necessarily a tree because a synset can have more than one hypernym. A small subgraph of the WordNet digraph appears below.

<b>The WordNet input file formats.</b> The files are in comma-separated values (CSV) format: each line contains a sequence of fields, separated by commas.

<ul>
<li>List of synsets. The file synsets.txt contains all noun synsets in WordNet, one per line. Line i of the file (counting from 0) contains the information for synset i. The first field is the synset id, which is always the integer i; the second field is the synonym set (or synset); and the third field is its dictionary definition (or gloss), which is not relevant to this project.</li>

<li>List of hypernyms. The file hypernyms.txt contains the hypernym relationships. Line i of the file (counting from 0) contains the hypernyms of synset i. The first field is the synset id, which is always the integer i; subsequent fields are the id numbers of the synset’s hypernyms.</li>
</ul>

<b>WordNet data type.</b> WordNet implements an immutable with the following API:

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)

    // returns all WordNet nouns
    public Iterable<String> nouns()

    // is the word a WordNet noun?
    public boolean isNoun(String word)

    // distance between nounA and nounB
    public int distance(String nounA, String nounB)

    // a synset (second field of synsets.txt) 
    // that is the common ancestor of nounA and nounB
    // in a shortest ancestral path
    public String sap(String nounA, String nounB)

    // do unit testing of this class
    public static void main(String[] args)

<b>Performance.</b>  WordNet uses space linear in the input size (size of synsets and hypernyms files). The constructor should takes time linear in the input size. The method isNoun() runs in time constant. The methods distance() and sap() run in time linear in the size of the WordNet digraph.

<h2>SAP.java</h2>

<b>Shortest ancestral path.</b> An ancestral path between two vertices v and w in a digraph is a directed path from v to a common ancestor x, together with a directed path from w to the same ancestor x. A shortest ancestral path is an ancestral path of minimum total length. We refer to the common ancestor in a shortest ancestral path as a shortest common ancestor.

<b>SAP data type.</b> SAP Implements an immutable data type SAP with the following API:

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G)

	// length of shortest ancestral path between v and w
	public int length(int v, int w)

	// a common ancestor of v and w that participates in a shortest ancestral path
	public int ancestor(int v, int w)

	// length of shortest ancestral path between any vertex in v and any vertex in w
	public int length(Iterable<Integer> v, Iterable<Integer> w)

	// a common ancestor that participates in shortest ancestral path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w)

	// do unit testing of this class
	public static void main(String[] args)

<b>Performance.</b>  All methods (and the constructor) take time at most proportional to E + V in the worst case, where E and V are the number of edges and vertices in the digraph, respectively. The data type uses space proportional to E + V.

<b>Test client.</b> The  test client takes the name of a digraph input file as as a command-line argument, constructs the digraph, reads in vertex pairs from standard input, and prints out the length of the shortest ancestral path between the two vertices and a common ancestor that participates in that path.

<b>Measuring the semantic relatedness of two nouns.</b> Semantic relatedness refers to the degree to which two concepts are related. 

We define the semantic relatedness of two WordNet nouns x and y as follows:
<ul>
<li>A = set of synsets in which x appears</li>
<li>B = set of synsets in which y appears</li>
<li>distance(x, y) = length of shortest ancestral path of subsets A and B</li>
<li>sca(x, y) = a shortest common ancestor of subsets A and B</li>
</ul>

<h2>Outcast.java</h2>
Given a list of WordNet nouns x1, x2, ..., xn, which noun is the least related to the others?

Outcast implements an immutable data type with the following API:

	public Outcast(WordNet wordnet)         // constructor takes a WordNet object
	public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
	public static void main(String[] args)  // test client (not used)

<h2>Data files for unit testing</h2>
These text files can be found in the data subfolder.
