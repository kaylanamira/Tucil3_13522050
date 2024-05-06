package src;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel{
    public int defaultrow = 1;
	public int defaultcol = 0;
    public int cellsize = 25; 
	public int cellspacing = 7;
    public Solver solver;
    public int wordLength;
    public int resultNum;
	public ArrayList<ArrayList<Cell>> boardCells;

    public Board(){
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(true);
    }

    public Board(Solver solver){
        super(null);
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(true);
        setAlignmentX(CENTER_ALIGNMENT);
        this.wordLength = solver.result.get(0).getWord().length();
        this.resultNum = solver.result.size();
        this.boardCells = new ArrayList<>();

        for (int j = 0; j < resultNum; j++){
            ArrayList<Cell> words = new ArrayList<>();
            for (int i = 0; i < wordLength; i++){
                words.add(new Cell(i, j));
                words.get(i).setBounds(i * cellsize, j*(cellspacing + cellsize), cellsize, cellsize);
                this.add(words.get(i));
            }
            this.boardCells.add(words);
            setWord(j,solver.result.get(j).getWord());
        }
    }

    public boolean isCellIdxValid(int row, int col) {
		return (row >= 0 && col >= 0  && row < this.resultNum && col < this.wordLength);	
	}

    public void handleCellSelect(int row, int col){
        if (col >= this.wordLength) {
			col = 0;
			row++;
		}
		if (col < 0) {
			col = this.wordLength-1;
			row--;
		}
		if (isCellIdxValid(row, col)&& !getCell(row, col).isLocked)
			getCell(row, col).requestFocus();
    }

    public Cell getCell(int row,int col){
        if (isCellIdxValid(row, col)) {
			return this.boardCells.get(row).get(col);
		}
		return null;
    }

    // set word of a row
    public void setWord(int row, String word){
        int len = word.length();
		if (len == this.wordLength && isCellIdxValid(row,0)) {
			for (int i = 0; i < len; i++) {
                getCell(row,i).setKeyLetter(false);
                getCell(row,i).setLetter(word.charAt(i));
            }
		}
    }

    public void setRowLock(int row, boolean state) {
		for (int i = 0; i < wordLength; i++) {
			getCell(row,i).setLock(state);
		}
	}

    class Cell extends JPanel {
        public Boolean keyletter = false;
        public boolean hasfocus = false;
    
        public String letter;
        public boolean isLocked;
        public int row, col;
    
        public Cell(int col,int row){
            super();
            setOpaque(true);
            setBackground(new Color(225,225,225)); //Color cellbgcolor = new Color(225,225,225);
            setBorder(BorderFactory.createLineBorder(Color.BLACK)); //public Color bordercolor = new Color(163,136,191);
            setFont(new Font("Archivo", Font.BOLD, 12));
            this.letter = "";
            this.isLocked = false;
            this.row = row;
            this.col = col;
    
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {if (!isLocked)requestFocus();}
                public void mouseEntered(MouseEvent e) {if (!isLocked)setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));}
                public void mouseExited(MouseEvent e) {if (!isLocked)setCursor(Cursor.getDefaultCursor());}
            });
    
            addFocusListener(new FocusAdapter() {
                    public void focusGained(FocusEvent evt) {
                        hasfocus = true;
                        defaultcol = col;
                        defaultrow = row;
                        repaint();
                    }
                    public void focusLost(FocusEvent evt) {
                        hasfocus = false;
                        repaint();
                    }
                }
            );
        }

        public void paint(Graphics graphics){
            super.paint(graphics);
            if (this.isLocked){setBackground(new Color(186,175,200));} // Color celllockcolor  = new Color(186,175,200);
            else if (this.hasfocus){setBackground(new Color(156,154,206));}
            else if (this.keyletter){setBackground(new Color(186,175,200));}
            else{setBackground(new Color(225,225,225));}

            if (this.letter.length()>0){
                Graphics2D g = (Graphics2D)graphics;
                FontMetrics font = g.getFontMetrics();
				int height = font.getHeight();
                int width = font.stringWidth(letter);
				int ascent = font.getAscent();
				setForeground(Color.black);
				g.drawString(this.letter, (cellsize-width)/2, ascent +(cellsize-height)/2);
            }
        }

        public void setLetter(String letter) {
			this.letter = letter.toUpperCase();
			repaint();
		}

        public void setLetter(char c) {
            setLetter(String.valueOf(c));
            repaint();
        }

		public void setLock(boolean lock) {
			this.isLocked = lock;
			repaint();
		}

		public void setKeyLetter(boolean state) {
			this.keyletter = state;
			repaint();
		}
    }
    
}
