package lw05.prelab;

import java.util.*;

public class Main {
    static int[][] graph;
    static int size;
    static String[] names;

    // Adds a two-way walkway (complete, do not change)
    static void addEdge(int from, int to, int minutes) {
        graph[from][to] = minutes;
        graph[to][from] = minutes;
    }

    static List<Integer> bfs(int start) {
        List<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            int current = queue.poll();                  // take the building at the front of the queue
            order.add(current);
            for (int next = 0; next < size; next++) {
                if (graph[current][next] > 0 && !visited.contains(next)) {                          // next is a neighbour that has not been visited
                    visited.add(next);
                    queue.add(next);                        // put next at the back of the queue
                }
            }
        }
        return order;
    }

    static Set<Integer> dfsVisited = new HashSet<>();   // shared by every recursive call of dfs()
    static List<Integer> dfsOrder = new ArrayList<>();  // shared by every recursive call of dfs()

    static void dfs(int current) {
        dfsVisited.add(current);                                    // mark current as visited
        dfsOrder.add(current);
        for (int next = 0; next < size; next++) {
            if (graph[current][next] > 0 && !dfsVisited.contains(next)) {
                dfs(next);                            // go deeper from next
            }
        }
    }

    static void dijkstraAlg(int start, int dest) {
        int[] dist = new int[size];
        for (int i = 0; i < size; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[start] = 0;
        boolean[] visited = new boolean[size];
        int[] prev = new int[size];

        for (int i = 0; i < size; i++) {
            int current = findTheNextNode(visited, dist);
            visited[current] = true;                                // mark current as visited
            for (int next = 0; next < size; next++) {
                int newDist = dist[current] + graph[current][next];
                if (graph[current][next] > 0 && newDist < dist[next]) {   // the new distance is shorter
                    dist[next] = newDist;
                    prev[next] = current;                        // remember that next is reached from current
                }
            }
        }

        System.out.println("=== Shortest Time from " + names[start] + " ===");
        for (int i = 0; i < size; i++) {
            System.out.println(names[i] + " : " + dist[i] + " min");
        }
        System.out.println("=== Shortest Route from " + names[start] + " to " + names[dest] + " ===");
        route(dest, start, prev);
        System.out.println();
        System.out.println("Total time: " + dist[dest] + " min");
    }

    // Helper of dijkstraAlg(): finds the unvisited building with the smallest distance
    static int findTheNextNode(boolean[] visited, int[] dist) {
        int min = Integer.MAX_VALUE;
        int closest = -1;
        for (int i = 0; i < size; i++) {
            if (!visited[i] && dist[i] < min) {                          // i is unvisited and closer than min
                min = dist[i];
                closest = i;
            }
        }
        return closest;
    }

    // Helper of dijkstraAlg(): prints the route from start to current
    static void route(int current, int start, int[] prev) {
        if (current == start) {
            System.out.print(names[current]);
        } else {
            route(prev[current], start, prev);                                // print the route up to the previous building
            System.out.print(" -> " + names[current]);
        }
    }

    // Prints the buildings as A -> B -> C (complete, do not change)
    static void printPath(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.print(names[list.get(i)]);
            if (i < list.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        String fileName = args.length > 0 ? args[0] : "campus.txt";
        try (Scanner scanner = new Scanner(new java.io.File(fileName))) {
            size = scanner.nextInt();
            names = new String[size];
            for (int i = 0; i < size; i++) {
                names[i] = scanner.next();
            }

            graph = new int[size][size];
            int edges = scanner.nextInt();
            for (int i = 0; i < edges; i++) {
                int from = scanner.nextInt();
                int to = scanner.nextInt();
                int minutes = scanner.nextInt();
                addEdge(from, to, minutes);
            }

            int start = scanner.nextInt();
            int dest = scanner.nextInt();

            System.out.println("=== BFS from " + names[start] + " ===");
            printPath(bfs(start));

            System.out.println("=== DFS from " + names[start] + " ===");
            dfs(start);
            printPath(dfsOrder);

            dijkstraAlg(start, dest);
        } catch (java.io.FileNotFoundException e) {
            System.out.println("File " + fileName + " tidak ditemukan.");
        }
    }
}
