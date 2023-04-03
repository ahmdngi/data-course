import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

/** Testklass.
 * @author jaanus
 */
public class GraphTaskTest {

   //   @Test (timeout=20000)
//   public void test1() {
//      GraphTask.main (null);
//      assertTrue ("There are no tests", true);
//   }
   @Test
   public void testCreateRandomSimpleGraph() {
      GraphTask.Graph g = new GraphTask().new Graph("G");
      g.createRandomSimpleGraph(5, 7);
      assertEquals("The graph should have 5 vertices.", 5, g.countVertices());
   }

   @Test
   public void testEccentricity() {
      GraphTask gt = new GraphTask();
      GraphTask.Graph g = gt.new Graph("G");
      GraphTask.Vertex v1 = g.createVertex("v1");
      GraphTask.Vertex v2 = g.createVertex("v2");
      GraphTask.Vertex v3 = g.createVertex("v3");
      GraphTask.Vertex v4 = g.createVertex("v4");

      g.createArc("a12", v1, v2);
      g.createArc("a21", v2, v1);
      g.createArc("a23", v2, v3);
      g.createArc("a32", v3, v2);
      g.createArc("a34", v3, v4);
      g.createArc("a43", v4, v3);

      int[][] adjMatrix = g.createAdjMatrix();
      for (int i = 0; i < adjMatrix.length; i++) {
         for (int j = 0; j < adjMatrix[i].length; j++) {
            System.out.print(adjMatrix[i][j] + " ");
         }
         System.out.println();
      }
      assertEquals("Eccentricity of v1 should be 3.", 3, g.eccentricity(v1));
      assertEquals("Eccentricity of v2 should be 2.", 2, g.eccentricity(v2));
      assertEquals("Eccentricity of v3 should be 2.", 2, g.eccentricity(v3));
      assertEquals("Eccentricity of v4 should be 3.", 3, g.eccentricity(v4));
   }


   @Test
   public void testEccentricity2() {
      GraphTask graphTask = new GraphTask();
      GraphTask.Graph graph = graphTask.new Graph("TestGraph");

      // Create vertices
      GraphTask.Vertex v1 = graph.createVertex("v1");
      GraphTask.Vertex v2 = graph.createVertex("v2");
      GraphTask.Vertex v3 = graph.createVertex("v3");
      GraphTask.Vertex v4 = graph.createVertex("v4");
      GraphTask.Vertex v5 = graph.createVertex("v5");

      // Create edges
      graph.createArc("a1", v1, v2);
      graph.createArc("a2", v2, v1);
      graph.createArc("a3", v2, v3);
      graph.createArc("a4", v3, v2);
      graph.createArc("a5", v3, v4);
      graph.createArc("a6", v4, v3);
      graph.createArc("a7", v4, v5);
      graph.createArc("a8", v5, v4);


      int[][] adjMatrix = graph.createAdjMatrix();
      for (int i = 0; i < adjMatrix.length; i++) {
         for (int j = 0; j < adjMatrix[i].length; j++) {
            System.out.print(adjMatrix[i][j] + " ");
         }
         System.out.println();
      }

      // Compute eccentricity of each vertex
      int e1 = graph.eccentricity(v1);
      int e2 = graph.eccentricity(v2);
      int e3 = graph.eccentricity(v3);
      int e4 = graph.eccentricity(v4);
      int e5 = graph.eccentricity(v5);

      // Test the computed eccentricities
      assertEquals("Eccentricity of v1 should be 4.", 4, e1);
      assertEquals("Eccentricity of v2 should be 3.", 3, e2);
      assertEquals("Eccentricity of v3 should be 2.", 2, e3);
      assertEquals("Eccentricity of v4 should be 3.", 3, e4);
      assertEquals("Eccentricity of v5 should be 4.", 4, e5);
   }


}

