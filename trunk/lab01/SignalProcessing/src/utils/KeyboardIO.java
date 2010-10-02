/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author cfouard
 */
public class KeyboardIO {

  private static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

  public static int readInteger(String prompt) {
    System.out.print(prompt);
    System.out.flush();
    return readInteger();
  }

  public static int readInteger() {
    try {
      return (Integer.parseInt(stdin.readLine()));
    } catch (Exception e) {
      inputError(e, "integer");
      return 0; // même si on passe jamais ici il faut un return !!
    }
  }

  public static String readString(String prompt) {
    System.out.print(prompt);
    System.out.flush();
    return readString();
  }

  public static String readString() {
    try {
      return (stdin.readLine());
    } catch (Exception e) {
      inputError(e, "string");

      return null;
    }
  }

  public static float readFloat(String prompt) {
    System.out.print(prompt);
    System.out.flush();
    return readFloat();
  }

  public static float readFloat() {

    try {
      return (Float.valueOf(stdin.readLine()).floatValue());
    } catch (Exception e) {
      inputError(e, "float");

      return (float) 0.0;
    }
  }

  public static double readDouble(String prompt) {
    System.out.print(prompt);
    System.out.flush();
    return readDouble();
  }

  public static double readDouble() {
    try {
      return (Double.valueOf(stdin.readLine()).doubleValue());
    } catch (Exception e) {
      inputError(e, "double");
      return 0.0;
    }
  }

  public static boolean readYesNo(String prompt) {
    System.out.print(prompt);
    System.out.flush();
    return readYesNo();
  }

  public static boolean readYesNo() {
    String ch;
    ch = readString();
    return (ch.toLowerCase().equals("y") || ch.toLowerCase().equals("yes"));
  }

   public static char readChar(String prompt) {
    System.out.print(prompt);
    System.out.flush();
    return readChar();
  }

 public static char readChar() {
    String ch;
    ch = readString();
    return ch.charAt(0);
  }

  private static void inputError(Exception e, String message) {
    System.out.println("Error while reading. Requested input: " + message);
//    System.out.println(e);
//    e.printStackTrace();
//    System.exit(1);
  }
}
