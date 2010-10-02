package utils;

import java.awt.*;
import javax.swing.*;

//for simplicity, I'm assuming the image data
//is loaded in memory (so getWidth() != -1)
public class ImagePanel extends JPanel {

    public ImagePanel() {
    }

    public ImagePanel(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
        revalidate();
    }

  @Override
    public Dimension getPreferredSize() {
        getInsets(insets);
        int w = insets.left + insets.right;
        int h = insets.top + insets.bottom;
        if (image != null) {
            w += image.getWidth(null);
            h += image.getHeight(null);
        }
        Dimension sz = new Dimension(w, h);
        System.out.println(sz);
        return sz;
    }

  @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

  @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) {
            return;
        }
        getInsets(insets);
        int w = this.getWidth() - insets.left - insets.right;
        int h = this.getHeight() - insets.top - insets.bottom;
        //clip in case image exceeds wxh
        Graphics g2 = (Graphics) g.create(insets.left, insets.top, w, h);
        int x = (w - image.getWidth(null)) / 2;
        int y = (h - image.getHeight(null)) / 2;
        g2.drawImage(image, x, y, this);	//this for ani gif
        g2.dispose();
    }
    private Image image;
    private Insets insets = new Insets(0, 0, 0, 0);

//    //sample main
//    public static void main(String[] argv) {
//        JFrame f = new JFrame("ImagePanel");
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
///*        URL url = ImagePanel.class.getResource("sampleImage.jpg");
//        Image image = new ImageIcon(url).getImage();
//*/
//
//        BufferedImage image = new BufferedImage(255, 255, BufferedImage.TYPE_BYTE_GRAY);
//        DataBuffer buff = (image.getRaster()).getDataBuffer();
//
//        for(int j = 100; j < 200; j++) {
//            for (int i = 50; i < 150; i++) {
//                int k = i + 255*j;
//                buff.setElem(k, k-50);
//            }
//        }
//
//        Container cp = f.getContentPane();
//        cp.add(new ImagePanel(image), BorderLayout.CENTER);
//        f.pack();
//        f.setVisible(true);
//    }
}
