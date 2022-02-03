package cn.zq.spshot.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author 华韵流风
 * @ClassName TestController
 * @Date 2021/12/24 17:18
 * @packageName cn.zq.spshot.test.controller
 * @Description TODO
 */
@CrossOrigin
@RestController
public class TestController {

    @Autowired
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @GetMapping
    public void verifyServer() {
        // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = null;
        try {
            out = response.getWriter();
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，否则接入失败
            if (checkSignature(signature, timestamp, nonce)) {
                out.print(echostr);
                out.flush();   //这个地方必须画重点，消息推送配置Token令牌错误校验失败，搞了我很久，必须要刷新！！！！！！！
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert out != null;
            out.close();

        }

    }


    /**
     * 验证签名
     *
     * @param signature signature
     * @param timestamp timestamp
     * @param nonce     nonce
     * @return checkSignature
     */
    private static boolean checkSignature(String signature, String timestamp, String nonce) {

        //与token 比较
        String[] arr = new String[]{"spshot", timestamp, nonce};

        // 将token、timestamp、nonce三个参数进行字典排序
        Arrays.sort(arr);

        StringBuilder content = new StringBuilder();

        for (String s : arr) {
            content.append(s);
        }

        MessageDigest md;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");

            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 将sha1加密后的字符串可与signature对比
        return tmpStr != null && tmpStr.equals(signature.toUpperCase());
    }


    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray byteArray
     * @return String
     */
    private static String byteToStr(byte[] byteArray) {
        StringBuilder strDigest = new StringBuilder();
        for (byte b : byteArray) {
            strDigest.append(byteToHexStr(b));
        }
        return strDigest.toString();
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte mByte
     * @return String
     */
    private static String byteToHexStr(byte mByte) {
        char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = digit[mByte & 0X0F];

        return new String(tempArr);
    }

}
