package com.task.demo.utils

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.*
import android.graphics.Bitmap.CompressFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import android.widget.ImageView
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.task.demo.configuration.App
import com.task.demo.configuration.App.Companion.getInstance
import jp.wasabeef.glide.transformations.BlurTransformation
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

object ImageUtils {
    fun getImageFormat(context: Context, uri: Uri?): CompressFormat {
        val resolver = context.contentResolver
        val mime = MimeTypeMap.getSingleton()
        val type = mime.getExtensionFromMimeType(resolver.getType(uri!!))
        return if (type != null) {
            when (type) {
                "jpeg" -> CompressFormat.JPEG
                "png" -> CompressFormat.WEBP
                "webp" -> CompressFormat.WEBP
                else -> CompressFormat.JPEG
            }
        } else CompressFormat.JPEG
    }

    /**
     * Convert drawable to bitmap
     *
     * @param drawable The drawable
     * @return Bitmap
     */
    fun drawable2Bitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            if (drawable.bitmap != null) {
                return drawable.bitmap
            }
        }
        val bitmap: Bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(
                1, 1,
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
            )
        } else {
            Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
            )
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * Scale bitmap
     *
     * @param src       Bitmap
     * @param newWidth  Width
     * @param newHeight Height
     * @param recycle   Recycle?
     * @return Bitmap
     */
    fun scaleBitmap(src: Bitmap, newWidth: Int, newHeight: Int, recycle: Boolean): Bitmap {
        val ret = Bitmap.createScaledBitmap(src, newWidth, newHeight, true)
        if (recycle && !src.isRecycled) src.recycle()
        return ret
    }

    /**
     * Convert bitmap to drawable
     *
     * @param context The context
     * @param bitmap  The bitmap
     * @return Drawable
     */
    fun bitmap2Drawable(context: Context, bitmap: Bitmap?): Drawable? {
        return if (bitmap == null) null else BitmapDrawable(context.resources, bitmap)
    }

    /**
     * Convert square image to circle
     *
     * @param bitmap Square bitmap
     * @return Circle bitmap
     */
    fun getCircleBitmap(bitmap: Bitmap): Bitmap {
        val output = Bitmap.createBitmap(
            bitmap.width,
            bitmap.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        val color = Color.RED
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawOval(rectF, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        bitmap.recycle()
        return output
    }

    @Throws(IOException::class)
    fun getBitmapFromUri(uri: Uri?): Bitmap {
        val parcelFileDescriptor =
            getInstance().contentResolver.openFileDescriptor(uri!!, "r")
        val fileDescriptor = parcelFileDescriptor?.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor?.close()
        return image
    }

    @Throws(IOException::class)
    fun convertBitmapToFile(bitmap: Bitmap): File {
        val f = File(getInstance().cacheDir, "temp")
        f.createNewFile()
        val bos = ByteArrayOutputStream()
        bitmap.compress(CompressFormat.PNG, 0 /*ignored for PNG*/, bos)
        val bitmapdata = bos.toByteArray()
        val fos = FileOutputStream(f)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()
        return f
    }

    /**
     * Display image
     *
     * @param src       int
     * @param imageView Imageview
     */
    fun displayImage(src: Int, imageView: ImageView) {
        Glide.with(imageView.context).load(src)
            .apply(RequestOptions().centerCrop())
            .into(imageView)
    }

    /**
     * Display image
     *
     * @param src       URI
     * @param imageView Imageview
     */
    fun displayImage(src: Uri?, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(src)
            .apply(RequestOptions().centerCrop())
            .into(imageView)
    }

    /**
     * Display image with rounded corners and without fitFX, center crop
     *
     * @param src       String
     * @param imageView Imageview
     */
    fun displayRoundedImageWithoutCrop(src: String?, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(src)
            .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(32)))
            .into(imageView)
    }

    /**
     * Display image with rounded corners and  without fitFX, center crop
     *
     * @param src       Uri
     * @param imageView Imageview
     */
    fun displayRoundedImageWithoutCrop(src: Uri?, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(src)
            .apply(RequestOptions().transform(RoundedCorners(32)))
            .into(imageView)
    }

    /**
     * Display image with rounded corners and  without fitFX, center crop
     *
     * @param src       String
     * @param imageView Imageview
     */
    fun displayRoundedImageWithoutCrop(src: String?, imageView: ImageView, radius: Int) {
        Glide.with(imageView.context)
            .load(src)
            .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(radius)))
            .into(imageView)
    }

    /**
     * Display image with rounded corners and  without fitFX, center crop
     *
     * @param src       int
     * @param imageView Imageview
     */
    fun displayRoundedImageWithoutCrop(src: Int, imageView: ImageView, radius: Int) {
        Glide.with(imageView.context)
            .load(src)
            .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(radius)))
            .into(imageView)
    }

    /**
     * Display image without fitFX, center crop
     *
     * @param src       String
     * @param imageView Imageview
     */
    fun displayImageWithoutCrop(src: String?, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(src)
            .error(android.R.drawable.ic_menu_gallery)
            .into(imageView)
    }

    /**
     * Display image without fitFX, center crop
     *
     * @param src       int
     * @param imageView Imageview
     */
    fun displayImageWithoutCrop(src: Int, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(src)
            .into(imageView)
    }

    /**
     * Display image without fitFX, center crop
     *
     * @param src       String
     * @param imageView Imageview
     */
    fun displayImageWithoutCrop(src: Uri?, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(src)
            .into(imageView)
    }

    /**
     * Display image without fitFX, center crop
     *
     * @param file       String
     * @param imageView Imageview
     */
    fun displayImageWithoutCrop(file: File, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(file)
            .into(imageView)
    }
    /**
     * Display image without fit
     *
     * @param src       String
     * @param imageView Imageview
     */
    fun displayImageWithoutFitWithCallback(
        src: String?, imageView: ImageView,
        callback: ImageUtilsCallback
    ) {
        Glide.with(imageView.context)
            .load(src)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    callback.onImageLoaded()
                    return false
                }
            })
            .into(imageView)
    }

    /**
     * Display image with rounded corners
     *
     * @param src       int
     * @param imageView Imageview
     */
    fun displayRoundedImage(src: Int, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(src)
            .apply(
                RequestOptions()
                    .transform(MultiTransformation(CenterCrop(), RoundedCorners(32)))
            )
            .into(imageView)
    }

    fun displayRoundedImage(src: Int, imageView: ImageView, radius: Int) {
        Glide.with(imageView.context)
            .load(src)
            .apply(
                RequestOptions()
                    .transform(MultiTransformation(CenterCrop(), RoundedCorners(radius)))
            )
            .into(imageView)
    }

    fun displayRoundedImage(src: String?, imageView: ImageView, radius: Int) {
        Glide.with(imageView.context)
            .load(src)
            .apply(
                RequestOptions().transform(
                    MultiTransformation(CenterCrop(),RoundedCorners(radius))
                )
            )
            .into(imageView)
    }

    /**
     * Display image with rounded corners
     *
     * @param src       String
     * @param imageView Imageview
     */
    fun displayRoundedImage(src: String?,imageView: ImageView) {
        Glide.with(imageView.context)
            .load(src)
            .apply(
                RequestOptions()
                    .transform(MultiTransformation(CenterCrop(), RoundedCorners(32)))
            )
            .into(imageView)
    }

    /**
     * Display image with a blur
     *
     * @param src       String
     * @param imageView Imageview
     */
    fun displayBlurredRoundedImage(src: String?,imageView: ImageView) {
        Glide.with(imageView.context)
            .load(src)
            .apply(
                RequestOptions()
                    .transform(
                        MultiTransformation(
                            RoundedCorners(32),
                            BlurTransformation(25, 2), CenterCrop()
                        )
                    )
            )
            .into(imageView)
    }

    fun getLocalBitmapUri(context: Context,imageUrl: String?): Uri {
        var bmp: Bitmap? = null
        try {
            val url = URL(imageUrl)
            val connection =
                url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            bmp = BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        var bmpUri = Uri.parse(imageUrl)
        if (bmp != null) try {
            val file = File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS
                ), "share_image_" + System.currentTimeMillis() + ".png"
            )
            file.parentFile.mkdirs()
            val out = FileOutputStream(file)
            bmp.compress(CompressFormat.PNG, 90, out)
            out.close()
            bmpUri = FileProvider.getUriForFile(
                context,
                context.applicationContext.packageName + ".fileprovider",
                file
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }

    interface ImageUtilsCallback {
        fun onImageLoaded()
    }

    fun getPath(context: Context, uri: Uri): String? {
        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) { // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(id)
                )
                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(
                    split[1]
                )
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            return getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )
        try {
            cursor = context.contentResolver.query(
                uri!!, projection, selection, selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    fun getFile(context: Context, uri: Uri?): File? {
        if (uri != null) {
            val path = getPath(context, uri)
            if (path != null && isLocal(path)) {
                return File(path)
            }
        }
        return null
    }

    /**
     * @return Whether the URI is a local one.
     */
    private fun isLocal(url: String?): Boolean {
        return url != null && !url.startsWith("http://") && !url.startsWith("https://")
    }

    fun getPathFromInputStreamUri(context: Context, uri: Uri): String? {
        var filePath: String? = null
        uri.authority?.let {
            try {
                context.contentResolver.openInputStream(uri).use {
                    val photoFile: File? = createTemporalFileFrom(it)
                    filePath = photoFile?.path
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return filePath
    }

    @Throws(IOException::class)
    private fun createTemporalFileFrom(inputStream: InputStream?): File? {
        var targetFile: File? = null
        return if (inputStream == null) targetFile
        else {
            var read: Int
            val buffer = ByteArray(8 * 1024)
            targetFile = createTemporalFile()
            FileOutputStream(targetFile).use { out ->
                while (inputStream.read(buffer).also { read = it } != -1) {
                    out.write(buffer, 0, read)
                }
                out.flush()
            }
            targetFile
        }
    }

    private fun createTemporalFile(): File {
        return File(App.getInstance().cacheDir, "${System.currentTimeMillis()}.jpg")
    }
}