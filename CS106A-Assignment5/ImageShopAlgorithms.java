/* 
 * This file contains image processing algorithms applied in ImageShop.
 * rotateLeft() can rotate a image in left direction;
 * rotateRight() can rotate a image in right direction;
 * flipHorizontal() can flip a image horizontally;
 * negative() can change a image to its inverse color;
 * greenScreen() can remove part of a image from the green background;
 * blur() can make a image blur at any level;
 * crop() can crop a image based on the part user selects with mouse;
 * equalize() can increase the contrast (enhance) a image
 * 
 * Student: Anyu Zhu
 * Section Leader: Peter Hansel
 */

import acm.graphics.*;

public class ImageShopAlgorithms implements ImageShopAlgorithmsInterface {

	/*
	 * This method rotate a image to left direction.
	 * The dimension of new image should be the inverse of that of original version:
	 * nCols * nRows
	 * pixel row index of new image should be (number of columns - column index - 1);
	 * pixel column index of new image should be the original row index.
	 */
	
	public GImage rotateLeft(GImage source) {
		int[][] pixels = source.getPixelArray();
		int nRows = numRows(pixels);
		int nCols = numCols(pixels);
		int[][] newLeftPixel = new int[nCols][nRows];
		for (int row = 0; row < nRows; row++) {
			for (int col = 0; col < nCols; col++) {
				int r = nCols - 1 - col;
				int c = row;
				newLeftPixel[r][c] = pixels[row][col];
			}
		}
		GImage leftVersion = new GImage(newLeftPixel);
		return leftVersion;
	}
	
	/*
	 * This method rotate a image to right direction.
	 * The dimension of new image should be the inverse of that of original version.
	 * pixel row index of new image should be the column index of original image;
	 * pixel column index should be (number of rows - row index - 1).
	 */

	public GImage rotateRight(GImage source) {
		int[][] pixels = source.getPixelArray();
		int nRows = numRows(pixels);
		int nCols = numCols(pixels);
		int[][] newRightPixel = new int[nCols][nRows];
		for (int row = 0; row < nRows; row++) {
			for (int col = 0; col < nCols; col++) {
				int c = nRows - 1 - row;
				int r = col;
				newRightPixel[r][c] = pixels[row][col];
			}
		}
		GImage rightVersion = new GImage(newRightPixel);
		return rightVersion;
	}
	
	/*
	 * This method flip a image in horizontal direction.
	 * The dimension of new image remain unchanged.
	 * pixel row index remain unchanged.
	 * pixel column index should be (number of columns - column index - 1).
	 */

	public GImage flipHorizontal(GImage source) {
		int[][] pixels = source.getPixelArray();
		int nRows = numRows(pixels);
		int nCols = numCols(pixels);
		int[][] newFlipHPixel = new int[nRows][nCols];
		for (int row = 0; row < nRows; row++) {
			for (int col = 0; col < nCols; col++) {
				int r = row;
				int c = nCols - 1 - col;
				newFlipHPixel[r][c] = pixels[row][col];
			}
		}
		GImage FlipHorizontal = new GImage(newFlipHPixel);
		return FlipHorizontal;
	}
	
	/*
	 * This method change a image's color to its inverse.
	 * Convert all the values of red, green and blue to their inverse value:
	 * 255 - original value.
	 * Use the inverse value to create new pixels.
	 */

	public GImage negative(GImage source) {
		int[][] pixels = source.getPixelArray();
		int nRows = numRows(pixels);
		int nCols = numCols(pixels);
		int[][] inversePixel = new int[nRows][nCols];
		for (int row = 0; row < nRows; row++) {
			for (int col = 0; col < nCols; col++) {
				int pixel = pixels[row][col];
				int red = GImage.getRed(pixel);
				int green = GImage.getGreen(pixel);
				int blue = GImage.getBlue(pixel);
				
				int newRed = 255 - red;
				int newGreen = 255 - green;
				int newBlue = 255 - blue;
				int newPixel = GImage.createRGBPixel(newRed, newGreen, newBlue);
				inversePixel[row][col] = newPixel;
			}
		}
		GImage inverseColor = new GImage(inversePixel);
		return inverseColor;
	}
	
