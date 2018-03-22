package com.abount.cg.commonlibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by mo_yu on 18/2/22.文件工具类
 */
public class FileUtil {

    private static SimpleDateFormat format;
    private static final String chatPathName = "chat";
    private static final String imagePathName = "image";
    private static final String recordsPathName = "records";
    private static final String videoPathName = "video";
    private static final String voicePathName = "voice";
    private static final String cropPathName = "crop";
    private static final String themePathName = "theme";
    private static final String pagePathName = "page";
    private static final String fontPathName = "font";
    private static final String tempPathName = "temp";
    private static final String policyPathName = "policy";

    public static final String FILE_NAME_CALENDAR_SHARE = "calendar_share.png";
    public static final String FILE_NAME_WEDDING_DATE_SHARE = "wedding_date_share.png";

    private static File getAlbumDir() {
        File dir = new File(Environment.getExternalStorageDirectory(), getAlbumName());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private static File getVideoAlumDir() {
        // storage/emulated/0/DCIM目录 如果没有这个目录，则取storage/emulated/0
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment
                .DIRECTORY_DCIM)
                .getAbsolutePath(), "Camera");
        if (!dir.exists()) {
            // storage/emulated/0/hunliji/video
            dir = new File(getAlbumDir(), videoPathName);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        return dir;
    }

