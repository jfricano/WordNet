import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
  private Digraph G;

  /**
   * initializes SAP object from a directed graph
   * 
   * @param G the Digraph
   */
  public SAP(Digraph G) {
    this.G = G;
  }

  /**
   * length of shortest ancestral path between two vertices
   * 
   * @param v first source vertex 
   * @param w second source vertex
   * @return length of shortest ancestral path between v and w; -1 if no such path
   */
  public int length(int v, int w) {
    Map<Integer, Integer> vPaths = getPaths(v);
    int sap = -1;
    HashMap<Integer, Integer> wPaths = new HashMap<>();
    Queue<Integer> q = new Queue<>();
    int s = w;

    wPaths.put(s, 0);
    q.enqueue(s);

    while (!q.isEmpty()) {
      int x = q.dequeue();
      for (int y : G.adj(x)) {
        if (!wPaths.containsKey(y)) {
          wPaths.put(y, wPaths.get(x) + 1);
          q.enqueue(y);
        }
      }
      if (vPaths.containsKey(x) && 
          (sap == -1 || vPaths.get(x) + wPaths.get(x) < sap)) {
        sap = vPaths.get(x) + wPaths.get(x);
      }
    }
    return sap;
  }

  /**
   * Returns shortest common ancestor to two source vertices of a digraph
   * 
   * @param v first source vertex
   * @param w second source vertex
   * @return  vertex that is shortest common ancestor to both v and w (-1 if none)
   */
  public int ancestor(int v, int w) {
    // run bfs from v to root, 
    //    add each vertex as key, distance as value
    // run bfs from w to root,
    //    if the node is found in v bfs,
    //    get the total dist and cmp to running total
    Map<Integer, Integer> vPaths = getPaths(v);
    int sap = -1;
    HashMap<Integer, Integer> wPaths = new HashMap<>();
    Queue<Integer> q = new Queue<>();
    int s = w;

    wPaths.put(s, 0);
    q.enqueue(s);

    while (!q.isEmpty()) {
      int x = q.dequeue();
      for (int y : G.adj(x)) {
        if (!wPaths.containsKey(y)) {
          wPaths.put(y, wPaths.get(x) + 1);
          q.enqueue(y);
        }
      }
      if (vPaths.containsKey(x) && (sap == -1 || vPaths.get(x) + wPaths.get(x) < 
                                                 vPaths.get(sap) + wPaths.get(sap))) {
        sap = x;
      }
    }
    return sap;
  }

  // helper function
  // uses bfs to search from vertex s to root
  // returns hashMap of all vertices from path v to root
  // key is vertex, value is distance from v
  private Map<Integer, Integer> getPaths(int s) {
    HashMap<Integer, Integer> pathMap = new HashMap<>();
    Queue<Integer> q = new Queue<>();
    
    pathMap.put(s, 0);
    q.enqueue(s);
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

  // length of shortest ancestral path between any vertex in v and any vertex in
  // w; -1 if no such path
  public int length(Iterable<Integer> v, Iterable<Integer> w) {
    // int queryLength;
    // int minLength = -1;
    // for (int i : v) {
    //   for (int j : w) {
    //     queryLength = length(i, j);
    //     if (queryLength == -1) continue;
    //     if (queryLength < minLength || minLength == -1) minLength = queryLength;
    //   }
    // }
    // return minLength;
    return 0;
  }

  // A (not necessarily the shortest) common ancestor that participates in shortest ancestral path; -1 if no such
  // path
  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    return 0;
  }

  // do unit testing of this class
  public static void main(String[] args) {
    In in = new In("data/digraph25.txt");
    Digraph G = new Digraph(in);
    // StdOut.println(G.toString());
    SAP sap = new SAP(G);
    for (int i = 0; i < G.V(); i++) {
      for (int j = i; j < G.V(); j++) {
        StdOut.println(i + ", " + j + ":\t\t" + sap.ancestor(i, j) + "\t" + sap.length(i, j));
      }
    }
  }
}
