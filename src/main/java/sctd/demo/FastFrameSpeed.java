package sctd.demo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * See also http://stackoverflow.com/questions/11159047/component-must-be-a-valid-peer-when-i-remove-frame-addcomponent
 * http://darrinadams.blogspot.com/2009/04/optimizing-java-2d-for-game.html
 */
public class FastFrameSpeed extends Canvas {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    final FastFrameSpeed game = new FastFrameSpeed();
    game.loopGame();
  }

  private BufferStrategy bsStrategy; // fast flips
  private boolean bRunning = true; // main loop
  private static final int CANVAS_X = 1024;
  private static final int CANVAS_Y = 768;

  public FastFrameSpeed(){
    //  Frame Setup
    final JFrame frame = new JFrame("Speed Test");
    frame.setBounds(0, 0, CANVAS_X, CANVAS_Y);
    frame.setLayout(null);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setVisible(true);

    frame.addWindowListener(new WindowAdapter() {  // a new way to close
      @Override
      public void windowClosing(WindowEvent e) {
        bRunning = false;
        sleep(10);
        frame.dispose();
      }
    });

    // Panel added
    JPanel pane = new JPanel();
    pane.setBounds(0, 0, CANVAS_X, CANVAS_Y);
    pane.setLayout(null);
    frame.add(pane);

    // this Canvas added
    setBounds(pane.getBounds());
    pane.add(this);
    setIgnoreRepaint(true); // active painting only on canvas, does this apply to swing?
    requestFocus();  // get focus for keys

    // make it fast with this strat
    createBufferStrategy(2);
    bsStrategy = getBufferStrategy();
  }

  public void loopGame() {
    // Timer and FPS
    long loopTime = System.currentTimeMillis();

    int fps = 0;
    int frames = 0;
    long startTime = System.currentTimeMillis();

    // TODO remove this external timer for weapons
    long shotTime = System.currentTimeMillis();

    while (bRunning) {
      long timeLapse = System.currentTimeMillis() - loopTime;  // used to move objects smoothly
      loopTime = System.currentTimeMillis();

      final Graphics2D g2d = (Graphics2D) bsStrategy.getDrawGraphics();

      // Wipe
      g2d.setColor(Color.black);
      g2d.fillRect(0, 0, CANVAS_X, CANVAS_Y);

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
      sleep(loopTime + 10 - System.currentTimeMillis());
    }
  }

  private static void sleep(long delay) {
    try {
      Thread.sleep(delay);
    } catch (Exception ignored) {
      Thread.interrupted();
    }
  }

}
