import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Jeffrey Ding
 * @userid jding94
 * @GTID 903754704
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if ((start == null) || (graph == null) || (!graph.getAdjList().containsKey(start))) {
            throw new IllegalArgumentException("Either one of your inputs is null or the key is not in the graph.");
        }
        Queue<Vertex<T>> queue = new LinkedList<>();
        Set<Vertex<T>> set = new HashSet<>();
        List<Vertex<T>> list = new ArrayList<>();

        queue.add(start);
        set.add(start);

        while (!queue.isEmpty()) {
            Vertex<T> temp = queue.remove();
            list.add(temp);
            for (VertexDistance<T> vdist : graph.getAdjList().get(temp)) {
                if (!set.contains(vdist.getVertex())) {
                    queue.add(vdist.getVertex());
                    set.add(vdist.getVertex());
                }
            }
        }
        return list;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if ((start == null) || (graph == null) || (!graph.getAdjList().containsKey(start))) {
            throw new IllegalArgumentException("Either one of your inputs is null or the key is not in the graph.");
        }
        Set<Vertex<T>> set = new HashSet<>();
        List<Vertex<T>> list = new ArrayList<>();

        dfs(start, graph, set, list);
        return list;
    }

    /**
     * dfs helper method that give recursion to the original dfs function.
     *
     * @param vertex The vertex that we are looking at
     * @param graph The graph to be looked through
     * @param set The answer set
     * @param list The list of visited vertices
     * @param <T> For generic usage
     */
    public static <T> void dfs(Vertex<T> vertex, Graph<T> graph, Set<Vertex<T>> set, List<Vertex<T>> list) {
        set.add(vertex);
        list.add(vertex);

        for (VertexDistance<T> vdist : graph.getAdjList().get(vertex)) {
            if (!set.contains(vdist.getVertex())) {
                dfs(vdist.getVertex(), graph, set, list);
            }
        }
    }
    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if ((start == null) || (graph == null) || (!graph.getAdjList().containsKey(start))) {
            throw new IllegalArgumentException("Either one of your inputs is null or the key is not in the graph.");
        }
        PriorityQueue<VertexDistance<T>> queue = new PriorityQueue<>();
        Map<Vertex<T>, Integer> map = new HashMap<>();
        Set<Vertex<T>> visited = new HashSet<>();

        for (Vertex<T> put : graph.getAdjList().keySet()) {
            if (put.equals(start)) {
                map.put(put, 0);
            } else {
                map.put(put, Integer.MAX_VALUE);
            }
        }

        queue.add(new VertexDistance<>(start, 0));
        while ((!queue.isEmpty()) && !(visited.size() == graph.getVertices().size())) {
            VertexDistance<T> temp = queue.remove();
            if (!visited.contains(temp.getVertex())) {
                visited.add(temp.getVertex());
            }
            for (VertexDistance<T> v : graph.getAdjList().get(temp.getVertex())) {
                int dist = temp.getDistance() + v.getDistance();
                if (map.get(v.getVertex()).compareTo(dist) > 0) {
                    map.put(v.getVertex(), dist);
                    queue.add(new VertexDistance<>(v.getVertex(), dist));
                    if (!visited.contains(v.getVertex())) {
                        visited.add(v.getVertex());
                    }
                }
            }
        }
        return map;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use PriorityQueue, java.util.Set, and any class that 
     * implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if ((start == null) || (graph == null) || (!graph.getAdjList().containsKey(start))) {
            throw new IllegalArgumentException("Either one of your inputs is null or the key is not in the graph.");
        }
        Set<Vertex<T>> visited = new HashSet<>();
        Set<Edge<T>> set = new HashSet<>();
        PriorityQueue<Edge<T>> queue = new PriorityQueue<>();

        for (VertexDistance<T> v : graph.getAdjList().get(start)) {
            queue.add(new Edge<>(start, v.getVertex(), v.getDistance()));
        }

        visited.add(start);
        while ((!queue.isEmpty()) && !(visited.size() == graph.getVertices().size())) {
            Edge<T> temp = queue.remove();
            if (!visited.contains(temp.getV())) {
                set.add(new Edge<>(temp.getU(), temp.getV(), temp.getWeight()));
                set.add(new Edge<T>(temp.getV(), temp.getU(), temp.getWeight()));
                visited.add(temp.getV());
                for (VertexDistance<T> tEdge : graph.getAdjList().get(temp.getV())) {
                    if (!visited.contains(tEdge.getVertex())) {
                        queue.add(new Edge<>(temp.getV(), tEdge.getVertex(), tEdge.getDistance()));
                    }
                }
            }
        }
        if (visited.size() == graph.getVertices().size()) {
            return set;
        } else {
            return null;
        }
    }
}