	/*
	 * This method remove the green background from a image.
	 * Use if statement to replace large green value with 0 alpha value.
	 * create a new image with new RGB value.
	 */

	public GImage greenScreen(GImage source) {
		int[][] pixels = source.getPixelArray();
		int nRows = numRows(pixels);
		int nCols = numCols(pixels);
		int[][] greenScreen = new int[nRows][nCols];
		for (int row = 0; row < nRows; row++) {
			for (int col = 0; col < nCols; col++) {
				int pixel = pixels[row][col];
				int red = GImage.getRed(pixel);
				int green = GImage.getGreen(pixel);
				int blue = GImage.getBlue(pixel);
				int bigger = Math.max(red, blue);
				if(green >= 2 * bigger) {
					greenScreen[row][col] = GImage.createRGBPixel(red, green, blue, 0);
				}else {
					greenScreen[row][col] = GImage.createRGBPixel(red, green, blue);
				}
			}
		}
		GImage greenScreenPic = new GImage(greenScreen);
		return greenScreenPic;
	}

	/*
	 * This method makes a image blur.
	 * use four if statements to deal with the four boundaries of the image,
	 * otherwise, select the surrounding eight pixels and apply the makePixelBlur() method.
	 * use the new pixel value to create a blurred image.
	 */
	
	public GImage blur(GImage source) {
		int[][] pixels = source.getPixelArray();
		int nRows = numRows(pixels);
		int nCols = numCols(pixels);
		int[][] blurPixel = new int[nRows][nCols];
		for (int row = 0; row < nRows; row++) {
			for (int col = 0; col < nCols; col++) {	
				int rMin = row - 1;
				int rMax = row + 1;
				int cMin = col - 1;
				int cMax = col + 1;
				if (row == 0) {
					rMin = row;
				}
				if (col == 0) {
					cMin = col;
				}
				if (col == nCols - 1) {
					cMax = col;
				}
				if (row == nRows - 1) {
					rMax = row;
				}
				blurPixel = makePixelBlur(rMin, rMax, cMin, cMax, pixels, blurPixel, row, col);
			}
		}
		GImage blurImage = new GImage(blurPixel);
		return blurImage;
	}

	/*
	 * This method crops a image based on the users selected area.
	 * The dimension of the new image should be cropWidth * cropHeight.
	 * Since (cropX, cropY) is the base point the user selected,
	 * pixel row index of new image should be that of original one minus cropY,
	 * pixel column index of new image should be that of original one minus cropX.
	 * Use the selected value of pixel to create a new image.
	 */
	
	public GImage crop(GImage source, int cropX, int cropY, int cropWidth, int cropHeight) {
		int[][] pixels = source.getPixelArray();
		int[][] cropPixel = new int[cropHeight][cropWidth];	
		for (int row = cropY; row < cropY + cropHeight; row++) {
			for (int col = cropX; col < cropX + cropWidth; col++) {
				int r = row - cropY;
				int c = col - cropX;
				cropPixel[r][c] = pixels[row][col];	
			}
		}
		GImage cropImage = new GImage(cropPixel);
		return cropImage;
	}
	
	/*
	 * This method increase the contrast level of a grey image.
	 * calculate the the luminosity and cumulative luminosity histogram and store them in arrays.
	 * The numerator of new RGB value is the sum of pixels which have lower luminosity than current pixel
	 * The denominator of new RGB value is the total number of pixels.
	 * Multiply the value by 255, round down, and get the final RGB value.
	 * Use the new RGB value to create a new image.
	 */

