import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
  private Digraph G;

  // *************************** CONSTRUCTOR ***************************
  /**
   * initializes SAP object from a directed graph
   * 
   * @param G the Digraph
   */
  public SAP(Digraph G) {
    this.G = G;
  }

  // *************************** PUBLIC METHODS ***************************
  /**
   * length of shortest ancestral path between two vertices
   * 
   * @param v first source vertex 
   * @param w second source vertex
   * @return length of shortest ancestral path between v and w; -1 if no such path
   */
  public int length(int v, int w) {
    Bag<Integer> vBag = new Bag<>();
    Bag<Integer> wBag = new Bag<>();
    vBag.add(v);
    wBag.add(w);
    return getShortest(vBag, wBag)[1];
  }

  /**
   * Returns shortest common ancestor to two source vertices of a digraph
   * 
   * @param v first source vertex
   * @param w second source vertex
   * @return  vertex that is shortest common ancestor to both v and w (-1 if none)
   */
  public int ancestor(int v, int w) {
    Bag<Integer> vBag = new Bag<>();
    Bag<Integer> wBag = new Bag<>();
    vBag.add(v);
    wBag.add(w);
    return getShortest(vBag, wBag)[0];
  }

  // length of shortest ancestral path between any vertex in v and any vertex in
  // w; -1 if no such path
  public int length(Iterable<Integer> v, Iterable<Integer> w) {
    return getShortest(v, w)[1];
  }

  // A (not necessarily the shortest) common ancestor that participates in shortest ancestral path; -1 if no such
  // path
  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    return getShortest(v, w)[0];
  }

  // *************************** PRIVATE METHODS ***************************
  // run bfs from v to root, 
  //    add each vertex as key, distance as value
  // run bfs from w to root,
  //    if the node is found in v bfs,
  //    get the total dist and cmp to running total
  private int[] getShortest(Iterable<Integer> v, Iterable<Integer> w) {
    Map<Integer, Integer> vPaths = getPaths(v);
    Map<Integer, Integer> wPaths = getPaths(w);
    int shortestLen = -1;
    int sap = -1;

    for (Map.Entry<Integer, Integer> entry : wPaths.entrySet()) {
      int wVertex = entry.getKey();
      int wDist = entry.getValue();
      if (vPaths.containsKey(wVertex) && 
          (vPaths.get(wVertex) + wDist < shortestLen || shortestLen == -1)) {
        sap = wVertex;
        shortestLen = vPaths.get(wVertex) + wDist;
      }
    }
    return new int[] {sap, shortestLen};
  }

  // helper function
  // uses bfs to search from vertex s to root
  // returns hashMap of all vertices from path v to root
  // key is vertex, value is distance from v
  private Map<Integer, Integer> getPaths(Iterable<Integer> s) {
    HashMap<Integer, Integer> pathMap = new HashMap<>();
    Queue<Integer> q = new Queue<>();

    for (int v : s) {
      pathMap.put(v, 0);
      q.enqueue(v);
    }
    
    while (!q.isEmpty()) {
      int v = q.dequeue();
      for (int w : G.adj(v)) {
        if (!pathMap.containsKey(w)) {
          pathMap.put(w, pathMap.get(v) + 1);
          q.enqueue(w);
        }
      }
    }
    return pathMap;
  }

  //*************************** MAIN METHOD ***************************
  /**
   * unit testing
   * 
   * @param args name of the file holding data to construct digraph
   */
  public static void main(String[] args) {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    
    // test ancestor(), length() with two single vertices
    for (int i = 0; i < G.V(); i++) {
      for (int j = i; j < G.V(); j++) {
        StdOut.println(i + ", " + j + ":\t\t" + sap.ancestor(i, j) + "\t" + sap.length(i, j));
      }
    }

    // test ancestor(), length() with bags of vertices
    Bag<Integer> v = new Bag<>();
    Bag<Integer> w = new Bag<>();
    v.add(7); v.add(0);
    w.add(12); w.add(11);
    StdOut.println(sap.ancestor(v, w) + "\t" + sap.length(v, w));
  }
}
