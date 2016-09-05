package sctd.demo;

import sctd.graphics.SpriteService;
import sctd.graphics.TileService;
import sctd.graphics.Viewport;
import sctd.graphics.support.DefaultViewportService;
import sctd.graphics.support.DemoSpriteService;
import sctd.graphics.support.DemoTileService;
import sctd.logic.command.MoveCommand;
import sctd.logic.command.PatrolCommand;
import sctd.logic.dispatcher.GameDispatcher;
import sctd.main.GameLoopCallback;
import sctd.main.MainGameWindow;
import sctd.model.GameField;
import sctd.model.GameUnit;
import sctd.model.PlayerAction;
import sctd.util.ScreenParameters;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Alexander Shabanov
 */
public final class SnowDemo extends GameLoopCallback {


  private final GameDispatcher dispatcher;
  private final GameField gameField;
  private final SpriteService spriteService;
  private final TileService tileService;
  private boolean allowMultipleSelection = false;

  private final Viewport viewport;

  public SnowDemo() {
    dispatcher = new GameDispatcher();
    gameField = new GameField(52, 33);

    final DefaultViewportService viewportService = new DefaultViewportService(gameField,
        ScreenParameters.getWidth(), ScreenParameters.getHeight());
    viewport = viewportService;
    spriteService = new DemoSpriteService(viewportService);
    tileService = new DemoTileService(viewportService);

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
          playerActions.add(PlayerAction.SCROLL_UP);
          break;
        case KeyEvent.VK_S:
          playerActions.add(PlayerAction.SCROLL_DOWN);
          break;
        case KeyEvent.VK_A:
          playerActions.add(PlayerAction.SCROLL_LEFT);
          break;
        case KeyEvent.VK_D:
          playerActions.add(PlayerAction.SCROLL_RIGHT);
          break;
        case KeyEvent.VK_CONTROL:
          allowMultipleSelection = true;
          break;
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
      switch (e.getKeyCode()) {
        case KeyEvent.VK_W:
          playerActions.remove(PlayerAction.SCROLL_UP);
          break;
        case KeyEvent.VK_S:
          playerActions.remove(PlayerAction.SCROLL_DOWN);
          break;
        case KeyEvent.VK_A:
          playerActions.remove(PlayerAction.SCROLL_LEFT);
          break;
        case KeyEvent.VK_D:
          playerActions.remove(PlayerAction.SCROLL_RIGHT);
          break;
        case KeyEvent.VK_CONTROL:
          allowMultipleSelection = false;
          break;
      }
    }
  };

  private final MouseAdapter mouseAdapter = new MouseAdapter() {

    @Override
    public void mousePressed(MouseEvent e) {
      // translate coordinates
      final double x = e.getX() + viewport.getViewportX();
      final double y = e.getY() + viewport.getViewportY();

      if (e.getButton() == MouseEvent.BUTTON1) {
        SnowDemo.this.dispatcher.select(x, y, allowMultipleSelection);
      } else if (e.getButton() == MouseEvent.BUTTON3) {
        SnowDemo.this.dispatcher.moveSelectedTo(x, y);
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

  private long counter = 0;
  private static final long COUNTER_GAP = 2;

  @Override
  public void draw(Graphics2D g2d) {
    tileService.drawGameField(g2d, gameField);

    final List<GameUnit> selectedUnits = new ArrayList<>();
    for (final GameUnit unit : dispatcher.getUnits()) {
      spriteService.drawUnit(unit, g2d);
      if (unit.isSelected()) {
        selectedUnits.add(unit);
      }
    }

    // show info about selected units
    g2d.setColor(Color.green);
    if (selectedUnits.size() == 1) {
      final GameUnit selectedUnit = selectedUnits.get(0);
      g2d.drawString("> selected unit: id=" + selectedUnit.getId() + ", life=" + selectedUnit.getLife(), 5, 60);
    } else if (selectedUnits.size() > 1) {
      g2d.drawString("> " + selectedUnits.size() + " units selected", 5, 60);
    }

    ++counter;
    if (counter == COUNTER_GAP) {
      dispatcher.tick();
      counter = 0;
    }

    if (playerActions.contains(PlayerAction.SCROLL_UP)) {
      viewport.scrollUp();
    }
    if (playerActions.contains(PlayerAction.SCROLL_DOWN)) {
      viewport.scrollDown();
    }
    if (playerActions.contains(PlayerAction.SCROLL_LEFT)) {
      viewport.scrollLeft();
    }
    if (playerActions.contains(PlayerAction.SCROLL_RIGHT)) {
      viewport.scrollRight();
    }
  }
}
