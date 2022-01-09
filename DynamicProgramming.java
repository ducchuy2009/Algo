public class DynamicProgramming {
    public static String[] solveGreedyGnomes(int[][] map, int rows, int columns) {
        int[][] goldTable = new int[rows][columns];
        int[][] pathTable = new int[rows][columns]; // 0 for go Down, 1 for go Right
        int maxGold = 0, bestX = 0, bestY = 0;

        for(int x = 0; x < rows; x++) {
            for(int y = 0; y < columns; y++) {
                if (map[x][y] == -1) { // if it is an "X"
                    goldTable[x][y] = -1;
                    continue;
                }
                goldTable[x][y] += map[x][y];
                if (x > 0 && y > 0 && (goldTable[x][y - 1] != -1 || goldTable[x - 1][y] != -1)) { // not in first row or first column
                    if (goldTable[x - 1][y] > goldTable[x][y - 1]) {
                        goldTable[x][y] += goldTable[x - 1][y];
                        pathTable[x][y] = 0;
                    } else {
                        goldTable[x][y] += goldTable[x][y - 1];
                        pathTable[x][y] = 1;
                    }
                } else if (x > 0 && goldTable[x-1][y] != -1) { // first column
                    goldTable[x][y] += goldTable[x-1][y];
                    pathTable[x][y]  = 0;
                } else if (y > 0 && goldTable[x][y-1] != -1) { // first row
                    goldTable[x][y] += goldTable[x][y - 1];
                    pathTable[x][y] = 1;
                } else if (x == 0 && y == 0) { // starting point
                    continue;
                } else { // if there was no through path
                    goldTable[x][y] = -1;
                }

                if (maxGold < goldTable[x][y]) { // update when found a better result
                    maxGold = goldTable[x][y];
                    bestX = x;
                    bestY = y;
                }
            }
        }
        StringBuilder steps = new StringBuilder(); // construct the path from the final position back to starting point
        while (bestX != 0 || bestY != 0) {
            if (pathTable[bestX][bestY] == 0) { // was going down
                steps.append("D");
                bestX--;
            } else {
                steps.append("R");
                bestY--;
            }
        }
        return new String[] {String.valueOf(maxGold), String.valueOf(steps.reverse())};
    }
}
