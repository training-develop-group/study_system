package com.example.study_system.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ConnectException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

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

@Service
public class ResourceServiceImpl extends BaseService implements IResourceService {

	public static String ORI_FILE_PATH = "F:\\study\\files\\"; // 输出文件路径

	/**
	 * 上传资源
	 *
	 * @param resourceInfo
	 * @return
	 */
	@Override
	public int uploadResourceInfo(ResourceInfo resourceInfo, MultipartFile file, String ffmpeg_path) {
		String oriName = ""; // 原文件名称
		String desFilePath = ""; // 文件路径
		Integer resType = 0; // 文件类型
		String uuid = ""; // 重命名名字（uuid）
		String extName = "";
		File path = new File(ORI_FILE_PATH);
		if (!path.exists()) { // 如果文件夹不存在
			path.mkdirs(); // 创建文件夹
		}

		if (file != null) {
			if (file.getSize() == 0) {
				return ResultEmun.UPLOAD_FILE_ISEMPTY.getCode();
			}
			oriName = file.getOriginalFilename(); // 获取原文件名
			long resSize = Math.round(file.getSize()); // 获取文件大小
			String fileType = file.getContentType(); // 获取文件类型
			System.err.println(fileType);
			extName = oriName.substring(oriName.lastIndexOf(".")).toLowerCase(); // 获取源文件后缀名
			System.err.println(extName);

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

			uuid = UUID.randomUUID().toString().replaceAll("-", ""); // 生成UUID
			String newName = uuid + extName; // UUID生成的新名字+后缀名

			try {
				String filePath = ORI_FILE_PATH; // 获取要保存的路径文件夹
				// 保存文件
				desFilePath = filePath + newName; // 保存文件路径
				File desFile = new File(desFilePath);
				if (desFile.createNewFile()) {
					desFile.setExecutable(true);
					desFile.setReadable(true);
					desFile.setWritable(true);
					System.out.println("is execute allow : " + desFile.canExecute());
				}
				file.transferTo(desFile);
				logger.info("保存文件路径:" + desFilePath);
				String resName = oriName.substring(0, oriName.lastIndexOf("."));
				resourceInfo.setResName(resName); // 添加原文件名
				resourceInfo.setResType(resType); // 添加文件类型
				resourceInfo.setPath(newName); // 添加保存文件路径
				if (resType != 1) {
					resourceInfo.setImgPath("");
				}
				resourceInfo.setResExt(extName); // 添加后缀名
				resourceInfo.setResSize(resSize); // 添加文件大小
				resourceInfo.setStatus(0); // 添加状态默认是 0：未发布
				Date date = new Date(); // 获取当前系统时间
				resourceInfo.setcTime(date); // 添加当前系统时间
				resourceInfo.setmTime(date); // 添加当前系统时间
				resourceInfo.setcUser(resourceInfo.getcUser()); // 添加创建用户
				resourceInfo.setmUser(resourceInfo.getcUser()); // 添加修改用户

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
//				try {
//					testExcel();
//				} catch (FileNotFoundException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
				try {
//					excel();
					genHtml("F:/02.xlsx", "F:/02.html");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				try {
//					getDataFromExcel("F:/05.xls");
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				
//				try {
//					excelToHtml("F:/02.xlsx", "F:/02.html");
//				} catch (EncryptedDocumentException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (InvalidFormatException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
				if (extName.equals(".doc") || extName.equals(".docx")) {
					try {
						docxToHtml(desFilePath, uuid);	
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
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
			commands.add("0.5");// 这个参数是设置截取视频多少秒时的画面
			// commands.add("-t");
			// commands.add("0.001");
			commands.add("-s");
			commands.add("700x525");// 设置截取图片大小
			commands.add(video_path.substring(0, video_path.lastIndexOf(".")) + ".jpg"); // 截取视频的文件名再加上jpg
			System.out.println(commands);
			System.out.println(videoFile);
			System.out.println(video_path.substring(0, video_path.lastIndexOf(".")));
			try {
				ProcessBuilder builder = new ProcessBuilder();
				builder.command(commands);
				builder.start(); // 执行命令并返回一个Process对象，用于获取对执行程序的输入和输出
				System.out.println("截取成功");
				resourceInfo.setImgPath(uuid + ".jpg");
			} catch (Exception e) {
				e.printStackTrace();

			} catch (IOException e) {
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
		String command = "C:\\Program Files (x86)\\OpenOffice 4\\program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
		try {
			p = Runtime.getRuntime().exec(command); // 调用cmd命令
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);

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
			path.mkdirs();// 多级文件夹目录
		}
		String imagePath = ORI_FILE_PATH + uuid + "\\image";
		String sourceFileName = desFilePath;
		String targetFileName = ORI_FILE_PATH + uuid + "\\" + uuid + ".html";
		System.err.println("path:" + path);
		System.err.println("imagePath:" + imagePath);
		System.err.println("targetFileName:" + targetFileName);

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
//		String path = "F:\\11.18";
//		String imagePath = "F:\\11.18\\image";
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
		options.URIResolver(new BasicURIResolver("image"));
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
//                        pic.writeImageContent (new FileOutputStream (path + pic.suggestFullFileName() ) );
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

	public void excel() throws IOException, ParserConfigurationException, TransformerException {
		String path = "F:\\";
		String file = "02.xlsx";
//		String path2 = "F:/123/123.jpg";

		InputStream input = new FileInputStream(path + file);
		HSSFWorkbook excelBook = new HSSFWorkbook(input);
		ExcelToHtmlConverter excelToHtmlConverter = new ExcelToHtmlConverter(
				DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
		excelToHtmlConverter.processWorkbook(excelBook);
		List pics = excelBook.getAllPictures();
		if (pics != null) {
			for (int i = 0; i < pics.size(); i++) {
				HSSFPictureData pic = (HSSFPictureData) pics.get(i);
				try {
//					pic.writeImageContent(new FileOutputStream(path + pic.suggestFullFileName()));
					new FileOutputStream(path + file).write(pic.getData());
					logger.error("1");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		Document htmlDocument = excelToHtmlConverter.getDocument();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(outStream);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);
		outStream.close();

		String content = new String(outStream.toByteArray());
		System.err.println(content);

		FileUtils.writeStringToFile(new File(path, "exportExcel.html"), content, "utf-8");
	}

	/**
	 * POI将Excel转化为HTML（包含图片，图片以文件形式保存）
	 * 
	 * @param excel excel全路径
	 * @param html  html全路径
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public void genHtml(String excel, String html)
			throws ParserConfigurationException, IOException, TransformerException {
		// 创建excel ExcelToHtmlConverter对象
		ExcelToHtmlConverter convert = new ExcelToHtmlConverter(
				DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
		// 列名不显示
//		convert.setOutputColumnHeaders(false);
		// 行号不显示
//		convert.setOutputRowNumbers(false);
		// 创建POI工作薄对象
		InputStream input = new FileInputStream(excel);
		HSSFWorkbook wb = new HSSFWorkbook(input);
		// 去除EXCEL每个sheet的名称
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			if (wb.getSheetAt(i) != null) {
				String sheetName = StringUtils.leftPad(" ", i + 1, " ");
				wb.setSheetName(i, sheetName);
			}
		}
		convert.processWorkbook(wb);

		// 创建HTML的内容（不包含图片）
		Document htmlDocument = convert.getDocument();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(outStream);
		TransformerFactory tfFactory = TransformerFactory.newInstance();
		Transformer tf = tfFactory.newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		tf.setOutputProperty(OutputKeys.METHOD, "html");
		tf.transform(domSource, streamResult);
		outStream.close();
		String content = new String(outStream.toByteArray());
		System.err.println("content1"+content);
		
		// 图片处理
		// 以文件方式存储图片
		String htmlImg = getImgHtml(wb, html);
		// 以二进制字符串方式保存在html文件中
//		 String htmlImg = getImgHtml_Base64(wb, html);
		if (!"".equals(htmlImg)) {
			int bodyIndex = content.lastIndexOf("</body>");
			String tbodyA = content.substring(0, bodyIndex);
			String tbodyB = content.substring(bodyIndex, content.length() - 1);
			StringBuilder sb = new StringBuilder();
			sb.append(tbodyA);
			sb.append(htmlImg);
			sb.append(tbodyB);
			content = sb.toString();
			System.err.println(sb);
			System.err.println("content2"+content);
		}
		// 生成HTML文件
		File file = new File(html);
		String htmlFolder = file.getParent();
		String htmlName = file.getName();
		FileUtils.writeStringToFile(new File(htmlFolder, htmlName), content, "utf-8");
	}

	/**
	 * Excel中图片转化为HTML,图片以文件方式存储
	 * 
	 * @param wb   Excel的工作簿
	 * @param html html文件的全路径
	 * @return 关于图片的html
	 * @throws IOException
	 */
	public String getImgHtml(HSSFWorkbook wb, String html) throws IOException {
		// 获取Excel所有的图片
		Map<String, HSSFPictureData> pics = getPictrues(wb);
		if (pics == null || pics.size() == 0) {
			return "";
		}
		// 图片文件夹 = html的文件夹 + html的名称
		File file = new File(html);
		String htmlFolder = file.getParent();
		String htmlName = file.getName();
		File imgFolder = new File(htmlFolder + "/" + htmlName.substring(0, htmlName.lastIndexOf(".")));
		// 判断图片文件夹是否存在, 不存在则创建
		if (!imgFolder.exists() && !imgFolder.isDirectory()) {
			imgFolder.mkdirs();
		}
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, HSSFPictureData> entry : pics.entrySet()) {
			HSSFPictureData pic = entry.getValue();
			// 保存图片, 图片路径 = HTML路径/HTML名称/sheet索引_行号_列号_单元格内的上边距_单元格内的左边距_uuid.后缀
			String ext = pic.suggestFileExtension();
			String imgName = entry.getKey() + "." + ext;
			byte[] data = pic.getData();
			// 创建图片
			FileOutputStream out = new FileOutputStream(imgFolder + "/" + imgName);
			out.write(data);
			out.close();
			// 图片在Excel中的坐标，sheet索引_行号_列号_单元格内的上边距_单元格内的左边距_uuid
			String[] arr = StringUtils.split(entry.getKey(), "_");
			// 图片的上边距和左边距
			float top = getTop(wb, Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[3]));
			float a = 23;
			System.err.println(top);	//两次输出的top值与实际表格位置差距太大
			System.err.println(arr[0]);
			float left = getLeft(wb.getSheetAt(Integer.parseInt(arr[0])), Integer.parseInt(arr[2]),
					Integer.parseInt(arr[4]));
			// margin设置为8，以保持和body的内边距一致
			String htmlImg = "<table style=\"position: absolute; margin:8; left: " + left + "; top: " + top+a
					 + "pt;\">\n<tbody>\n<tr>\n<td>\n";
			htmlImg += "<image src=\"" + htmlName.substring(0, htmlName.lastIndexOf(".")) + "/" + imgName + "\" />";
			htmlImg += "\n</td>\n</tr>\n</tbody>\n</table>\n";
			sb.append(htmlImg);
		}
		return sb.toString();
	}

	/**
	 * Excel中图片转化为HTML,图片以二进制字符串方式存储在html文件中
	 * 
	 * @param wb   Excel的工作簿
	 * @param html html文件的全路径
	 * @return 关于图片的html
	 * @throws IOException
	 */
	public String getImgHtml_Base64(HSSFWorkbook wb, String html) throws IOException {
		// 获取Excel所有的图片
		Map<String, HSSFPictureData> pics = getPictrues(wb);
		if (pics == null || pics.size() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, HSSFPictureData> entry : pics.entrySet()) {
			HSSFPictureData pic = entry.getValue();
			byte[] data = pic.getData();
			String baseStr = Base64.getEncoder().encodeToString(data);
			// 图片在Excel中的坐标，sheet索引_行号_列号_单元格内的上边距_单元格内的左边距_uuid
			String[] arr = StringUtils.split(entry.getKey(), "_");
			float top = getTop(wb, Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[3]));
			float left = getLeft(wb.getSheetAt(Integer.parseInt(arr[0])), Integer.parseInt(arr[2]),
					Integer.parseInt(arr[4]));
			// margin设置为8，以保持和body的内边距一致
			String htmlImg = "<table style=\"position: absolute; margin:8; left: " + left + "; top: " + top
					+ "pt;\">\n<tbody>\n<tr>\n<td>\n";
			htmlImg += "<image src=\"data:image/png;base64," + baseStr + "\" />";
			htmlImg += "\n</td>\n</tr>\n</tbody>\n</table>\n";
			sb.append(htmlImg);
		}
		return sb.toString();
	}

	/**
	 * Excel的图片获取
	 * 
	 * @param wb Excel的工作簿
	 * @return Excel的图片，键格式：sheet索引_行号_列号_单元格内的上边距_单元格内的左边距_uuid
	 */
	public Map<String, HSSFPictureData> getPictrues(HSSFWorkbook wb) {
		Map<String, HSSFPictureData> map = new HashMap<String, HSSFPictureData>();
		// getAllPictures方法只能获取不同的图片，如果Excel中存在相同的图片，只能得到一张图片
		List<HSSFPictureData> pics = wb.getAllPictures();
		if (pics.size() == 0) {
			return map;
		}
		for (Integer sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			HSSFSheet sheet = wb.getSheetAt(sheetIndex);
			HSSFPatriarch patriarch = sheet.getDrawingPatriarch();
			if (patriarch == null) {
				continue;
			}
			for (HSSFShape shape : patriarch.getChildren()) {
				HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
				if (shape instanceof HSSFPicture) {
					HSSFPicture pic = (HSSFPicture) shape;
					int picIndex = pic.getPictureIndex() - 1;
					HSSFPictureData picData = pics.get(picIndex);
					// 键格式：sheet索引_行号_列号_单元格内的上边距_单元格内的左边距_uuid
					String key = sheetIndex + "_" + anchor.getRow1() + "_" + anchor.getCol1() + "_" + anchor.getDy1()
							+ "_" + anchor.getDx1() + "_" + UUID.randomUUID();
					map.put(key, picData);
				}
			}
		}
		return map;
	}

	/**
	 * Excel中单元格的上边距（HTML的上边距），支持多Sheet，递加，单位pt
	 * 
	 * @param wb         Excel的工作簿
	 * @param sheetIndex Sheet的索引
	 * @param rowIndex   单元格所在行号
	 * @param dy         单元格内的上边距
	 * @return 上边距
	 */
	public float getTop(HSSFWorkbook wb, Integer sheetIndex, Integer rowIndex, int dy) {
		float top = 0;
		HSSFSheet sheet = null;
		// 左侧Sheet的上边距
		for (Integer i = 0; i < sheetIndex; i++) {
			sheet = wb.getSheetAt(i);
			// 获得总行数
			Integer rowNum = sheet.getLastRowNum() + 1;
			// 空sheet的总行高是0，空行的行高是0
			top += getTop(sheet, rowNum, 0);
		}
		// 当前sheet的上边距
		sheet = wb.getSheetAt(sheetIndex);
		System.err.println("索引对象"+sheet);
		top += getTop(sheet, rowIndex, dy);
		return top;
	}

	/**
	 * Sheet中单元格的上边距，单位pt，上边距 = SUM(上方每个单元格的高度) + 单元格内的上边距
	 * 
	 * @param sheet    Excel的Sheet
	 * @param rowIndex 单元格所在行号
	 * @param dy       单元格内的上边距
	 * @return 上边距
	 */
	public float getTop(HSSFSheet sheet, Integer rowIndex, int dy) {
		float top = 0;
		// SUM(上方每个单元格的高度)
		for (int i = 0; i < rowIndex; i++) {
			HSSFRow row = sheet.getRow(i);
			// 排除空行(HTML转化时也被排除了)
			if (row == null) {
				top += 13.5;
				continue;
			}
			top += row.getHeightInPoints();
			System.err.println("每个单元格的行高："+row.getHeightInPoints());
		}
		// 单元格内的上边距单位转化为pt
		if (dy != 0) {
			top += dy / 20.00f;
		}
		System.err.println("每次top返回的高："+top);
		return top;
	}

	/**
	 * Sheet中单元格的左边距，单位px，左边距 = SUM(左侧每个单元格的宽度) + 单元格内的左边距
	 * 
	 * @param sheet    Excel的Sheet
	 * @param colIndex 单元格所在列号，
	 * @param dx       单元格内的左边距
	 * @return 左边距
	 */
	public float getLeft(HSSFSheet sheet, Integer colIndex, int dx) {
		float left = 0;
		// SUM(左侧每个单元格的宽度)
		for (int i = 0; i < colIndex; i++) {
			float width = sheet.getColumnWidthInPixels(i);
			left += width;
		}
		// 单元格内的左边距单位转化为px
		if (dx != 0) {
			int cw = sheet.getColumnWidth(colIndex);
			int def = sheet.getDefaultColumnWidth() * 256;
			float px = (cw == def ? 32.00f : 36.56f);
			left += dx / px;
		}
		return left;
	}

	private static String UPLOAD_FILE = "E:/";

	/**
	 * 测试
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws EncryptedDocumentException
	 */
      public static void excelToHtml(String path,String htmlPositon) throws EncryptedDocumentException, InvalidFormatException, IOException {
          InputStream is = null;
          String htmlExcel = null;
          String[] str = path.split("/");
          String fileName = str[str.length-1];
          try {
              File sourcefile = new File(path);
              is = new FileInputStream(sourcefile);
              Workbook wb = WorkbookFactory.create(is);// 此WorkbookFactory在POI-3.10版本中使用需要添加dom4j
              if (wb instanceof XSSFWorkbook) {
                  XSSFWorkbook xWb = (XSSFWorkbook) wb;
                  htmlExcel = ResourceServiceImpl.getExcelInfo(xWb, true);
              } else if (wb instanceof HSSFWorkbook) {
                  HSSFWorkbook hWb = (HSSFWorkbook) wb;
                  htmlExcel = ResourceServiceImpl.getExcelInfo(hWb, true);
              }
              writeFile(htmlExcel,htmlPositon,fileName);
          } catch (Exception e) {
              e.printStackTrace();
          } finally {
              try {
                  is.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      
      @SuppressWarnings("resource")
      private static void writeFile(String content,String htmlPath, String fileName){
          File file2 = new File(htmlPath);
          StringBuilder sb = new StringBuilder();
          try {
              file2.createNewFile();//创建文件
   
              sb.append("<html><head><meta http-equiv=\"Content-Type\" charset=\"utf-8\"><title>"+fileName+"</title></head><body>");
              sb.append("<div>");
              sb.append(content);
              sb.append("</div>");
             sb.append("</body></html>");
  
             PrintStream printStream = new PrintStream(new FileOutputStream(file2));
  
             printStream.println(sb.toString());//将字符串写入文件
  
         } catch (IOException e) {
  
             e.printStackTrace();
         }
  
     }

	/**
	 * 程序入口方法
	 * 
	 * @param filePath    文件的路径
	 * @param isWithStyle 是否需要表格样式 包含 字体 颜色 边框 对齐方式
	 * @return
	 *         <table>
	 *         ...
	 *         </table>
	 *         字符串
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws EncryptedDocumentException
	 */
     public String readExcelToHtml(String filePath, boolean isWithStyle) throws EncryptedDocumentException, InvalidFormatException, IOException {
 
         InputStream is = null;
         String htmlExcel = null;
         try {
             File sourcefile = new File(filePath);
             is = new FileInputStream(sourcefile);
             Workbook wb = WorkbookFactory.create(is);
             if (wb instanceof XSSFWorkbook) {
                 XSSFWorkbook xWb = (XSSFWorkbook) wb;
                 htmlExcel = ResourceServiceImpl.getExcelInfo(xWb, isWithStyle);
             } else if (wb instanceof HSSFWorkbook) {
                HSSFWorkbook hWb = (HSSFWorkbook) wb;
                 htmlExcel = ResourceServiceImpl.getExcelInfo(hWb, isWithStyle);
             }
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             try {
                 is.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         return htmlExcel;
     }
 
     public static String getExcelInfo(Workbook wb, boolean isWithStyle) throws IOException {
 
         StringBuffer sb = new StringBuffer();
         for(int i=0;i<wb.getNumberOfSheets();i++) {
               Sheet sheet = wb.getSheetAt(i);// 获取第一个Sheet的内容
               String sheetName = sheet.getSheetName();
               int lastRowNum = sheet.getLastRowNum();
               Map<String, String> map[] = getRowSpanColSpanMap(sheet);
               sb.append("<h3>"+sheetName+"</h3>");
               sb.append("<table style='border-collapse:collapse;' width='100%'>");
            // map等待存储excel图片
               Map<String, PictureData> sheetIndexPicMap = getSheetPictrues(i, sheet, wb);
               Map<String, String> imgMap = new HashMap<String, String>();
               if (sheetIndexPicMap != null) {
                   imgMap = printImg(sheetIndexPicMap);
                   printImpToWb(imgMap, wb);
               }
               Row row = null; // 兼容
               Cell cell = null; // 兼容
               for (int rowNum = sheet.getFirstRowNum(); rowNum <= lastRowNum; rowNum++) {
                   row = sheet.getRow(rowNum);
                   if (row == null) {
                       sb.append("<tr><td > &nbsp;</td></tr>");
                       continue;
                   }
                   sb.append("<tr>");
                   int lastColNum = row.getLastCellNum();
                   for (int colNum = 0; colNum < lastColNum; colNum++) {
                       cell = row.getCell(colNum);
                       if (cell == null) { // 特殊情况 空白的单元格会返回null
                           sb.append("<td>&nbsp;</td>");
                           continue;
                       }
                       String imageHtml = "";
                       String imageRowNum = i + "_" + rowNum + "_" + colNum;
                      if (sheetIndexPicMap != null && sheetIndexPicMap.containsKey(imageRowNum)) {
                           String imagePath = imgMap.get(imageRowNum);
                           imageHtml = "<img src='" + imagePath + "' style='height:auto;'>";
                       }
                       String stringValue = getCellValue(cell);
                       if (map[0].containsKey(rowNum + "," + colNum)) {
                           String pointString = map[0].get(rowNum + "," + colNum);
                          map[0].remove(rowNum + "," + colNum);
                           int bottomeRow = Integer.valueOf(pointString.split(",")[0]);
                          int bottomeCol = Integer.valueOf(pointString.split(",")[1]);
                           int rowSpan = bottomeRow - rowNum + 1;
                           int colSpan = bottomeCol - colNum + 1;
                           sb.append("<td rowspan= '" + rowSpan + "' colspan= '" + colSpan + "' ");
                      } else if (map[1].containsKey(rowNum + "," + colNum)) {
                           map[1].remove(rowNum + "," + colNum);
                           continue;
                       } else {
                           sb.append("<td ");
                       }
                       // 判断是否需要样式
                       if (isWithStyle) {
                           dealExcelStyle(wb, sheet, cell, sb);// 处理单元格样式
                       }
                      sb.append(">");
                       if (sheetIndexPicMap != null && sheetIndexPicMap.containsKey(imageRowNum)) {
                          sb.append(imageHtml);
                      }
                       if (stringValue == null || "".equals(stringValue.trim())) {
                           sb.append(" &nbsp; ");
                       } else {
                           // 将ascii码为160的空格转换为html下的空格（&nbsp;）
                         sb.append(stringValue.replace(String.valueOf((char) 160), "&nbsp;"));
                     }
                       sb.append("</td>");
                   }
                   sb.append("</tr>");
              }

               sb.append("</table>");
         }
         
       
         return sb.toString();
     }

	/**
	 * 获取Excel图片公共方法
	 *
	 * @param sheetNum 当前sheet编号
	 * @param sheet    当前sheet对象
	 * @param workbook 工作簿对象
	 * @return Map key:图片单元格索引（0_1_1）String，value:图片流PictureData
	 */
     public static Map<String, PictureData> getSheetPictrues(int sheetNum, Sheet sheet, Workbook workbook) {
         if (workbook instanceof HSSFWorkbook) {
            return getSheetPictrues03(sheetNum, (HSSFSheet) sheet, (HSSFWorkbook) workbook);
        } else if (workbook instanceof XSSFWorkbook) {
             return getSheetPictrues07(sheetNum, (XSSFSheet) sheet, (XSSFWorkbook) workbook);
         } else {
             return null;
         }
     }

	/**
	 * 获取Excel2003图片
	 *
	 * @param sheetNum 当前sheet编号
	 * @param sheet    当前sheet对象
	 * @param workbook 工作簿对象
	 * @return Map key:图片单元格索引（0_1_1）String，value:图片流PictureData
	 * @throws IOException
	 */
    private static Map<String, PictureData> getSheetPictrues03(int sheetNum,
                                                                HSSFSheet sheet, HSSFWorkbook workbook) {
         Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();
         List<HSSFPictureData> pictures = workbook.getAllPictures();
         if (pictures.size() != 0) {
             for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) {
                 HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
                 shape.getLineWidth();
                 if (shape instanceof HSSFPicture) {
                     HSSFPicture pic = (HSSFPicture) shape;
                     int pictureIndex = pic.getPictureIndex() - 1;
                     HSSFPictureData picData = pictures.get(pictureIndex);
                     String picIndex = String.valueOf(sheetNum) + "_"
                             + String.valueOf(anchor.getRow1()) + "_"
                             + String.valueOf(anchor.getCol1());
                     sheetIndexPicMap.put(picIndex, picData);
                 }
             }
             return sheetIndexPicMap;
         } else {
             return null;
        }
     }

	/**
	 * 获取Excel2007图片
	 *
	 * @param sheetNum 当前sheet编号
	 * @param sheet    当前sheet对象
	 * @param workbook 工作簿对象
	 * @return Map key:图片单元格索引（0_1_1）String，value:图片流PictureData
	 */
    private static Map<String, PictureData> getSheetPictrues07(int sheetNum,
                                                                XSSFSheet sheet, XSSFWorkbook workbook) {
         Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();
         for (POIXMLDocumentPart dr : sheet.getRelations()) {
             if (dr instanceof XSSFDrawing) {
                 XSSFDrawing drawing = (XSSFDrawing) dr;
                List<XSSFShape> shapes = drawing.getShapes();
                 for (XSSFShape shape : shapes) {
                     XSSFPicture pic = (XSSFPicture) shape;
                     XSSFClientAnchor anchor = pic.getPreferredSize();
                     CTMarker ctMarker = anchor.getFrom();
                     String picIndex = String.valueOf(sheetNum) + "_"
                             + ctMarker.getRow() + "_"
                             + ctMarker.getCol();
                     sheetIndexPicMap.put(picIndex, pic.getPictureData());
                 }
             }
         }
         return sheetIndexPicMap;
     }

	/**
	 * 对图片单元格赋值使其可读取到
	 * <p>
	 * add by CJ 2018年5月21日
	 * </p>
	 *
	 * @param imgMap
	 * @param wb
	 */
     @SuppressWarnings("unused")
     private static void printImpToWb(Map<String, String> imgMap, Workbook wb) {
         Sheet sheet = null;
         Row row = null;
         String[] sheetRowCol = new String[3];
         for (String key : imgMap.keySet()) {
             sheetRowCol = key.split("_");
             sheet = wb.getSheetAt(Integer.parseInt(sheetRowCol[0]));
             row = sheet.getRow(Integer.parseInt(sheetRowCol[1])) == null ? sheet.createRow(Integer.parseInt(sheetRowCol[1])) :
                     sheet.getRow(Integer.parseInt(sheetRowCol[1]));
             Cell cell = row.getCell(Integer.parseInt(sheetRowCol[2])) == null ? row.createCell(Integer.parseInt(sheetRowCol[2])) :
                     row.getCell(Integer.parseInt(sheetRowCol[2]));
         }
     }

     public static Map<String, String> printImg(Map<String, PictureData> map) throws IOException {
         Map<String, String> imgMap = new HashMap<String, String>();
         String imgName = null;
         try {
             Object key[] = map.keySet().toArray();
             for (int i = 0; i < map.size(); i++) {
                 // 获取图片流
                PictureData pic = map.get(key[i]);
                 // 获取图片索引
                 String picName = key[i].toString();
                 // 获取图片格式
                 String ext = pic.suggestFileExtension();
                 byte[] data = pic.getData();
                 File uploadFile = new File(UPLOAD_FILE);
                 if (!uploadFile.exists()) {
                     uploadFile.mkdirs();
                 }
                 imgName = picName + "_" + new Date().getTime() + "." + ext;
                 FileOutputStream out = new FileOutputStream(UPLOAD_FILE + imgName);
                 imgMap.put(picName, UPLOAD_FILE + imgName);
                 out.write(data);
                 out.flush();
                 out.close();
             }
         } catch (Exception e) {
         }
         return imgMap;
     }

     @SuppressWarnings("unchecked")
     private static Map<String, String>[] getRowSpanColSpanMap(Sheet sheet) {
         Map<String, String> map0 = new HashMap<String, String>();
         Map<String, String> map1 = new HashMap<String, String>();
         int mergedNum = sheet.getNumMergedRegions();
         CellRangeAddress range = null;
         for (int i = 0; i < mergedNum; i++) {
             range = sheet.getMergedRegion(i);
             int topRow = range.getFirstRow();
             int topCol = range.getFirstColumn();
             int bottomRow = range.getLastRow();
             int bottomCol = range.getLastColumn();
             map0.put(topRow + "," + topCol, bottomRow + "," + bottomCol);
             // System.out.println(topRow + "," + topCol + "," + bottomRow + ","
             // + bottomCol);
             int tempRow = topRow;
             while (tempRow <= bottomRow) {
                 int tempCol = topCol;
                 while (tempCol <= bottomCol) {
                     map1.put(tempRow + "," + tempCol, "");
                     tempCol++;
                 }
                 tempRow++;
             }
             map1.remove(topRow + "," + topCol);
         }
         
         @SuppressWarnings("rawtypes")
         Map[] map = { map0, map1 };
         return map;
     }
 
	/**
	 * 获取表格单元格Cell内容
	 * 
	 * @param cell
	 * @return
	 */
     private static String getCellValue(Cell cell) {
         String result = new String();
         switch (cell.getCellType()) {
         case Cell.CELL_TYPE_NUMERIC:// 数字类型
             if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                 SimpleDateFormat sdf = null;
                 if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
                     sdf = new SimpleDateFormat("HH:mm");
                 } else {// 日期
                     sdf = new SimpleDateFormat("yyyy-MM-dd");
                 }
                 Date date = cell.getDateCellValue();
                result = sdf.format(date);
             } else if (cell.getCellStyle().getDataFormat() == 58) {
                 // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                 double value = cell.getNumericCellValue();
                 Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                result = sdf.format(date);
             } else {
                 double value = cell.getNumericCellValue();
                 CellStyle style = cell.getCellStyle();
                 DecimalFormat format = new DecimalFormat();
                 String temp = style.getDataFormatString();
                 // 单元格设置成常规
                 if (temp.equals("General")) {
                     format.applyPattern("#");
                 }
                 result = format.format(value);
             }
             break;
         case Cell.CELL_TYPE_STRING:// String类型
             result = cell.getRichStringCellValue().toString();
             break;
         case Cell.CELL_TYPE_BLANK:
             result = "";
            break;
         default:
             result = "";
            break;
         }
         return result;
     }
 
	/**
	 * 处理表格样式
	 * 
	 * @param wb
	 * @param sheet
	 * @param cell
	 * @param sb
	 */
     private static void dealExcelStyle(Workbook wb, Sheet sheet, Cell cell, StringBuffer sb) {
 
         CellStyle cellStyle = cell.getCellStyle();
         if (cellStyle != null) {
             short alignment = cellStyle.getAlignment();
             sb.append("align='" + convertAlignToHtml(alignment) + "' ");// 单元格内容的水平对齐方式
             short verticalAlignment = cellStyle.getVerticalAlignment();
             sb.append("valign='" + convertVerticalAlignToHtml(verticalAlignment) + "' ");// 单元格中内容的垂直排列方式
             if (wb instanceof XSSFWorkbook) {
                 XSSFFont xf = ((XSSFCellStyle) cellStyle).getFont();
                short boldWeight = xf.getBoldweight();
                 sb.append("style='");
                 sb.append("font-weight:" + boldWeight + ";"); // 字体加粗
                 sb.append("font-size: " + xf.getFontHeight() / 2 + "%;"); // 字体大小
                 int columnWidth = sheet.getColumnWidth(cell.getColumnIndex());
                 sb.append("width:" + columnWidth + "px;");
                 XSSFColor xc = xf.getXSSFColor();
                 if (xc != null && !"".equals(xc)) {
                     String string = xc.getARGBHex();
                     if(string!=null&& !"".equals(string)) {
                         sb.append("color:#" + string.substring(2) + ";"); // 字体颜色
                     }
                 }
 
                 XSSFColor bgColor = (XSSFColor) cellStyle.getFillForegroundColorColor();
                 if (bgColor != null && !"".equals(bgColor) && bgColor!=null) {
                    String argbHex = bgColor.getARGBHex();
                    if(argbHex!=null && !"".equals(argbHex)) {
                         sb.append("background-color:#" + argbHex.substring(2) + ";"); // 背景颜色
                    }
                 }
                 sb.append(getBorderStyle(0, cellStyle.getBorderTop(),
                        ((XSSFCellStyle) cellStyle).getTopBorderXSSFColor()));
                sb.append(getBorderStyle(1, cellStyle.getBorderRight(),
                         ((XSSFCellStyle) cellStyle).getRightBorderXSSFColor()));
                 sb.append(getBorderStyle(2, cellStyle.getBorderBottom(),
                         ((XSSFCellStyle) cellStyle).getBottomBorderXSSFColor()));
                 sb.append(getBorderStyle(3, cellStyle.getBorderLeft(),
                         ((XSSFCellStyle) cellStyle).getLeftBorderXSSFColor()));

             } else if (wb instanceof HSSFWorkbook) {
 
                HSSFFont hf = ((HSSFCellStyle) cellStyle).getFont(wb);
                 short boldWeight = hf.getBoldweight();
                 short fontColor = hf.getColor();
                 sb.append("style='");
                 HSSFPalette palette = ((HSSFWorkbook) wb).getCustomPalette(); // 类HSSFPalette用于求的颜色的国际标准形式
                 HSSFColor hc = palette.getColor(fontColor);
                 sb.append("font-weight:" + boldWeight + ";"); // 字体加粗
                 sb.append("font-size: " + hf.getFontHeight() / 2 + "%;"); // 字体大小
                 String fontColorStr = convertToStardColor(hc);
                 if (fontColorStr != null && !"".equals(fontColorStr.trim())) {
                     sb.append("color:" + fontColorStr + ";"); // 字体颜色
                 }
                 int columnWidth = sheet.getColumnWidth(cell.getColumnIndex());
                 sb.append("width:" + columnWidth + "px;");
                 short bgColor = cellStyle.getFillForegroundColor();
                 hc = palette.getColor(bgColor);
                 String bgColorStr = convertToStardColor(hc);
                 if (bgColorStr != null && !"".equals(bgColorStr.trim())) {
                     sb.append("background-color:" + bgColorStr + ";"); // 背景颜色
                 }
                 sb.append(getBorderStyle(palette, 0, cellStyle.getBorderTop(), cellStyle.getTopBorderColor()));
                 sb.append(getBorderStyle(palette, 1, cellStyle.getBorderRight(), cellStyle.getRightBorderColor()));
                sb.append(getBorderStyle(palette, 3, cellStyle.getBorderLeft(), cellStyle.getLeftBorderColor()));
                 sb.append(getBorderStyle(palette, 2, cellStyle.getBorderBottom(), cellStyle.getBottomBorderColor()));
             }

             sb.append("' ");         }
     }

	/**
	 * 单元格内容的水平对齐方式
	 * 
	 * @param alignment
	 * @return
	 */
     private static String convertAlignToHtml(short alignment) {
 
         String align = "left";
         switch (alignment) {
         case CellStyle.ALIGN_LEFT:
             align = "left";
             break;
         case CellStyle.ALIGN_CENTER:
             align = "center";
            break;
        case CellStyle.ALIGN_RIGHT:
             align = "right";
             break;
         default:
             break;
        }
         return align;
     }
 
	/**
      * 单元格中内容的垂直排列方式
      * 
      * @param verticalAlignment
      * @return
      */
     private static String convertVerticalAlignToHtml(short verticalAlignment) {
 
         String valign = "middle";
         switch (verticalAlignment) {
         case CellStyle.VERTICAL_BOTTOM:
             valign = "bottom";
             break;
         case CellStyle.VERTICAL_CENTER:
            valign = "center";
             break;
         case CellStyle.VERTICAL_TOP:
             valign = "top";
             break;
         default:
             break;
         }
         return valign;
     }
 
     private static String convertToStardColor(HSSFColor hc) {
 
         StringBuffer sb = new StringBuffer("");
         if (hc != null) {
             if (HSSFColor.AUTOMATIC.index == hc.getIndex()) {
                 return null;
             }
             sb.append("#");
             for (int i = 0; i < hc.getTriplet().length; i++) {
                 sb.append(fillWithZero(Integer.toHexString(hc.getTriplet()[i])));
             }
         }
 
         return sb.toString();
     }
 
     private static String fillWithZero(String str) {
         if (str != null && str.length() < 2) {
             return "0" + str;
         }
         return str;
     }
 
     static String[] bordesr = { "border-top:", "border-right:", "border-bottom:", "border-left:" };
     static String[] borderStyles = { "solid ", "solid ", "solid ", "solid ", "solid ", "solid ", "solid ", "solid ",
             "solid ", "solid", "solid", "solid", "solid", "solid" };
 
     private static String getBorderStyle(HSSFPalette palette, int b, short s, short t) {
         if (s == 0)
             return bordesr[b] + borderStyles[s] + "#d0d7e5 1px;";
         String borderColorStr = convertToStardColor(palette.getColor(t));
         borderColorStr = borderColorStr == null || borderColorStr.length() < 1 ? "#000000" : borderColorStr;
         return bordesr[b] + borderStyles[s] + borderColorStr + " 1px;";
 
     }
 
     private static String getBorderStyle(int b, short s, XSSFColor xc) {

         if (s == 0)
             return bordesr[b] + borderStyles[s] + "#d0d7e5 1px;";
         if (xc != null && !"".equals(xc)) {
            String borderColorStr = xc.getARGBHex();// t.getARGBHex();
             borderColorStr = borderColorStr == null || borderColorStr.length() < 1 ? "#000000"
                    : borderColorStr.substring(2);
             return bordesr[b] + borderStyles[s] + borderColorStr + " 1px;";
         }
 
         return "";
     }
 

		public static void getDataFromExcel(String filePath) throws IOException{
			//判断是否为excel类型文件
			if(!filePath.endsWith(".xls")&&!filePath.endsWith(".xlsx")){
				System.out.println("文件不是excel类型");
			}
			
			FileInputStream fis =null;
			Workbook wookbook = null;
			Sheet sheet =null;
			//获取一个绝对地址的流
			fis = new FileInputStream(filePath);
			
			try {
				//2003版本的excel，用.xls结尾
				wookbook = new HSSFWorkbook(fis);
		
			} catch (Exception ex) {
				//ex.printStackTrace();
				try {
					//2007版本的excel，用.xlsx结尾
					fis = new FileInputStream(filePath);
					wookbook = new XSSFWorkbook(fis);//得到工作簿
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Map<String, PictureData> maplist=null;

			sheet = wookbook.getSheetAt(0);
			// 判断用07还是03的方法获取图片  
			if (filePath.endsWith(".xls")) {
				maplist = getPictures1((HSSFSheet) sheet);
			} else if(filePath.endsWith(".xlsx")){
				maplist = getPictures2((XSSFSheet) sheet);
			}
			try {
				printImg(maplist);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				} 
		}


	/**
	 * 获取图片和位置 (xls) 
	 * @param sheet           
	 * @throws IOException       
	 */
	public static Map<String, PictureData> getPictures1(HSSFSheet sheet) throws IOException {
		Map<String, PictureData> map = new HashMap<String, PictureData>();
		List<HSSFShape> list = sheet.getDrawingPatriarch().getChildren();
		for (HSSFShape shape : list) {
			if (shape instanceof HSSFPicture) {
				HSSFPicture picture = (HSSFPicture) shape;
				HSSFClientAnchor cAnchor = (HSSFClientAnchor) picture.getAnchor();
				PictureData pdata = picture.getPictureData();
				String key = cAnchor.getRow1() + "-" + cAnchor.getCol1(); // 行号-列号
				map.put(key, pdata);
			}
		}
		return map;
	}

	/**
		 *        * 获取图片和位置 (xlsx)        * @param sheet        * @return       
		 * * @throws IOException       
		 */
	public static Map<String, PictureData> getPictures2 (XSSFSheet sheet) throws IOException {
		Map<String, PictureData> map = new HashMap<String, PictureData>();
		List<POIXMLDocumentPart> list = sheet.getRelations();
		for (POIXMLDocumentPart part : list) {
			if (part instanceof XSSFDrawing) {
				XSSFDrawing drawing = (XSSFDrawing) part;
				List<XSSFShape> shapes = drawing.getShapes();
				for (XSSFShape shape : shapes) {
					XSSFPicture picture = (XSSFPicture) shape;
					XSSFClientAnchor anchor = picture.getPreferredSize();
					CTMarker marker = anchor.getFrom();
					String key = marker.getRow() + "-" + marker.getCol();
						map.put(key, picture.getPictureData());
					}
				}
			}
			return map;
	}

	// 图片写出
//	public static void printImg(Map<String, PictureData> sheetList) throws IOException {
//	//for (Map<String, PictureData> map : sheetList) {
//			Object key[] = sheetList.keySet().toArray();
//			for (int i = 0; i < sheetList.size(); i++) {
//				// 获取图片流  
//				PictureData pic = sheetList.get(key[i]);
//				// 获取图片索引  
//				String picName = key[i].toString();
//				// 获取图片格式  
//				String ext = pic.suggestFileExtension();
//				byte[] data = pic.getData();
//				//图片保存路径 
//				FileOutputStream out = new FileOutputStream("F:\\" + picName + "." + ext);
//				out.write(data);
//				out.close();
//			}
//			//} 
//
//		}


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
