//Name: Sanjana Kondabathini, id: 1001984312

//importing the neccessery packages
import java.util.*;
import java.util.Scanner;

public class RedBluenim{
    public static final int RED_MARB = 2;
    public static final int BLUE_MARB = 3;
    public static int depth;
//Command line innvocation
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Usage: java RedBluenim <n-red> <n-blue> <firstplayer> <depth>");
            System.exit(1);
        }

        int num_red_aplpha = Integer.parseInt(args[0]);
        int num_blue_aplpha = Integer.parseInt(args[1]);
        String playerWhoPlaysFirst = args[2];
        depth = Integer.parseInt(args[3]);

        if (!(playerWhoPlaysFirst.equals("human") || playerWhoPlaysFirst.equals("computer"))) {
            System.out.println("Opps! Invalid entry first player. Enter 'human' or 'computer'");
            System.exit(1);
        }

        System.out.println("Here Starting the red-blue nim game with " + num_red_aplpha +
                " red and " + num_blue_aplpha + " blue marbles.");

        int playerWhopresently = (playerWhoPlaysFirst.equals("human")) ? 0 : 1;
        Scanner scaSXK = new Scanner(System.in);
//Play a game of Red-Blue Nim against a human player.
        while (num_red_aplpha > 0 && num_blue_aplpha > 0) {
            if (playerWhopresently == 0) {
                System.out.println("Enter the marble color such as 'red' or 'blue': ");
                String marble_clr_redandblue = scaSXK.next().toLowerCase();

                while (!marble_clr_redandblue.equals("red") && !marble_clr_redandblue.equals("blue")) {
                    System.out.print("Enter the marble color such as 'red' or 'blue': ");
                    marble_clr_redandblue = scaSXK.next().toLowerCase();
                }

                System.out.print("Insert the number of " + marble_clr_redandblue + " marbles to remove: ");
                int nOfMarbletoRmv = scaSXK.nextInt();

                if (marble_clr_redandblue.equals("red")) {
                    num_red_aplpha -= nOfMarbletoRmv;
                } else {
                    num_blue_aplpha -= nOfMarbletoRmv;
                }
// human turn
                System.out.println("Now Human removes " + nOfMarbletoRmv + " " + marble_clr_redandblue + " marble" +
                        ((nOfMarbletoRmv > 1) ? "s" : "") + ".");
                System.out.println("There are " + num_red_aplpha + " red marbles and " +
                        num_blue_aplpha + " blue marbles remaining.");
            } else {
                int[] movingTiles = minmax_ab(num_red_aplpha, num_blue_aplpha,depth,true);
                String marble_clr_redandblue = (movingTiles[0] == 0) ? "red" : "blue";
                int nOfMarbletoRmv = movingTiles[1];
// computer turn
                if (marble_clr_redandblue.equals("red")) {
                    num_red_aplpha -= nOfMarbletoRmv;
                } else {
                    num_blue_aplpha -= nOfMarbletoRmv;
                }

                System.out.println("Now Computer removes " + nOfMarbletoRmv + " " + marble_clr_redandblue + " marble" +
                        ((nOfMarbletoRmv > 1) ? "s" : "") + ".");
                System.out.println("There are " + num_red_aplpha + " red marbles and " +
                        num_blue_aplpha + " blue marbles remaining.");
            }

            playerWhopresently = (playerWhopresently + 1) % 2;
        }

        if (num_red_aplpha == 0) {
            int value = num_blue_aplpha * BLUE_MARB;
            System.out.println("Game done");
            System.out.println("Computer Won with the final score of " + value + ".");
        } else {
            int value = num_red_aplpha * RED_MARB;
            System.out.println("Game done");
            System.out.println("Human Won with the final score of " + value + ".");
        }

        scaSXK.close();
    }
    private static int[] makingthemarbmovenext(int num_red_aplpha, int num_blue_aplpha, int[] movingTiles) {
        int[] nextnewtile = {num_red_aplpha, num_blue_aplpha};
        int pileSXKCount = movingTiles[0];
        int marblesToRemove = movingTiles[1];
        
        if (pileSXKCount == 0) {
            nextnewtile[0] -= marblesToRemove;
        } else {
            nextnewtile[1] -= marblesToRemove;
        }
        
        return nextnewtile;
    }
    
    private static List<int[]> alphageneratingMove(int num_red_aplpha, int num_blue_aplpha) {
        List<int[]> computeRM = new ArrayList<>();
    
        for (int sxkmu = 0; sxkmu < 2; sxkmu++) {
            int playerMarbles = (sxkmu == 0) ? num_red_aplpha : num_blue_aplpha;
    
            for (int sxkl = 1; sxkl <= playerMarbles; sxkl++) {
                computeRM.add(new int[]{sxkmu, sxkl});
            }
        }
    
        return computeRM;
    }
    
    //Implementiong the Recursive function to find the best move using Minimax algorithm.
    //Checking if node is a leaf node or not
    public static int[] minmax_ab(int num_red_aplpha, int num_blue_aplpha, int depth, boolean maximizingPlayer) {
        int value;
        int priorityMove;
        int[] bestMove = new int[2];
        int[] PresentMoveRandBMarble = new int[2];
    //if depth limit has been reached, return a heuristic value for the current node

        if (num_red_aplpha == 0 || num_blue_aplpha == 0 || depth == 0) {
            value = (num_red_aplpha + num_blue_aplpha) * ((num_red_aplpha == 0) ? BLUE_MARB : RED_MARB);
            return new int[]{-1, value};
        }
    // priority move corresponds to the best move that picks
        if (maximizingPlayer) {
            priorityMove = Integer.MIN_VALUE;
    
            for (int sxkmu = 0; sxkmu <= num_red_aplpha; sxkmu++) {
                PresentMoveRandBMarble[0] = 0;
                PresentMoveRandBMarble[1] = sxkmu;
    
                int[] nextMove = minmax_ab(num_red_aplpha - sxkmu, num_blue_aplpha, depth - 1, false);
    
                value = nextMove[1];
    
                if (value >= priorityMove) {
                    priorityMove = value;
                    bestMove[0] = PresentMoveRandBMarble[0];
                    bestMove[1] = PresentMoveRandBMarble[1];
                }
            }
    
            for (int sxkmu = 0; sxkmu <= num_blue_aplpha; sxkmu++) {
                PresentMoveRandBMarble[0] = 1;
                PresentMoveRandBMarble[1] = sxkmu;
    
                int[] nextMove = minmax_ab(num_red_aplpha, num_blue_aplpha - sxkmu, depth - 1, false);
    
                value = nextMove[1];
    
                if (value > priorityMove) {
                    priorityMove = value;
                    bestMove[0] = PresentMoveRandBMarble[0];
                    bestMove[1] = PresentMoveRandBMarble[1];
                }
            }
        } else {
            priorityMove = Integer.MAX_VALUE;
    
            for (int sxkmu = 0; sxkmu <= num_red_aplpha; sxkmu++) {
                PresentMoveRandBMarble[0] = 0;
                PresentMoveRandBMarble[1] = sxkmu;
    
                int[] nextMove = minmax_ab(num_red_aplpha - sxkmu, num_blue_aplpha, depth - 1, true);
    
                value = nextMove[1];
    
                if (value <= priorityMove) {
                    priorityMove = value;
                    bestMove[0] = PresentMoveRandBMarble[0];
                    bestMove[1] = PresentMoveRandBMarble[1];
                }
            }
    // presentmove represents the current move
            for (int sxkmu = 0; sxkmu <= num_blue_aplpha; sxkmu++) {
                PresentMoveRandBMarble[0] = 1;
                PresentMoveRandBMarble[1] = sxkmu;
    
                int[] nextMove = minmax_ab(num_red_aplpha, num_blue_aplpha - sxkmu, depth - 1, true);
    
                value = nextMove[1];
    
                if (value < priorityMove) {
                    priorityMove = value;
                    bestMove[0] = PresentMoveRandBMarble[0];
                    bestMove[1] = PresentMoveRandBMarble[1];
                }
            }
        }
    
        return bestMove;
    }
    //imlemeneting the evaluate function
    private static int evaluatingthePresentMoveRandBMarble(int num_red_aplpha, int num_blue_aplpha, int depth, int sxk4313, int betasxk, boolean maximizingPlayer) {
        if (num_red_aplpha == 0 || num_blue_aplpha == 0) {
            return (num_red_aplpha + num_blue_aplpha) * ((num_red_aplpha == 0) ? BLUE_MARB : RED_MARB);
        }
    
        if (depth == 0) {
            return 0;
        }
    
        int value;
        if (maximizingPlayer) {
            value = Integer.MIN_VALUE;
    
            for (int[] tempMovingTiles : alphageneratingMove(num_red_aplpha, num_blue_aplpha)) {
                int[] nextnewtile = makingthemarbmovenext(num_red_aplpha, num_blue_aplpha, tempMovingTiles);
    
                value = Math.max(value, evaluatingthePresentMoveRandBMarble(nextnewtile[0], nextnewtile[1], depth - 1, sxk4313, betasxk, false));
    
                sxk4313 = Math.max(sxk4313, value);
                if (betasxk <= sxk4313) {
                    break;
                }
            }
        } else {
            value = Integer.MAX_VALUE;
    // Returns the minimax value and respected move for a given node in the game tree,
    //implementing the Minimax algorithm that includes Alpha-Beta Pruning.
            for (int[] tempMovingTiles : alphageneratingMove(num_red_aplpha, num_blue_aplpha)) {
                int[] nextnewtile = makingthemarbmovenext(num_red_aplpha, num_blue_aplpha, tempMovingTiles);
    
                value = Math.min(value, evaluatingthePresentMoveRandBMarble(nextnewtile[0], nextnewtile[1], depth - 1, sxk4313, betasxk, true));
    
                betasxk = Math.min(betasxk, value);
                if (betasxk <= sxk4313) {
                    break;
                }
            }
        }
    
        return value;
    }
}