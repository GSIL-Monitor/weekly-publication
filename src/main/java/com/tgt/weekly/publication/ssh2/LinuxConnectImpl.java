package com.tgt.weekly.publication.ssh2;

import com.jcraft.jsch.*;
import com.tgt.weekly.publication.entity.DM;
import com.tgt.weekly.publication.properties.DMProperties;
import com.tgt.weekly.publication.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static com.tgt.weekly.publication.constant.CommonConstant.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/18
 * @Time: 14:59
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Slf4j
@Service
public class LinuxConnectImpl implements LinuxConnect<DM, Object> {

    private static final String COMPRESS_PATH="/home/domain/weekly-new/compress";
//    private static final String COMPRESS_PATH="F:\\test-weekly";

    private final DMProperties dmProperties;

    /**
     * todo? 有更好方案？
     */
    private final DataContext dataContext = new DataContext();
    private final HeartDataHandle heartDataHandle = new HeartDataHandle();
    private final DeductDataHandle deductDataHandle = new DeductDataHandle();
    private final CertifyDataHandle certifyDataHandle = new CertifyDataHandle();
    private final InitialDataHandle initialDataHandle = new InitialDataHandle();
    private final DisconnectDataHandle disconnectDataHandle = new DisconnectDataHandle();
    private final WifiDataHandle wifiDataHandle = new WifiDataHandle();
    private final CpuDataHandle cpuDataHandle = new CpuDataHandle();
    private final MemDataHandle memDataHandle = new MemDataHandle();
    private final ClientsDataHandle clientsDataHandle = new ClientsDataHandle();
    private final DiscDataHandle discDataHandle = new DiscDataHandle();

    public LinuxConnectImpl(DMProperties dmProperties) {
        this.dmProperties = dmProperties;
    }

    @Override
    public boolean execScript(DM dm) {
        Session session = null;
        ChannelExec channelExec = null;
        try {
            session = getSession(dm, 60000);
            channelExec = (ChannelExec) session.openChannel("exec");
            log.info(String.format("%s %d %d %s", dm.getCommand(), dmProperties.getStart(), dmProperties.getEnd(), StringUtils.substringBeforeLast(dm.getFileName(), ".")));
            channelExec.setCommand(String.format("%s %d %d %s", dm.getCommand(), dmProperties.getStart(), dmProperties.getEnd(), StringUtils.substringBeforeLast(dm.getFileName(), ".")));
            channelExec.setErrStream(System.err);
            InputStream in = channelExec.getInputStream();
            channelExec.connect();
            //没有执行结果
            log.info("执行结果为：", IOUtils.toString(in, Charset.defaultCharset()));
        } catch (JSchException | IOException e) {
            log.error("执行脚本出错");
            e.printStackTrace();
            return false;
        }finally {
            if (channelExec != null){
                channelExec.disconnect();
            }
            if (session != null){
                session.disconnect();
            }
        }
        return true;
    }

    @Override
    public void download(DM dm) {
        Session session = null;
        ChannelSftp channelSftp = null;
        File zipFile = new File(String.format("%s%s%s", COMPRESS_PATH, SEPARATOR, dm.getFileName()));
        byte[] buff = new byte[1024];
        try{
            session  = getSession(dm, 60000);
            channelSftp  = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            channelSftp.cd(dm.getPath());
            InputStream inputStream = channelSftp.get(dm.getFileName());
            OutputStream outputStream = new FileOutputStream(zipFile);
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.close();
            inputStream.close();
        } catch (JSchException | SftpException | IOException e) {
            log.error("执行下载出错");
            e.printStackTrace();
        }finally {
            if (channelSftp != null){
                channelSftp.quit();
            }
            if (session != null){
                session.disconnect();
            }
        }
    }

    @Override
    public void generateData() {
        combineData();
        writeFile();
    }

    private Session getSession(DM dm, int timeout) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(dm.getUser(), dm.getHost(), 22);
        //"StrictHostKeyChecking"如果设为"yes"，ssh将不会自动把计算机的密匙加入"$HOME/.ssh/known_hosts"文件，且一旦计算机的密匙发生了变化，就拒绝连接。
        session.setConfig("StrictHostKeyChecking", "no");
        session.setTimeout(timeout);
        session.setPassword(dm.getPassword());
        session.connect();
        return session;
    }

    public void combineData(){
        //todo？ 多余判断这里暂时忽略
        File files = new File(COMPRESS_PATH);
        int flag = 0;
        for (File file : files.listFiles()){
            if (!file.isDirectory()){
                try {
                    ZipInputStream zin = new ZipInputStream(new FileInputStream(file));
                    ZipEntry ze;
                    while ((ze = zin.getNextEntry()) != null){
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new ZipFile(file).getInputStream(ze)));
                        String line;
                        while ((line = reader.readLine()) != null){
                            switch(line){
                                case HEART:
                                    dataContext.setDataHandle(heartDataHandle);
                                    break;
                                case DEDUCT:
                                    dataContext.setDataHandle(deductDataHandle);
                                    break;
                                case CERTIFY:
                                    dataContext.setDataHandle(certifyDataHandle);
                                    break;
                                case WIFI:
                                    dataContext.setDataHandle(wifiDataHandle);
                                    break;
                                case DISCONNECT:
                                    dataContext.setDataHandle(disconnectDataHandle);
                                    break;
                                case INITIAL:
                                    dataContext.setDataHandle(initialDataHandle);
                                    break;
                                case CPU:
                                    dataContext.setDataHandle(cpuDataHandle);
                                    dataContext.handle(line);
                                    break;
                                case MEM:
                                    dataContext.setDataHandle(memDataHandle);
                                    dataContext.handle(line);
                                    break;
                                case CLIENTS:
                                    dataContext.setDataHandle(clientsDataHandle);
                                    dataContext.handle(line);
                                    break;
                                case DISC:
                                    dataContext.setDataHandle(discDataHandle);
                                    dataContext.handle(line);
                                    break;
                                default:
                                    dataContext.handle(line);
                            }
                        }
                        log.info("结束..");
                        reader.close();
                    }
                    zin.close();
                } catch (IOException e) {
                    log.error("合并数据操作出错");
                    e.printStackTrace();
                }
            }
        }
    }

    public void writeFile(){
        heartDataHandle.doWrite();
        deductDataHandle.doWrite();
        certifyDataHandle.doWrite();
        initialDataHandle.doWrite();
        disconnectDataHandle.doWrite();
        wifiDataHandle.doWrite();
        cpuDataHandle.doWrite();
        memDataHandle.doWrite();
        clientsDataHandle.doWrite();
    }
}
