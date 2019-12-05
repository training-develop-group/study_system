package com.example.study_system.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
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
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
//				office转pdf
//				officeOpenPDF(desFilePath, desPath);
				logger.info("成功进入方法");
//			try {
//				testExcel();
//			} catch (FileNotFoundException e2) {
//				e2.printStackTrace();
//			}

			/*
			 * try {
			 * 	genHtml("F:/07.xls", "F:/07.html"); 
			 * } catch (ParserConfigurationException e1) {
			 * 	e1.printStackTrace();
			 * } catch (IOException e1) {
			 * 	block e1.printStackTrace();
			 * } catch (TransformerException e1) {
			 * 	block e1.printStackTrace();
			 * }
			 */

//			try {
//				getDataFromExcel("F:/05.xls");
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}

//			try {
//				excelToHtml("F:/02.xlsx", "F:/02.html");
//			} catch (EncryptedDocumentException e) {
//				e.printStackTrace();
//			} catch (InvalidFormatException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//				if (extName.equals(".doc") || extName.equals(".docx")) {
//					try {
//						docxToHtml(desFilePath, uuid);	
//					} catch (Exception e) {
//						e.printStackTrace();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
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
//			commands.add("-t");
//			commands.add("0.001");
			commands.add("-s");
			// 设置截取图片大小
			commands.add("700x525");
			// 截取视频的文件名再加上jpg
			commands.add(video_path.substring(0, video_path.lastIndexOf(".")) + ".jpg");
			try {
				ProcessBuilder builder = new ProcessBuilder();
				builder.command(commands);
				// 执行命令并返回一个Process对象，用于获取对执行程序的输入和输出
				builder.start();
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
			 // 调用cmd命令
			p = Runtime.getRuntime().exec(command);
		} catch (IOException e1) {
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
		convert.setOutputColumnHeaders(false);
		// 行号不显示
		convert.setOutputRowNumbers(false);
		// 创建POI工作薄对象
		InputStream input = new FileInputStream(excel);
		HSSFWorkbook wb = new HSSFWorkbook(input);
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(1);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) (font.getFontHeightInPoints() / 4 * 3)); // 设置字体大小
		System.err.println("字体大小：" + font.getFontHeightInPoints());
		Map
    <String, Long> maxWidthMap = new HashMap
        <String, Long>();
		int length = 0;
		System.err.println("总行数" + sheet.getLastRowNum());

//		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
//			length = sheet.getRow(i).getCell(0).getStringCellValue().getBytes().length;
//			sheet.getRow(i).getCell(0).setCellType(CellType.STRING);
//			if (length == 0) {
//				continue;
//			}
//			
//			String cellStr = sheet.getRow(i).getCell(0).getStringCellValue();
//			System.err.println(cellStr);
//			System.err.println(length);
//		}
		if (ResourceServiceImpl.isMergedRegion(sheet, 1, 1)) {
			System.err.println("是合并单元格");
		} else {
			System.err.println("不是合并单元格");
		}
