import java.util.*;



//https://www.geeksforgeeks.org/graph-measurements-length-distance-diameter-eccentricity-radius-center/
/** Container class to different classes, that makes the whole
 * set of classes one class formally.
 */
public class GraphTask {

   /** Main method. */
   public static void main (String[] args) {
      GraphTask g = new GraphTask();
      g.run("v5");
   }
   public void run(String vertexId) {
      Graph g = new Graph("G");
      g.createRandomSimpleGraph(6, 9);
      System.out.println (g);

      // Find the vertex with the given vertexId
      Vertex vertexToFind = null;
      Vertex currentVertex = g.first;
      while (currentVertex != null) {
         if (currentVertex.id.equals(vertexId)) {
            vertexToFind = currentVertex;
            break;
         }
         currentVertex = currentVertex.next;
      }

      if (vertexToFind == null) {
         System.out.println("Vertex with ID " + vertexId + " not found.");
         return;
      }

      // Calculate the eccentricity of the specified vertex
      int vertexEccentricity = g.eccentricity(vertexToFind);

      System.out.println("Eccentricity of vertex " + vertexId + ": " + vertexEccentricity);
   }

//   public void run() {
//      Graph g = new Graph ("G");
//      g.createRandomSimpleGraph (22,21);
//      System.out.println (g);
//      Vertex v = g.first;
//
//      int graphEccentricity = g.findEccentricity();
//
//      System.out.println("Eccentricity of the graph: " + graphEccentricity);
//
//      int vertexEccentricity = g.eccentricity(v);
//
//      System.out.println("Eccentricity of the given vertex " + vertexEccentricity);
//
//   }


   class Vertex {

      private String id;
      private Vertex next;
      private Arc first;
      private int info = 0;
      // You can add more fields, if needed

      Vertex (String s, Vertex v, Arc e) {
         id = s;
         next = v;
         first = e;
      }

      Vertex (String s) {
         this (s, null, null);
      }

      @Override
      public String toString() {
         return id;
      }

   }


   /** Arc represents one arrow in the graph. Two-directional edges are
    * represented by two Arc objects (for both directions).
    */
   class Arc {

      private String id;
      private Vertex target;
      private Arc next;
      private int info = 0;
      // You can add more fields, if needed

      Arc (String s, Vertex v, Arc a) {
         id = s;
         target = v;
         next = a;
      }

      Arc (String s) {
         this (s, null, null);
      }

      @Override
      public String toString() {
         return id;
      }

   }


   class Graph {

      private String id;
      private Vertex first;
      private int info = 0;
      // You can add more fields, if needed

      Graph (String s, Vertex v) {
         id = s;
         first = v;
      }

      Graph (String s) {
         this (s, null);
      }

      @Override
      public String toString() {
         String nl = System.getProperty ("line.separator");
         StringBuffer sb = new StringBuffer (nl);
         sb.append (id);
         sb.append (nl);
         Vertex v = first;
         while (v != null) {
            sb.append (v.toString());
            sb.append (" -->");
            Arc a = v.first;
            while (a != null) {
               sb.append (" ");
               sb.append (a.toString());
               sb.append (" (");
               sb.append (v.toString());
               sb.append ("->");
               sb.append (a.target.toString());
               sb.append (")");
               a = a.next;
            }
            sb.append (nl);
            v = v.next;
         }
         return sb.toString();
      }

      public Vertex createVertex (String vid) {
         Vertex res = new Vertex (vid);
         res.next = first;
         first = res;
         eccentricity(res);
         return res;
      }

      public Arc createArc (String aid, Vertex from, Vertex to) {
         Arc res = new Arc (aid);
         res.next = from.first;
         from.first = res;
         res.target = to;
         return res;
      }

      /**
       * Create a connected undirected random tree with n vertices.
       * Each new vertex is connected to some random existing vertex.
       * @param n number of vertices added to this graph
       */
      public void createRandomTree (int n) {
         if (n <= 0)
            return;
         Vertex[] varray = new Vertex [n];
         for (int i = 0; i < n; i++) {
            varray [i] = createVertex ("v" + String.valueOf(n-i));
            if (i > 0) {
               int vnr = (int)(Math.random()*i);
               createArc ("a" + varray [vnr].toString() + "_"
                  + varray [i].toString(), varray [vnr], varray [i]);
               createArc ("a" + varray [i].toString() + "_"
                  + varray [vnr].toString(), varray [i], varray [vnr]);
            } else {}
         }
      }

