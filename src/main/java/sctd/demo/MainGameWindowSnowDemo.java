package sctd.demo;

import sctd.main.GameLoopCallback;
import sctd.main.MainGameWindow;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alexander Shabanov
 */
public final class MainGameWindowSnowDemo extends GameLoopCallback {
  private int x = 50;
  private int y = 50;
  private int w = 10;
  private int delta = 1;

  private int fx = 250;
  private int fy = 250;
  private int fw = 20;
  private int fdelta = 2;

  private enum PlayerAction {
    GO_UP,
    GO_DOWN,
    GO_LEFT,
    GO_RIGHT,
  }

  private final Set<PlayerAction> playerActions = new HashSet<>(20);

  private final KeyAdapter keyAdapter = new KeyAdapter() {
    @Override
    public void keyPressed(KeyEvent e) {
      switch (e.getKeyCode()) {
        case KeyEvent.VK_W:
          playerActions.add(PlayerAction.GO_UP);
          break;
        case KeyEvent.VK_S:
          playerActions.add(PlayerAction.GO_DOWN);
          break;
        case KeyEvent.VK_A:
          playerActions.add(PlayerAction.GO_LEFT);
          break;
        case KeyEvent.VK_D:
          playerActions.add(PlayerAction.GO_RIGHT);
          break;
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
      switch (e.getKeyCode()) {
        case KeyEvent.VK_W:
          playerActions.remove(PlayerAction.GO_UP);
          break;
        case KeyEvent.VK_S:
          playerActions.remove(PlayerAction.GO_DOWN);
          break;
        case KeyEvent.VK_A:
          playerActions.remove(PlayerAction.GO_LEFT);
          break;
        case KeyEvent.VK_D:
          playerActions.remove(PlayerAction.GO_RIGHT);
          break;
      }
    }
  };

  public static void main(String[] args) {
    final MainGameWindow mainGameWindow = new MainGameWindow();
    final MainGameWindowSnowDemo snowDemo = new MainGameWindowSnowDemo();
    mainGameWindow.addKeyListener(snowDemo.keyAdapter);

    mainGameWindow.setGameLoopCallback(snowDemo);
    mainGameWindow.startLoop();
  }

  @Override
  public void draw(Graphics2D g2d) {
    g2d.setPaint(Color.YELLOW);
    final double halfWidth = w / 2;
    g2d.fill(new Rectangle2D.Double(x - halfWidth, y - halfWidth, w, w));
    x = x + delta;
    if (x > 700) {
      delta = -1;
    } else  if (x < 50) {
      delta = 1;
    }

    g2d.setPaint(Color.RED);
    g2d.fill(new Rectangle2D.Double(fx - fw / 2, fy - fw / 2, fw, fw));

    if (playerActions.contains(PlayerAction.GO_UP)) {
      fy = fy - fdelta;
    }
    if (playerActions.contains(PlayerAction.GO_DOWN)) {
      fy = fy + fdelta;
    }
    if (playerActions.contains(PlayerAction.GO_LEFT)) {
      fx = fx - fdelta;
    }
    if (playerActions.contains(PlayerAction.GO_RIGHT)) {
      fx = fx + fdelta;
    }
  }
}
