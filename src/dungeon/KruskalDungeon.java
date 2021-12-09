package dungeon;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * To implement and make a kruskal's Project3.Dungeon with n*m nodes and mentioned
 * interconnectivity.
 * The class uses modified Kruskal's algorithm to build a Project3.Dungeon with n*m and
 * interconnectivity as
 * zero initially. then it adds nodes from the leftover edges to the desired interconnectivity.
 */
public class KruskalDungeon implements Dungeon {
  private Node[][] maze;
  private List<Edge> potentialPaths;
  private int iConnect;
  private List<Edge> leftOvers;
  private int rows;
  private int cols;
  private int[] start;
  private int[] end;
  private List<Edge> connected;
  private List<ArrayList<Integer>> pathSets;
  private boolean wrapping;
  private int seed;
  private GameCharacter p;
  private float treasureP;
  private List<Node> caveList;
  private List<Node> pitList;
  private Movable monster;


  /**
   * Constructor to initialize the Kruskal's dungeon with initial values. Takes in Number of rows,
   * Number of columns, interconnectivity, wrapping condition, treasure percentage, number of
   * otyughs and the seed value.
   */
  public KruskalDungeon(int nRow, int nCol, int iConnect, boolean wrapping, int treasureP,
                        int numberOfOtyugh, int seed) {
    if (nRow < 1 || nCol < 1 || iConnect < 0 || treasureP < 0 || numberOfOtyugh < 1
        || numberOfOtyugh > nRow * nCol) {
      throw new IllegalArgumentException();
    }
    caveList = new ArrayList<Node>();
    pathSets = new ArrayList<>();
    potentialPaths = new ArrayList<Edge>();
    rows = nRow - 1;
    leftOvers = new ArrayList<Edge>();
    cols = nCol - 1;
    maze = new NodeImpl[nRow][nCol];
    this.treasureP = treasureP;
    int count = 1;
    for (int i = 0; i < nRow; i++) {
      for (int j = 0; j < nCol; j++) {
        maze[i][j] = new NodeImpl(count);
        count++;
      }
    }
    this.wrapping = wrapping;
    this.iConnect = iConnect;
    connected = new ArrayList<Edge>();
    this.start = new int[2];
    this.end = new int[2];
    this.seed = seed;
    createPaths();
    createLeftOvers();
    connectNodes();
    selectStartEnd();
    markType();
    Random r;
    //System.out.println(getOtyughs());
    pitList = new ArrayList<Node>();
    r = new Random(seed);
    monster = new MovableImpl(end.clone());
    //System.out.println(toString());
    String[] names = {"Arnold", "John", "Jack", "Harry", "Larry"};
    p = new PlayerImpl(names[r.nextInt(4) - 1], start);
    maze[start[0]][start[1]].placePlayer();
    placeOtyughs(numberOfOtyugh);
    fillTreasure();
    fillArrows();
    markPits();
    markThief();

  }

  private void markPits() {
    int count = (int) (0.1 * caveList.size());
    Collections.shuffle(caveList, new Random(seed));
    while (count != 0) {
      if (!caveList.get(count).hasDemon()
          && maze[getEnd()[0]][getEnd()[1]] != caveList.get(count)) {
        caveList.get(count).setPit();
        pitList.add(caveList.get(count));
        System.out.println("Pits added at : " + caveList.get(count).getId());
        count--;
      }
    }

  }

  private void markThief() {
    Random random = new Random();
    int r;
    int c;
    do {
      r = random.nextInt(rows);
      c = random.nextInt(cols);
    }
    while (r == end[0] && c == end[1] || maze[r][c].hasPit());
    maze[r][c].setThief();
    System.out.println("Thief at " + maze[r][c].getId());
  }

  @Override
  public boolean hasPitNearby() {
    Movable m = new MovableImpl(p.getLocation());
    String movable = getMovable();
    if (movable.contains("N")) {
      movePlayerNorth(m);
      if (maze[m.getLocation()[0]][m.getLocation()[1]].hasPit()) {
        return true;
      }
      movePlayerSouth(m);
    }
    if (movable.contains("E")) {
      movePlayerEast(m);
      if (maze[m.getLocation()[0]][m.getLocation()[1]].hasPit()) {
        return true;
      }
      movePlayerWest(m);
    }
    if (movable.contains("W")) {
      movePlayerWest(m);
      if (maze[m.getLocation()[0]][m.getLocation()[1]].hasPit()) {
        return true;
      }
      movePlayerEast(m);
    }
    if (movable.contains("S")) {
      movePlayerSouth(m);
      if (maze[m.getLocation()[0]][m.getLocation()[1]].hasPit()) {
        return true;
      }
      movePlayerNorth(m);
    }
    return false;

  }