//		sheet.setColumnWidth(i, columnWidth * 256);
//		for (int i = 1; i <= row.getPhysicalNumberOfCells(); i++) {
//			for (int j = 1; j <= sheet.getLastRowNum(); j++) {
////				sheet.getRow(j).getCell(i).setCellType(CellType.STRING);
//				HSSFCell cell2 = sheet.getRow(j).getCell(i);
//				length = sheet.getRow(j).getCell(i).getStringCellValue().getBytes().length;
//				maxWidthMap.put(j+","+i, Math.max(length, maxWidthMap.get(j) == null ? -1 : maxWidthMap.get(j)));
//			}
//		}
//		System.err.println(maxWidthMap);
//		for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
//			if (ResourceServiceImpl.isMergedRegion(sheet, row.getRowNum(), 1)) {
//				continue;
//			}
////			sheet.autoSizeColumn(i);//只支持英文和数字
//			int columnWidth = sheet.getColumnWidth(i) / 256;
//			System.err.println(columnWidth);
//			for (int j = 0; j < sheet.getLastRowNum(); j++) {
//				HSSFCell cell = sheet.getRow(j).getCell(i);//如果有合并单元格，就会有getCell()==null的情况，需要createCell();
//				if (cell == null) {
//					cell = sheet.getRow(j).createCell(i);
//				}
//				int intlength = cell.toString().getBytes("GBK").length;
//				if (columnWidth < intlength + 1) {  
//					columnWidth = intlength + 1;  
//				}
//			}
//			sheet.setColumnWidth(i, columnWidth * 256); //对中文列调整列宽
//		}
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
		}
		// 生成HTML文件
		File file = new File(html);
		String htmlFolder = file.getParent();
		String htmlName = file.getName();
		FileUtils.writeStringToFile(new File(htmlFolder, htmlName), content, "utf-8");
	}

	public static boolean isMergedRegion(HSSFSheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
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
		Map
        <String, HSSFPictureData> pics = getPictrues(wb);
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
		for (Map.Entry
            <String, HSSFPictureData> entry : pics.entrySet()) {
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
			System.err.println(entry.getKey());
			// 图片的上边距和左边距
			float top = getTop(wb, Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[3]));
			System.err.println("getImgHtml: arr[0]:" + Integer.parseInt(arr[0]) + ", arr[1]:" + Integer.parseInt(arr[1])
					+ ", arr[2]:" + Integer.parseInt(arr[3]));
			System.err.println(top); // 两次输出的top值与实际表格位置差距太大
			float left = getLeft(wb.getSheetAt(Integer.parseInt(arr[0])), Integer.parseInt(arr[2]),
					Integer.parseInt(arr[4]));
			// margin设置为8，以保持和body的内边距一致
			String htmlImg = "";
			htmlImg += "<image src=\"" + htmlName.substring(0, htmlName.lastIndexOf(".")) + "/" + imgName + "\" "
					+ "style=\"position: absolute; margin: 8; left: " + left + "; top: " + top + "pt;\" />";
			htmlImg += "";
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
//	public String getImgHtml_Base64(HSSFWorkbook wb, String html) throws IOException {
//		// 获取Excel所有的图片
//		Map<String, HSSFPictureData> pics = getPictrues(wb);
//		if (pics == null || pics.size() == 0) {
//			return "";
//		}
//		StringBuilder sb = new StringBuilder();
//		for (Map.Entry<String, HSSFPictureData> entry : pics.entrySet()) {
//			HSSFPictureData pic = entry.getValue();
//			byte[] data = pic.getData();
//			String baseStr = Base64.getEncoder().encodeToString(data);
//			// 图片在Excel中的坐标，sheet索引_行号_列号_单元格内的上边距_单元格内的左边距_uuid
//			String[] arr = StringUtils.split(entry.getKey(), "_");
//			float top = getTop(wb, Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[3]));
//			System.err.println("getImgHtml_Base64: arr[0]" + Integer.parseInt(arr[0]) + "arr[1]" + Integer.parseInt(arr[1]) + "arr[2]" +Integer.parseInt(arr[3]));
//
//			float left = getLeft(wb.getSheetAt(Integer.parseInt(arr[0])), Integer.parseInt(arr[2]),
//					Integer.parseInt(arr[4]));
//			// margin设置为8，以保持和body的内边距一致
//			String htmlImg = "<table style=\"position: absolute; margin:8; left: " + left + "; top: " + top
//					+ "pt;\">\n<tbody>\n<tr>\n<td>\n";
//			htmlImg += "<image src=\"data:image/png;base64," + baseStr + "\" />";
//			htmlImg += "\n</td>\n</tr>\n</tbody>\n</table>\n";
//			sb.append(htmlImg);
//		}
//		return sb.toString();
//	}

	/**
	 * Excel的图片获取
	 * 
	 * @param wb Excel的工作簿
	 * @return Excel的图片，键格式：sheet索引_行号_列号_单元格内的上边距_单元格内的左边距_uuid
	 */
	public Map
                        <String, HSSFPictureData> getPictrues(HSSFWorkbook wb) {
		Map
                            <String, HSSFPictureData> map = new HashMap
                                <String, HSSFPictureData>();
		// getAllPictures方法只能获取不同的图片，如果Excel中存在相同的图片，只能得到一张图片
		List
                                    <HSSFPictureData> pics = wb.getAllPictures();
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
		System.err.println("索引对象" + sheet);
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
		int index = 0;
		// SUM(上方每个单元格的高度)
		for (int i = 0; i < rowIndex; i++) {
			HSSFRow row = sheet.getRow(i);
			// 排除空行(HTML转化时也被排除了)
			if (row == null) {
//				top += 13.5;
				continue;
			}
			top += row.getHeightInPoints();
			System.err.println("第" + (i + 1) + "的行高：" + row.getHeightInPoints());
			index++;
		}
		// 单元格内的上边距单位转化为pt
		if (dy != 0) {
			top += dy / 20.00f;
		}
		System.err.println("每次top返回的高：" + top);
		System.err.println("行数" + index);
		return top + index * 3 + index;
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
		for (int i = 0; i < colIndex + 1; i++) {
			float width = sheet.getColumnWidthInPixels(i);
			left += width;
			System.err.println("第" + i + "列:：  宽：" + width + "左：" + left);
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
