import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;

public class CardUtil {

	public static File dir = new File("C:\\Program Files\\SpectromancerGathering\\Images\\Cards");
	public static File dir2 = new File("C:\\Program Files\\SpectromancerGathering\\Images\\Faces");


	public static void main(String[] argv) throws Exception {

		File[] bigcards = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith("big.jpg");
			}
		});

		File[] smallcards = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return !name.toLowerCase().endsWith("big.jpg") && !name.toLowerCase().endsWith("tga");
			}
		});
		
		File[] tgabigcards = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith("big.tga");
			}
		});

		File[] tgasmallcards = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith("tga") && !name.toLowerCase().endsWith("big.tga");
			}
		});

		File[] faces = dir2.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith("jpg");
			}
		});
	

		StringBuffer smallSB = new StringBuffer();
		StringBuffer largeSB = new StringBuffer();
		StringBuffer largeTGASB = new StringBuffer();
		StringBuffer smallTGASB = new StringBuffer();
		StringBuffer facesSB = new StringBuffer();

		int rows = 20;
		int cols = smallcards.length / 20 + 1;
		int largeSize = 150;
		int tgaLargeSize = 165;
		int tgaSmallSize = 84;
		int smallSize = 80;
		int faceSize = 120;

		BufferedImage smallcanvas = new BufferedImage(rows * smallSize, cols * smallSize, BufferedImage.TYPE_INT_ARGB);
		BufferedImage largecanvas = new BufferedImage(rows * largeSize, cols * largeSize, BufferedImage.TYPE_INT_ARGB);
		BufferedImage largeTGAcanvas = new BufferedImage(rows * tgaLargeSize, cols * tgaLargeSize, BufferedImage.TYPE_INT_ARGB);
		BufferedImage smallTGAcanvas = new BufferedImage(rows * tgaSmallSize, cols * tgaSmallSize, BufferedImage.TYPE_INT_ARGB);
		BufferedImage facecanvas = new BufferedImage(rows * faceSize, cols * faceSize, BufferedImage.TYPE_INT_ARGB);

		
		
		int index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (index > bigcards.length - 1)
					break;
				BufferedImage input = getImage(bigcards[index]);
				largecanvas.getGraphics().drawImage(input, i * largeSize, j * largeSize, null);
				packImage(largeSB, FilenameUtils.getBaseName(bigcards[index].getName().toLowerCase()), i * largeSize, j * largeSize, largeSize, largeSize, index);
				index++;
			}
		}

		ImageIO.write(largecanvas, "PNG", new File("largeTiles.png"));
		writePackFile("largeCardsPack.txt", largeSB, "largeTiles.png");

		index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (index > smallcards.length - 1)
					break;
				BufferedImage input = getImage(smallcards[index]);
				smallcanvas.getGraphics().drawImage(input, i * smallSize, j * smallSize, null);
				packImage(smallSB, FilenameUtils.getBaseName(smallcards[index].getName().toLowerCase()), i * smallSize, j * smallSize, smallSize, smallSize, index);
				index++;
			}
		}

		ImageIO.write(smallcanvas, "PNG", new File("smallTiles.png"));
		writePackFile("smallCardsPack.txt", smallSB, "smallTiles.png");
		
		index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (index > tgabigcards.length - 1)
					break;
				BufferedImage input = getImage(tgabigcards[index]);
				largeTGAcanvas.getGraphics().drawImage(input, i * tgaLargeSize, j * tgaLargeSize, null);
				packImage(largeTGASB, FilenameUtils.getBaseName(tgabigcards[index].getName().toLowerCase()), i * tgaLargeSize, j * tgaLargeSize, tgaLargeSize, tgaLargeSize, index);
				index++;
			}
		}

		ImageIO.write(largeTGAcanvas, "PNG", new File("largeTGATiles.png"));
		writePackFile("largeTGACardsPack.txt", largeTGASB, "largeTGATiles.png");
		
		
		index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (index > tgasmallcards.length - 1)
					break;
				BufferedImage input = getImage(tgasmallcards[index]);
				smallTGAcanvas.getGraphics().drawImage(input, i * tgaSmallSize, j * tgaSmallSize, null);
				packImage(smallTGASB, FilenameUtils.getBaseName(tgasmallcards[index].getName().toLowerCase()), i * tgaSmallSize, j * tgaSmallSize, tgaSmallSize, tgaSmallSize, index);
				index++;
			}
		}

		ImageIO.write(smallTGAcanvas, "PNG", new File("smallTGATiles.png"));
		writePackFile("smallTGACardsPack.txt", smallTGASB, "smallTGATiles.png");
		
		
		
		

		index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (index > faces.length - 1)
					break;
				BufferedImage input = ImageIO.read(faces[index]);
				facecanvas.getGraphics().drawImage(input, i * faceSize, j * faceSize, null);
				packImage(facesSB, FilenameUtils.getBaseName(faces[index].getName().toLowerCase()), i * faceSize, j * faceSize, faceSize, faceSize, index);
				index++;
			}
		}

		ImageIO.write(facecanvas, "PNG", new File("faceTiles.png"));
		writePackFile("faceCardsPack.txt", facesSB, "faceTiles.png");

	}
	
	public static BufferedImage getImage(File file) throws IOException {
		BufferedImage input = null;
		if (file.getName().toLowerCase().endsWith("jpg")) {
			input = ImageIO.read(file);
		} else {
			TargaReader tr = new TargaReader();
			input = tr.getImage(file);

		    BufferedImage dest = getCroppedImage(input, 0);
			input = dest;
		}
		return input;
	}
	
	private static BufferedImage getCroppedImage(BufferedImage source, double tolerance) {
		// Get our top-left pixel color as our "baseline" for cropping
		int baseColor = source.getRGB(0, 0);

		int width = source.getWidth();
		int height = source.getHeight();

		int topY = Integer.MAX_VALUE, topX = Integer.MAX_VALUE;
		int bottomY = -1, bottomX = -1;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (colorWithinTolerance(baseColor, source.getRGB(x, y), tolerance)) {
					if (x < topX)
						topX = x;
					if (y < topY)
						topY = y;
					if (x > bottomX)
						bottomX = x;
					if (y > bottomY)
						bottomY = y;
				}
			}
		}

		BufferedImage destination = new BufferedImage((bottomX - topX + 1), (bottomY - topY + 1), BufferedImage.TYPE_INT_ARGB);

		destination.getGraphics().drawImage(source, 0, 0, destination.getWidth(), destination.getHeight(), topX, topY, bottomX, bottomY, null);

		return destination;
	}

	private static boolean colorWithinTolerance(int a, int b, double tolerance) {
		int aAlpha = (int) ((a & 0xFF000000) >>> 24); // Alpha level
		int aRed = (int) ((a & 0x00FF0000) >>> 16); // Red level
		int aGreen = (int) ((a & 0x0000FF00) >>> 8); // Green level
		int aBlue = (int) (a & 0x000000FF); // Blue level

		int bAlpha = (int) ((b & 0xFF000000) >>> 24); // Alpha level
		int bRed = (int) ((b & 0x00FF0000) >>> 16); // Red level
		int bGreen = (int) ((b & 0x0000FF00) >>> 8); // Green level
		int bBlue = (int) (b & 0x000000FF); // Blue level

		double distance = Math.sqrt((aAlpha - bAlpha) * (aAlpha - bAlpha) + (aRed - bRed) * (aRed - bRed) + (aGreen - bGreen) * (aGreen - bGreen) + (aBlue - bBlue) * (aBlue - bBlue));

		// 510.0 is the maximum distance between two colors
		// (0,0,0,0 -> 255,255,255,255)
		double percentAway = distance / 510.0d;

		return (percentAway > tolerance);
	}

	public static void packImage(StringBuffer buffer, String name, int x, int y, int width, int height, int index) {
		if (name.endsWith("big")) name = name.substring(0,name.length()-3);
		buffer.append(name + "\n");
		buffer.append("  rotate: false\n");
		buffer.append("  xy: " + x + ", " + y + "\n");
		buffer.append("  size: " + width + ", " + height + "\n");
		buffer.append("  orig: " + width + ", " + height + "\n");
		buffer.append("  offset: 0, 0\n");
		buffer.append("  index: " + index + "\n");
	}

	public static void writePackFile(String packFileName, StringBuffer buffer, String imageName) throws IOException {
		File packFile = new File(packFileName);
		if (packFile.exists())
			packFile.delete();

		FileWriter writer = new FileWriter(packFile, true);

		writer.write("\n" + imageName + "\n");
		writer.write("format: RGBA8888\n");
		writer.write("filter: Linear,Linear\n");
		writer.write("repeat: none\n");

		writer.write(buffer.toString());

		writer.close();
	}
	
	static class TargaReader {
		public BufferedImage getImage(File f) throws IOException {
			byte[] buf = new byte[(int) f.length()];
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
			bis.read(buf);
			bis.close();
			return decode(buf);
		}

		private int offset;

		private int btoi(byte b) {
			int a = b;
			return (a < 0 ? 256 + a : a);
		}

		private int read(byte[] buf) {
			return btoi(buf[offset++]);
		}

		public BufferedImage decode(byte[] buf) throws IOException {
			offset = 0;

			// Reading header bytes
			// buf[2]=image type code 0x02=uncompressed BGR or BGRA
			// buf[12]+[13]=width
			// buf[14]+[15]=height
			// buf[16]=image pixel size 0x20=32bit, 0x18=24bit
			// buf{17]=Image Descriptor Byte=0x28 (00101000)=32bit/origin
			// upperleft/non-interleaved
			for (int i = 0; i < 12; i++)
				read(buf);
			int width = read(buf) + (read(buf) << 8); // 00,04=1024
			int height = read(buf) + (read(buf) << 8); // 40,02=576
			read(buf);
			read(buf);

			int n = width * height;
			int[] pixels = new int[n];
			int idx = 0;

			if (buf[2] == 0x02 && buf[16] == 0x20) { // uncompressed BGRA
				while (n > 0) {
					int b = read(buf);
					int g = read(buf);
					int r = read(buf);
					int a = read(buf);
					int v = (a << 24) | (r << 16) | (g << 8) | b;
					pixels[idx++] = v;
					n -= 1;
				}
			} else if (buf[2] == 0x02 && buf[16] == 0x18) { // uncompressed BGR
				while (n > 0) {
					int b = read(buf);
					int g = read(buf);
					int r = read(buf);
					int a = 255; // opaque pixel
					int v = (a << 24) | (r << 16) | (g << 8) | b;
					pixels[idx++] = v;
					n -= 1;
				}
			} else {
				// RLE compressed
				while (n > 0) {
					int nb = read(buf); // num of pixels
					if ((nb & 0x80) == 0) { // 0x80=dec 128, bits 10000000
						for (int i = 0; i <= nb; i++) {
							int b = read(buf);
							int g = read(buf);
							int r = read(buf);
							pixels[idx++] = 0xff000000 | (r << 16) | (g << 8) | b;
						}
					} else {
						nb &= 0x7f;
						int b = read(buf);
						int g = read(buf);
						int r = read(buf);
						int v = 0xff000000 | (r << 16) | (g << 8) | b;
						for (int i = 0; i <= nb; i++)
							pixels[idx++] = v;
					}
					n -= nb + 1;
				}
			}

			BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			bimg.setRGB(0, 0, width, height, pixels, 0, width);
			return bimg;
		}
	}
		
	
}