	public GImage equalize(GImage source) {
		int[] luminosityArray = computeLuminosityHist(source);
		int[] cumLuminosityArray = computeCumLuminosityHist(luminosityArray);
		
		int[][] pixels = source.getPixelArray();
		int nRows = numRows(pixels);
		int nCols = numCols(pixels);
		int[][] contrastPixel = new int[nRows][nCols];
		for (int row = 0; row < nRows; row++) {
			for (int col = 0; col < nCols; col++) {
				int pixel = pixels[row][col];
				int red = GImage.getRed(pixel);
				int green = GImage.getGreen(pixel);
				int blue = GImage.getBlue(pixel);
				int luminosity = computeLuminosity(red, green, blue);
				
				int numerator = cumLuminosityArray[luminosity];
				int denominator = nRows * nCols;
				int val = Math.round(255 * numerator / denominator);
				contrastPixel[row][col] = GImage.createRGBPixel(val, val, val);
			}
		}	
		GImage equalizeImage = new GImage(contrastPixel);
		return equalizeImage;
	}
	
	/*
	 * calculate the number of rows of a 2D array
	 */
	
	private int numRows(int[][] arr) {
		return arr.length;
	}
	
	/*
	 * calculate the number of columns of a 2D array
	 */
	
	private int numCols(int[][] arr) {
		return arr[0].length;
	}
	
	/**
	 * This method calculate the average RGB value of surrounding eight points and place them in a 2D array
	 * which can be used to create a new blurred image.
	 * @param rMin: the upper row index
	 * @param rMax: the lower row index
	 * @param cMin: the left column index
	 * @param cMax: the right column index
	 * @param pixels: the original pixels value
	 * @param blurPixel: the new blurred pixel value
	 * @param row: the number of rows of all pixels
	 * @param col: the number of columns of all pixels
	 * @return: a 2D array of the average RGB values of surrounding eight points of each pixel.
	 */
	
	private int[][] makePixelBlur(int rMin, int rMax, int cMin, int cMax, int[][] pixels, int[][] blurPixel, int row, int col){
		int neighbours = 0; 
		int red = 0;
		int green = 0;
		int blue = 0; 
		for (int r = rMin; r <= rMax; r++) {
			for (int c = cMin; c <= cMax; c++) {
				int pixel = pixels[r][c];
				red += GImage.getRed(pixel);
				green += GImage.getGreen(pixel);
				blue += GImage.getBlue(pixel);
				neighbours++;
			}
		}
		int averageRed = red/neighbours;
		int averageGreen = green/neighbours;
		int averageBlue = blue/neighbours;
		blurPixel[row][col] = GImage.createRGBPixel(averageRed, averageGreen, averageBlue);
		return blurPixel;
	}
	
	/*
	 * This method calculate the luminosity histogram of the image.
	 * place all the luminosity value in an luminosity array.
	 */
	
	private int[] computeLuminosityHist(GImage source) {
		int[][] pixels = source.getPixelArray();
		int[] luminosityArray = new int[256];
		int nRows = numRows(pixels);
		int nCols = numCols(pixels);
		for (int row = 0; row < nRows; row++) {
			for (int col = 0; col < nCols; col++) {
				int pixel = pixels[row][col];
				int red = GImage.getRed(pixel);
				int green = GImage.getGreen(pixel);
				int blue = GImage.getBlue(pixel);
				int luminosity = computeLuminosity(red, green,blue);
				luminosityArray[luminosity]++;
			}
		}
		return luminosityArray;
	}
	
	/**
	 * This method calculate the cumulative luminosity array. 
	 * the (i + 1) entry of cumLuminosityArray is the sum of ith entry of itself and (i + 1) entry of luminosity array.
	 * @param: luminosityArray: the luminosity array we get from computeLuminosityHist()
	 * @return: the cumulative luminosity array calculated based on the luminosity array.
	 */
	
	private int [] computeCumLuminosityHist(int[] luminosityArray) {
		int[] cumLuminosityArray = new int[256];
		cumLuminosityArray[0] = luminosityArray[0];
		for (int i = 0; i < cumLuminosityArray.length - 1; i++) {
			cumLuminosityArray[i + 1] = luminosityArray[i + 1] + cumLuminosityArray[i];
		}
		return cumLuminosityArray;
	}
}
