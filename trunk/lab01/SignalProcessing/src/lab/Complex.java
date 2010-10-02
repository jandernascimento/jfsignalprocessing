/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lab;

/**
 *
 * @author cfouard
 */
public class Complex implements java.lang.Comparable{

  private double re;
  private double im;

  public Complex() {
    this.re = 0.0;
    this.im = 0.0;
  }

  public Complex(double re, double im) {
    this.re = re;
    this.im = im;
  }

  public Complex(Complex input) {
    this.re = input.getRe();
    this.im = input.getIm();
  }

  public double getIm() {
    return im;
  }

  public double getRe() {
    return re;
  }

  public void setIm(double im) {
    this.im = im;
  }

  public void setRe(double re) {
    this.re = re;
  }

  public Complex getConjugate() {
    return new Complex(this.re, this.im * (-1));
  }

  public void add(Complex op) {
    this.setRe(this.re + op.getRe());
    this.setIm(this.im + op.getIm());
  }

  public void sub(Complex op) {
    double newRe = this.re - op.getRe();
    double newIm = this.im - op.getIm();
    this.re = newRe;
    this.im = newIm;
  }

  public void mul(Complex op) {
    double newRe = this.re * op.getRe() - this.im * op.getIm();
    double newIm = this.re * op.getIm() + this.im * op.getRe();
    this.re = newRe;
    this.im = newIm;
  }

  public double getNorm() {
    return Math.sqrt(this.re * this.re + this.im * this.im);
  }

  public double getAngle() {
    return Math.atan2(this.im, this.re);
  }

  public void multiplyByReal(double real) {
    this.re *= real;
    this.im *= real;
  }

  public void setRealImaginary(double real, double imaginary) {
    this.re = real;
    this.im = imaginary;
  }

  public void setMagnitudeAngle(double magnitude, double angle) {
    this.re = magnitude * Math.cos(angle);
    this.im = magnitude * Math.sin(angle);
  }

  @Override
  public String toString() {
    if (this.re == 0) {
      if (this.im == 0) {
        return "0";
      } else {
        return (this.im + "i");
      }
    } else {
      if (this.im == 0) {
        return String.valueOf(this.re);
      } else if (this.im < 0) {
        return (this.re + " " + this.im + "i");
      } else {
        return (this.re + " +" + this.im + "i");
      }
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Complex other = (Complex) obj;
    if (this.re != other.re) {
      return false;
    }
    if (this.im != other.im) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 59 * hash + (int) (Double.doubleToLongBits(this.re) ^ (Double.doubleToLongBits(this.re) >>> 32));
    hash = 59 * hash + (int) (Double.doubleToLongBits(this.im) ^ (Double.doubleToLongBits(this.im) >>> 32));
    return hash;
  }

  public static Complex createFromCarthesian(double real, double imaginary) {
    Complex result = new Complex(real, imaginary);
    return result;
  }

  public static Complex createFromPolar(double magnitude, double angle) {
    Complex result = new Complex();
    result.setRe(magnitude * Math.cos(angle));
    result.setIm(magnitude * Math.sin(angle));
    return result;
  }

  public int compareTo(Object obj) {
   if ((obj == null) || (getClass() != obj.getClass()))
     throw new ClassCastException("A Complex object is expected.");

    final Complex other = (Complex) obj;
    Double thisNorm = this.getNorm();
    Double objNorm  = other.getNorm();

    return thisNorm.compareTo(objNorm);
  }

  
}
