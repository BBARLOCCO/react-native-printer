package com.reactlibrary.command.sdk;
 
import android.graphics.Bitmap; 
import android.graphics.Bitmap.Config; 
import android.graphics.Canvas; 
import android.graphics.Color; 
import android.graphics.ColorMatrix; 
import android.graphics.ColorMatrixColorFilter; 
import android.graphics.Matrix; 
import android.graphics.Paint; 
 
import java.io.UnsupportedEncodingException; 
 
class Other { 
    int nStartOrgx = 5; 
    int nBarcodetype = 0x49; 
    int nBarcodeWidth = 4; 
    int nBarcodeHeight = 4; 
    int nBarcodeFontType = 0; 
    int nBarcodeFontPosition = 0x02; 
    String strBarcode = "123456678"; 
    int nOrgx = nStartOrgx * 12;// nStartOrgx 起始缩进的位置-->>以下几个参数请参考array.txt 
    int nType = 65 + nBarcodetype; // nBarcodetype 条码的类型 
    int nWidthX = nBarcodeWidth + 2;// nBarcodeWidth 条码宽度 
    int nHeight = (nBarcodeHeight + 1) * 24; // nBarcodeHeight 条码高度 
    int nHriFontType = nBarcodeFontType; // nBarcodeFontType 条码文本字体 
    int nHriFontPosition = nBarcodeFontPosition; // nBarcodeFontPosition 条码文本位置 
 
