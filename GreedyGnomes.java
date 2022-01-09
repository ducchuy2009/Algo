import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GreedyGnomes{

  private final String REGEX_POSITIVE_INTEGER = "^[1-9]\\d*$";

  private int rows;
  private int columns;
  private int[][] map;

  enum Algo {
    DYNAMIC_PROGRAMMING,
    EXHAUSTIVE_SEARCH
  }

  public void loadMap(String filePath) throws Exception {
    List<String> lines = readFile(filePath);
    Optional<int[]> mapParametersOpt = getMapSizes(lines.get(0));
    if (mapParametersOpt.isPresent()) {
      this.rows = mapParametersOpt.get()[0];
      this.columns = mapParametersOpt.get()[1];
      if (rows != lines.size() -1) {
        throw new Exception("Invalid input! Number of rows provided in map parameters " +
                "is not equal to actual number of rows in the map");
      } else if (lines.get(1).split(" ")[0].equals("X")) {
        throw new Exception("Invalid input! Start position must not be 'X'");
      } else {
        this.map = new int[rows][columns];
        for(int x = 0; x < this.map.length; x++) {
          String[] row = lines.get(x+1).split(" ");
          if (this.map[x].length == row.length) {
            for (int y = 0; y < this.map[x].length; y++) {
              if (!isValidCell(row[y])) {
                throw new Exception("Cell " + x + "," + y + " does not contain valid value");
              } else {
                if (row[y].equals("X")) {
                  this.map[x][y] = -1;
                } else if (row[y].equals(".")) {
                  this.map[x][y] = 0;
                } else {
                  this.map[x][y] = Integer.parseInt(row[y]);
                }
              }
            }
          } else {
            throw new Exception("Invalid input! Number of columns provided in map parameters " +
                    "is not equal to actual number of cells in row " + x);
          }
        }
      }
    }
  }

  public String[] resolve(Algo algo) {
    if (algo == Algo.DYNAMIC_PROGRAMMING)
        return DynamicProgramming.solveGreedyGnomes(map, this.rows, this.columns);
    else {
      return ExhaustiveSearch.solveGreedyGnomes(map, this.rows, this.columns);
    }
  }

  public void display(){
    for (int[] rows : this.map) {
      for (int cell : rows) {
        System.out.printf("%1$3s", cell);
      }
      System.out.println();
    }
  }

  public void displayResolvedMap(String steps) {
    int currentX = 0, currentY = 0, stepIndex = 0;
    String currentCell;
    for (int x = 0; x < this.map.length; x++) {
      for (int y = 0; y < this.map[x].length; y++) {
        if (x == currentX && y == currentY) {
          if (map[x][y] == 0) {
            currentCell = "+ ";
          } else {
            currentCell = "G ";
          }
          if (stepIndex < steps.length()) {
            if (steps.charAt(stepIndex) == 'D') currentX++;
            else currentY++;
          }
          stepIndex++;
        } else if (map[x][y] == -1) {
          currentCell = "X ";
        } else if (map[x][y] == 0) {
          currentCell = ". ";
        } else {
          currentCell = map[x][y] + " ";
        }
        System.out.printf("%1$3s", currentCell);
      }
      System.out.println();
    }
  }

  private Optional<int[]> getMapSizes(String line) throws Exception {
    String[] parameters = line.split(" ");
    if (parameters.length != 2) {
      throw new Exception("Invalid Input! First line must contain exactly two parameters separated by a space");
    } else if (parameters[0].matches(REGEX_POSITIVE_INTEGER) && parameters[1].matches(REGEX_POSITIVE_INTEGER)) {
      return Optional.of(new int[] {Integer.parseInt(parameters[0]), Integer.parseInt(parameters[1])});
    }
    return Optional.empty();
  }

  private List<String> readFile(String filePath) throws Exception {
    List<String> lines = new ArrayList<>();
    String inputLine;

    try {
      FileInputStream fis = new FileInputStream(filePath);
      Scanner sc = new Scanner(fis);
      while(sc.hasNextLine())
      {
        inputLine = sc.nextLine();
        if (inputLine.length() > 0) {
          lines.add(inputLine);
        }
      }
      sc.close();
    }
    catch(IOException e) {
      e.printStackTrace();
      throw new Exception("Invalid file");
    }
    return lines;
  }

  private boolean isValidCell(String cell) {
    return cell.equals("X") || cell.equals(".") || cell.matches(REGEX_POSITIVE_INTEGER);
  }
}
