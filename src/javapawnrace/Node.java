package javapawnrace;

/**
 * Created by daniel on 14/01/16.
 */
public class Node {
    private Color turn;
    private Color player;
    private Move lastMove;
    private Node lastNode;
    private Move[] possibleMoves;
    private Node[] nextNodes;
    private int depth;
    private int finalCost;

    public Node(Move lastMove, Node lastNode, Move[] possibleMoves, int depth, Color turn, Color player) {
        this.lastMove = lastMove;
        this.possibleMoves = possibleMoves;
        this.depth = depth;
        this.lastNode = lastNode;
        this.turn = turn;
        this.player = player;
    }

    public void addNextNodes (Node[] nodes) {
        this.nextNodes = nodes;
    }

    public void setFinalCost(int cost) {
        finalCost = cost;
    }

    public int getFinalCost() {
        return finalCost;
    }

    public Node[] getNextNodes() {
        return nextNodes;
    }

    public Color getTurn() {
        return turn;
    }

    public Color getPlayer() {
        return player;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public Node getLastNode() {
        return lastNode;
    }
}