  private void monsterMoveRandom() {
    String[] dir = new String[] {"N", "W", "S", "E"};
    int[] loc = new int[2];
    Random random = new Random();
    int index = random.nextInt(4);
    loc[0] = monster.getLocation()[0];
    loc[1] = monster.getLocation()[1];
    while (!getMovable(loc).contains(dir[index])) {
      index = random.nextInt(4);
    }
    switch (dir[index]) {
      case "N":
        movePlayerNorth(monster);
        break;
      case "E":
        movePlayerEast(monster);
        break;
      case "W":
        movePlayerWest(monster);
        break;
      case "S":
        movePlayerSouth(monster);
        break;
      default:
        //Not required

    }

    System.out.println(
        "Monster Location: " + monster.getLocation()[0] + "\t" + monster.getLocation()[1]);

  }

  /**
   * To check if game is over. i.e. if player died/ eaten by otyugh.
   */
  @Override
  public int isGameOver(int seed) {
    if (maze[getPlayerLocation()[0]][getPlayerLocation()[1]].hasDemon()) {
      if (maze[getPlayerLocation()[0]][getPlayerLocation()[1]].getDemons().get(0).getHealth()
          == 1) {
        Random random = new Random(seed);
        //Returns 1 when you manage to escape a monster.
        if (random.nextInt(1) == 0) {
          return 1;
        }
        //Returns 2 when you get caught and eaten my a monster.
        else {
          return 2;
        }
      } else {
        return 2;
      }
    }

    // Returns 0 if no harm done.
    return 0;
  }


  /**
   * To get thr number of arrows with the player in the dungeon.
   *
   * @return Integer number of arrows.
   */
  @Override
  public int getPlayerArrows() {
    return p.getArrows();
  }

  private void moveDirection(char direction, Movable temp) {
    switch (direction) {
      case 'N':
      case 'n':
        movePlayerNorth(temp);
        break;
      case 'S':
      case 's':
        movePlayerSouth(temp);
        break;
      case 'W':
      case 'w':
        movePlayerWest(temp);
        break;
      case 'E':
      case 'e':
        movePlayerEast(temp);
        break;
      default:
    }
  }


  /**
   * The method which allows a player to shoot an arrow by specifying the distance and direction
   * that the arrow must travel.
   *
   * @param direction N E S W one of the directions.
   * @param distance  Distance specifies the distance that player feels the otyugh is at.
   * @return 1 if otyugh injured, 2 if otyugh killed and 0 if no harm done.
   */
  @Override
  public int[] shoot(char direction, int distance) {
    if (!"N W E S".contains(Character.toString(direction)) || distance < 0) {
      throw new IllegalArgumentException();
    }
    Movable temp = new MovableImpl(getPlayerLocation());
    p.arrowUsed();
    int i;
    int j;
    while (distance != 0) {

      i = temp.getLocation()[0];
      j = temp.getLocation()[1];
      //System.out.println(i + " " + j + "\t" + distance);
      if (maze[i][j].isTunnel()) {
        if (getMovable(temp.getLocation()).contains(Character.toString(direction))) {
          moveDirection(direction, temp);
          distance--;
        } else if ((getMovable(temp.getLocation()).contains("E")
            || getMovable(temp.getLocation()).contains("S"))) {
          moveDirection('E', temp);
          direction = 'E';
          distance--;
        } else if (getMovable(temp.getLocation()).contains("W")
            || getMovable(temp.getLocation()).contains("N")) {
          moveDirection('W', temp);
          direction = 'W';
          distance--;
        }
      } else {
        if (getMovable(temp.getLocation()).contains(Character.toString(direction))) {
          moveDirection(direction, temp);
          distance--;
        } else {
          throw new IllegalStateException();
        }
      }
    }
    if (maze[temp.getLocation()[0]][temp.getLocation()[1]].hasDemon() && distance == 0) {
      ///System.out.println("hit");
      //System.out.println(getOtyughs());
      return maze[temp.getLocation()[0]][temp.getLocation()[1]].hitDemon();
    }
    int[] res = new int[2];
    return res;
  }