      /**
       * Create an adjacency matrix of this graph.
       * Side effect: corrupts info fields in the graph
       * @return adjacency matrix
       */
      public int[][] createAdjMatrix() {
         info = 0;
         Vertex v = first;
         while (v != null) {
            v.info = info++;
            v = v.next;
         }
         int[][] res = new int [info][info];
         v = first;
         while (v != null) {
            int i = v.info;
            Arc a = v.first;
            while (a != null) {
               int j = a.target.info;
               res [i][j]++;
               a = a.next;
            }
            v = v.next;
         }
         return res;
      }

      /**
       * Create a connected simple (undirected, no loops, no multiple
       * arcs) random graph with n vertices and m edges.
       * @param n number of vertices
       * @param m number of edges
       */
      public void createRandomSimpleGraph (int n, int m) {
         if (n <= 0)
            return;
         if (n > 2500)
            throw new IllegalArgumentException ("Too many vertices: " + n);
         if (m < n-1 || m > n*(n-1)/2)
            throw new IllegalArgumentException 
               ("Impossible number of edges: " + m);
         first = null;
         createRandomTree (n);       // n-1 edges created here
         Vertex[] vert = new Vertex [n];
         Vertex v = first;
         int c = 0;
         while (v != null) {
            vert[c++] = v;
            v = v.next;
         }
         int[][] connected = createAdjMatrix();
         int edgeCount = m - n + 1;  // remaining edges
         while (edgeCount > 0) {
            int i = (int)(Math.random()*n);  // random source
            int j = (int)(Math.random()*n);  // random target
            if (i==j) 
               continue;  // no loops
            if (connected [i][j] != 0 || connected [j][i] != 0) 
               continue;  // no multiple edges
            Vertex vi = vert [i];
            Vertex vj = vert [j];
            createArc ("a" + vi.toString() + "_" + vj.toString(), vi, vj);
            connected [i][j] = 1;
            createArc ("a" + vj.toString() + "_" + vi.toString(), vj, vi);
            connected [j][i] = 1;
            edgeCount--;  // a new edge happily created
         }
      }
      /////////////////////////////////////////////
      //https://github.com/CarterZhou/algorithms_practice/blob/master/algorithms4th/graph/undirected/GraphProperties.java
      //https://github.com/junglie85/sedgewick_algorithms/blob/master/src/main/java/uk/ashleybye/sedgewick/graph/Eccentricity.java
         public int countVertices() {
            int count = 0;
            Vertex current = first;
            while (current != null) {
               count++;
               current = current.next;
               }
            //System.out.println (count);
            return count;
            }
      /**
       * Find the eccentricity of a given vertex in the graph.
       * The eccentricity of a vertex is the maximum distance between it and any other vertex in the graph.
       * Returns -1 if the graph is not connected.
       * @param v the vertex to find the eccentricity of
       * @return the eccentricity of the given vertex or -1 if the graph is not connected
       */
      public int eccentricity(Vertex v) {
         int[] distances = distancesFrom(v);
         int maxDistance = 0;
         for (int i = 0; i < distances.length; i++) {
            if (distances[i] == Integer.MAX_VALUE) {
               return -1; // The graph is not connected
            }
            if (distances[i] > maxDistance) {
               maxDistance = distances[i];
            }
         }
         return maxDistance;
      }

      /**
       * Compute the distances from a given vertex to all other vertices in the graph.
       * @param v the source vertex
       * @return an array containing the distances from the source vertex to all other vertices
       */
      private int[] distancesFrom(Vertex v) {
         int[] distances = new int[countVertices()];
         for (int i = 0; i < distances.length; i++) {
            distances[i] = Integer.MAX_VALUE;
         }
         distances[v.info] = 0;

         Queue<Vertex> queue = new LinkedList<>();
         queue.add(v);

         while (!queue.isEmpty()) {
            Vertex current = queue.remove();
            Arc a = current.first;
            while (a != null) {
               if (distances[a.target.info] == Integer.MAX_VALUE) {
                  distances[a.target.info] = distances[current.info] + 1;
                  queue.add(a.target);
               }
               a = a.next;
            }
         }
         return distances;
      }
      /**
       * Calculate the eccentricity of the graph.
       * The eccentricity of a graph is the maximum eccentricity of its vertices.
       * @return the eccentricity of the graph
       */
      public int findEccentricity() {
         int maxEccentricity = 0;
         Vertex current = first;
         while (current != null) {
            int currentEccentricity = eccentricity(current);
            if (currentEccentricity > maxEccentricity) {
               maxEccentricity = currentEccentricity;
            }
            current = current.next;
         }
         return maxEccentricity;
      }

   }

} 

