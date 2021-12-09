package gutest;

import static org.junit.Assert.assertEquals;
import controller.ViewController;
import controller.GuiControllerInterface;
import org.junit.Before;
import org.junit.Test;
import view.MockView;
import view.ViewInterface;

/**
 * To test the controller using a mock model and a mock view to determine if the functionality of
 * the controller is working as per expectations.
 */
public class ViewControllerTest {

  Appendable out;
  GuiControllerInterface controller;
  ViewInterface view;

  @Before
  public void setup() {
    out = new StringBuilder();
    view = new MockView(out);
    controller = new ViewController(view);
  }

  @Test
  public void makeDungeon() {
    controller.makeDungeon(5, 5, 4, true, 50, 1);
    assertEquals("makeDungeon", out.toString());
  }

  @Test
  public void getMovable() {
    controller.getMovable(1);
    assertEquals("getMovable", out.toString());
  }


  @Test
  public void move() {
    controller.move("N");
    assertEquals("move", out.toString());
  }

  @Test
  public void shoot() {
    controller.shoot("N", 1);
    assertEquals("shoot", out.toString());
  }

  @Test
  public void updateInformation() {
    controller.updateInformation();
    assertEquals("updateInformation", out.toString());
  }

  @Test
  public void pickArrow() {
    controller.pickArrow();
    assertEquals("pickArrow", out.toString());
  }

  @Test
  public void pickTreasure() {
    controller.pickTreasure();
    assertEquals("pickTreasure", out.toString());
  }

  @Test
  public void poisonShoot() {
    controller.poisonShoot("N", 2);
    assertEquals("poisonShoot", out.toString());
  }

  @Test
  public void restartSameDungeon() {
    controller.restartSameDungeon();
    assertEquals("restartSameDungeon", out.toString());
  }

  @Test
  public void restartNewDungeon() {
    controller.restartNewDungeon();
    assertEquals("restartNewDungeon", out.toString());
  }

  @Test
  public void quit() {
    controller.quit();
    assertEquals("quit", out.toString());
  }
}