package com.brendanzhao.monkeydash;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.Timer;

public class MonkeyDashController implements KeyListener {
	
	private MonkeyDashModel model;
	private MonkeyDashView view;
	private Timer timer;
	
	public MonkeyDashController(MonkeyDashModel model, MonkeyDashView view) {
		this.model = model;
		this.view = view;
		this.view.addKeyListener(this);
		
		timer = new Timer(Constants.TIMER_TICK_MILLISECONDS, new GameTimerListener());
		timer.setInitialDelay(Constants.INITIAL_TIMER_DELAY);
		timer.start();
	}
	
	public void moveBlocks(List<Block> blocks) {
		for (Block b : blocks) {
			if (b.getX() <= Block.getImage().getWidth() * -1) {
				b.setX(Constants.SPACE_BETWEEN_BLOCKS * 3 + Block.getImage().getWidth() * 2);
			}
			
			b.setX(b.getX() - Constants.PIXEL_SPEED_PER_TICK);
		}
	}
	
	public boolean monkeyIsOnBlock(Monkey monkey, List<Block> blocks) {
		if (monkey.getY() != Constants.BLOCK_LEVITATION_HEIGHT - monkey.getCurrentImageFrame().getHeight()) {
			return false;
		}
		
		for (Block b : blocks) {
			if (monkey.getX() + Constants.MONKEY_BLOCK_COLLISION_OFFSET > b.getX() && monkey.getX() < b.getX() + Block.getImage().getWidth() - Constants.MONKEY_BLOCK_COLLISION_OFFSET) {
				return true;
			}
		}
		
		return false;
	}
	
	public void applyMonkeyGravity(Monkey monkey, List<Block> blocks) {
		if (!monkeyIsOnBlock(monkey, blocks)) {
			monkey.setVerticalVelocity(monkey.getVerticalVelocity() + Constants.GRAVITY);
			
			if (monkey.getState() == MonkeyState.FirstRun) {
				monkey.setState(MonkeyState.Falling);
			}
		} else if (monkey.getVerticalVelocity() > 0) {
			monkey.setVerticalVelocity(0);
			monkey.setState(MonkeyState.FirstRun);
		}
	}
	
	public void updateMonkeyPosition(Monkey monkey) {
		int newYPosition = monkey.getY() + monkey.getVerticalVelocity();
		
		if (monkey.getY() < Constants.BLOCK_LEVITATION_HEIGHT - monkey.getCurrentImageFrame().getHeight() && newYPosition > Constants.BLOCK_LEVITATION_HEIGHT - monkey.getCurrentImageFrame().getHeight()) {
			monkey.setY(Constants.BLOCK_LEVITATION_HEIGHT - monkey.getCurrentImageFrame().getHeight());
		} else {
			monkey.setY(newYPosition);
		}
	}
	
	public void processKeyInput(Monkey monkey, int keyCode) {
		switch(monkey.getState()) {
			case FirstRun:
			case SecondRun:
				if (keyCode == KeyEvent.VK_SPACE) {
					model.getMonkey().setVerticalVelocity(Constants.JUMP_STRENGTH);
					model.getMonkey().setState(MonkeyState.FirstJump);
				}
				break;
			case FirstJump:
				if (keyCode == KeyEvent.VK_SPACE) {
					model.getMonkey().setVerticalVelocity(Constants.JUMP_STRENGTH);
					model.getMonkey().setState(MonkeyState.SecondJump);
				}
				break;
			case SecondJump:
			case Falling:
			default:
				break;
		}
	}
	
	public int animateMonkeyRun(Monkey monkey, int currentTickInSecond) {
		if (currentTickInSecond % Constants.TIMER_TICKS_RUN_ANIMATION == 0) {
			if (monkey.getState() == MonkeyState.FirstRun) {
				monkey.setState(MonkeyState.SecondRun);
			} else if (monkey.getState() == MonkeyState.SecondRun) {
				monkey.setState(MonkeyState.FirstRun);
			}
			
			return 1;
		}
		
		return ++currentTickInSecond;
	}
	
	class GameTimerListener implements ActionListener {
		private int animationTickTracker;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			animationTickTracker = animateMonkeyRun(model.getMonkey(), animationTickTracker);
			applyMonkeyGravity(model.getMonkey(), model.getBlocks());
			updateMonkeyPosition(model.getMonkey());
			moveBlocks(model.getBlocks());
			view.repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		processKeyInput(model.getMonkey(), e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Intentionally blank		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// Intentionally blank
	}
}