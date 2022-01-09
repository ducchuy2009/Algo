public class ExhaustiveSearch {
    private static int[][] trace_support;
    private static boolean trace_visited;
    private static int[][] map;

    public static String[] solveGreedyGnomes(int[][] map, int rows, int columns) {
        ExhaustiveSearch.map = map;

        int maxGold = 0;
        int bestX = 0;
        int bestY = 0;

        // Loop through the maps
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                trace_visited = false;
                int gold = bf(i, j);
                if (gold > maxGold && trace_visited) {
                    maxGold = gold;
                    bestX = i;
                    bestY = j;
                }
            }
        }

        trace_support = new int[rows][columns];
        maxGold = trace(bestX, bestY);
        // Output path
        StringBuilder steps = new StringBuilder();

        while (bestX != 0 || bestY != 0) {
            // Record directions
            if (trace_support[bestX][bestY] == 1) {
                steps.append("D");
                bestX--;
            } else {
                steps.append("R");
                bestY--;
            }
        }
        steps.reverse();
        
        // return an array of int: [0] = max gold, [1] = previous step; previous step:
        // 1: R, 2: D
        return new String[] {String.valueOf(maxGold), String.valueOf(steps)};
    }

    // Return direction of trace
    // Trace back from the final point up to the first point [0;0]
    private static int trace(int x, int y) {
        
        // Current position is in the middle of the map
        if (x > 0 && y > 0) {
            if (map[x][y] == -1) {
                return -1;
            }

            int d = map[x][y] + trace(x - 1, y);
            int r = map[x][y] + trace(x, y - 1);

            if (d > r) {
                trace_support[x][y] = 1; // Direction "D"
                return d;
            } else {
                trace_support[x][y] = 0; // Direction "R"
                return r;
            }

        // Current position is in top of the map
        } else if (x > 0) {
            if (map[x][y] == -1) {
                return -1;
            }

            trace_support[x][y] = 1;
            return (map[x][y] + trace(x - 1, y));

        // Current position is in the left of the map
        } else if (y > 0) {
            if (map[x][y] == -1) {
                return -1;
            }

            trace_support[x][y] = 0;
            return (map[x][y] + trace(x, y - 1));

        // Current position is in the first point
        } else if (x == 0 && y == 0) {
            return map[x][y];

        }

        return 0;
    }

    // Return max gold
    private static int bf(int x, int y) {
        
        // Current position is in the middle of the map
        if (x > 0 && y > 0) {
            if (map[x][y] == -1) {
                return -1;
            }
            return Math.max((map[x][y] + bf(x - 1, y)), (map[x][y] + bf(x, y - 1)));

        // Current position is in top of the map
        } else if (x > 0) {
            if (map[x][y] == -1) {
                return -1;
            }
            return (map[x][y] + bf(x - 1, y));

        // Current position is in the left of the map
        } else if (y > 0) {
            if (map[x][y] == -1) {
                return -1;
            }
            return (map[x][y] + bf(x, y - 1));

        // Current position is in the first point
        } else if (x == 0 && y == 0) {
            trace_visited = true;
            return map[x][y];

        }

        return 0;
    }
}