    public static Bitmap toGrayscale(Bitmap bmpOriginal) { 
        int width, height; 
        height = bmpOriginal.getHeight(); 
        width = bmpOriginal.getWidth(); 
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, 
          Config.ARGB_8888); 
        Canvas c = new Canvas(bmpGrayscale); 
        Paint paint = new Paint(); 
        ColorMatrix cm = new ColorMatrix(); 
        cm.setSaturation(0); 
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm); 
        paint.setColorFilter(f); 
        c.drawBitmap(bmpOriginal, 0, 0, paint); 
        return bmpGrayscale; 
    } 
       
       // 缩放，暂时需要public以便调试，完成之后不用这个。 
       public static Bitmap resizeImage(Bitmap bitmap, int w, int h) { 
       
        // load the origial Bitmap 
        Bitmap BitmapOrg = bitmap; 
       
        int width = BitmapOrg.getWidth(); 
        int height = BitmapOrg.getHeight(); 
        int newWidth = w; 
        int newHeight = h; 
       
        // calculate the scale 
        float scaleWidth = ((float) newWidth) / width; 
        float scaleHeight = ((float) newHeight) / height; 
       
        // create a matrix for the manipulation 
        Matrix matrix = new Matrix(); 
        // resize the Bitmap 
        matrix.postScale(scaleWidth, scaleHeight); 
        // if you want to rotate the Bitmap 
        // matrix.postRotate(45); 
       
        // recreate the new Bitmap 
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width, 
          height, matrix, true); 
       
        // make a Drawable from Bitmap to allow to set the Bitmap 
        // to the ImageView, ImageButton or what ever 
        return resizedBitmap; 
       } 
       
       // 16*16 
       private static int[][] Floyd16x16 = /* Traditional Floyd ordered dither */ 
       { 
         { 0, 128, 32, 160, 8, 136, 40, 168, 2, 130, 34, 162, 10, 138, 42, 
           170 }, 
         { 192, 64, 224, 96, 200, 72, 232, 104, 194, 66, 226, 98, 202, 74, 
           234, 106 }, 
         { 48, 176, 16, 144, 56, 184, 24, 152, 50, 178, 18, 146, 58, 186, 
           26, 154 }, 
         { 240, 112, 208, 80, 248, 120, 216, 88, 242, 114, 210, 82, 250, 
           122, 218, 90 }, 
         { 12, 140, 44, 172, 4, 132, 36, 164, 14, 142, 46, 174, 6, 134, 38, 
           166 }, 
         { 204, 76, 236, 108, 196, 68, 228, 100, 206, 78, 238, 110, 198, 70, 
           230, 102 }, 
         { 60, 188, 28, 156, 52, 180, 20, 148, 62, 190, 30, 158, 54, 182, 
           22, 150 }, 
         { 252, 124, 220, 92, 244, 116, 212, 84, 254, 126, 222, 94, 246, 
           118, 214, 86 }, 
         { 3, 131, 35, 163, 11, 139, 43, 171, 1, 129, 33, 161, 9, 137, 41, 
           169 }, 
         { 195, 67, 227, 99, 203, 75, 235, 107, 193, 65, 225, 97, 201, 73, 
           233, 105 }, 
         { 51, 179, 19, 147, 59, 187, 27, 155, 49, 177, 17, 145, 57, 185, 
           25, 153 }, 
         { 243, 115, 211, 83, 251, 123, 219, 91, 241, 113, 209, 81, 249, 
           121, 217, 89 }, 
         { 15, 143, 47, 175, 7, 135, 39, 167, 13, 141, 45, 173, 5, 133, 37, 
           165 }, 
         { 207, 79, 239, 111, 199, 71, 231, 103, 205, 77, 237, 109, 197, 69, 
           229, 101 }, 
         { 63, 191, 31, 159, 55, 183, 23, 151, 61, 189, 29, 157, 53, 181, 
           21, 149 }, 
         { 254, 127, 223, 95, 247, 119, 215, 87, 253, 125, 221, 93, 245, 
           117, 213, 85 } }; 
       // 8*8 
       private static int[][] Floyd8x8 = { { 0, 32, 8, 40, 2, 34, 10, 42 }, 
         { 48, 16, 56, 24, 50, 18, 58, 26 }, 
         { 12, 44, 4, 36, 14, 46, 6, 38 }, 
         { 60, 28, 52, 20, 62, 30, 54, 22 }, 
         { 3, 35, 11, 43, 1, 33, 9, 41 }, 
         { 51, 19, 59, 27, 49, 17, 57, 25 }, 
         { 15, 47, 7, 39, 13, 45, 5, 37 }, 
         { 63, 31, 55, 23, 61, 29, 53, 21 } }; 
       // 4*4 
       @SuppressWarnings("unused") 
       private static int[][] Floyd4x4 = { { 0, 8, 2, 10 }, { 12, 4, 14, 6 }, 
         { 3, 11, 1, 9 }, { 15, 7, 13, 5 } }; 
       
       /**
        * 将256色灰度图转换为2值图 
        *  
        * @param orgpixels 
        * @param xsize 
        * @param ysize 
        * @param despixels 
        */ 
       public static void format_K_dither16x16(int[] orgpixels, int xsize, 
         int ysize, byte[] despixels) { 
        int k = 0; 
        for (int y = 0; y < ysize; y++) { 
       
         for (int x = 0; x < xsize; x++) { 
       
          if ((orgpixels[k] & 0xff) > Floyd16x16[x & 15][y & 15]) 
           despixels[k] = 0;// black 
          else 
           despixels[k] = 1; 
       
          k++; 
         } 
        } 
       
       } 
       
       /**
        * 将256色灰度图转换为2值图 
        *  
        * @param orgpixels 
        * @param xsize 
        * @param ysize 
        * @param despixels 
        */ 
       public static void format_K_dither8x8(int[] orgpixels, int xsize, 
         int ysize, byte[] despixels) { 
        int k = 0; 
        for (int y = 0; y < ysize; y++) { 
       
         for (int x = 0; x < xsize; x++) { 
       
          if (((orgpixels[k] & 0xff) >> 2) > Floyd8x8[x & 7][y & 7]) 
           despixels[k] = 0;// black 
          else 
           despixels[k] = 1; 
       
          k++; 
         } 
        } 
       } 
       
       public static void format_K_threshold(int[] orgpixels, int xsize, 
         int ysize, byte[] despixels) { 
       
        int graytotal = 0; 
        int grayave = 128; 
        int i, j; 
        int gray; 
       
        int k = 0; 
        for (i = 0; i < ysize; i++) { 
       
         for (j = 0; j < xsize; j++) { 
       
          gray = orgpixels[k] & 0xff; 
          graytotal += gray; 
          k++; 
         } 
        } 
        grayave = graytotal / ysize / xsize; 
       
        // 二值化 
        k = 0; 
        for (i = 0; i < ysize; i++) { 
       
         for (j = 0; j < xsize; j++) { 
       
          gray = orgpixels[k] & 0xff; 
       
          if (gray > grayave) 
           despixels[k] = 0;// white 
          else 
           despixels[k] = 1; 
       
          k++; 
         } 
        } 
       } 
       
       /*
        * 对灰度图(ARGB_8888)执行平均阀值算法(滤去0和255不考虑) 
        *  
        * 可以先调用toGrayscale从彩色图片生成灰度图 再调用该函数，将灰度图片转成2值图片 
        */ 
       public static void format_K_threshold(Bitmap mBitmap) { 
       
        int graytotal = 0; 
        int grayave = 128; 
        int graycnt = 1; 
        int gray; 
       
        int ysize = mBitmap.getHeight(); 
        int xsize = mBitmap.getWidth(); 
       
        int i, j; 
        for (i = 0; i < ysize; ++i) { 
         for (j = 0; j < xsize; ++j) { 
          gray = mBitmap.getPixel(j, i) & 0xFF; 
          if (gray != 0 && gray != 255) { 
           graytotal += gray; 
           ++graycnt; 
          } 
         } 
        } 
        grayave = graytotal / graycnt; 
       
        // 根据前面的计算，求得一个平均阀值 
        for (i = 0; i < ysize; i++) { 
       
         for (j = 0; j < xsize; j++) { 
       
          gray = mBitmap.getPixel(j, i) & 0xFF; 
       
          if (gray > grayave) 
           mBitmap.setPixel(j, i, Color.WHITE); 
          else 
           mBitmap.setPixel(j, i, Color.BLACK); 
         } 
        } 
    } 
       
    public static Bitmap alignBitmap(Bitmap bitmap, int wbits, int hbits, 
         int color) { 
        // 已经是对齐的，可以直接返回。 
        if ((bitmap.getWidth() % wbits == 0) 
          && (bitmap.getHeight() % hbits == 0)) 
         return bitmap; 
       
        int width = bitmap.getWidth(); 
        int height = bitmap.getHeight(); 
        int[] pixels = new int[width * height]; 
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height); 
       
        int newwidth = (width + wbits - 1) / wbits * wbits; 
        int newheight = (height + hbits - 1) / hbits * hbits; 
        int[] newpixels = new int[newwidth * newheight]; 
        Bitmap newbitmap = Bitmap.createBitmap(newwidth, newheight, 
          Config.ARGB_8888); 
        for (int i = 0; i < newheight; ++i) { 
         for (int j = 0; j < newwidth; ++j) { 
          if ((i < height) && (j < width)) 
           newpixels[i * newwidth + j] = pixels[i * width + j]; 
          else 
           newpixels[i * newwidth + j] = color; 
         } 
        } 
        newbitmap.setPixels(newpixels, 0, newwidth, 0, 0, newwidth, newheight); 
        return newbitmap; 
    }
    public static byte[] thresholdToBWPic(Bitmap mBitmap) { 
        int[] pixels = new int[mBitmap.getWidth() * mBitmap.getHeight()]; 
        byte[] data = new byte[mBitmap.getWidth() * mBitmap.getHeight()]; 
 
        mBitmap.getPixels(pixels, 0, mBitmap.getWidth(), 0, 0, 
                mBitmap.getWidth(), mBitmap.getHeight()); 
 
        // for the toGrayscale, we need to select a red or green or blue color 
        format_K_threshold(pixels, mBitmap.getWidth(), 
                mBitmap.getHeight(), data); 
 
        return data; 
    } 
 
    /**
     * 将ARGB图转换为二值图，0代表黑，1代表白 
     * 
     * @param mBitmap 
     * @return 
     */ 
    public  byte[] bitmapToBWPix(Bitmap mBitmap) { 
 
        int[] pixels = new int[mBitmap.getWidth() * mBitmap.getHeight()]; 
        byte[] data = new byte[mBitmap.getWidth() * mBitmap.getHeight()]; 
 
        mBitmap.getPixels(pixels, 0, mBitmap.getWidth(), 0, 0, 
                mBitmap.getWidth(), mBitmap.getHeight()); 
 
        // for the toGrayscale, we need to select a red or green or blue color 
        format_K_dither16x16(pixels, mBitmap.getWidth(), 
                mBitmap.getHeight(), data); 
 
        return data; 
    } 
 
    // nWidth必须为8的倍数,这个只需在上层控制即可 
    // 之所以弄成一维数组，是因为一维数组速度会快一点 
    private static int[] p0 = {0, 0x80}; 
    private static int[] p1 = {0, 0x40}; 
    private static int[] p2 = {0, 0x20}; 
    private static int[] p3 = {0, 0x10}; 
    private static int[] p4 = {0, 0x08}; 
    private static int[] p5 = {0, 0x04}; 
    private static int[] p6 = {0, 0x02}; 
 
    // 1行作为1个图片，这样打印不会乱 
    @SuppressWarnings("unused") 
    public static byte[] pixToCmd(byte[] src, int nWidth, int nMode) { 
        // nWidth = 384; nHeight = 582; 
        int nHeight = src.length / nWidth; 
        byte[] data = new byte[8 + (src.length / 8)]; 
        data[0] = 0x1d; 
        data[1] = 0x76; 
        data[2] = 0x30; 
        data[3] = (byte) (nMode & 0x01); 
        data[4] = (byte) ((nWidth / 8) % 0x100);// (xl+xh*256)*8 = nWidth 
        data[5] = (byte) ((nWidth / 8) / 0x100); 
        data[6] = (byte) ((nHeight) % 0x100);// (yl+yh*256) = nHeight 
        data[7] = (byte) ((nHeight) / 0x100); 
        int k = 0; 
        for (int i = 8; i < data.length; i++) { 
            // 不行，没有加权 
            data[i] = (byte) (p0[src[k]] + p1[src[k + 1]] + p2[src[k + 2]] 
                    + p3[src[k + 3]] + p4[src[k + 4]] + p5[src[k + 5]] 
                    + p6[src[k + 6]] + src[k + 7]); 
            k = k + 8; 
        } 
        return data; 
 
    } 
 
    public static byte[] eachLinePixToCmd(byte[] src, int nWidth, int nMode) { 
        int nHeight = src.length / nWidth; 
        int nBytesPerLine = nWidth / 8; 
        byte[] data = new byte[nHeight * (8 + nBytesPerLine)]; 
        int offset = 0; 
        int k = 0; 
        for (int i = 0; i < nHeight; i++) { 
            offset = i * (8 + nBytesPerLine); 
            data[offset + 0] = 0x1d; 
            data[offset + 1] = 0x76; 
            data[offset + 2] = 0x30; 
            data[offset + 3] = (byte) (nMode & 0x01); 
            data[offset + 4] = (byte) (nBytesPerLine % 0x100); 
            data[offset + 5] = (byte) (nBytesPerLine / 0x100); 
            data[offset + 6] = 0x01; 
            data[offset + 7] = 0x00; 
            for (int j = 0; j < nBytesPerLine; j++) { 
                data[offset + 8 + j] = (byte) (p0[src[k]] + p1[src[k + 1]] 
                        + p2[src[k + 2]] + p3[src[k + 3]] + p4[src[k + 4]] 
                        + p5[src[k + 5]] + p6[src[k + 6]] + src[k + 7]); 
                k = k + 8; 
            } 
        } 
 
        return data; 
    } 
 
    public static byte[] byteArraysToBytes(byte[][] data) { 
 
        int length = 0; 
        for (int i = 0; i < data.length; i++) 
            length += data[i].length; 
        byte[] send = new byte[length]; 
        int k = 0; 
        for (int i = 0; i < data.length; i++) 
            for (int j = 0; j < data[i].length; j++) 
                send[k++] = data[i][j]; 
        return send; 
    } 
}