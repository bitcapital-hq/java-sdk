/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package app.btcore.java;

import org.junit.Test;
import static org.junit.Assert.*;

import java.security.InvalidKeyException;

public class RequestSigningTest {
  @Test
  public void testSimpleSignMethod() {
    RequestSigning classUnderTest = new RequestSigning();
    try {
      String result = classUnderTest.sign("test", "test");
      System.out.println(result);
      assertEquals("should sign a simple string", result, "88cd2108b5347d973cf39cdf9053d7dd42704876d8c9a9bd8e2d168259d3ddf7");
    } catch (InvalidKeyException exception) {
      fail(exception.getMessage());
    }
  }
}
