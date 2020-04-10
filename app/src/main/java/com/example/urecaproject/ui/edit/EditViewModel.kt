package com.example.urecaproject.ui.edit

import android.graphics.Bitmap
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.urecaproject.model.FilterModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.invoke
import org.opencv.core.*
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc

class EditViewModel : ViewModel() {

    private val _bitmap = MutableLiveData<Bitmap>()
    private val _filter = MutableLiveData<FilterModel>()
    private val _visible = MutableLiveData<Int>()
    private val _cut = MutableLiveData<Boolean>()
    private val _finish = MutableLiveData<Boolean>()

    val bitmap : LiveData<Bitmap> = _bitmap
    val filter : LiveData<FilterModel> = _filter
    val visible : LiveData<Int> = _visible
    val cut : LiveData<Boolean> = _cut
    val finish : LiveData<Boolean> = _finish

    init{
        _visible.value = View.VISIBLE
        _cut.value = false
        _finish.value = false
    }

    fun setBitmap (value: Bitmap) {
        _bitmap.value = value
    }

    fun setFilter(value: FilterModel) {
        _filter.value = value
    }

    fun setVisible (value : Int) {
        _visible.value = value
    }

    fun allowCut (value : Boolean) {
        _cut.value = value
    }

    fun finishCut (value : Boolean) {
        _finish.value = value

    }

    suspend fun extractForegroundFromBackground(coordinates: Coordinates, currentPhotoPath: String) : String {
        // TODO: Provide complex object that has both path and extension

        // Matrices that OpenCV will be using internally
        val bgModel = Mat()
        val fgModel = Mat()

        val srcImage = Imgcodecs.imread(currentPhotoPath)
        val iterations = 5

        // Mask image where we specify which areas are background, foreground or probable background/foreground
        val firstMask = Mat()

        val source = Mat(1, 1, CvType.CV_8U, Scalar(Imgproc.GC_PR_FGD.toDouble()))
        val rect = Rect(coordinates.first, coordinates.second)

        // Run the grab cut algorithm with a rectangle (for subsequent iterations with touch-up strokes,
        // flag should be Imgproc.GC_INIT_WITH_MASK)
        Imgproc.grabCut(srcImage, firstMask, rect, bgModel, fgModel, iterations, Imgproc.GC_INIT_WITH_RECT)

        // Create a matrix of 0s and 1s, indicating whether individual pixels are equal
        // or different between "firstMask" and "source" objects
        // Result is stored back to "firstMask"
        Core.compare(firstMask, source, firstMask, Core.CMP_EQ)

        // Create a matrix to represent the foreground, filled with white color
        val foreground = Mat(srcImage.size(), CvType.CV_8UC3, Scalar(255.0, 255.0, 255.0))

        // Copy the foreground matrix to the first mask
        srcImage.copyTo(foreground, firstMask)

        // Create a red color
        val color = Scalar(255.0, 0.0, 0.0, 255.0)
        // Draw a rectangle using the coordinates of the bounding box that surrounds the foreground
        Imgproc.rectangle(srcImage, coordinates.first, coordinates.second, color)

        // Create a new matrix to represent the background, filled with white color
        val background = Mat(srcImage.size(), CvType.CV_8UC3, Scalar(255.0, 255.0, 255.0))

        val mask = Mat(foreground.size(), CvType.CV_8UC1, Scalar(255.0, 255.0, 255.0))
        // Convert the foreground's color space from BGR to gray scale
        Imgproc.cvtColor(foreground, mask, Imgproc.COLOR_BGR2GRAY)

        // Separate out regions of the mask by comparing the pixel intensity with respect to a threshold value
        Imgproc.threshold(mask, mask, 254.0, 255.0, Imgproc.THRESH_BINARY_INV)

        // Create a matrix to hold the final image
        val dst = Mat()
        // copy the background matrix onto the matrix that represents the final result
        background.copyTo(dst)

        val vals = Mat(1, 1, CvType.CV_8UC3, Scalar(0.0))
        // Replace all 0 values in the background matrix given the foreground mask
        background.setTo(vals, mask)

        // Add the sum of the background and foreground matrices by applying the mask
        Core.add(background, foreground, dst, mask)

        // Save the final image to storage
        Imgcodecs.imwrite(currentPhotoPath + "_tmp.jpg", dst)

        // Clean up used resources
        firstMask.release()
        source.release()
        bgModel.release()
        fgModel.release()
        vals.release()
        dst.release()

        return currentPhotoPath + "_tmp.jpg"
    }

}