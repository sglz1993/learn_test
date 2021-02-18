package org.py.test.log_hello.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class QRCodeTest {


    /**
     * 参考：https://www.cnblogs.com/zjw2004112/p/11584422.html
     * 解析参考：https://bbs.csdn.net/topics/392467265
     *  建议提高精度和复杂模式开启
     *    //精度
     *    hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
     *    //复杂模式
     *    hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
     * 实测可行
     * @throws IOException
     * @throws WriterException
     * @throws NotFoundException
     */
    @Test
    public void testQRCode() throws IOException, WriterException, NotFoundException {
        final int width = 300;
        final int height = 300;
        final String format = "png";
        final String content = "https://www.baidu.com";

        //定义二维码的参数
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //L级：约可纠错7%的数据码字,M级：约可纠错15%的数据码字,Q级：约可纠错25%的数据码字,H级：约可纠错30%的数据码字
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
        hints.put(EncodeHintType.MARGIN, 2);

        //生成二维码
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        Path file = new File("/Users/pengyue.du/Code/Meijia/Work01/learn_test/java/logger/logback-hello-01/lib/img.png").toPath();
        MatrixToImageWriter.writeToPath(bitMatrix, format, file);
        System.out.println("生成成功,路径：" + file.toString());
        System.out.println("------------------------------");
        //解析二维码
        MultiFormatReader formatReader = new MultiFormatReader();
        BufferedImage image = ImageIO.read(file.toFile());
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        Result result = formatReader.decode(binaryBitmap, hints);
        System.out.println("二维码解析结果：" + result.toString());
        System.out.println("二维码的格式：" + result.getBarcodeFormat());
        System.out.println("二维码的文本内容：" + result.getText());
    }

}
