package com.example.study_system.service.impl;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.example.study_system.emun.ResultEmun;
import com.example.study_system.model.ResourceInfo;
import com.example.study_system.service.base.BaseService;
import com.example.study_system.service.iface.IResourceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ResourceServiceImpl extends BaseService implements IResourceService {

	public static String ORI_FILE_PATH = "E:\\公司项目\\学习管理系统\\study\\files\\"; // 输出文件路径
	public static String OPEN_OFFICE4 = "D:\\OpenOffice 4\\program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";

	/**
	 * 上传资源
	 *
	 * @param resourceInfo
	 * @return
	 */
	@Override
	public int uploadResourceInfo(ResourceInfo resourceInfo, MultipartFile file, String ffmpeg_path) {
		// 原文件名称
		String oriName = "";
		// 文件路径
		String desFilePath = "";
		// 文件类型
		Integer resType = 0;
		// 重命名名字（uuid）
		String uuid = "";
		String extName = "";
		File path = new File(ORI_FILE_PATH);
		// 如果文件夹不存在
		if (!path.exists()) {
			// 创建文件夹
			path.mkdirs();
		}

		if (file != null) {
			if (file.getSize() == 0) {
				return ResultEmun.UPLOAD_FILE_ISEMPTY.getCode();
			}
			// 获取原文件名
			oriName = file.getOriginalFilename();
			// 获取文件大小
			long resSize = Math.round(file.getSize());
			// 获取文件类型
			String fileType = file.getContentType();
			// 获取源文件后缀名
			extName = oriName.substring(oriName.lastIndexOf(".")).toLowerCase();

			// 截取文件后缀名判断文件类型
			if (extName.equals(".mp4") || extName.equals(".avi") || extName.equals(".mpg") || extName.equals(".mpeg")
					|| extName.equals(".wmv") || extName.equals(".rmvb") || extName.equals(".rm")
					|| extName.equals(".flv") || extName.equals(".mov")) {
				fileType = "1";
				resType = Integer.valueOf(fileType);
			} else if (extName.equals(".ogv") || extName.equals(".mp3") || extName.equals(".wav")
					|| extName.equals(".wma") || extName.equals(".cd") || extName.equals(".wav")
					|| extName.equals(".aiff") || extName.equals(".aac") || extName.equals(".midi")) {
				fileType = "2";
				resType = Integer.valueOf(fileType);
			} else if (extName.equals(".txt") || extName.equals(".doc") || extName.equals(".docx")
					|| extName.equals(".xlsx") || extName.equals(".xls") || extName.equals(".ppt")
					|| extName.equals(".pptx") || extName.equals(".pdf")) {
				fileType = "3";
				resType = Integer.valueOf(fileType);
			} else {
				return ResultEmun.THIS_FILE_FORMAT_IS_NOT_SUPPORTED.getCode();
			}
			
			// 生成UUID
			uuid = UUID.randomUUID().toString().replaceAll("-", "");
			// UUID生成的新名字+后缀名
			String newName = uuid + extName;

			try {
				// 获取要保存的路径文件夹
				String filePath = ORI_FILE_PATH;
				// 保存文件，保存文件路径
				desFilePath = filePath + newName;
				File desFile = new File(desFilePath);
				if (desFile.createNewFile()) {
					desFile.setExecutable(true);
					desFile.setReadable(true);
					desFile.setWritable(true);
				}
				file.transferTo(desFile);
				logger.info("保存文件路径:" + desFilePath);
				String resName = oriName.substring(0, oriName.lastIndexOf("."));
				// 添加原文件名
				resourceInfo.setResName(resName);
				// 添加文件类型
				resourceInfo.setResType(resType);
				// 添加保存文件路径
				resourceInfo.setPath(newName);
				if (resType != 1) {
					resourceInfo.setImgPath("");
				}
				// 添加后缀名
				resourceInfo.setResExt(extName);
				// 添加文件大小
				resourceInfo.setResSize(resSize);
				// 添加状态默认是 0：未发布
				resourceInfo.setStatus(0);
				// 获取当前系统时间
				Date date = new Date();
				// 添加当前系统时间
				resourceInfo.setcTime(date);
				// 添加当前系统时间
				resourceInfo.setmTime(date);
				// 添加创建用户
				resourceInfo.setcUser(resourceInfo.getcUser());
				// 添加修改用户
				resourceInfo.setmUser(resourceInfo.getcUser());

				System.out.println("文件类型" + "resType:" + resType);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 判断文件类型是否为视频类型
		if (resourceInfo.getResType() == 1) {
			VideoScreenshots(resourceInfo, desFilePath, ffmpeg_path, uuid);
		} else if (resourceInfo.getResType() == 3 && !extName.equals(".pdf")) {
			if (!extName.equals(".txt")) {
				String desPath = ORI_FILE_PATH + uuid + ".pdf";
				officeOpenPDF(desFilePath, desPath);
				logger.info("成功进入方法");
				if (extName.equals(".doc") || extName.equals(".docx")) {
					try {
						docxToHtml(desFilePath, uuid);	
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return resourceInfoMapper.insert(resourceInfo);
	}

	// 使用ffmpeg截取视频里的一帧图片
	private void VideoScreenshots(ResourceInfo resourceInfo, String desFilePath, String ffmpeg_path, String uuid) {
		String video_path = desFilePath;
		File videoFile = new File(video_path);
		// 测试此抽象路径名表示的文件或目录是否存在
		if (videoFile.exists()) {
			List<String> commands = new ArrayList<>();
			commands.add(ffmpeg_path);
			commands.add("-i");
			commands.add(video_path);
			commands.add("-y");
			commands.add("-f");
			commands.add("image2");
			commands.add("-ss");
			// 这个参数是设置截取视频多少秒时的画面
			commands.add("0.5");
			commands.add("-s");
			// 设置截取图片大小
			commands.add("700x525");
			// 截取视频的文件名再加上jpg
			commands.add(video_path.substring(0, video_path.lastIndexOf(".")) + ".jpg");
			System.out.println(commands);
			System.out.println(videoFile);
			System.out.println(video_path.substring(0, video_path.lastIndexOf(".")));
			try {
				ProcessBuilder builder = new ProcessBuilder();
				builder.command(commands);
				// 执行命令并返回一个Process对象，用于获取对执行程序的输入和输出
				builder.start();
				System.out.println("截取成功");
				resourceInfo.setImgPath(uuid + ".jpg");
			} catch (Exception e) {
				e.printStackTrace();

			}
		} else {
			System.err.println("路径	[" + video_path + "]对应的视频文件不存在!");
		}
	}

	// 使用openoffice把office文档转换成为pdf文件
	private void officeOpenPDF(String inputFile, String pdfFile) {

		Process p = null;
		// 判断源文件目录是否存在
		File inputFiles = new File(inputFile);
		if (!inputFiles.exists()) {
			logger.info("源文件不存在!");
			return;
		}

		// 判断输出文件上级目录是否存在
		File outputFile = new File(pdfFile);
		if (!outputFile.getParentFile().exists()) {
			outputFile.getParentFile().exists();
		}

		// 调用openoffice服务线程
		try {
			 // 调用cmd命令
			p = Runtime.getRuntime().exec(OPEN_OFFICE4);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		OpenOfficeConnection connection = new SocketOpenOfficeConnection(8888);

		try {
			connection.connect();
			DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
			converter.convert(inputFiles, outputFile);
			connection.disconnect();

		} catch (ConnectException e) {
			System.out.println("openoffice报错");
			e.printStackTrace();
		}

	}
	

	public String docxToHtml(String desFilePath, String uuid) throws Exception, IOException {
		File path = new File(ORI_FILE_PATH + uuid);
		if (!path.exists()) {
			// 多级文件夹目录
			path.mkdirs();
		}
		String imagePath = ORI_FILE_PATH + uuid + "\\image";
		String sourceFileName = desFilePath;
		String targetFileName = ORI_FILE_PATH + uuid + "\\" + uuid + ".html";

		OutputStreamWriter outputStreamWriter = null;
		try {
			XWPFDocument document = new XWPFDocument(new FileInputStream(sourceFileName));
			XHTMLOptions options = XHTMLOptions.create();
			// 存放图片的文件夹
			options.setExtractor(new FileImageExtractor(new File(imagePath)));
			// html中图片的路径
			options.URIResolver(new BasicURIResolver("image"));
			outputStreamWriter = new OutputStreamWriter(new FileOutputStream(targetFileName), "utf-8");
			XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
			xhtmlConverter.convert(document, outputStreamWriter, options);
		} finally {
			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}
		}
		return targetFileName;
	}

	public void testExcel() throws FileNotFoundException {
		File path = new File(ResourceUtils.getURL("classpath:").getPath());
		String imagePath = path.getAbsolutePath() + "\\image";
		String sourceFileName = "F:\\02.xlsx";
		String targetFileName = path.getAbsolutePath() + "\\01.html";
		String file = "\\02.xlsx";
		HSSFWorkbook excelBook = null;
		ExcelToHtmlConverter excelToHtmlConverter = null;
		try {
			InputStream input = new FileInputStream(sourceFileName);
			excelBook = new HSSFWorkbook(input);
			excelToHtmlConverter = new ExcelToHtmlConverter(
					DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		// 加载html页面时图片路径
		XHTMLOptions options = XHTMLOptions.create();
		// 图片保存文件夹路径
		options.setExtractor(new FileImageExtractor(new File(imagePath)));
		options.URIResolver( new BasicURIResolver("image"));
		excelToHtmlConverter.setOutputRowNumbers(false);
		excelToHtmlConverter.setOutputHiddenRows(false);
		excelToHtmlConverter.setOutputColumnHeaders(false);
		excelToHtmlConverter.setOutputHiddenColumns(true);
		excelToHtmlConverter.processWorkbook(excelBook);
		List pics = excelBook.getAllPictures();
		if (pics != null) {
			for (int i = 0; i < pics.size(); i++) {
				HSSFPictureData pic = (HSSFPictureData) pics.get(i);
				try {
					new FileOutputStream(imagePath + "11").write(pic.getData());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		Document htmlDocument = excelToHtmlConverter.getDocument();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(outStream);
		TransformerFactory tf = TransformerFactory.newInstance();
		try {
			Transformer serializer = tf.newTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty(OutputKeys.METHOD, "html");
			serializer.transform(domSource, streamResult);
		} catch (TransformerException e) {
			e.printStackTrace();
		} finally {
			try {
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String content = new String(outStream.toByteArray());

		try {
			FileUtils.writeStringToFile(new File(path, "exportExcel.html"), content, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除资源
	 *
	 * @param resId
	 * @return
	 */
	@Override
	@Transactional
	public int deleteResourceInfoByResId(Long resId) {
		String path = resourceInfoMapper.selectByPrimaryKey(resId).getPath();
		String filePath = ORI_FILE_PATH + path;
		File file = new File(filePath);
		System.err.println(filePath);
		if (file.exists() && file.isFile()) {
			file.delete();
		}
		return resourceInfoMapper.deleteByPrimaryKey(resId);
	}

	/**
	 * 修改资源名
	 *
	 * @param resId
	 * @return
	 */
	@Override
	public int modifyResourceNameByResId(Long resId, String resName) {
		if (resName.equals("")) {
			return 0;
		}
		return resourceInfoMapper.updateByPrimaryKey(resId, resName);
	}

	/**
	 * 获取资源详情
	 *
	 * @param resId
	 * @return
	 */
	@Override
	public ResourceInfo getResourceDetailByResId(Long resId) {
		return resourceInfoMapper.selectByPrimaryKey(resId);
	}

	/**
	 * 获取资源列表
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageInfo<ResourceInfo> getResourceList(Integer pageNum, Integer pageSize, String resName, Integer resType) {
		PageHelper.startPage(pageNum, pageSize);
		List<ResourceInfo> resourceList = resourceInfoMapper.selectList(resName, resType);
		PageInfo<ResourceInfo> result = new PageInfo<>(resourceList);
		return result;
	}

	/**
	 * 获取资源数
	 */
	@Override
	public int getResourceListCount() {
		return resourceInfoMapper.selectListCount();
	}

}
