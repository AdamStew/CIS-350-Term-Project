package package1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Kate McGowan, Adam Stewart, Sierra Ellison
 * 
 * @version 1.0
 * 
 * 
 */

public class MineSweeperGUI extends JPanel {
	private JButton[][] board;
	private Cell cell;
	private JButton quitButton;
	private JButton resetButton;
	private JButton minesButton;
	private JPanel buttonPanel;
	private JPanel gamePanel;
	private ImageIcon smiley;
	private ImageIcon mine;
	private ImageIcon flag;
	private MineSweeperGame game;

	/**
	 * Constructor initializing game and GUI.
	 */
	public MineSweeperGUI() {
		game = new MineSweeperGame();

		smiley = new ImageIcon("smiley.gif");
		mine = new ImageIcon("mine.jpg");
		flag = new ImageIcon("flag.png");

		quitButton = new JButton("Quit");
		quitButton.setFont(new Font("Arial", Font.PLAIN, 10));
		quitButton.addActionListener(new ButtonListener());

		resetButton = new JButton(smiley);
		resetButton.addActionListener(new ButtonListener());

		minesButton = new JButton("Mines");
		minesButton.addActionListener(new ButtonListener());
		minesButton.setFont(new Font("Arial", Font.PLAIN, 10));

		buttonPanel = new JPanel();
		buttonPanel.add(quitButton);
		buttonPanel.add(resetButton);
		buttonPanel.add(minesButton);

		gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(game.getRows(), game.getCols()));
		gamePanel.setBackground(Color.gray);
		board = new JButton[game.getRows()][game.getCols()];
		createButtons();
		setLayout(new BorderLayout());
		add(buttonPanel, BorderLayout.NORTH);
		add(gamePanel, BorderLayout.CENTER);

	}

	/**
	 * A method to create the grid of buttons.
	 */
	private void createButtons() {
		for (int row = 0; row < game.getRows(); row++) {
			for (int col = 0; col < game.getCols(); col++) {
				// cannot be enabled with ImageIcon
				board[row][col] = new JButton();
				board[row][col].setPreferredSize(new Dimension(40, 40));
				board[row][col].addActionListener(new ButtonListener());
				board[row][col].addMouseListener(new ButtonListener());
				gamePanel.add(board[row][col]);

			}
		}
	}

	/**
	 * A method that is called when a button is clicked.
	 */
	private class ButtonListener implements ActionListener, MouseListener {
		public void actionPerformed(ActionEvent event) {
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
	}
}