  @Override
  public int[] poisonShot(char direction, int distance) {
    if (!"N W E S".contains(Character.toString(direction)) || distance < 0) {
      throw new IllegalArgumentException();
    }
    int[] res = new int[2];
    res[0] = 0;
    res[1] = 0;
    p.arrowUsed();
    if (shoot(direction, distance)[0] == 1) {
      res = shoot(direction, distance);
      p.arrowUsed();
    }
    return res;
  }

  /**
   * Function to create potential paths(edges) in the maze where each potential path is an edge.
   * The number of potential paths for n nodes is given by (n*2)-(n+m).
   */
  @Override
  public void createPaths() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        //System.out.println(maze[i][j].getId());
        potentialPaths.add(new Edge(maze[i][j], maze[i + 1][j]));
        potentialPaths.add(new Edge(maze[i][j], maze[i][j + 1]));
      }
    }

    // To create paths for the last row.
    for (int i = 0; i < cols; i++) {
      //System.out.println(maze[rows][i].getId());
      potentialPaths.add(new Edge(maze[rows][i], maze[rows][i + 1]));
      if (wrapping) {
        potentialPaths.add(new Edge(maze[rows][i], maze[0][i]));
      }
    }
    //To create paths in the last column.
    for (int j = 0; j < rows; j++) {
      //System.out.println(maze[j][cols].getId());
      potentialPaths.add(new Edge(maze[j][cols], maze[j + 1][cols]));
      if (wrapping) {
        potentialPaths.add(new Edge(maze[j][cols], maze[j][0]));
      }
    }
    if (wrapping) {
      potentialPaths.add(new Edge(maze[rows][cols], maze[rows][0]));
      potentialPaths.add(new Edge(maze[rows][cols], maze[0][cols]));
    }

    //System.out.println(potentialPaths.size());
  }


  /**
   * Helper for Creating leftovers list.
   */
  private ArrayList<Integer> retHash(int n) {
    ArrayList<Integer> hs = new ArrayList<>();
    hs.add(n);
    return hs;
  }

  /**
   * Helper method to mark the type of Node by counting the number of entrances.
   */
  private void markType() {
    int entrance;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        entrance = maze[i][j].getEntrances();
        if (entrance == 2) {
          //System.out.println("tunnel");
          maze[i][j].markType("Tunnel");

        } else if (entrance == 1 || entrance == 3 || entrance == 4) {
          maze[i][j].markType("Cave");
          caveList.add(maze[i][j]);
          //maze[i][j].placeOtyugh();
          //System.out.println("cave");
        }
      }
    }
  }

  /**
   * Function to place an otyugh by specifying the number of otyughs.
   *
   * @param numberOfOtyughs Integer number.
   */
  private void placeOtyughs(int numberOfOtyughs) {
    int count = 1;
    int id;
    Random random = new Random(seed);
    maze[getEnd()[0]][getEnd()[1]].placeOtyugh(1);
    System.out.println("otyugh placed at " + end[0] + " " + end[1]);
    while (count < numberOfOtyughs) {
      count++;
      id = random.nextInt(caveList.size());
      caveList.get(id).placeOtyugh(count);
      System.out.println("otyugh placed at " + caveList.get(id).getId());
      //System.out.println(caveList.get(id).getDemons());

    }

  }

  /**
   * To fill treasure in the specified percentage of caves in the dungeon.
   */
  private void fillTreasure() {

    double filledCave = (treasureP / 100.0) * caveList.size();
    //System.out.println(treasureP / 100 + " " + caveList.size());
    Random random = new Random(seed);
    int count = 0;
    int id;
    //System.out.println(filledCave);
    while (count <= (int) filledCave) {
      id = random.nextInt(caveList.size());
      caveList.get(id).fillTreasure();
      count++;
      //caveList.toString();
    }
  }

  /**
   * Method to fill the dungeon with arrows in the same frequency as tunnels spread all over the
   * dungeon.
   */
  private void fillArrows() {
    double filledArrow = (treasureP / 100.0) * ((rows + 1) * (cols + 1));
    Random random = new Random(seed);
    int row;
    int col;
    int count = 0;
    while (count < (int) filledArrow) {
      row = random.nextInt(rows);
      col = random.nextInt(cols);
      maze[row][col].placeArrow();
      count++;
    }
  }


  /**
   * Returns a string with the directions of freedom to a player i.e. the openings available to a
   * player to move.
   */
  @Override
  public String getMovable() {
    return getMovable(p.getLocation());
  }

  /**
   * Method which returns the movable locations as a text for a particular location.
   *
   * @param loc Location for which the movable is requested
   * @return String containing Directions.
   */
  @Override
  public String getMovable(int loc) throws IllegalArgumentException {
    if (loc < 0) {
      throw new IllegalArgumentException();
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (loc == maze[i][j].getId()) {
          int[] l = {i, j};
          return getMovable(l);
        }
      }
    }
    throw new IllegalArgumentException("Illegal location");
  }

  /**
   * Returns the movable directions for a particular location.
   *
   * @param loc Node Location.
   * @return Available directions for movement.
   */
  @Override
  public String getMovable(int[] loc) {
    if (loc[0] < 0 || loc[1] < 0) {
      throw new IllegalArgumentException();
    }
    StringBuilder movable = new StringBuilder();
    if (maze[loc[0]][loc[1]].getNorth() != null) {
      movable.append(" N ");
    }
    if (maze[loc[0]][loc[1]].getSouth() != null) {
      movable.append(" S ");
    }
    if (maze[loc[0]][loc[1]].getWest() != null) {
      movable.append(" W ");
    }
    if (maze[loc[0]][loc[1]].getEast() != null) {
      movable.append(" E ");
    }
    return movable.toString();
  }

  /**
   * To return the start of the dungeon in the form of an arrow of 2 integers row number and
   * column number.
   */
  @Override
  public int[] getStart() {
    int[] res = new int[2];
    res[0] = start[0];
    res[1] = start[1];
    return res;
  }

  /**
   * To return the end of the dungeon in the form of an arrow of 2 integers row number and
   * column number.
   */
  @Override
  public int[] getEnd() {
    int[] res = new int[2];
    res[0] = end[0];
    res[1] = end[1];
    return res;
  }

  /**
   * to get the number of caves in the dungeon.
   *
   * @return integer size of caveList
   */
  @Override
  public int getCaveListSize() {
    return caveList.size();
  }

  /**
   * To get the number of caves with treasures in them.
   *
   * @return integer count of caves with one or more treasure in them.
   */
  @Override
  public int getTreasuredCaves() {
    int count = 0;
    for (Node cave : caveList) {
      if (cave.getTreasureNum() > 0) {
        count++;
      }
    }
    return count;
  }

  @Override
  public boolean movePlayer(String direction) {
    if (!"N W E S".contains(direction)) {
      throw new IllegalArgumentException();
    }
    boolean res = false;
    switch (direction) {
      case "N":
        res = movePlayerNorth();
        break;
      case "S":
        res = movePlayerSouth();
        break;
      case "W":
        res = movePlayerWest();
        break;
      case "E":
        res = movePlayerEast();
        break;
      default:

    }
    monsterMoveRandom();
    //
    //System.out.println("End : " + end[0] + "\t" + end[1]);
    return res;

  }

  /**
   * To select start and end position randomly with a minimum distance of 5 units between them.
   *
   * @return true if a pair found in 1000 iterations. false if not found .
   */
  private boolean selectStartEnd() {
    for (int i = 0; i < 2000; i++) {
      if (startEndHelper() >= 5) {
        return true;
      }
    }
    return false;
  }

  /**
   * Helper to selectStartEnd(). Computes the distance between the chosen start and end using
   * Manhattan distance.
   *
   * @return distance between the two Nodes.
   */
  private int startEndHelper() {

    this.start = RandomNodeSelector.getRandomNode(cols, rows, seed);
    this.end = RandomNodeSelector.getRandomNode(cols, rows, seed + 1);
    return distance(start, end);
  }

  /**
   * Compute the distance between two Nodes in the Maze using manhattan distance.
   */
  @Override
  public int distance(int[] a, int[] b) {
    if (a[0] < 0 || a[1] < 0 || b[0] < 0 || b[1] < 0) {
      throw new IllegalArgumentException();
    }
    int x1;
    int y1;
    int distanceDirect = 0;
    x1 = Math.abs(start[0] - end[0]);
    y1 = Math.abs(start[1] - end[1]);
    distanceDirect = x1 + y1;
    int distanceWrapping = 100;

    if (wrapping) {
      if (Math.abs(Math.abs(start[0] - end[0])) < start[0] + Math.abs(rows - end[0])) {
        x1 = Math.abs(start[0] - end[0]);
      } else {
        x1 = start[0] + Math.abs(rows - end[0]) + 1;
      }
      if (Math.abs(start[1] - end[1]) < start[1] + Math.abs(cols - end[1])) {
        y1 = Math.abs(start[1] - end[1]);
      } else {
        y1 = start[1] + Math.abs(cols - end[1]) + 1;
      }
      distanceWrapping = x1 + y1;
    }
    return (Math.min(distanceDirect, distanceWrapping));
  }


  /**
   * Method to create the leftovers edges from potential paths and pathSets.
   */
  private void createLeftOvers() {
    leftOvers = new ArrayList<Edge>();
    //To make a pathSet consisting of sets of node id's. Initially all nodes are in separate sets.
    for (int i = 0; i <= rows; i++) {
      for (int j = 0; j <= cols; j++) {
        pathSets.add(retHash(maze[i][j].getId()));
      }
    }

    pathSets = Collections.synchronizedList(pathSets);
    //To randomize the Potential paths.
    Collections.shuffle(potentialPaths, new Random(3));
    // We iterate through the potential paths to check if the nodes of the edge are connected.
    for (Edge e : potentialPaths) {
      for (int j = 0; j < pathSets.size(); j++) {
        //If found to be connected the edge is added to the leftover edges.
        if (pathSets.get(j).contains(e.getAId()) && pathSets.get(j).contains(e.getBId())) {
          leftOvers.add(e);
        }
        //If the edge is not found to be connected they are joined and their sets are joined(Union).
        else if (pathSets.get(j).contains(e.getAId())) {
          for (int i = 0; i < pathSets.size(); i++) {
            if (pathSets.get(i).contains(e.getBId())) {
              pathSets.get(j).addAll(pathSets.get(i));
              connected.add(e);
              pathSets.remove(pathSets.get(i));
            }
          }
        } else if (pathSets.get(j).contains(e.getBId())) {
          for (int i = 0; i < pathSets.size(); i++) {
            if (pathSets.get(i).contains(e.getAId())) {
              pathSets.get(j).addAll(pathSets.get(i));
              pathSets.remove(pathSets.get(i));
              connected.add(e);
            }
          }
        }
      }
    }
  }

  /**
   * To connect the nodes in the Maze by iterating through the connected edges list which we got
   * from finding leftovers.
   */
  private void connectNodes() {
    //To connect nodes to form a Minimum spanning tree.
    connectHelper(connected, connected.size());
    //To connect nodes to comply with interconnectivity.
    connectHelper(leftOvers, iConnect);
  }

  /**
   * Helper to the connectNodes function.
   */
  private void connectHelper(List<Edge> con, int size) throws IllegalArgumentException {
    if (con == null) {
      throw new IllegalArgumentException();
    }
    int count = 0;
    for (Edge e : con) {
      //System.out.println(e.getAId() + "->" + e.getBId());
      if (count <= size) {
        for (int i = 0; i <= rows; i++) {
          for (int j = 0; j <= cols; j++) {
            if (e.getAId() == maze[i][j].getId()) {
              //To check if it's a wrapping edge.
              if (e.getBId() > e.getAId()) {
                if (e.getBId() == e.getAId() + 1) {
                  maze[i][j].connectEast(maze[i][j + 1]);
                  maze[i][j + 1].connectWest(maze[i][j]);
                } else if (e.getBId() == e.getAId() + cols + 1) {
                  maze[i][j].connectSouth(maze[i + 1][j]);
                  maze[i + 1][j].connectNorth(maze[i][j]);
                }
              } else {
                if (e.getBId() == e.getAId() - (rows * (cols + 1))) {
                  maze[i][j].connectSouth(maze[0][j]);
                  maze[0][j].connectNorth(maze[i][j]);
                } else {
                  maze[i][j].connectEast(maze[i][0]);
                  maze[i][0].connectWest(maze[i][j]);
                }
              }
            }
          }
        }
        count++;
      }
    }

  }

  /**
   * To check if the player reached the end of the dungeon.
   *
   * @return true if yes or false
   */
  private boolean playerAtEnd() {
    return p.getLocation() == end;
  }

  /**
   * To represent the dungeon in a visualised manner.
   *
   * @return String.
   */
  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i <= rows; i++) {
      str.append("\n");
      if (i == 0) {
        for (int j = 0; j <= cols; j++) {
          str.append(maze[i][j].toStringNorth());
        }
      }
      str.append("\n");
      str.append(maze[i][0].toStringFirstCol());
      for (int j = 1; j <= cols; j++) {
        str.append(maze[i][j].toString());
      }
      str.append("\n");
      for (int j = 0; j <= cols; j++) {
        str.append(" ");
        str.append(maze[i][j].toStringSouth());
      }
    }
    return str.toString();
  }

  /**
   * To get the treasure percentage in the dungeon. It's the treasure percentage passed when
   * creating the dungeon.
   */
  @Override
  public float getTreasureP() {
    return treasureP;
  }

  /**
   * To make a player pick the treasure at the location if it is available.
   */
  @Override
  public boolean pickTreasure() {
    int r = p.getLocation()[0];
    int c = p.getLocation()[1];
    if (maze[r][c].getTreasureNum() > 0) {
      p.pickTreasure(maze[r][c].getTreasures().get(0));
      maze[r][c].removeTreasure();
      return true;
    } else {
      return false;
    }

  }

  /**
   * To get the player location ID of the node in which the player is located at.
   *
   * @return ID of the node.
   */
  @Override
  public int getPlayerLocationId() {
    return maze[p.getLocation()[0]][p.getLocation()[1]].getId();
  }


  /**
   * Default method To move the player south if possible by checking if there exists a
   * connection to the south.
   */
  @Override
  public boolean movePlayerSouth() {
    return movePlayerSouth(p);
  }

  /**
   * To move the player south if possible by checking if there exists a connection to the south.
   */
  @Override
  public boolean movePlayerSouth(Movable p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    int[] location = p.getLocation();
    if (maze[location[0]][location[1]].getSouth() != null) {
      if (wrapping && p.getLocation()[0] == rows) {
        p.moveSouthWrapping(rows);
      } else {
        p.moveSouth();
      }
      maze[location[0]][location[1]].removePlayer();
      location = p.getLocation();
      maze[location[0]][location[1]].placePlayer();
      return p.getLocation()[0] == end[0] && p.getLocation()[1] == end[1];
    } else {
      throw new IllegalArgumentException("South is null");
    }
  }

  /**
   * Default method
   * To move the player North if possible by checking if there exists a connection to the North.
   */
  @Override
  public boolean movePlayerNorth() {
    return movePlayerNorth(p);
  }

  /**
   * To move the player North if possible by checking if there exists a connection to the North.
   */
  @Override
  public boolean movePlayerNorth(Movable p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    int[] location = p.getLocation();
    if (maze[location[0]][location[1]].getNorth() != null) {
      if (wrapping && p.getLocation()[0] == 0) {
        p.moveNorthWrapping(rows);
      } else {
        p.moveNorth();
      }
      maze[location[0]][location[1]].removePlayer();
      location = p.getLocation();
      maze[location[0]][location[1]].placePlayer();
      return p.getLocation()[0] == end[0] && p.getLocation()[1] == end[1];
    } else {
      throw new IllegalArgumentException("North is null");
    }
  }

  /**
   * Default method
   * To move the player West if possible by checking if there exists a connection to the North.
   */
  @Override
  public boolean movePlayerWest() {
    return movePlayerWest(p);
  }

  /**
   * To move the player West if possible by checking if there exists a connection to the West.
   */
  @Override
  public boolean movePlayerWest(Movable p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    int[] location = p.getLocation();
    if (maze[location[0]][location[1]].getWest() != null) {
      if (wrapping && p.getLocation()[1] == 0) {
        p.moveWestWrapping(cols);
      } else {
        p.moveWest();
      }
      maze[location[0]][location[1]].removePlayer();
      location = p.getLocation();
      //System.out.println(location[0]+" "+ location[1]);
      maze[location[0]][location[1]].placePlayer();
      return p.getLocation()[0] == end[0] && p.getLocation()[1] == end[1];
    } else {
      throw new IllegalArgumentException("West is null");
    }
  }

  /**
   * To get Number of Otyughs in the dungeon for testing.
   *
   * @return Integer number of Otyughs
   */
  @Override
  public int getNumberOfOtyughs() {
    int count = 0;
    for (int i = 0; i <= rows; i++) {
      for (int j = 0; j <= cols; j++) {
        if (maze[i][j].hasDemon()) {
          count += maze[i][j].getDemons().size();
        }
      }
    }
    return count;
  }

  /**
   * To check if End has Otyugh.
   *
   * @return true if it has and false if it doesn't.
   */
  @Override
  public boolean endHasOtyugh() {
    return maze[getEnd()[0]][getEnd()[1]].hasDemon();
  }

  /**
   * Default method
   * To move the player East if possible by checking if there exists a connection to the North.
   */
  @Override
  public boolean movePlayerEast() {
    return movePlayerEast(p);
  }

  /**
   * To move the player East if possible by checking if there exists a connection to the East.
   */
  @Override
  public boolean movePlayerEast(Movable p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    int[] location = p.getLocation();
    if (maze[location[0]][location[1]].getEast() != null) {
      if (wrapping && p.getLocation()[1] == cols) {
        p.moveEastWrapping(cols);
      } else {
        p.moveEast();
      }
      maze[location[0]][location[1]].removePlayer();
      location = p.getLocation();
      maze[location[0]][location[1]].placePlayer();
      return p.getLocation()[0] == end[0] && p.getLocation()[1] == end[1];
    } else {
      throw new IllegalArgumentException("East is null");
    }
  }

  /**
   * To get the count of unreachable nodes in the dungeon.
   */
  @Override
  public int getUnreachableCount() {
    int count = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (maze[i][j].getEntrances() == 0) {
          count++;
        }
      }
    }
    return count;
  }


  /**
   * To get the pungency of a location by scanning the otyughs nearby.
   *
   * @return pungency as integer 1 for weak, 2 for strong.
   */
  @Override
  public int scanOtyugh() {
    Movable temp = new MovableImpl(getPlayerLocation());
    int pungency = 0;
    int distance = 0;
    pungency = sniffOtyughs(temp, pungency, distance);
    maze[temp.getLocation()[0]][temp.getLocation()[1]].removePlayer();
    return pungency;
  }

  /**
   * Helper function to scanOtyugh() which is called recursively till pungency is found.
   */
  private int sniffOtyughs(Movable temp, int pungency, int distance) {
    if (getMovable(temp.getLocation()).contains("N")) {
      movePlayerNorth(temp);
      distance++;
      if (maze[temp.getLocation()[0]][temp.getLocation()[1]].hasDemon()) {
        pungency++;
      }
      if (pungency >= 2) {
        movePlayerSouth(temp);
        return 2;
      }
      if (distance <= 2) {
        pungency = sniffOtyughs(temp, pungency, distance);
      }

      movePlayerSouth(temp);
    }

    if (getMovable(temp.getLocation()).contains("E")) {
      movePlayerEast(temp);
      distance++;
      if (maze[temp.getLocation()[0]][temp.getLocation()[1]].hasDemon()) {
        pungency++;
      }
      if (pungency >= 2) {
        movePlayerWest(temp);
        return 2;
      }
      if (distance < 2) {
        pungency = sniffOtyughs(temp, pungency, distance);
      }
      //System.out.println("\n" + temp.getLocation()[0] + " " + temp.getLocation()[1]);
      //System.out.println(toString());
      //System.out.println(maze[temp.getLocation()[0]][temp.getLocation()[1]].getWest());

      movePlayerWest(temp);
    }
    if (getMovable(temp.getLocation()).contains("W")) {

      movePlayerWest(temp);
      distance++;
      if (maze[temp.getLocation()[0]][temp.getLocation()[1]].hasDemon()) {
        pungency++;
      }
      if (pungency >= 2) {
        movePlayerEast(temp);
        return 2;
      }
      if (distance <= 2) {
        pungency = sniffOtyughs(temp, pungency, distance);
      }
      movePlayerEast(temp);
    }
    if (getMovable(temp.getLocation()).contains("S")) {
      movePlayerSouth(temp);
      distance++;
      if (maze[temp.getLocation()[0]][temp.getLocation()[1]].hasDemon()) {
        pungency++;
      }
      if (pungency >= 2) {
        movePlayerNorth(temp);
        return 2;
      }
      if (distance <= 2) {
        pungency = sniffOtyughs(temp, pungency, distance);
      }
      movePlayerNorth(temp);
    }

    return pungency;
  }


  /**
   * To get the description of a player which includes the name,location and list of treasures.
   */
  @Override
  public String describePlayer() {
    return p.toString();
  }

  /**
   * To get the player location in the form a 2d array with row and column number.
   */
  @Override
  public int[] getPlayerLocation() {
    return p.getLocation();
  }

  /**
   * To get the total number of potential paths generated/possible in the dungeon.
   */
  @Override
  public int getPotentialPaths() {
    return potentialPaths.size();
  }

  /**
   * To get the contents of the Node to be displayed to the user.
   *
   * @return String of contents
   */
  @Override
  public String getContents() {
    StringBuilder str = new StringBuilder();
    if (maze[getPlayerLocation()[0]][getPlayerLocation()[1]].getTreasures().size() > 0) {
      str.append("Available Treasures:");
      for (Treasure t : maze[getPlayerLocation()[0]][getPlayerLocation()[1]].getTreasures()) {
        str.append(t.toString()).append("\t");
      }
      str.append("\n");
    }
    if (maze[getPlayerLocation()[0]][getPlayerLocation()[1]].getArrows() > 0) {
      str.append(maze[getPlayerLocation()[0]][getPlayerLocation()[1]].getArrows())
          .append(" Arrows available");
      str.append("\n");
    }
    return str.toString();
  }

  /**
   * To get the List of treasures in the node.
   *
   * @return String of treasures.
   */
  @Override
  public String getTreasures() {
    StringBuilder str = new StringBuilder();
    str.append("\n");
    for (Node n : caveList) {
      str.append(n.getId()).append(n.getTreasures()).append("\n");
    }
    return str.toString();
  }

  /**
   * Method to pick an arrow in the Node when available.
   *
   * @return true if successful and false if unsuccessful.
   */
  @Override
  public boolean pickArrows() {
    int count = maze[p.getLocation()[0]][getPlayerLocation()[1]].pickArrows();
    if (count > 0) {
      p.pickArrows(count);
      return true;
    } else {
      return false;
    }
  }

  /**
   * To get the list of otyughs in the caves of dungeon. For testing purpose.
   *
   * @return List as string.
   */
  @Override
  public String getOtyughs() {
    StringBuilder str = new StringBuilder();
    str.append("\n");
    for (Node n : caveList) {
      str.append(n.getId()).append(n.getDemons()).append("\n");
    }
    return str.toString();
  }

  @Override
  public int getRows() {
    return rows;
  }

  @Override
  public int getCols() {
    return cols;
  }

  @Override
  public int getInterConnectivity() {
    return iConnect;
  }

  @Override
  public boolean getWrapping() {
    return wrapping;
  }

  @Override
  public boolean checkPit() {
    return maze[p.getLocation()[0]][p.getLocation()[1]].hasPit();
  }

  @Override
  public int getPlayerTreasures() {
    return p.getTreasures();
  }

  @Override
  public void bridgeThePit() {
    p.useTreasure();
  }

  @Override
  public void removePit() {
    maze[p.getLocation()[0]][p.getLocation()[1]].removePit();
  }

  @Override
  public List<Treasure> getPlayerTreasuresList() {
    return p.getTreasuresList();
  }

  @Override
  public int getNodeTreasures() {
    return maze[getPlayerLocation()[0]][getPlayerLocation()[1]].getTreasures().size();
  }

  @Override
  public int getNodeArrows() {
    return maze[getPlayerLocation()[0]][getPlayerLocation()[1]].getArrows();
  }

  @Override
  public boolean nodeHasThief() {
    return maze[p.getLocation()[0]][p.getLocation()[1]].hasThief();
  }

  @Override
  public void getRobbedByThief() {
    p.loseAllTreasure();
  }

  @Override
  public boolean nodeHasNinja() {
    // System.out.println(getPlayerLocation()[0]+"\t"+getPlayerLocation()[1]);
    return (Arrays.equals(p.getLocation(), monster.getLocation()));
  }

  @Override
  public boolean pitAdded() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (maze[i][j].hasPit()) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean thiefAdded() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (maze[i][j].hasThief()) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean ninjaAdded() {
    return monster.getLocation()[0] > 0 && monster.getLocation()[1] > 0;
  }

  @Override
  public boolean ninjaMovable() {
    int[] loc = monster.getLocation();
    monsterMoveRandom();
    return monster.getLocation()[0] != loc[0] || monster.getLocation()[1] != loc[1];
  }
}


