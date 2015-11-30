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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alexander Shabanov
 */
public final class SnowDemo extends GameLoopCallback {

  private int fx = 250;
  private int fy = 250;

  private final GameDispatcher dispatcher;

  private enum PlayerAction {
    GO_UP,
    GO_DOWN,
    GO_LEFT,
    GO_RIGHT,
  }

  public SnowDemo() {
    dispatcher = new GameDispatcher();

    final GameUnit unit1 = dispatcher.addUnit(50, 50, 48, 1);
    unit1.setMaximumVelocity(4);
    unit1.setAcceleration(0.5);
    unit1.getCommands().newCommand(new PatrolCommand(200, 250));


    final GameUnit unit2 = dispatcher.addUnit(100, 100, 64, 2);
    unit2.setMaximumVelocity(3);
    unit2.setAcceleration(0.8);
    unit2.getCommands().newCommand(new MoveCommand(150, 220));
    unit2.getCommands().addCommand(new MoveCommand(100, 120));
    unit2.getCommands().addCommand(new MoveCommand(150, 200));
    unit2.getCommands().addCommand(new MoveCommand(180, 400));

    final GameUnit unit3 = dispatcher.addUnit(100, 480, 128, 3);
    unit3.setMaximumVelocity(2);
    unit3.setAcceleration(0.1);
    unit3.getCommands().newCommand(new PatrolCommand(720, 500));
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

  private final MouseAdapter mouseAdapter = new MouseAdapter() {

    @Override
    public void mousePressed(MouseEvent e) {
      //System.out.println("button=" + e.getButton());

      if (e.getButton() == MouseEvent.BUTTON1) {
        SnowDemo.this.dispatcher.select(e.getX(), e.getY());
      } else if (e.getButton() == MouseEvent.BUTTON3) {
        SnowDemo.this.dispatcher.moveSelectedTo(e.getX(), e.getY());
      }
    }
  };

  public static void main(String[] args) {
    final MainGameWindow mainGameWindow = new MainGameWindow();
    final SnowDemo snowDemo = new SnowDemo();
    mainGameWindow.getCanvas().addKeyListener(snowDemo.keyAdapter);
    mainGameWindow.getCanvas().addMouseListener(snowDemo.mouseAdapter);

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
    if (unit.isSelected()) {
      g2d.setPaint(Color.GREEN);
      final double gap = unit.getSize() / 4;
      g2d.draw(new Ellipse2D.Double(unit.getX(), unit.getY() + gap, unit.getSize(), unit.getSize() - gap));
    }

    Color bkColor = Color.GRAY;
    Color triColor = Color.WHITE;
    switch (unit.getSpriteId()) {
      case 1:
        bkColor = Color.YELLOW;
        triColor = Color.GRAY;
        break;

      case 2:
        bkColor = Color.GREEN;
        triColor = Color.WHITE;
        break;

      case 3:
        bkColor = Color.WHITE;
        triColor = Color.RED;
        break;
    }

    // center coord
    final int size = (int) unit.getSize();
    final int cx = (int) (unit.getX() + size / 2.0);
    final int cy = (int) (unit.getY() + size / 2.0);

    g2d.rotate(unit.getAngle(), cx, cy);

    // prepare polygon array
    final int[] xp = new int[3];
    final int[] yp = new int[3];

    // inner rectangle (wings)
    {
      xp[0] = cx + size / 8; xp[1] = xp[2] = cx - size / 8;
      yp[0] = cy; yp[1] = cy - (3 * size) / 8; yp[2] = cy + (3 * size) / 8;

      g2d.setPaint(bkColor);
      g2d.fillPolygon(xp, yp, 3);
    }

    // outer rectangle (body)
    {
      xp[0] = cx + (size * 3) / 8; xp[1] = xp[2] = cx - (size * 3) / 8;
      yp[0] = cy; yp[1] = cy - size / 4; yp[2] = cy + size / 4;

      g2d.setPaint(triColor);
      g2d.fillPolygon(xp, yp, 3);
    }

    g2d.rotate(-unit.getAngle(), cx, cy); // restore angle
  }
}
