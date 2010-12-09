/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lab;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * This class handles images input/output and helps converting an image to a 1D signal
 * to process it with standard signal processing tools.
 *
 * In our case, we considere images as 2D signals containing grey levels (we will
 * not considere colored images). Grey levels are stored on one byte (of type char)
 * and their values may go from 0 to 255.
 *
 *
 * Example of very simple 2D grey level image:
 *
 *                                      width = number of columns
 *           -------------- i ------------------->  width = 7
 *           0     1     2     3     4     5     6
 *        |-----------------------------------------|
 *        |     |     |     |     |     |     |     |
 *   | 0  |  0  |  0  |  0  | 15  |  0  | 0   |  0  |
 *   |    |     |     |     |     |     |     |     |
 *   |    |-----------------------------------------|
 *   |    |     |     |     |     |     |     |     |
 *   | 1  |  0  | 12  |  16 | 155 |238  | 76  |  0  |
 *   |    |     |     |     |     |     |     |     |
 *   j    |-----------------------------------------|
 *   |    |     |     |     |     |     |     |     |
 *   | 2  |  0  |  38 |  45 | 176 | 255 | 255 |  0  |
 *   |    |     |     |     |     |     |     |     |
 *   |    |-----------------------------------------|
 *        |     |     |     |     |     |     |     |
 *   | 3  |  0  |  0  |  0  |  0  |  0  |  0  |  0  |
 *  \|/   |     |     |     |     |     |     |     |
 *        |-----------------------------------------|
 * height = number of lines
 * height = 4
 *
 *
 * To go further and learn more on images and image processing, choose the
 * Image/Vision options at the second semester.
 *
 * @author Celine Fouard
 * 
 *  
 **/
public class Image {

  private int width;
  private int height;
  private char[][] image;

  public Image(int width, int height) {
    this.width = width;
    this.height = height;
    this.image = new char[height][width]; 
  }

