package dungeon;

/**
 * Class to denote an edge consisting of two nodes and a connection between them. It is used to
 * find the Minimum spanning path in the dungeon.
 */
class Edge {
  private final Node a;
  private final Node b;

  /**
   * Constructor to initialize the object of edge with two nodes.
   */
  public Edge(Node a, Node b) {
    this.a = new NodeImpl(a);
    this.b = new NodeImpl(b);
  }

  @Override
  public String toString() {
    return (String.format("%s %s", a.toString(), b.toString()));
  }

  /**
   * To get the id of node A in the edge considering node is from A to B.
   */
  public int getAId() {
    return a.getId();
  }

  /**
   * To get the id of node B in the edge considering node is from A to B.
   */
  public int getBId() {
    return b.getId();
  }
}
