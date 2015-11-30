package sctd.main;

import sctd.util.Delay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

/**
 * @author Alexander Shabanov
 */
public final class MainGameWindow {
  public static final int DEFAULT_CANVAS_WIDTH = 1024;
  public static final int DEFAULT_CANVAS_HEIGHT = 768;

  private BufferStrategy bsStrategy; // fast flips
  private boolean bRunning = true; // main loop
  private final int canvasWidth = DEFAULT_CANVAS_WIDTH;
  private final int canvasHeight = DEFAULT_CANVAS_HEIGHT;
  private GameLoopCallback gameLoopCallback;
  private final Canvas canvas;

  public GameLoopCallback getGameLoopCallback() {
    return gameLoopCallback;
  }

  public void setGameLoopCallback(GameLoopCallback gameLoopCallback) {
    this.gameLoopCallback = gameLoopCallback;
  }

  public Component getCanvas() {
    return canvas;
  }

  public MainGameWindow() {
    this.canvas = new Canvas();

    final String title = "MainGameWindow";

    final JFrame frame = new JFrame(title);
    frame.setBounds(0, 0, canvasWidth, canvasHeight);
    frame.setLayout(null);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setVisible(true);

    // close frame handler
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        bRunning = false;
        Delay.sleep(10); // this is needed to avoid repaints on closing

        frame.dispose();
      }
    });

    // Panel added
    final JPanel pane = new JPanel();
    pane.setBounds(0, 0, canvasWidth, canvasHeight);
    pane.setLayout(null);
    frame.add(pane);

    // this Canvas added
    canvas.setBounds(pane.getBounds());
    pane.add(canvas);
    canvas.setIgnoreRepaint(true); // active painting only on canvas, does this apply to swing?
    canvas.requestFocus();  // get focus for keys

    // make it fast with this strat
    canvas.createBufferStrategy(2);
    bsStrategy = canvas.getBufferStrategy();
  }

  public void startLoop() {
    // Timer and FPS
    long loopTime;
    int fps = 0;
    int frames = 0;
    long startTime = System.currentTimeMillis();

    while (bRunning) {
      loopTime = System.currentTimeMillis();

      final Graphics2D g2d = (Graphics2D) bsStrategy.getDrawGraphics();

      // Wipe with black color
      g2d.setColor(Color.black);
      g2d.fillRect(0, 0, canvasWidth, canvasHeight);

      // Draw game loop data
      if (getGameLoopCallback() != null) {
        getGameLoopCallback().draw(g2d);
      }

      // fps counter
      if ((System.currentTimeMillis() - startTime) > 1000){
        startTime = System.currentTimeMillis();
        fps = frames;
        frames = 0;
      }

      ++frames;
      g2d.setColor(Color.green);
      g2d.drawString("fps: " + fps, 5, 30);


      // finally, we've completed drawing so clear up the graphics
      // and flip the buffer over
      g2d.dispose();
      bsStrategy.show();

      // Attempt to sleep for a consistent time. Smooths out bumps in processing.
      Delay.sleep(loopTime + 10 - System.currentTimeMillis());
    }
  }
}
