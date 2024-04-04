package model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the pixel class.
 */
public class PixelTest {


  //tests the toString method.
  @Test
  public void testToString() {
    assertEquals(new Pixel(100, 100, 100, 100).toString(), "100 100 100 100");
    assertEquals(new Pixel(100, 0, 100, 100).toString(), "100 0 100 100");
    assertFalse(new Pixel().toString().equals("100 0 100 100"));
  }

  //tests the toStringHSL and toStringRGB methods, and both conversion methods.
  @Test
  public void testToStringHSL() {
    assertEquals(new Pixel(100, 100, 100, 100).toString(), "100 100 100 100");
    assertEquals(new Pixel(100, 0, 100, 100).toString(), "100 0 100 100");
    Pixel test = new Pixel(255, 254, 255);
    Pixel test2 = new Pixel(359, .5, .5);
    assertEquals(test.toStringHSL(), "300.0 1.0 0.9980392156862745");
    assertEquals(test2.toStringRGB(), ("191 63 65"));
  }
}