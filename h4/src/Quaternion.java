import java.util.*;
//https://home.apache.org/~luc/commons-math-3.6-RC2-site/jacoco/org.apache.commons.math3.complex/Quaternion.java.html
//https://android.googlesource.com/platform/external/jmonkeyengine/+/59b2e6871c65f58fdad78cd7229c292f6a177578/engine/src/core/com/jme3/math/Quaternion.java
//https://eater.net/quaternions/video/intro
/** Quaternions. Basic operations. */
public class Quaternion {

   // TODO!!! Your fields here!
   private static final double THRESHOLD = 0.000001;
   private final double a;
   private final double b;
   private final double c;
   private final double d;

   /** Constructor from four double values.
    * @param a real part
    * @param b imaginary part i
    * @param c imaginary part j
    * @param d imaginary part k
    */
   public Quaternion (double a, double b, double c, double d) {
      // TODO!!! Your constructor here!
         this.a = a;
         this.b = b;
         this.c = c;
         this.d = d;
   }

   /** Real part of the quaternion.
    * @return real part
    */
   public double getRpart() {
      //return 0.; // TODO!!!
      return a;
   }

   /** Imaginary part i of the quaternion. 
    * @return imaginary part i
    */
   public double getIpart() {
      //return 0.; // TODO!!!
      return b;
   }

   /** Imaginary part j of the quaternion. 
    * @return imaginary part j
    */
   public double getJpart() {
      //return 0.; // TODO!!!
      return c;
   }

   /** Imaginary part k of the quaternion. 
    * @return imaginary part k
    */
   public double getKpart() {
      //return 0.; // TODO!!!
      return d;
   }

   /** Conversion of the quaternion to the string.
    * @return a string form of this quaternion: 
    * "a+bi+cj+dk"
    * (without any brackets)
    */
   @Override
   public String toString() {
      //return ""; // TODO!!!
      StringBuilder sb = new StringBuilder();
      sb.append(a);
      if (b >= 0) {
         sb.append("+");
      }
      sb.append(b).append("i");
      if (c >= 0) {
         sb.append("+");
      }
      sb.append(c).append("j");
      if (d >= 0) {
         sb.append("+");
      }
      sb.append(d).append("k");
      return sb.toString();
   }

   /** Conversion from the string to the quaternion. 
    * Reverse to <code>toString</code> method.
    * @throws IllegalArgumentException if string s does not represent 
    *     a quaternion (defined by the <code>toString</code> method)
    * @param s string of form produced by the <code>toString</code> method
    * @return a quaternion represented by string s
    */
   public static Quaternion valueOf (String s) {
      //return null; // TODO!!!
      try {
         int start = 0;
         int end = s.indexOf('+', 1);
         double a = Double.parseDouble(s.substring(start, end));
         start = end;
         end = s.indexOf('i', start + 1);
         double b = Double.parseDouble(s.substring(start + 1, end));
         start = end;
         end = s.indexOf('j', start + 1);
         double c = Double.parseDouble(s.substring(start + 1, end));
         start = end;
         end = s.indexOf('k', start + 1);
         double d = Double.parseDouble(s.substring(start + 1, end));
         return new Quaternion(a, b, c, d);
      } catch (Exception e) {
         throw new IllegalArgumentException("Invalid quaternion string: " + s);
      }
   }

   /** Clone of the quaternion.
    * @return independent clone of <code>this</code>
    */
   @Override
   public Object clone() throws CloneNotSupportedException {
      //return null; // TODO!!!
      return new Quaternion(a, b, c, d);
   }

   /** Test whether the quaternion is zero. 
    * @return true, if the real part and all the imaginary parts are (close to) zero
    */
   public boolean isZero() {
      //return false; // TODO!!!
      return Math.abs(a) < THRESHOLD && Math.abs(b) < THRESHOLD && Math.abs(c) < THRESHOLD && Math.abs(d) < THRESHOLD;
      //return a == 0 && b == 0 && c == 0 && d == 0;

   }

   /** Conjugate of the quaternion. Expressed by the formula 
    *     conjugate(a+bi+cj+dk) = a-bi-cj-dk
    * @return conjugate of <code>this</code>
    */
   public Quaternion conjugate() {
      //return null; // TODO!!!
      return new Quaternion(a, -b, -c, -d);
   }

   /** Opposite of the quaternion. Expressed by the formula 
    *    opposite(a+bi+cj+dk) = -a-bi-cj-dk
    * @return quaternion <code>-this</code>
    */
   public Quaternion opposite() {
      //return null; // TODO!!!
      return new Quaternion(-a, -b, -c, -d);
   }

   /** Sum of quaternions. Expressed by the formula 
    *    (a1+b1i+c1j+d1k) + (a2+b2i+c2j+d2k) = (a1+a2) + (b1+b2)i + (c1+c2)j + (d1+d2)k
    * @param q addend
    * @return quaternion <code>this+q</code>
    */
   public Quaternion plus (Quaternion q) {
      //return null; // TODO!!!
      return new Quaternion(a + q.a, b + q.b, c + q.c, d + q.d);
   }

