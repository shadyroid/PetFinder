package com.shady.boyot.ui.camera

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.CameraInfoUnavailableException
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.Builder
import androidx.camera.core.ImageCapture.FLASH_MODE_AUTO
import androidx.camera.core.ImageCapture.FLASH_MODE_OFF
import androidx.camera.core.ImageCapture.FLASH_MODE_ON
import androidx.camera.core.ImageCapture.OnImageSavedCallback
import androidx.camera.core.ImageCapture.OutputFileOptions
import androidx.camera.core.ImageCapture.OutputFileResults
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.MeteringPointFactory
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceOrientedMeteringPointFactory
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.shady.boyot.R
import com.shady.boyot.base.BaseFragment
import com.shady.boyot.classes.utils.FlashMode
import com.shady.boyot.databinding.FragmentCameraBinding
import com.shady.boyot.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@AndroidEntryPoint
class CameraFragment : BaseFragment() {
    val mainViewModel: MainViewModel by activityViewModels()

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }

    private var isFront = false
    private var imageCapture: ImageCapture? = null
    private var flash = FlashMode.FLASH_OFF
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var binding: FragmentCameraBinding
    private var isSettingsOpened: Boolean = false

    private val requestCamerPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startCamera(CameraSelector.DEFAULT_BACK_CAMERA)
            outputDirectory = getOutputDirectory()
            cameraExecutor = Executors.newSingleThreadExecutor()

            binding.btnRequestPermission.visibility = View.GONE
            binding.llCameraContainer.visibility = View.VISIBLE
        } else {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                showPermissionSettingsDialog();
            } else {
                binding.btnRequestPermission.visibility = View.VISIBLE
                binding.llCameraContainer.visibility = View.GONE
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::binding.isInitialized) {
            binding = FragmentCameraBinding.inflate(inflater, container, false)
            init()
        }
        requestCamerPermissionLauncher.launch(Manifest.permission.CAMERA)
        return binding.root
    }


    private fun init() {
        binding.cCapture.setOnClickListener { takePhoto() }

        binding.cChange.setOnClickListener {
            isFront = if (isFront) {
                startCamera(CameraSelector.DEFAULT_BACK_CAMERA)
                false
            } else {
                startCamera(CameraSelector.DEFAULT_FRONT_CAMERA)
                true
            }
        }

        binding.cFlash.setOnClickListener {
            when (flash) {
                FlashMode.FLASH_OFF -> {
                    flash = FlashMode.FLASH_AUTO
                    binding.cFlash.setImageResource(R.mipmap.ic_flash_auto)
                }

                FlashMode.FLASH_AUTO -> {
                    flash = FlashMode.FLASH_ON
                    binding.cFlash.setImageResource(R.mipmap.ic_flash_on)
                }

                FlashMode.FLASH_ON -> {
                    flash = FlashMode.FLASH_OFF
                    binding.cFlash.setImageResource(R.mipmap.ic_flash_off)
                }
            }

        }
        binding.btnRequestPermission.setOnClickListener {
            requestCamerPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

    }

    override fun onResume() {
        super.onResume()
        if (isSettingsOpened) {
            isSettingsOpened = false
            requestCamerPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun showPermissionSettingsDialog() {
        context?.let {
            val builder: AlertDialog.Builder = AlertDialog.Builder(it)
            builder.setTitle(getString(R.string.permission_required))
            builder.setMessage(
                getString(R.string.to_use_this_app_you_must_grant_camera_permission_please_go_to_app_settings_and_enable_the_camera_permission)
            )
            builder.setPositiveButton(getString(R.string.open_settings)) { _, _ -> openAppSettings() }
            builder.setNegativeButton(getString(R.string.cancel)) { _, _ ->
                binding.btnRequestPermission.visibility = View.VISIBLE
                binding.llCameraContainer.visibility = View.GONE
            }
            builder.show()
        }
    }

    private fun openAppSettings() {
        isSettingsOpened = true
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", activity?.packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    private fun startCamera(cameraSelector: CameraSelector) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.cViewFinder.surfaceProvider)
                }
            imageCapture = Builder()
                .build()
            try {
                cameraProvider.unbindAll()

                val camera = cameraProvider.bindToLifecycle(
                    requireActivity(), cameraSelector, preview, imageCapture
                )

                binding.cViewFinder.afterMeasured {
                    binding.cViewFinder.setOnTouchListener { _, event ->
                        return@setOnTouchListener when (event.action) {
                            MotionEvent.ACTION_DOWN -> {
                                true
                            }

                            MotionEvent.ACTION_UP -> {
                                binding.cFocus.visibility = View.VISIBLE
                                val factory: MeteringPointFactory =
                                    SurfaceOrientedMeteringPointFactory(
                                        binding.cViewFinder.width.toFloat(),
                                        binding.cViewFinder.height.toFloat()
                                    )
                                val autoFocusPoint = factory.createPoint(event.x, event.y)
                                try {

                                    camera.cameraControl.startFocusAndMetering(
                                        FocusMeteringAction.Builder(
                                            autoFocusPoint,
                                            FocusMeteringAction.FLAG_AF
                                        ).apply {
                                            disableAutoCancel()
                                        }.build()
                                    )
                                    binding.cFocus.translationX =
                                        event.x - (binding.cFocus.width / 2)
                                    binding.cFocus.translationY =
                                        event.y - (binding.cFocus.height / 2)
                                } catch (e: CameraInfoUnavailableException) {
                                }
                                true
                            }

                            else -> false
                        }
                    }
                }
            } catch (exc: Exception) {
            }

        }, ContextCompat.getMainExecutor(requireActivity()))
    }

    private inline fun View.afterMeasured(crossinline block: () -> Unit) {
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (measuredWidth > 0 && measuredHeight > 0) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    block()
                }
            }
        })
    }


    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = File.createTempFile(
            SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()),
            ".jpg",
            outputDirectory
        )

        val outputOptions = OutputFileOptions.Builder(photoFile).build()

        when (flash) {
            FlashMode.FLASH_ON -> imageCapture.flashMode = FLASH_MODE_ON
            FlashMode.FLASH_OFF -> imageCapture.flashMode = FLASH_MODE_OFF
            FlashMode.FLASH_AUTO -> imageCapture.flashMode = FLASH_MODE_AUTO
        }

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                }

                override fun onImageSaved(output: OutputFileResults) {
                    mainViewModel.onCameraTakePictureMutableStateFlow.value = Uri.fromFile(
                        photoFile
                    ).toString()
                    popBackStack()
                }
            })
    }


    private fun getOutputDirectory(): File {
        val mediaDir = requireActivity().externalMediaDirs.firstOrNull()?.let {
            File(it, "Krafttopia").apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireActivity().filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::cameraExecutor.isInitialized)
            cameraExecutor.shutdown()
    }

}