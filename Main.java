public class Main {
  static long startTime, stopTime;

  public static void main(String[] args) {
    GreedyGnomes greedyGnomes = new GreedyGnomes();
    String[] results;

    // load and display map
    try {
      greedyGnomes.loadMap(args[0]);
      System.out.println("Map is loaded and converted as follow:");
      greedyGnomes.display();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }

    // Solve by dynamic programming
    System.out.println("\nSolving with dynamic programming ------------------------------------");
    startTime = System.nanoTime();
    results = greedyGnomes.resolve(GreedyGnomes.Algo.DYNAMIC_PROGRAMMING);
    stopTime = System.nanoTime();
    System.out.println("Gold:" + results[0] + " Steps:" + results[1].length() + " Path:" + results[1]
            + " - Execution Time: " + (stopTime - startTime) + " ns") ;
    greedyGnomes.displayResolvedMap(results[1]);

    // Solve by Exhaustive search
    System.out.println("\nSolving with exhaustive search ------------------------------------");
    startTime = System.nanoTime();
    results = greedyGnomes.resolve(GreedyGnomes.Algo.EXHAUSTIVE_SEARCH);
    stopTime = System.nanoTime();
    System.out.println("Gold:" + results[0] + " Steps:" + results[1].length() + " Path:" + results[1]
            + " - Execution Time: " + (stopTime - startTime) + " ns") ;
    greedyGnomes.displayResolvedMap(results[1]);
  }
}
