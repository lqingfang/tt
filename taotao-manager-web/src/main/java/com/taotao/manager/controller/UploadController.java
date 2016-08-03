package com.taotao.manager.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.utils.FastDFSClient;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.PicResult;

@Controller
public class UploadController {

	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	/**
	 * 需求：上传图片，上传分布式文件系统fastDfs
	 * 请求：common.js :/pic/upload
	 * 参数：uploadFile，参数名称不能改变，js中固定传递参数名称
	 * 返回值：kindEditor富文本编辑器规定返回值是json格式
	 * 上传成功：{"error":0,url:"",message:null}
	 * 上传失败：{"error":1,url:null,message:"上传失败"}
	 * @param uploadFile
	 * @return
	 */
	@RequestMapping("/pic/upload")
	public @ResponseBody String uploadPic(MultipartFile uploadFile) {
		
		//获取上传文件扩展名
		String filename = uploadFile.getOriginalFilename();
		String extName  = filename.substring(filename.lastIndexOf(".")+1);
		
		try {
			
			//创建fastDFS工具类对象，加载配置文件，连接fastDFS分布式文件系统
			FastDFSClient fClient = new FastDFSClient("classpath:client.conf");
			//上传文件
			String url = fClient.uploadFile(uploadFile.getBytes(), extName);
			//组合文件绝对路径
			url = IMAGE_SERVER_URL+url;
			
			//上传成功：返回  error=0,url 图片地址
			PicResult pic = new PicResult();
			pic.setError(0);
			pic.setUrl(url);
			
			String json = JsonUtils.objectToJson(pic);
			return json;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//上传失败：返回 error=1,message 失败信息
			PicResult pic = new PicResult();
			pic.setError(1);
			pic.setMessage("上传失败");
			
			String json = JsonUtils.objectToJson(pic);
			return json;
		}
		return null;
	}
}