    public static File getPolicyDir(Context context) {
        File dir = new File(context.getExternalCacheDir(), policyPathName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private static File getAlbumDir(Context context) {
        if (context == null) {
            return getAlbumDir();
        }
        File dir = context.getFilesDir();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private static File getCropAlbumDir() {
        File dir = new File(getAlbumDir(), cropPathName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private static File getChatAlbumDir(Context context) {
        File dir = new File(getAlbumDir(context), chatPathName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private static File getUserChatAlbumDir(Context context, String user) {
        File dir = new File(getChatAlbumDir(context), user);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private static File getImageAlbumDir() {
        File dir = new File(getAlbumDir(), imagePathName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static File getVideoAlbumDir() {
        File dir = getVideoAlumDir();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private static File getVoiceAlbumDir(Context context, String user) {
        File dir = new File(getUserChatAlbumDir(context, user), voicePathName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private static File getRecordsAlbumDir(Context context) {
        File dir = new File(getAlbumDir(context), recordsPathName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private static File getThemeAlbumDir(Context context) {
        File dir = new File(getAlbumDir(context), themePathName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private static File getPageAlbumDir(Context context) {
        File dir = new File(getAlbumDir(context), pagePathName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private static File getFontAlbumDir(Context context) {
        File dir = new File(getAlbumDir(context), fontPathName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private static String getAlbumName() {
        return "hunliji";
    }

    public static File createImageFile() {
        if (format == null) {
            format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        }
        String timeStamp = format.format(new Date());
        String imageFileName = timeStamp + ".jpg";

        return new File(getImageAlbumDir(), imageFileName);
    }

    public static File createImageFile(String path) {
        String fileName = path;
        try {
            fileName = new URL(path).getPath();
        } catch (MalformedURLException ignored) {
            fileName = CommonUtil.getMD5(fileName);
        }
        if(!fileName.contains(".")){
            fileName=fileName + ".png";
        }
        return new File(getImageAlbumDir(), fileName);
    }

    public static File createImagePngFile() {
        if (format == null) {
            format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        }
        String timeStamp = format.format(new Date());
        String imageFileName = timeStamp + ".png";
        return new File(getImageAlbumDir(), imageFileName);
    }

    public static File createImagePngFile(String imageFileName) {
        return new File(getImageAlbumDir(), imageFileName);
    }

    public static File createTempPngFile() {
        if (format == null) {
            format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        }
        String timeStamp = format.format(new Date());
        String imageFileName = timeStamp + ".png";
        return new File(getImageAlbumDir(), imageFileName);
    }


    public static File createCropImageFile() {
        if (format == null) {
            format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        }
        String timeStamp = format.format(new Date());
        String imageFileName = timeStamp + ".jpg";

        return new File(getCropAlbumDir(), imageFileName);
    }

    public static File createVideoFile() {
        if (format == null) {
            format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        }
        String timeStamp = format.format(new Date());
        String videoFileName = timeStamp + ".mp4";

        return new File(getVideoAlbumDir(), videoFileName);
    }


    public static File createSoundFile(Context context) {
        if (format == null) {
            format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        }
        String timeStamp = format.format(new Date());
        String musicFileName = timeStamp + ".mp3";

        return new File(getRecordsAlbumDir(context), musicFileName);
    }


    public static File createUserVoiceFile(Context context, String user) {
        if (format == null) {
            format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        }
        String timeStamp = format.format(new Date());
        String musicFileName = timeStamp + ".amr";

        return new File(getVoiceAlbumDir(context, user), musicFileName);
    }

    public static File getUserVoiceFile(Context context, String user, String path) {
        if (path.startsWith("http://") || path.startsWith("https://")) {
            Uri uri = Uri.parse(path);
            String fileName = removeFileSeparator(uri.getPath());
            return new File(getVoiceAlbumDir(context, user), fileName);

        }
        return new File(path);

    }

    public static File createThemeFile(Context context, String url) {
        String fileName = url;
        try {
            fileName = new URL(url).getFile();
        } catch (MalformedURLException ignored) {
        }
        fileName = CommonUtil.getMD5(fileName);
        return new File(getThemeAlbumDir(context), fileName);
    }

    public static File createPageFile(Context context, String url) {
        return createPageFile(context, url, null);
    }

    public static File createPageFile(Context context, String url, String format) {
        String fileName = url;
        try {
            fileName = new URL(url).getFile();
        } catch (MalformedURLException ignored) {
        }
        fileName = CommonUtil.getMD5(fileName);
        if (!TextUtils.isEmpty(format)) {
            fileName = fileName + "." + format;
        }
        return new File(getPageAlbumDir(context), fileName);
    }

    public static File createFontFile(Context context, String path) {
        String fileName = path;
        try {
            fileName = new URL(path).getFile();
        } catch (MalformedURLException ignored) {
        }
        fileName = CommonUtil.getMD5(fileName);
        return new File(getFontAlbumDir(context), fileName);
    }

    public static void saveStringToFile(String content, OutputStream out) {
        OutputStreamWriter writer = new OutputStreamWriter(out);
        try {
            writer.write(content);
            writer.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveStreamToFile(
            InputStream in, OutputStream out) {
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
            out.flush();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param filePath 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFolder(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile()) {
                // 为文件时调用删除文件方法
                return deleteFile(file);
            } else {
                // 为目录时调用删除目录方法
                return deleteDirectory(filePath);
            }
        }
    }

    public static boolean deleteFile(String path) {
        return !TextUtils.isEmpty(path) && deleteFile(new File(path));
    }

    public static boolean deleteFile(File file) {
        boolean isDeleted = false;
        if (file != null && file.exists()) {
            try {
                isDeleted = file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!isDeleted) {
                file.deleteOnExit();
            }
        }
        return isDeleted;
    }

    /**
     * 删除文件夹以及目录下的文件
     *
     * @param filePath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    private static boolean deleteDirectory(String filePath) {
        boolean flag = false;
        //如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        //遍历删除文件夹下的所有文件(包括子目录)
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                //删除子文件
                flag = deleteFile(files[i]);
                if (!flag)
                    break;
            } else {
                //删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag)
            return false;
        //删除当前空目录
        return dirFile.delete();
    }

    public static String removeFileSeparator(String filePath) {
        if (filePath.startsWith(File.separator)) {
            filePath = filePath.substring(1);
        }
        return filePath.replace(File.separator, "-");
    }

    public static String splitFileSeparator(String filePath) {
        if (filePath.startsWith(File.separator)) {
            filePath = filePath.substring(1);
        }
        if (filePath.contains(File.separator)) {
            String[] strings = filePath.split(File.separator);
            return strings[strings.length - 1];
        }
        return filePath;
    }

    public static String getTemporaryFileName(Context mContext) {
        return mContext.getCacheDir()
                .getAbsolutePath() + File.separator + "temp_record";
    }

    /**
     * 解压缩功能.
     * 将zipFile文件解压到folderPath目录下.
     *
     * @throws Exception
     */
    public static boolean unZipFile(String zipFile, String folderPath) throws IOException {

        ZipFile zfile = new ZipFile(zipFile);
        Enumeration zList = zfile.entries();
        ZipEntry ze = null;
        byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {
            ze = (ZipEntry) zList.nextElement();
            if (ze.isDirectory()) {
                String dirstr = folderPath + ze.getName();
                dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
                File f = new File(dirstr);
                f.mkdir();
                continue;
            }
            OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(
                    folderPath,
                    ze.getName())));
            InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
            int readLen = 0;
            while ((readLen = is.read(buf, 0, 1024)) != -1) {
                os.write(buf, 0, readLen);
            }
            is.close();
            os.close();
        }
        zfile.close();
        return true;
    }

    private static File getRealFileName(String baseDir, String absFileName) {
        String[] dirs = absFileName.split("/");
        File ret = new File(baseDir);
        String substr;
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                substr = dirs[i];
                try {
                    //substr.trim();
                    substr = new String(substr.getBytes("8859_1"), "GB2312");

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                ret = new File(ret, substr);

            }
            //Log.d("upZipFile", "1ret = "+ret);
            if (!ret.exists())
                ret.mkdirs();
            substr = dirs[dirs.length - 1];
            try {
                //substr.trim();
                substr = new String(substr.getBytes("8859_1"), "GB2312");
                //Log.d("upZipFile", "substr = "+substr);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            ret = new File(ret, substr);
            //Log.d("upZipFile", "2ret = "+ret);
            return ret;
        }
        return ret;
    }
    private static int getOrientation(String path) {
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return 90;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return 180;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return 270;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isFileExists(File file) {
        return file != null && file.isFile() && file.exists() && file.length() > 0;
    }

    /**
     * 将bitmap保存到本地sdcard
     *
     * @param bitmap
     */
    public static void saveImageToLocalFile(
            final Context context, final Bitmap bitmap, final Action1<String> finishAction) {
        if (bitmap == null) {
            return;
        }

        try {
            final File imgFile = FileUtil.createImagePngFile();
            if (!imgFile.exists()) {
                imgFile.createNewFile();
            }

            Observable<String> saveObb = Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    try {
                        FileOutputStream stream = new FileOutputStream(imgFile);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        stream.flush();
                        stream.close();
                        subscriber.onNext(imgFile.getAbsolutePath());
                    } catch (Exception e) {
                        Observable.just("")
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(finishAction);
                        e.printStackTrace();
                    }
                }
            });

            Action1<String> notifyAction = new Action1<String>() {
                @Override
                public void call(String path) {
                    //通知相册更新图片
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uri = Uri.fromFile(imgFile);
                    intent.setData(uri);
                    context.sendBroadcast(intent);
                    Toast.makeText(context, "保存成功:" + path, Toast.LENGTH_SHORT)
                            .show();
                    Observable.just(path)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(finishAction);
                }
            };
            saveObb.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(notifyAction);

        } catch (IOException e) {
            e.printStackTrace();
            Observable.just("")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(finishAction);
        }

    }

    public static void saveImageToLocalFileWithOutNotify(
            final Bitmap bitmap, final Action1<String> finishAction) {
        File file = FileUtil.createImagePngFile();
        saveImageToLocalFileWithOutNotify(bitmap, file, finishAction);
    }

    public static void saveImageToLocalFileWithOutNotify(
            final Bitmap bitmap, final File file, final Action1<String> finishAction) {
        if (bitmap == null) {
            return;
        }
        Observable<String> saveObb = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    FileOutputStream stream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    stream.flush();
                    stream.close();
                    subscriber.onNext(file.getAbsolutePath());
                } catch (Exception e) {
                    Observable.just("")
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(finishAction);
                    e.printStackTrace();
                }
            }
        });
        saveObb.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(finishAction);
    }

}
