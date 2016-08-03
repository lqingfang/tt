package com.taotao.upload.test;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.taotao.utils.FastDFSClient;

public class MyUploadPic {
	
	//使用fastDFS上传图片
	@Test
	public void uploadPic() throws Exception {
		//指定上传图片绝对路径
		String path = "C:\\Users\\sally\\Desktop\\hua.jpg";
		//指定服务器路径，指定Client.conf绝对路径
		String clientPath = "E:\\chuanzhi\\workspace_0413\\workspace_mytaotao\\taotao\\taotao-manager-web\\src\\main\\resources\\client.conf";
		//加载配置文件，连接服务器
		ClientGlobal.init(clientPath);
		//创建tracker客户端
		TrackerClient tClient = new TrackerClient();
		//从客户端获取tracker对象
		TrackerServer trackerServer = tClient.getConnection();
		StorageServer storageServer = null;
		//创建storageClient客户端
		StorageClient sClient = new StorageClient(trackerServer,storageServer);
		//上传
		String[] str = sClient.upload_file( path, "jpg", null);
		for (String string : str) {
			System.out.println(string);
		}
	}
	
	//使用工具类上传文件
	@Test
	public void uploadPic02() throws Exception {
		//指定上传图片绝对路径
		String path = "C:\\Users\\sally\\Desktop\\hua.jpg";
		//创建工具类对象，通过构造函数加载配置文件
		FastDFSClient fClient = new FastDFSClient("classpath:client.conf");
		//上传文件
		String uploadFile = fClient.uploadFile(path,  "jpg", null);
		System.out.println(uploadFile);
	}
		
}
