package src;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Game extends JPanel {
    public JPanel gamecontrol;
    public Board board;
    public Solver solver;
    public String runtime;
    public String errorMsg;
    public int visitedLength;
    public int pathLength;

    public Game(){
        this.board = new Board();
        this.runtime = "0 ms";
        this.errorMsg = "";
        this.solver = null;
        this.pathLength = 0;
        this.visitedLength = 0;
    }

    public void setBoard(Solver s){
        this.remove(board);
        this.board = new Board(s);
        this.add(board);
        board.revalidate();
        board.repaint();
    }

    public void initAlgorithm(String startWord, String endWord, int algoType) {
        clearResult("",0,0,"0");
        try {
            int len = startWord.length();
            if (len != endWord.length()){
                String errmsg = "Words must have same length!\n";
                clearResult(errmsg,0,0,"0");
                System.out.println(errorMsg);
                return;
            }
            else if (startWord.equals(endWord)){
                String errmsg = "Start word is equal to end word!\n";
                clearResult(errmsg,0,0,"0");
                System.out.println(errorMsg);
                return;
            }
            else{
                Dictionary d = new Dictionary(len);
                if (!d.contains(startWord) || !d.contains(endWord)){
                    String errmsg = "Your word is not on dictionary!\n";
                    clearResult(errmsg,0,0,"0");
                    System.out.println(errorMsg);
                    return;
                }

                long startTime = System.nanoTime();
                if (algoType == 1){
                    this.solver = new GBFS(startWord, endWord, d);
                }
                else if (algoType == 2){
                    this.solver = new AStar(startWord, endWord, d);
                }
                else{
                    this.solver = new Ucs(startWord, endWord, d);
                }
                this.solver.solve();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                this.solver.printResult();

                double milliseconds = duration / 1_000_000.0;
                DecimalFormat df = new DecimalFormat("#0.000");
                String formattedDuration = df.format(milliseconds) + " ms";

                System.out.println("Total runtime: " + formattedDuration);
                this.runtime = formattedDuration;
                this.visitedLength = solver.visitedWords;
                if (this.solver.result.size() > 0) {
                    this.setBoard(this.solver);
                    this.errorMsg = "";
                    this.pathLength = solver.result.size();
                }
                else{
                    this.errorMsg = "No path found!\n";
                    clearResult(errorMsg, 0, visitedLength, formattedDuration);
                }
                // return this.solver.result;
            }
        } catch (FileNotFoundException e) {
            String errmsg = "No dictionary file found\n";
            clearResult(errmsg,0,0,"0");
            System.out.println(errorMsg);
            return;
            // return null;
        } 
        
    }

    public void clearResult(String errmsg, int pathLength, int visitedLength, String runtime){
        this.remove(board);
        this.board = new Board();
        this.add(board);
        this.errorMsg = errmsg;
        this.runtime = runtime;
        this.pathLength = pathLength;
        this.visitedLength = visitedLength;
        this.revalidate(); 
        this.repaint();
    }

    public JPanel createUI(){
        JPanel pane = new JPanel(new FlowLayout());

        JPanel wordPanel = new RoundedPanel();
        wordPanel.setBorder(getBorder());
        wordPanel.setLayout(new GridLayout(4, 1));
        wordPanel.setBackground(Color.white);
        wordPanel.setPreferredSize(new Dimension(250, 150));
        JLabel labelStartWord = new JLabel("Start word: ");
        labelStartWord.setHorizontalAlignment(SwingConstants.CENTER);
        JTextField fieldStartWord = new JTextField(10);
        JLabel labelEndWord = new JLabel("End word: ");
        labelEndWord.setHorizontalAlignment(SwingConstants.CENTER);
        JTextField fieldEndWord = new JTextField(10);
        wordPanel.add(labelStartWord);
        wordPanel.add(fieldStartWord);
        wordPanel.add(labelEndWord);
        wordPanel.add(fieldEndWord);
        pane.add(wordPanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new RoundedPanel();
        centerPanel.setBorder(getBorder());
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setPreferredSize(new Dimension(250, 150));
        JPanel radioPanel = new JPanel(new GridLayout(3, 1));
        radioPanel.setOpaque(false);
        JRadioButton radioButtonGBFS = new JRadioButton("Greedy BFS");
        JRadioButton radioButtonAStar = new JRadioButton("A*");
        JRadioButton radioButtonUCS = new JRadioButton("UCS");
        ButtonGroup buttonAlgoType = new ButtonGroup();
        buttonAlgoType.add(radioButtonGBFS);
        buttonAlgoType.add(radioButtonAStar);
        buttonAlgoType.add(radioButtonUCS);
        radioPanel.add(radioButtonGBFS);
        radioPanel.add(radioButtonAStar);
        radioPanel.add(radioButtonUCS);
        centerPanel.add(radioPanel, BorderLayout.NORTH);
        
        JPanel resultPanel = new RoundedPanel(18,18);
        resultPanel.setBorder(getBorder());
        resultPanel.setLayout(new GridLayout(0, 1));
        resultPanel.setPreferredSize(new Dimension(250, 150));
        JLabel runtimeLabel = new JLabel("Runtime: ");
        runtimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JTextField runtimeText = new JTextField(10); //to do..
        runtimeText.setAlignmentX(CENTER_ALIGNMENT);
        JLabel visitedLabel = new JLabel("Visited words: ");
        visitedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JTextField visitedText = new JTextField(10);
        visitedText.setAlignmentX(CENTER_ALIGNMENT);
        JLabel pathLengthLabel = new JLabel("Path length: ");
        pathLengthLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JTextField pathLengthText = new JTextField(10);
        pathLengthText.setAlignmentX(CENTER_ALIGNMENT);
        JLabel errorText = new JLabel();
        errorText.setForeground(Color.red);
        errorText.setAlignmentX(CENTER_ALIGNMENT);
        
        resultPanel.setVisible(false); 
        resultPanel.add(runtimeLabel);
        resultPanel.add(runtimeText);
        resultPanel.add(visitedLabel);
        resultPanel.add(visitedText);
        resultPanel.add(pathLengthLabel);
        resultPanel.add(pathLengthText);
        resultPanel.add(errorText);
        
        JButton buttonSearch = new JButton("Search");
        centerPanel.add(buttonSearch, BorderLayout.SOUTH);
        pane.add(centerPanel, BorderLayout.CENTER);
        buttonSearch.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int algoSelection;
                if (radioButtonGBFS.isSelected()){
                    algoSelection = 1;
                }
                else if (radioButtonAStar.isSelected()){
                    algoSelection = 2;
                }
                else{
                    algoSelection = 3;
                }
                initAlgorithm(fieldStartWord.getText(), fieldEndWord.getText(), algoSelection);
                runtimeText.setText(runtime);
                visitedText.setText(String.valueOf(visitedLength));
                pathLengthText.setText(String.valueOf(pathLength));
                errorText.setText(errorMsg);
                errorText.setFont(new Font("Archivo", Font.BOLD, 12));
                resultPanel.setVisible(true);
            }
        });
        
        pane.add(resultPanel, BorderLayout.SOUTH);
		pane.setBackground(new Color(255, 219, 88));
		pane.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
		return pane;
    }

    public void startGame() throws Exception{
        gamecontrol = createUI();
		this.setBackground(new Color(255, 219, 88));
		this.setLayout(new GridLayout(1,2));
		this.add(gamecontrol,BorderLayout.WEST);
		this.add(board, BorderLayout.EAST);
    }

    public static void main(String[] args) throws Exception {
        Game game = new Game();
        Frame f = new Frame("Word Ladder Solver");
        f.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e){
                System.exit(0);
            }
        });
    
        game.setSize(400,400);
        game.setBorder(new EmptyBorder(20, 20, 20, 20)); //Spacer = 20
        f.add(game);
        f.pack(); //sized window to fit the preferred size and layouts of its subcomponents
        f.setSize(620,640);
        game.startGame();
        f.setVisible(true);
    }
}

class RoundedPanel extends JPanel {
    private int arcWidth;
    private int arcHeight;

    public RoundedPanel() {
        this.arcWidth = 15;
        this.arcHeight = 15;
        setOpaque(false); 
        setBackground(Color.white);
    }
    public RoundedPanel(int arcWidth, int arcHeight) {
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        setOpaque(false); 
        setBackground(Color.white);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setBackground(Color.white);
        g2d.setColor(Color.black);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
        g2d.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
    }
}