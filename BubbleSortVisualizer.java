import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BubbleSortVisualizer extends JPanel {
    private static final int NUM_BLOCKS = 20;
    private static final int BLOCK_WIDTH = 30;
    private static final int MAX_BLOCK_HEIGHT = 100;
    private static final int SORT_DELAY = 100;

    private int[] blockValues;
    private Color[] blockColors;

    public BubbleSortVisualizer() {
        blockValues = new int[NUM_BLOCKS];
        blockColors = new Color[NUM_BLOCKS];
        generateArray();
    }

    // Generate random array values
    private void generateArray() {
        Random random = new Random();
        for (int i = 0; i < NUM_BLOCKS; i++) {
            blockValues[i] = random.nextInt(MAX_BLOCK_HEIGHT) + 1;
            blockColors[i] = Color.GRAY;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < NUM_BLOCKS; i++) {
            g.setColor(blockColors[i]);
            int x = i * BLOCK_WIDTH;
            int height = blockValues[i] * 3;
            int y = getHeight() - height;
            g.fillRect(x, y, BLOCK_WIDTH - 2, height);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(blockValues[i]), x + 5, y - 5);
        }
    }

    private void swap(int i, int j) {
        int temp = blockValues[i];
        blockValues[i] = blockValues[j];
        blockValues[j] = temp;
        Color tempColor = blockColors[i];
        blockColors[i] = blockColors[j];
        blockColors[j] = tempColor;
    }

    public void bubbleSort() {
        new Thread(() -> {
            try {
                for (int i = 0; i < blockValues.length; i++) {
                    for (int j = 0; j < blockValues.length - i - 1; j++) {
                        blockColors[j] = Color.RED;
                        blockColors[j + 1] = Color.RED;
                        repaint();
                        Thread.sleep(SORT_DELAY);

                        if (blockValues[j] > blockValues[j + 1]) {
                            swap(j, j + 1);
                            repaint();
                            Thread.sleep(SORT_DELAY);
                        }

                        blockColors[j] = Color.GRAY;
                        blockColors[j + 1] = Color.GRAY;
                    }
                    blockColors[blockValues.length - i - 1] = Color.GREEN;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bubble Sort Visualization");
        BubbleSortVisualizer sorter = new BubbleSortVisualizer();
        frame.add(sorter);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        sorter.bubbleSort();
    }
}
