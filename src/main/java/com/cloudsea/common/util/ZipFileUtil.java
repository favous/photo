package com.cloudsea.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * @author zhangxiaorong
 *
 *         2014-2-26
 * 
 *         这个ZipOutputStream用的是apache的，它可以设定编码，
 *         java.util.zip的ZipOutputStream只能默认utf-8的编码，而一些工具可能不支持utf-8的解码
 */
public class ZipFileUtil {
	static Log log = LogFactory.getLog(ZipFileUtil.class);

	/**
	 * packagePath 制作的zip文件所在的文件夹的地址 zipFileName 制作的zip文件名 originFileList
	 * 用来制作的源文件File集合
	 */
	public static void makeZipFile(String packagePath, String zipFileName, List<File> originFileList, String encoding) {
		try {
			if (!zipFileName.endsWith(".zip"))
				zipFileName += ".zip";

			File file = new File(packagePath + "\\" + zipFileName);
			if (file.exists())
				return;

			FileOutputStream fout = new FileOutputStream(file);
			ZipOutputStream zipStream = new ZipOutputStream(fout);
			zipStream.setEncoding(encoding);

			for (int i = 0; originFileList != null && i < originFileList.size(); i++) {
				writeToZipFile(originFileList.get(i), zipStream, "");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param originFileList
	 *            源文件
	 * @return 返回一个压缩包的流
	 */
	public static ByteArrayOutputStream makeZipFileStream(List<File> originFileList, String encoding) {
		if (originFileList == null || originFileList.size() == 0)
			return null;

		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ZipOutputStream zipStream = new ZipOutputStream(bout);
		zipStream.setEncoding(encoding);

		try {
			for (int i = 0; i < originFileList.size(); i++) {
				writeToZipFile(originFileList.get(i), zipStream, "");
			}
			zipStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bout;
	}

	/**
	 * 
	 * @param originFileMap
	 *            源文件 String:文件名，File文件对象
	 * @return 返回一个压缩包的流
	 */
	public static ByteArrayOutputStream makeZipFileStream(Map<String, File> originFileMap, String encoding) {
		if (originFileMap == null || originFileMap.size() == 0)
			return null;

		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ZipOutputStream zipStream = new ZipOutputStream(bout);
		zipStream.setEncoding(encoding);

		try {
			for (Entry<String, File> entry : originFileMap.entrySet()) {
				writeToZipFile(entry, zipStream, "");
			}
			zipStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bout;
	}

	/**
	 * 
	 * @param innerPath
	 *            压缩包内的文件路径
	 * @param rightType
	 *            要求的文件类型,如果是文件夹，则不起作用
	 * @return
	 */
	public static List<byte[]> readFromZipFile(String innerPath, String[] rightType) {
		List<byte[]> list = new ArrayList<byte[]>();
		try {
			ZipFile file = new ZipFile(new File(innerPath));
			InputStream is = null;
			Enumeration<? extends ZipEntry> enumer = file.getEntries();

			while (enumer.hasMoreElements()) {
				byte[] bytes = null;
				ZipEntry entry = enumer.nextElement();
				is = file.getInputStream(entry);
				String fileName = entry.getName().toLowerCase();

				if (entry.isDirectory()) {
					bytes = StreamUtil.bufferedReadToByteArray(is);
					list.add(bytes);
				}

				else {
					for (int i = 0; i < rightType.length; i++) {
						if (fileName.endsWith(rightType[i])) {
							bytes = StreamUtil.bufferedReadToByteArray(is);
							list.add(bytes);
						}
					}
				}

				is.close();
			}
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 功能:将一个指定目录压缩
	 * 
	 * @param srcfile
	 *            ：要压缩文件目录
	 * @param zipfile
	 *            ：压缩之后的文件路径
	 */
	public static void zipFiles(String srcPath, File zipFile) {

		File srcdir = new File(srcPath);
		if (!srcdir.exists())
			throw new RuntimeException(srcPath + "不存在！");
		Project prj = new Project();
		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(zipFile);
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(srcdir);
		zip.addFileset(fileSet);
		zip.execute();
	}

	private static void writeToZipFile(File file, ZipOutputStream zipStream, String parentEntryName) throws IOException {

		if (file == null)
			return;

		String entryName = parentEntryName + "\\" + file.getName();
		if (file.isFile()) {
			ZipEntry entry = new ZipEntry(entryName);
			zipStream.putNextEntry(entry);
			InputStream in = StreamUtil.getInputStream(file);
			byte[] bytes = StreamUtil.bufferedReadToByteArray(in);
			zipStream.write(bytes);
		}

		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				writeToZipFile(f, zipStream, entryName);
			}
		}
	}

	/**
	 * @param entry
	 *            String:文件名，File文件对象
	 * @param zipStream
	 *            压缩的文件都写入此流中
	 * @param parentEntryName
	 *            压缩文件内部的上级entry路径
	 * @throws IOException
	 */
	private static void writeToZipFile(Entry<String, File> entry, ZipOutputStream zipStream, String parentEntryName)
			throws IOException {

		if (entry.getValue() == null)
			return;

		String entryName = parentEntryName == null || parentEntryName.trim().equals("") ? entry.getKey()
				: parentEntryName + "\\" + entry.getKey();

		if (entry.getValue().isFile()) {
			ZipEntry zipEntry = new ZipEntry(entryName);
			zipStream.putNextEntry(zipEntry);
			InputStream in = StreamUtil.getInputStream(entry.getValue());
			byte[] bytes = StreamUtil.bufferedReadToByteArray(in);
			zipStream.write(bytes);
		}

		if (entry.getValue().isDirectory()) {
			File[] files = entry.getValue().listFiles();
			for (File f : files) {
				writeToZipFile(f, zipStream, entryName);
			}
		}
	}

	public static void main(String[] s) {

		Map<String, File> originFileList = new HashMap<String, File>();
		originFileList.put("asd.doc", new File("D:/申请书模板/审核模板.rtf"));
		// originFileList.put("add.doc", new File("D:/申请书模板/2审核模板.rtf"));
		// originFileList.put("aa", new File("D:/ruixing"));

		ByteArrayOutputStream bout = ZipFileUtil.makeZipFileStream(originFileList, "gbk");
		StreamUtil.bufferedWriteByteArray(bout.toByteArray(), "c:\\aaaaa.zip");

		List<File> list = new ArrayList<File>();
		list.add(new File("D:/申请书模板/2审核模板.rtf"));
		// list.add(new File("D:/申请书模板/审核模板.rtf"));
		ByteArrayOutputStream bout2 = ZipFileUtil.makeZipFileStream(originFileList, "gbk");
		StreamUtil.bufferedWriteByteArray(bout2.toByteArray(), "c:\\bbbb.zip");

		System.out.println('\u0000');
	}

}
