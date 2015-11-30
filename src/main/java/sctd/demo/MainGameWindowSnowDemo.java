package sctd.demo;

import sctd.logic.command.MoveCommand;
import sctd.logic.command.PatrolCommand;
import sctd.logic.dispatcher.GameDispatcher;
import sctd.main.GameLoopCallback;
import sctd.main.MainGameWindow;
import sctd.model.GameUnit;

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

  private int fx = 250;
  private int fy = 250;

  private final GameDispatcher dispatcher;

  private enum PlayerAction {
    GO_UP,
    GO_DOWN,
    GO_LEFT,
    GO_RIGHT,
  }

  public MainGameWindowSnowDemo() {
    dispatcher = new GameDispatcher();

    final GameUnit unit1 = dispatcher.addUnit(50, 50, 16, 1);
    unit1.getCommands().newCommand(new PatrolCommand(200, 250));


    final GameUnit unit2 = dispatcher.addUnit(100, 100, 24, 2);
    unit2.getCommands().newCommand(new MoveCommand(150, 220));
    unit2.getCommands().addCommand(new MoveCommand(100, 120));
    unit2.getCommands().addCommand(new MoveCommand(150, 200));
    unit2.getCommands().addCommand(new MoveCommand(180, 400));
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
    mainGameWindow.getCanvas().addKeyListener(snowDemo.keyAdapter);

    mainGameWindow.setGameLoopCallback(snowDemo);
    mainGameWindow.startLoop();
  }

  long counter = 0;
  static final long COUNTER_GAP = 2;

  @Override
  public void draw(Graphics2D g2d) {
    for (final GameUnit unit : dispatcher.getUnits()) {
      drawUnit(unit, g2d);
    }

    ++counter;
    if (counter == COUNTER_GAP) {
      dispatcher.tick();
      counter = 0;
    }


    g2d.setPaint(Color.RED);
    int fw = 20;
    g2d.fill(new Rectangle2D.Double(fx - fw / 2, fy - fw / 2, fw, fw));

    int fdelta = 2;
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

  //
  // Private
  //

  private static void drawUnit(GameUnit unit, Graphics2D g2d) {
    Color color;
    switch (unit.getSpriteId()) {
      case 1:
        color = Color.YELLOW;
        break;

      case 2:
        color = Color.GREEN;
        break;

      default:
        color = Color.GRAY;
    }

    final double gap = 2.0;
    final double x = unit.getX() + gap;
    final double y = unit.getY() + gap;
    final double w = unit.getSize() - 2 * gap;

    g2d.setPaint(color);
    g2d.fill(new Rectangle2D.Double(x, y, w, w));
  }
}
