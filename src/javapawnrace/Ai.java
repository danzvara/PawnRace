package javapawnrace;

import java.util.Random;

/**
 * Created by daniel on 14/01/16.
 */
public class Ai {

    private Board board;
    private Game game;
    private Player p1;
    private Player p2;
    private Random random;

    public Ai(Board board, Game game, Player p1, Player p2) {
        this.board = board;
        this.game = game;
        this.p1 = p1;
        this.p2 = p2;
    }

    public Move getNextMove(Move[] possibleMoves, Color player) {
        Node startNode = new Node(null, null, possibleMoves, 4, player, player);
        Node[] nextNodes = getNextNodes(possibleMoves, player, player, 4, startNode);
        startNode.addNextNodes(nextNodes);
        int[] costs = new int[nextNodes.length];
        int i = 0;
        random = new Random();
        Node maxNode = nextNodes[random.nextInt(nextNodes.length)];
        int max = 0;

        for (Node n : nextNodes) {
            costs[i] = walkPath(n);
            if (costs[i] > max) {
                max = costs[i];
                maxNode = n;
            }
        }

        return maxNode.getLastMove();
    }

    private Node[] getNextNodes(Move[] possibleMoves, Color turn, Color player, int depth, Node lastNode) {
        Node[] nextNodes = new Node[possibleMoves.length];
        Node[] nextNextNodes;
        int i = 0;
        int final_cost = 0;

        if (depth == 1) {
            for (Move move : possibleMoves) {
                final_cost = evalPath(move, lastNode);
                nextNodes[i] = new Node(move, lastNode, null, 0, getOpponent(turn), player);
                nextNodes[i].setFinalCost(final_cost);
                i++;
            }

            return nextNodes;
        }

        for (Move move : possibleMoves) {
            simulateMove(move);
            if (turn == player) {
                nextNodes[i] = new Node(move, lastNode, p1.getAllValidMoves(), depth - 1, getOpponent(turn), player);
                nextNextNodes = getNextNodes(p1.getAllValidMoves(), getOpponent(turn), player, depth - 1, nextNodes[i]);
                nextNodes[i].addNextNodes(nextNextNodes);
            } else {
                nextNodes[i] = new Node(move, lastNode, p2.getAllValidMoves(), depth - 1, getOpponent(turn), player);
                nextNextNodes = getNextNodes(p2.getAllValidMoves(), getOpponent(turn), player, depth - 1, nextNodes[i]);
                nextNodes[i].addNextNodes(nextNextNodes);
            }

            unSimulateMove(move);
            i++;
        }

        return nextNodes;
    }

    private int evalPath(Move move, Node lastNode) {
        int cost = 0;

        if (lastNode == null) {
            return cost;
        }

        if (lastNode.getTurn() == lastNode.getPlayer()) {
            if (move.isCaptured()) {
                cost += 2;
            }

            if (p2.isPassedPawn(move.getTo())) {
                cost += 2;
            }
        } else {
            if (move.isCaptured()) {
                cost = -3;
            }

            if (p1.isPassedPawn(move.getTo())) {
                cost -= 2;
            }
        }

        return (cost + evalPath(lastNode.getLastMove(), lastNode.getLastNode()));
    }

    private int walkPath(Node node) {

        if (node.getNextNodes() == null) {
            return node.getFinalCost();
        }

        int[] costs = new int[node.getNextNodes().length];
        for (int i = 0; i < node.getNextNodes().length; i++) {
            costs[i] = walkPath((node.getNextNodes())[i]);
        }

        return maximum(costs);
    }

    private Color getOpponent(Color current) {
        if (current == Color.BLACK) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    private int maximum(int[] ls) {
        int max = 0;
        for (int i = 0; i < ls.length; i++) {
            if (ls[i] > max) {
                max = ls[i];
            }
        }

        return max;
    }

    private void simulateMove(Move move) {
        board.applyMove(move);
    }

    private void unSimulateMove(Move move) {
        board.unApplyMove(move);
    }
}