   /** Product of quaternions. Expressed by the formula
    *  (a1+b1i+c1j+d1k) * (a2+b2i+c2j+d2k) = (a1a2-b1b2-c1c2-d1d2) + (a1b2+b1a2+c1d2-d1c2)i +
    *  (a1c2-b1d2+c1a2+d1b2)j + (a1d2+b1c2-c1b2+d1a2)k
    * @param q factor
    * @return quaternion <code>this*q</code>
    */
   public Quaternion times (Quaternion q) {
      //return null; // TODO!!!
      double aa = a * q.a - b * q.b - c * q.c - d * q.d;
      double bb = a * q.b + b * q.a + c * q.d - d * q.c;
      double cc = a * q.c - b * q.d + c * q.a + d * q.b;
      double dd = a * q.d + b * q.c - c * q.b + d * q.a;
      return new Quaternion(aa, bb, cc, dd);
   }

   /** Multiplication by a coefficient.
    * @param r coefficient
    * @return quaternion <code>this*r</code>
    */
   public Quaternion times (double r) {
      //return null; // TODO!!!
      return new Quaternion(a * r, b * r, c * r, d * r);
   }

   /** Inverse of the quaternion. Expressed by the formula
    *     1/(a+bi+cj+dk) = a/(a*a+b*b+c*c+d*d) + 
    *     ((-b)/(a*a+b*b+c*c+d*d))i + ((-c)/(a*a+b*b+c*c+d*d))j + ((-d)/(a*a+b*b+c*c+d*d))k
    * @return quaternion <code>1/this</code>
    */
   public Quaternion inverse() {
      //return null; // TODO!!!
      //double normSquared = a * a + b * b + c * c;
      double normSquared = a * a + b * b + c * c + d * d;
      if (isZero() || normSquared == 0) {
         throw new ArithmeticException("Quaternion is zero or has zero norm");
      }
      double inverseNormSquared = 1.0 / normSquared;
      return new Quaternion(a * inverseNormSquared, -b * inverseNormSquared, -c * inverseNormSquared, -d * inverseNormSquared);

   }

   /** Difference of quaternions. Expressed as addition to the opposite.
    * @param q subtrahend
    * @return quaternion <code>this-q</code>
    */
   public Quaternion minus (Quaternion q) {
      //return null; // TODO!!!
      return new Quaternion(a - q.a, b - q.b, c - q.c, d - q.d);
   }

   /** Right quotient of quaternions. Expressed as multiplication to the inverse.
    * @param q (right) divisor
    * @return quaternion <code>this*inverse(q)</code>
    */
   public Quaternion divideByRight (Quaternion q) {
      //return null; // TODO!!!
      if (q.isZero()) {
         throw new ArithmeticException("Division by zero");
      }
      return this.times(q.conjugate()).times(q.inverse());
   }

   /** Left quotient of quaternions.
    * @param q (left) divisor
    * @return quaternion <code>inverse(q)*this</code>
    */
   public Quaternion divideByLeft (Quaternion q) {
      //return null; // TODO!!!
      if (q.isZero()) {
         throw new ArithmeticException("Division by zero");
      }
      return q.conjugate().times(this).times(q.inverse());
   }
   
   /** Equality test of quaternions. Difference of equal numbers
    *     is (close to) zero.
    * @param qo second quaternion
    * @return logical value of the expression <code>this.equals(qo)</code>
    */
   @Override
   public boolean equals (Object qo) {
      //return false; // TODO!!!
      if (this == qo) return true;
      if (!(qo instanceof Quaternion)) return false;
      Quaternion that = (Quaternion) qo;
      return Math.abs(a - that.a) < THRESHOLD && Math.abs(b - that.b) < THRESHOLD && Math.abs(c - that.c) < THRESHOLD && Math.abs(d - that.d) < THRESHOLD;
   }

   /** Dot product of quaternions. (p*conjugate(q) + q*conjugate(p))/2
    * @param q factor
    * @return dot product of this and q
    */
   public Quaternion dotMult (Quaternion q) {
      //return null; // TODO!!!
      double real = a * q.a + b * q.b + c * q.c + d * q.d;
      double bi = b * q.a + a * q.b + d * q.c - c * q.d;
      double cj = c * q.a - d * q.b + a * q.c + b * q.d;
      double dk = d * q.a + c * q.b - b * q.c + a * q.d;
      return new Quaternion(real, bi, cj, dk);
      //return a * q.a + b * q.b + c * q.c + d * q.d;
   }

   /** Integer hashCode has to be the same for equal objects.
    * @return hashcode
    */
   @Override
   public int hashCode() {
      //return 0; // TODO!!!
      return Objects.hash(a, b, c, d);
   }

   /** Norm of the quaternion. Expressed by the formula 
    *     norm(a+bi+cj+dk) = Math.sqrt(a*a+b*b+c*c+d*d)
    * @return norm of <code>this</code> (norm is a real number)
    */
   public double norm() {
      //return 0.; // TODO!!!
      return Math.sqrt(a * a + b * b + c * c + d * d);
   }

   /** Main method for testing purposes. 
    * @param //arg command line parameters
    */
   public static void main (String[] args) {
      // TODO!!! Your example runs here
   }

}

