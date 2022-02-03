package cn.zq.spshot.common.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Set;

/**
 * ftp工具类
 * Created on 2021/12/8
 *
 * @author 华韵流风
 */
@Component
@ConfigurationProperties(prefix = "ftp.config")
@EnableConfigurationProperties
public class FtpUtil {

    /**
     * 主机名或ip
     */
    private String host;
    /**
     * 端口号
     */
    private int port;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 连接ftp
     *
     * @return boolean
     */
    private FTPClient connectFtp(String filePath) {
        FTPClient ftp = new FTPClient();
        try {
            //定义返回的状态码
            int reply;
            //连接ftp
            ftp.connect(host, port);
            //输入账号和密码
            ftp.login(username, password);
            //接收状态码，如果成功返回230，失败返回503
            reply = ftp.getReplyCode();
            //根据状态码检测ftp的连接，调用isPositiveCompletion(reply)，成功返回true
            if (!FTPReply.isPositiveCompletion(reply)) {
                //说明连接失败，需要断开连接
                return null;
            }
            //切换上传目录changWorkingDirectory(linux上的文件夹)
            if (!ftp.changeWorkingDirectory(filePath)) {
                //说明切换目录失败
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ftp;
    }

    /**
     * 上传文件
     *
     * @return boolean
     */
    public boolean uploadFile(String filePath, String fileName, InputStream inputStream) {

        FTPClient ftp = connectFtp(filePath);
        try {
            if (ftp != null) {
                //以字节流形式上传
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                //上传方法，失败返回false
                if (!ftp.storeFile(fileName, inputStream)) {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //先logout再disconnect，否则报错
                //退出ftp
                ftp.logout();
                //断开连接
                ftp.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    /**
     * @param filePath  文件路径
     * @param fileNames 需删除的文件数组
     * @return boolean[]
     */
    public boolean[] deleteFile(String filePath, Set<String> fileNames) {
        FTPClient ftp = connectFtp(filePath);
        boolean[] res = new boolean[fileNames.size()];
        try {

            if (ftp != null) {
                //删除文件
                int i = 0;
                for (String fileName : fileNames) {
                    res[i++] = ftp.deleteFile(fileName);
                }
            } else {
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //先logout再disconnect，否则报错
                //退出ftp
                ftp.logout();
                //断开连接
                ftp.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

}