  public void fill(char val) {
    for (int j = 0; j < this.height; j++) {
      for (int i = 0; i < this.width; i++) {
        this.image[j][i] = val;
      }
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public void save(String filename) {
    File f;

    f = new File(filename);
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
    // Copy the image in the buffer
    DataBuffer buff = (img.getRaster()).getDataBuffer();
    for (int j = 0; j < height; j++) {
      for (int i = 0; i < width; i++) {
        int k = i + width * j;
        buff.setElem(k, image[j][i]);
      }
    }
    try {
      String ext = filename.substring(filename.lastIndexOf("."), filename.length()) + 1;
//      ImageIO.write(img, ext, f);
      ImageIO.write(img, "png", f);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void display() {
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
    // Copy the image in the buffer
    DataBuffer buff = (img.getRaster()).getDataBuffer();
    for (int j = 0; j < height; j++) {
      for (int i = 0; i < width; i++) {
        int k = i + width * j;
        buff.setElem(k, image[j][i]);
      }
    }
    JFrame f = new JFrame("ImagePanel");

    Container cp = f.getContentPane();
    cp.add(new utils.ImagePanel(img), BorderLayout.CENTER);
    f.pack();
    f.setVisible(true);
  }

  public static Image open(String filename) {
    Image result;
    File f;
    int width, height;

    // Initialisations
    result = null;
    f = new File(filename);
    // La méthode read de la classe ImageIO peut renvoyer une exception
    try {
      BufferedImage img = ImageIO.read(f);
      width = img.getWidth();
      height = img.getHeight();
      result = new Image(width, height);
      DataBuffer buff = img.getRaster().getDataBuffer();
      for (int j = 0; j < height; j++) {
        for (int i = 0; i < width; i++) {
          result.image[j][i] = (char) buff.getElem(i + width * j);
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return result;
  }

  /* ************************************************************************* */
  /*                                                                           */
  /*         Computer Exercise number 04                                       */
  /*                                                                           */
  /* ************************************************************************* */
  /**
   * Sub-sample the current image:
   * returns an image of size this.width / factor, this.height / factor.
   * This new image is filled with one out of factor pixels of the current
   * image in each direction. For example, if factor is equal to 2, the returned
   * image will take one pixel out of 2 in x and one pixel out of 2 in y
   * directions of the current image.
   * Its size will be 4 times smaller than the current one.
   *
   * If the original width or height is not a multiple of factor, then the
   * subsampled image is copleted with zeros.
   * @param factor factor of sampling (it is an isotropic sampling: the factor
   * is the same in the X and Y directions.
   * @return the sub-sampled image
   */
  public Image subSamples(int factor) {
        int newWidth = this.width / factor;
        int newHeight = this.height / factor;
        Image newImg = new Image(newWidth, newHeight);

        // Please write your code here...

        for (int j = 0; j < newHeight; j++) 
            for (int i = 0; i < newWidth; i++) {
                if ((j * factor < this.height) && (i * factor < this.width)) 
                    newImg.image[j][i] = this.image[j * factor][i * factor];
                else 
                    newImg.image[j][i] = (char) 0;                
            }
        return newImg;
    }

  /**
   *
   * Transform the 2D image into a 1D signal (spread out columns on one line).
   * For instance, the example above should return the following 1D signal:
   * index    0   1   2   3   4   5   6    7   8   9   10  11 12  13   14  15  16  17  18  19  20   21  22  23  24  25  26  27
   * value  | 0 | 0 | 0 |15 | 0 | 0 | 0 || 0 |12 |16 |155|238|76 | 0 || 0 |38 |45 |176|255|255| 0 || 0 | 0 | 0 | 0 | 0 | 0 | 0 ||
   *
   * @return a 1D signal with double values corresponding to grey levels.
   */
   public Signal getAsSignal() {
        Signal signal = new Signal();
        signal.settName("Image");

        // Please write your code here
        int k = 0;
        for(int j=0; j<this.height;j++)
            for(int i=0;i<this.width;i++)
            {
                    signal.addElement(k,this.image[j][i]);
                    k++;
            }
        return signal;
    }

  /**
   * Creates an image from a 1D signal
   * @param signal
   * @param width
   * @param height
   */
  public Image(Signal signal, int width, int height) {
    this.width = width;
    this.height = height;
    this.image = new char[height][width];

    // Please writeyour code here
    if(signal.getNbSamples() != width*height)
        System.out.println("The number of samples("+signal.getNbSamples()+") is different of [width("+ width+")*height("+height+")] of the image.");
    
    int h=0,w=0;
    char ch_var;
    for(int i=0;i<signal.getNbSamples();i++){
        if (signal.getValueOfIndex(i)<0)
            ch_var=(char) 0;
        else if(signal.getValueOfIndex(i)>255)
            ch_var=(char) 255;
        else
            ch_var=(char) Math.round(signal.getValueOfIndex(i));
            
        this.image[h][w]=ch_var;

        w++;
        if (w==width){
            w=0;
            h++;
        }
      }
  }

  /*
   *
   *                                              columns numbers
   *           -------------- i ------------------->  cols = 7
   *           0     1     2     3     4     5     6                           kcols: 3
   *        -------------------------------------------        ------ik-------->
   *        |     |     |     |     |     |     |     |          0     1     2
   *   | 0  |     |     |     |     |     |     |     |        #####|#####|#####
   *   |    |     |     |     |-------ii------->|     |     |  #    |     |    #
   *   |    -------------------------------------------     |0 #    |     |    #
   *   |    |     |     |    ||#####|#####|#####|     |     |  #    |     |    #
   *   | 1  |     |     |    ||# ii |...  |... #|     |     |  -----------------
   *        |     |     |    ||# jj |...  |... #|     |    jk  #    |     |    #
   *   j    -----------------|-------------------------     |1 #    |     |    #
   *        |     |     |   jj|# ...|.... |... #|     |     |  #    |     |    #
   *   | 2  |     |     |    ||# ...|(i,j)|... #|     |     |  -----------------
   *   |    |     |     |    ||#    |     |    #|     |     |  #    |     |    #
   *   |    -----------------|-------------------------     |2 #    |     |    #
   *   |    |     |     |    ||#... | ... | ii #|     |     |  #    |     |    #
   *   | 3  |     |     |    ||#... | ... | jj #|     |    \ / #####|#####|#####
   *  \|/   |     |     |   \ /#####|#####|#####|     |
   *        -------------------------------------------     krows
   * rows numbers                                             3
   *  rows = 4
   *
   *  As kernel is stored as an image with char, the final result is devided by 
   *   the summ of all the values of the kernel.
   */
  public Image convolve(Image kernel) {
    Image result = new Image(this.width, this.height);
    int j;  // row    index of the current image
    int i;  // column index of the current image
    int jk; // row    index of the kernel;
    int ik; // column index of the kernel;
    double newval; // temporary variable to store the voxel value computation
    // We suppose kernel.height and kernel.width to be odd
    int kernelCenteri; // index of the central column of the kernel
    int kernelCenterj; // index of the central row of the kernel
    double kernelTotalValue;

    kernelCenteri = kernel.width / 2;
    kernelCenterj = kernel.height / 2;

    result.fill((char) 0);

    // Compute the sum of all the values of the kernel.
    kernelTotalValue = 0.0;
    for (j = 0; j < kernel.height; j++)
      for(i = 0; i < kernel.width; i++)
        kernelTotalValue += kernel.image[j][i];

    // Actual convolution computation
    for (j = 0; j < height; j++) {
      for (i = 0; i < width; i++) {

        newval = 0.0;

        for (jk = 0; jk < kernel.height; jk++) {
          for (ik = 0; ik < kernel.width; ik++) {
            // (ii, jj):  index of the image where
            // the (ik, jk) sample of the kernel is
            int ii = i + ik - kernelCenteri;
            int jj = j + jk - kernelCenterj;

            // Check the points are inside image (to avoid memory problems)
            if ((jj > 0) && (jj < this.height) &&
                    (ii > 0) && (ii < this.width)) {
              newval += this.image[jj][ii] * kernel.image[jk][ik];
            }
          }
        }
        // Normalization of the value for the image not to be kernelTotalValue times
        // its original values.
        newval = newval / kernelTotalValue;
        result.image[j][i] = (char) newval;
      }
    }
    return result;
  }
}
