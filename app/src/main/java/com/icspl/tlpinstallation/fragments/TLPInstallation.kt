package com.icspl.tlpinstallation.fragments

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.content.ContentValues.TAG
import android.database.sqlite.SQLiteDatabase
import android.graphics.*
import android.location.Location
import android.media.ExifInterface
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.icspl.tlpinstallation.Adaptor.TLPCAbleAdapter
import com.icspl.tlpinstallation.Adaptor.TLPInstallationAdaptor
import com.icspl.tlpinstallation.BuildConfig
import com.icspl.tlpinstallation.DBHelper.DbHelper
import com.icspl.tlpinstallation.MainActivity
import com.icspl.tlpinstallation.Model.TLPCableModel
import com.icspl.tlpinstallation.Model.TLPInstallationModel

import com.icspl.tlpinstallation.R
import com.icspl.tlpinstallation.constants.DbConstant
import com.icspl.tlpinstallation.handler.ApiService
import com.icspl.tlpinstallation.utils.Comman
import es.dmoral.toasty.Toasty
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_sync_data.*
import kotlinx.android.synthetic.main.tlp_installation.*
import kotlinx.android.synthetic.main.tlp_installation.view.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class TLPInstallation : Fragment() ,TLPInstallationAdaptor.Photoclickhandler {


    internal lateinit var mView: View
    private lateinit var mDatabase: SQLiteDatabase
    private var mHelper: DbHelper? = null
    private lateinit var mContext: Context
    private var mActivity: FragmentActivity? = null
    private lateinit var preferences: SharedPreferences
    private var mService: ApiService? = null
    private var insertSection:Boolean = true
    private var insertInstallationdetails:Boolean = true


    private lateinit var mlist:List<TLPInstallationModel>
    private lateinit var mlistforcable:List<TLPCableModel>
    private lateinit var mlistforthermitweilding:List<TLPCableModel>

    private var adaptor:TLPInstallationAdaptor?=null
    private var adaptorcable:TLPCAbleAdapter?=null
    private var adapterthermit:TLPCAbleAdapter?=null
    private var viewHolder:TLPInstallationAdaptor.Holder?=null
    private var viewHoldercable:TLPCAbleAdapter.Holder?=null
    private var viewHolderthermit:TLPCAbleAdapter.Holder?=null
    private var imageToUploadUri: Uri? = null
    private val IMAGE_REQUEST_CODE = 2103
    internal lateinit var imageBtnFile: File
    lateinit var test: Button
    private var latLong:String=""
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var radiotext: String=""
    private lateinit var tpe_cable: String
    lateinit var radiobtn:RadioButton

    var tlp_identification_no:String=""
    lateinit var final_rg_fm:String
    lateinit var final_rg_ts_painting:String
    lateinit var final_rg_cable_terminals:String
    lateinit var final_rg_holidaychk:String
    lateinit var final_rg_c_chk:String
     var anode_identification_no:String=""
    lateinit var installation_depth:String
    lateinit var tpe_anode:String
    lateinit var distance_pipeline:String
    lateinit var lengthtail_cable:String
    lateinit var final_rg_termination:String
    lateinit var final_tlpno:String
    val arraycableno: ArrayList<String> = ArrayList()
    val arrytypecable: ArrayList<String> = ArrayList()
    val arrythermitno: ArrayList<String> = ArrayList()
    val arrytypethermit: ArrayList<String> = ArrayList()

    lateinit var final_chainage:String
    lateinit var final_location:String
    lateinit var final_cable_type:String
    var final_attachment:String=""
    lateinit var  final_noofcableused:String
    lateinit var  final_noofthermitused:String
   lateinit var arr1cableno:String
   lateinit var  arr1typecable:String
   lateinit var  arr2thermitno:String
   lateinit var  arr2thermittype:String
     lateinit var thermitedittext:EditText
     lateinit var cablenoedittext:EditText
    var finalcableno:String=""
    var finalthermitno:String=""


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.tlp_installation, container, false)
        var activity: MainActivity = getActivity() as MainActivity;
        activity.addsectionname.visibility=View.VISIBLE
        activity.addsectionno.visibility=View.VISIBLE
        activity.linearlength.visibility=View.VISIBLE
        activity.linearspecification.visibility=View.VISIBLE
        activity.lineardiameter.visibility=View.VISIBLE
        init()
        return mView
    }
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("ResourceAsColor")
    override fun photoclickcallback(v: View?, rvbtn_cam: Button?) {
        test= rvbtn_cam!!;
        pickimage()
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    fun pickimage() {
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/ICS SMS/Images/"
        val newdir = File(dir)
        newdir.mkdirs()

        val file = dir + "SMS_" + DateFormat.format("yyyy-MM-dd_hhmmss", Date()).toString() + ".jpg"

        imageBtnFile = File(file)
        try {
            imageBtnFile.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }


        imageToUploadUri = FileProvider.getUriForFile(mContext!!, BuildConfig.APPLICATION_ID + ".provider", imageBtnFile)
        //        File impfile=new File(context.getFilesDir(),"");
        //        Uri imageToUploadUri=getUriForFile()

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageToUploadUri)
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            cameraIntent.clipData = ClipData.newRawUri("", imageToUploadUri)

            cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivityForResult(cameraIntent, IMAGE_REQUEST_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE) {
            imageActivityResult()
        }
    }

    @SuppressLint("StaticFieldLeak")
    fun imageActivityResult() {
        try {
            object : AsyncTask<Void, Void, String>() {
                override fun doInBackground(vararg voids: Void): String {
                    //Activity activity= new Activity();
                    val path = compressImage(imageBtnFile.toString(), activity)
                    Log.i(TAG, "doInBackground: path: $path")
                    return path
                }


                override fun onPostExecute(path: String?) {
                    super.onPostExecute(path)
                    try {
                        if (path != null) {

                            test.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
                            test.tag=path
                            Toast.makeText(activity, "file attached", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(activity, "not attached", Toast.LENGTH_SHORT).show()
                        }
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                        Toast.makeText(activity, "Failed to Attach File", Toast.LENGTH_SHORT).show()
                    }

                }
            }.execute()
        } catch (ex: Exception) {
            ex.printStackTrace()
            Toast.makeText(mContext, "Failed to Attach File", Toast.LENGTH_SHORT).show()
        }


    }

    fun compressImage(imageUri: String, activity: Activity?): String {
        var filename = ""
        try {
            val filePath = getRealPathFromURI(imageUri, activity!!)
            var scaledBitmap: Bitmap? = null

            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            var bmp = BitmapFactory.decodeFile(filePath, options)

            var actualHeight = options.outHeight
            var actualWidth = options.outWidth
            val maxHeight = 816.0f
            val maxWidth = 612.0f
            var imgRatio = (actualWidth / actualHeight).toFloat()
            val maxRatio = maxWidth / maxHeight

            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight
                    actualWidth = (imgRatio * actualWidth).toInt()
                    actualHeight = maxHeight.toInt()
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth
                    actualHeight = (imgRatio * actualHeight).toInt()
                    actualWidth = maxWidth.toInt()
                } else {
                    actualHeight = maxHeight.toInt()
                    actualWidth = maxWidth.toInt()

                }
            }

            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)
            options.inJustDecodeBounds = false
            options.inDither = false
            options.inPurgeable = true
            options.inInputShareable = true
            options.inTempStorage = ByteArray(16 * 1024)

            try {
                bmp = BitmapFactory.decodeFile(filePath, options)
            } catch (exception: OutOfMemoryError) {
                exception.printStackTrace()

            }

            try {
                scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
            } catch (exception: OutOfMemoryError) {
                exception.printStackTrace()
            }

            val ratioX = actualWidth / options.outWidth.toFloat()
            val ratioY = actualHeight / options.outHeight.toFloat()
            val middleX = actualWidth / 2.0f
            val middleY = actualHeight / 2.0f

            val scaleMatrix = Matrix()
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)

            val canvas: Canvas
            if (scaledBitmap != null) {
                canvas = Canvas(scaledBitmap)
                canvas.setMatrix(scaleMatrix)
                canvas.drawBitmap(bmp, middleX - bmp.width / 2, middleY - bmp.height / 2, Paint(Paint.FILTER_BITMAP_FLAG))
            }


            val exif: ExifInterface
            try {
                exif = ExifInterface(filePath!!)

                val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)

                val matrix = Matrix()
                if (orientation == 6) {
                    matrix.postRotate(90f)

                } else if (orientation == 3) {
                    matrix.postRotate(180f)

                } else if (orientation == 8) {
                    matrix.postRotate(270f)

                }
                if (scaledBitmap != null) {
                    scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.width, scaledBitmap.height, matrix, true)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val out: FileOutputStream
            filename = getFilename()
            try {
                out = FileOutputStream(filename)
                scaledBitmap?.compress(Bitmap.CompressFormat.JPEG, 80, out)

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return filename
    }

    private fun getFilename(): String {

        val file = File(Environment.getExternalStorageDirectory().path, "ICS SMS/")
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.absolutePath + "/" + "ECD_" + UUID.randomUUID().toString() + "_IMG_" + System.currentTimeMillis() + ".jpg"

    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        val totalPixels = (width * height).toFloat()
        val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++
        }

        return inSampleSize
    }

    private fun getRealPathFromURI(contentURI: String, activity: Activity): String? {
        val contentUri = Uri.parse(contentURI)

        val cursor = activity.contentResolver.query(contentUri, null, null, null, null)
        if (cursor == null) {
            return contentUri.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            return cursor.getString(idx)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {
        mContext = mView.context
        mActivity = (activity) as FragmentActivity
        mHelper = DbHelper(mContext)
        mDatabase = mHelper!!.writableDatabase
        preferences = mContext.getSharedPreferences(getString(R.string.user), Context.MODE_PRIVATE)
        mService = Comman.getAPI()
        mlist= ArrayList();
        mlistforcable=ArrayList()
        mlistforthermitweilding=ArrayList()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext)
        mView.submit.visibility=View.GONE
        mView.saveAsDraft.visibility=View.VISIBLE
        //latLong=getLocation()

        // Check if GPS is available
        if (!SmartLocation.with(mContext).location().state().isGpsAvailable) {
            Toasty.error(mContext, "GPS Required ):", Toast.LENGTH_SHORT).show()
            return
        } else {
            latLong = getLocation()
        }

        fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    latLong= location.toString()
                    // Got last known location. In some rare situations this can be null.
                }


//added by kiran 7jan20


      mView.et_no_of_cable.addTextChangedListener(object : TextWatcher
    {
        override fun afterTextChanged(p0: Editable?) {
            if (p0.toString()=="")
            {
                ( mlistforcable as ArrayList<TLPCableModel>).clear()
                mView.rv_typecable.layoutManager=LinearLayoutManager(mContext)
                adaptorcable= TLPCAbleAdapter(mContext,mlistforcable)
                mView.rv_typecable.adapter=adaptorcable
            }
            else {
                cablenoedittext=mView.findViewById(R.id.et_no_of_cable)
                finalcableno=cablenoedittext.text.toString()

                if (p0.toString().toInt() > 0) {
                    (mlistforcable as ArrayList<TLPCableModel>).clear()
                    var model: TLPCableModel
                    var activity: MainActivity = getActivity() as MainActivity;
                    var values = activity.getdata()
                    var contractorName = values[0]
                    var clientName = values[1]
                    var sectionName = values[2]
                    var sectionNo = values[3]
                 //    var cursorr=mDatabase.rawQuery("select tlp_no From " + DbConstant.TempTLPEntry.TABLE_TEMP_TLP + " WHERE " + DbConstant.TempTLPEntry.CLIENT_NAME + "='" + clientName + "'AND " + DbConstant.TempTLPEntry.CONTRACTOR_NAME + "='" + contractorName + "' AND  " + DbConstant.TempTLPEntry.SECTION_NAME + "='" + sectionName + "' AND " + DbConstant.TempTLPEntry.SECTION_No + "='" + sectionNo + "'" , null)
                 //   var finaltlp=cursorr.getString(cursorr.getColumnIndex(DbConstant.TempTLPEntry.TLP_NO))
                    if(contractorName==""|| clientName==""||sectionName==""||sectionNo==""||clientName=="Select"||sectionName=="Select"||sectionNo=="Select")
                    {
                        Toasty.error(mContext,"Select client name and section name and section no").show()
                        mView.noOfTLPInstallation.setText("")
                        return
                    }

                //    var cursor = DbHelper(mContext).getTLPDetails(clientName, contractorName, sectionName, sectionNo)
                    var cursor = DbHelper(mContext).getTLPDetailsCAble(clientName, contractorName, sectionName, sectionNo,final_tlpno)

                    for (i in 1..p0.toString().toInt()) {
                        //add method here check if data available
                        if (cursor.count >= i) {
                            cursor.moveToNext()
                            var cabletype = cursor.getString(cursor.getColumnIndex(DbConstant.TempTLPEntry.CABLE_USED_TYPE))
                            var usedno = cursor.getString(cursor.getColumnIndex(DbConstant.TempTLPEntry.CABLE_USED_NO))
                             model = TLPCableModel(cabletype, usedno)

                        } else {
                            model = TLPCableModel("", "")
                        }

                        (mlistforcable as ArrayList<TLPCableModel>).add(model)
                    }
                    if (mlistforcable.isEmpty())
                    {
                      Toasty.error(mContext,"Fill cable type").show()
                        return
                    }
                    mView.rv_typecable.layoutManager = LinearLayoutManager(mContext)
                    adaptorcable = TLPCAbleAdapter(mContext, mlistforcable )
                    mView.rv_typecable.adapter = adaptorcable
                    // (mView.rvtlp.adapter as TLPInstallationAdaptor).notifyDataSetChanged()

                }

            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

    }
    )

        mView.et_no_thermit.addTextChangedListener(object : TextWatcher
        {
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString()=="")
                {

                    ( mlistforthermitweilding as ArrayList<TLPCableModel>).clear()
                    mView.rv_thermitweilding.layoutManager=LinearLayoutManager(mContext)
                    adapterthermit= TLPCAbleAdapter(mContext,mlistforthermitweilding)
                    mView.rv_thermitweilding.adapter=adapterthermit
                }
                else {
                    thermitedittext=mView.findViewById(R.id.et_no_thermit)
                    finalthermitno= thermitedittext.text.toString()
                    if (p0.toString().toInt() > 0) {
                        (mlistforthermitweilding as ArrayList<TLPCableModel>).clear()
                        var model: TLPCableModel
                        var activity: MainActivity = getActivity() as MainActivity;
                        var values = activity.getdata()
                        var contractorName = values[0]
                        var clientName = values[1]
                        var sectionName = values[2]
                        var sectionNo = values[3]

                        var cursor = DbHelper(mContext).getTLPDetailsTHermit(clientName, contractorName, sectionName, sectionNo,final_tlpno)
                        for (i in 1..p0.toString().toInt()) {
                            //add method here check if data available
                            if (cursor.count >= i) {
                                cursor.moveToNext()
                                var thermittype = cursor.getString(cursor.getColumnIndex(DbConstant.TempTLPEntry.CABLE_THERMIT_TYPE))
                                var thermitno = cursor.getString(cursor.getColumnIndex(DbConstant.TempTLPEntry.CABLE_THERMIT_USED_NO))
                                model = TLPCableModel(thermittype, thermitno)

                            } else {
                                model = TLPCableModel("", "")
                            }

                            (mlistforthermitweilding as ArrayList<TLPCableModel>).add(model)
                        }
                        mView.rv_thermitweilding.layoutManager = LinearLayoutManager(mContext)
                        adapterthermit = TLPCAbleAdapter(mContext, mlistforthermitweilding )
                        mView.rv_thermitweilding.adapter = adapterthermit
                        // (mView.rvtlp.adapter as TLPInstallationAdaptor).notifyDataSetChanged()

                    }

                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        }
        )


        mView.noOfTLPInstallation.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mView.submit.visibility=View.GONE
                mView.saveAsDraft.visibility=View.VISIBLE
                mView.linear_header_tlpno.visibility=View.VISIBLE

                if(s.toString()=="") {
                    (mlist as ArrayList<TLPInstallationModel>).clear()
                    mView.rvtlp.layoutManager = LinearLayoutManager(mContext)
                    adaptor= TLPInstallationAdaptor(mContext,mlist, this@TLPInstallation)
                    mView.rvtlp.adapter = adaptor
                }
                else {
                    if (s.toString().toInt()==1) {
                        (mlist as ArrayList<TLPInstallationModel>).clear()
                        var model: TLPInstallationModel
                        var activity :MainActivity= getActivity() as MainActivity;
                        var values = activity.getdata()
                        var contractorName=values[0]
                        var clientName=values[1]
                        var sectionName=values[2]
                        var sectionNo=values[3]
                        //added kiran
                      //getTLPNo(clientName,contractorName,sectionName,sectionNo)
                        if(contractorName==""|| clientName==""||sectionName==""||sectionNo==""||clientName=="Select"||sectionName=="Select"||sectionNo=="Select")
                        {
                            Toasty.error(mContext,"Select client name and section name and section no").show()
                            mView.noOfTLPInstallation.setText("")
                            return
                        }
                        var cursor=DbHelper(mContext).getTLPDetails(clientName,contractorName,sectionName,sectionNo)
                        for (i in 1..s.toString().toInt()) {
                            //add method here check if data available
                            if(cursor.count>=i)
                            {
                                cursor.moveToNext()
                                var tlpNo=cursor.getString(cursor.getColumnIndex(DbConstant.TempTLPEntry.TLP_NO))
                                var chainage=cursor.getString(cursor.getColumnIndex(DbConstant.TempTLPEntry.CHAINAGE))
                                var location=cursor.getString(cursor.getColumnIndex(DbConstant.TempTLPEntry.LOCATION))
                                var image=cursor.getString(cursor.getColumnIndex(DbConstant.TempTLPEntry.PHOTO))
                              //  var typeofcable=cursor.getString(cursor.getColumnIndex(DbConstant.TempTLPEntry.TYPEOFCABLE))
                                var valueofcable=cursor.getString(cursor.getColumnIndex(DbConstant.TempTLPEntry.CABLE_TYPE))
                                model= TLPInstallationModel(tlpNo,chainage,location,image,valueofcable)

                                final_tlpno=tlpNo

                            }
                            else
                            {
                                model = TLPInstallationModel("", "", "", "NA","NO")
                            }

                            (mlist as ArrayList<TLPInstallationModel>).add(model)
                        }
                        mView.rvtlp.layoutManager = LinearLayoutManager(mContext)
                        adaptor= TLPInstallationAdaptor(mContext,mlist, this@TLPInstallation)
                        mView.rvtlp.adapter = adaptor
                        // (mView.rvtlp.adapter as TLPInstallationAdaptor).notifyDataSetChanged()

                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
//added

     mView.final_savedraft_anodendtlp.setOnClickListener {


         savetempdata(final_tlpno)
     }

        mView.saveAsDraft.setOnClickListener(View.OnClickListener {
            val activity: MainActivity = getActivity() as MainActivity;
            val values = activity.getdata()
            val contractorName = values[0]
            val clientName = values[1]
            val sectionName = values[2]
            val sectionNo = values[3]
            val diameter = values[4]
            val specification = values[5]
            val length = values[6]
            val clientId=values[7]
            if (latLong == "") {
                latLong = getLocation()
                Log.e("Location", latLong)
            }
            if ((clientName == "") || (clientName == "Select") || (contractorName == "") || (contractorName == "Select") || (sectionName == "") || (sectionName == "Select") || (sectionNo == "") || (sectionNo == "Select") || (diameter == "") || (specification == "") || (length == "")) {
                Toasty.error(mContext, "Please fill all the * Required Field").show()
                return@OnClickListener
            }
            if (insertSection) {
                val values = ContentValues()
                values.put(DbConstant.SectionEntry.CLIENT_NAME, clientName)
                values.put(DbConstant.SectionEntry.CONTRACTOR_NAME, contractorName)
                values.put(DbConstant.SectionEntry.SECTION_NAME, sectionName)
                values.put(DbConstant.SectionEntry.SECTION_No, sectionNo)
                values.put(DbConstant.SectionEntry.DIAMETER, diameter)
                values.put(DbConstant.SectionEntry.SPECIFICATION, specification)
                values.put(DbConstant.SectionEntry.LENGTH, length)
                values.put(DbConstant.SectionEntry.CLIENT_ID, clientId)
                values.put(DbConstant.SectionEntry.SERVER_STATUS, "Pending")


                var id = mDatabase.insert(DbConstant.SectionEntry.TABLE_SECTION, null, values)
                if (id > 0) {
                    Log.i("Insert Section Details", "Insert success")
                } else {
                    Log.i("Insert Section Details", "Insert failed")
                }
            }
            for (i in 1..(adaptor?.itemCount ?: 0)) {
                viewHolder = mView.rvtlp.findViewHolderForAdapterPosition(i - 1) as TLPInstallationAdaptor.Holder?
                if ((if (viewHolder != null) viewHolder!!.tlpno.getText().length else 0) > 0) {
                    if ( (viewHolder!!.sp_for_typecable.selectedItem.toString() == "SELECT TYPE OF TLP") )
                    {
                        Toasty.error(mContext,"PLEASE SELECT CABLE TYPE FIRST")
                    }
                   else{
                        try {

                            Log.e("Loop", i.toString())
                            val btnTag = if (viewHolder!!.Photo.getTag() == null) "NA" else viewHolder!!.Photo.getTag().toString()
                            val chainage = if (viewHolder!!.chainage.text.toString() == "") "NA" else viewHolder!!.chainage.text.toString()
                            val location = if (viewHolder!!.location.text.toString() == "") "NA" else viewHolder!!.location.text.toString()
                            val tlpNo = if (viewHolder!!.tlpno.text.toString() == "") "NA" else viewHolder!!.tlpno.text.toString()
                            final_tlpno=tlpNo

                            val valuecable = if (viewHolder!!.sp_for_typecable.selectedItem.toString() == "SELECT TYPE OF TLP") "NA" else viewHolder!!.sp_for_typecable.selectedItem.toString()

                            saveContentValues(i.toString(),clientName,contractorName,sectionName,sectionNo,diameter,specification,length, tlpNo,chainage.toUpperCase(),location.toUpperCase(),btnTag,clientId,valuecable)
                            if(i== adaptor!!.itemCount)
                            {
                                //mView.noOfTLPInstallation.setText("")
                                mView.submit.visibility=View.VISIBLE
                                mView.saveAsDraft.visibility=View.GONE
                                mView.linearlayout_tlpinstalation.visibility=View.VISIBLE
                                mView.final_savedraft_anodendtlp.visibility=View.VISIBLE
                                if(valuecable=="M")
                                {
                                    mView.linearlayout_anodeinstallation.visibility=View.VISIBLE
                                }
                                else
                                {
                                    mView.linearlayout_anodeinstallation.visibility=View.GONE
                                }
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                } else
                    return@OnClickListener


        }
        })

        // added bykiran 8jAN2020
        mView.final_submit_anodendtlp.setOnClickListener(View.OnClickListener {
            if(noOfTLPInstallation.text.toString()=="" )
            {
                Toasty.error(mContext,"Please Fill atleast one TLP installation to submit").show()
               // mView.final_submit_anodendtlp.visibility=View.GONE
                return@OnClickListener
            }
            val activity :MainActivity= getActivity() as MainActivity;
            val values = activity.getdata()
            val contractorName=values[0]
            val clientName=values[1]
            val sectionName=values[2]
            val sectionNo=values[3]
            val diameter=values[4]
            val specification=values[5]
            val length=values[6]

            var TLPList:ArrayList<String>
            TLPList=ArrayList();
            TLPList.add("Client Name :"+clientName)
            TLPList.add("Contractor Name :"+contractorName)
            TLPList.add("Section Name :"+sectionName)
            TLPList.add("Section No. :"+sectionNo)
            TLPList.add("Pipe Diameter And thickness :"+diameter)
            TLPList.add("Pipe Specification :"+specification)
            TLPList.add("Pipe Section Length :"+length)
            TLPList.add("TLP INSTALLATION DETAILS:")
            TLPList.add("TLP TEST STATION IDENTIFICATION NO :" +tlp_identification_no)
            TLPList.add("FOUNDATION & MOUNTING :" +final_rg_fm)
            TLPList.add("PAINTING & GASKET :" +final_rg_ts_painting)
            TLPList.add("CABLE TERMINALS :" +final_rg_cable_terminals)
            TLPList.add("HOLIDAY CHECK & RESTORATION :" +final_rg_holidaychk)
            TLPList.add("CONTINUITY CHECK :" +final_rg_c_chk)
            TLPList.add("ANODE INSTALLATION DETAILS:")
            TLPList.add("ANODE IDENTIFICATION NO :" +anode_identification_no)
            TLPList.add("ANODE TYPE :" +tpe_anode)
            TLPList.add("INSTALLATION DEPTH :" +installation_depth)
            TLPList.add("DISTANCE FROM PIPELINE :" +distance_pipeline)
            TLPList.add("LENGTH OF TAIL CABLE :" +lengthtail_cable)
            TLPList.add("TERMINATION WITH STATION :" +final_rg_termination)



            for(i in 1..(adaptor?.itemCount ?: 0))
            {
                viewHolder= mView.rvtlp.findViewHolderForAdapterPosition(i-1) as TLPInstallationAdaptor.Holder?
                if((if (viewHolder != null) viewHolder!!.tlpno.getText().length else 0) > 0)
                {
                    try {
                       final_chainage= viewHolder?.chainage?.getText().toString()
                      final_location = viewHolder?.location?.getText().toString()
                      final_cable_type=viewHolder!!.sp_for_typecable.selectedItem.toString()
                        final_attachment=viewHolder!!.Photo.getTag().toString()

                        val mStringBuilder = "TLP No.: " +
                                viewHolder?.tlpno?.getText().toString() +
                                "\n" +
                                "Chainage.: " +
                                viewHolder?.chainage?.getText().toString() +
                                "\n" +
                                "Location.: " +
                                viewHolder?.location?.getText().toString() +
                                "\n" +
                                "TYPE OF Cable.: " +

                                ( if (viewHolder!!.sp_for_typecable.selectedItem == "SELECT TYPE OF CABLE") "No Cable" else viewHolder!!.sp_for_typecable.selectedItem) +
                                "\n" +
                                "Photos: " + if (viewHolder!!.Photo.getTag() == null) "Not Uploaded" else "Uploaded"


                        TLPList.add(mStringBuilder)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else
                    return@OnClickListener
            }

           arraycableno.clear()
            arrytypecable.clear()
            for(i in 1..(adaptorcable?.itemCount ?: 0))
            {
              final_noofcableused=adaptorcable?.itemCount.toString()
                viewHoldercable= mView.rv_typecable.findViewHolderForAdapterPosition(i-1) as TLPCAbleAdapter.Holder?
                if((if (viewHoldercable != null) viewHoldercable!!.cableno.getText().length else 0) > 0)
                {
                    try {
                        val mStringBuilder1 = "cableno: " +
                                viewHoldercable?.cableno?.getText().toString() +
                                "\n" +

                                "TYPE OF Cable.: " +

                                ( if (viewHoldercable!!.sp_cable_type.selectedItem == "SELECT TYPE OF CABLE") "No Cable" else viewHoldercable!!.sp_cable_type.selectedItem)



                         arraycableno.add( viewHoldercable?.cableno?.getText().toString())


                        arrytypecable.add( viewHoldercable?.sp_cable_type?.selectedItem.toString())



                        TLPList.add(mStringBuilder1)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else
                    return@OnClickListener
            }
             arr1cableno=arraycableno.toString()
              arr1typecable=arrytypecable.toString()
            TLPList.add(arraycableno.toString())
            TLPList.add(arrytypecable.toString())
            arrythermitno.clear()
            arrytypethermit.clear()
           for(i in 1..(adapterthermit?.itemCount ?: 0))
            {
              final_noofthermitused=adapterthermit?.itemCount.toString()
                viewHolderthermit= mView.rv_thermitweilding.findViewHolderForAdapterPosition(i-1) as TLPCAbleAdapter.Holder?
                if((if (viewHolderthermit != null) viewHolderthermit!!.cableno.getText().length else 0) > 0)
                {
                    try {
                        val mStringBuilder1 = "thermitcableno: " +
                                viewHolderthermit?.cableno?.getText().toString() +
                                "\n" +

                                "TYPE OF thermitCable.: " +

                                ( if (viewHolderthermit!!.sp_cable_type.selectedItem == "SELECT TYPE OF CABLE") "No Cable" else viewHolderthermit!!.sp_cable_type.selectedItem)

                        arrythermitno.add( viewHolderthermit?.cableno?.getText().toString())
                        arrytypethermit.add( viewHolderthermit?.sp_cable_type?.selectedItem .toString())

                        TLPList.add(mStringBuilder1)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else
                    return@OnClickListener
            }
            arr2thermitno=arrythermitno.toString()
            arr2thermittype=arrytypethermit.toString()
            TLPList.add(arrythermitno.toString())
            TLPList.add(arrytypethermit.toString())
            val alertDialog = AlertDialog.Builder(getActivity())
            val inflater = getActivity()!!.layoutInflater
            alertDialog.setTitle("FINAL SUBMIT \n check entries before submitting data")
            val convertView = inflater.inflate(R.layout.layout_preview_list, null)
            alertDialog.setView(convertView)
            alertDialog.setPositiveButton(getString(R.string.submit)) { dialogInterface, i ->
                dialogInterface.cancel()
              //  handle_submit_btn(clientName,contractorName,sectionName,sectionNo)
                finalsubmitfinaltableentry(clientName,contractorName,sectionName,sectionNo,diameter,specification,
                        length,final_tlpno,final_chainage,final_location,final_cable_type,final_attachment,tlp_identification_no,
                        final_rg_fm,final_rg_ts_painting,final_rg_cable_terminals,final_noofcableused,arr1cableno,arr1typecable,
                        final_noofthermitused,arr2thermitno,arr2thermittype,final_rg_holidaychk,final_rg_c_chk,anode_identification_no,
                        tpe_anode,installation_depth,distance_pipeline,lengthtail_cable,final_rg_termination)


            }

            alertDialog.setNegativeButton("Cancel") { dialogInterface, i -> dialogInterface.cancel() }
            val lv = convertView.findViewById<ListView>(R.id.lv_preview)
            lv.setClickable(false)
            val adapter = ArrayAdapter<String>(getActivity()!!, android.R.layout.simple_list_item_1, TLPList)
            lv.setAdapter(adapter)
            alertDialog.show()

        })

        mView.submit.setOnClickListener(View.OnClickListener {
            if(noOfTLPInstallation.text.toString()=="" )
            {
                Toasty.error(mContext,"Please Fill  one TLP installation to submit").show()
                mView.submit.visibility=View.GONE
                return@OnClickListener
            }
            val activity :MainActivity= getActivity() as MainActivity;
            val values = activity.getdata()
            val contractorName=values[0]
            val clientName=values[1]
            val sectionName=values[2]
            val sectionNo=values[3]
            val diameter=values[4]
            val specification=values[5]
            val length=values[6]

            var TLPList:ArrayList<String>
            TLPList=ArrayList();
            TLPList.add("Client Name :"+clientName)
            TLPList.add("Contractor Name :"+contractorName)
            TLPList.add("Section Name :"+sectionName)
            TLPList.add("Section No. :"+sectionNo)
            TLPList.add("Pipe Diameter And thickness :"+diameter)
            TLPList.add("Pipe Specification :"+specification)
            TLPList.add("Pipe Section Length :"+length)

            for(i in 1..(adaptor?.itemCount ?: 0))
            {
                viewHolder= mView.rvtlp.findViewHolderForAdapterPosition(i-1) as TLPInstallationAdaptor.Holder?
                if((if (viewHolder != null) viewHolder!!.tlpno.getText().length else 0) > 0)
                {
                    try {
                        val mStringBuilder = "TLP No.: " +
                                viewHolder?.tlpno?.getText().toString() +
                                "\n" +
                                "Chainage.: " +
                                viewHolder?.chainage?.getText().toString() +
                                "\n" +
                                "Location.: " +
                                viewHolder?.location?.getText().toString() +
                                "\n" +
                                "TYPE OF Cable.: " +

                                ( if (viewHolder!!.sp_for_typecable.selectedItem == "SELECT TYPE OF CABLE") "No Cable" else viewHolder!!.sp_for_typecable.selectedItem) +
                                "\n" +
                                "Photos: " + if (viewHolder!!.Photo.getTag() == null) "Not Uploaded" else "Uploaded"


                        TLPList.add(mStringBuilder)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else
                    return@OnClickListener
            }
            val alertDialog = AlertDialog.Builder(getActivity())
            val inflater = getActivity()!!.layoutInflater
            alertDialog.setTitle("FINAL SUBMIT \n check entries before submitting data")
            val convertView = inflater.inflate(R.layout.layout_preview_list, null)
            alertDialog.setView(convertView)
            alertDialog.setPositiveButton(getString(R.string.submit)) { dialogInterface, i ->
                dialogInterface.cancel()
               // handle_submit_btn(clientName,contractorName,sectionName,sectionNo)
            }

            alertDialog.setNegativeButton("Cancel") { dialogInterface, i -> dialogInterface.cancel() }
            val lv = convertView.findViewById<ListView>(R.id.lv_preview)
            lv.setClickable(false)
            val adapter = ArrayAdapter<String>(getActivity()!!, android.R.layout.simple_list_item_1, TLPList)
            lv.setAdapter(adapter)
            alertDialog.show()

        })

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun finalsubmitfinaltableentry(clientName: String, contractorName: String, sectionName: String,
                                           sectionNo: String, diameter: String, specification: String, length: String,
                                           finalTlpno: String, finalChainage: String, finalLocation: String, finalCableType: String,
                                           finalAttachment: String, tlpIdentificationNo: String, finalRgFm: String,
                                           finalRgTsPainting: String, finalRgCableTerminals: String, finalNoofcableused: String,
                                           arr1cableno: String, arr1typecable: String, finalNoofthermitused: String,
                                           arr2thermitno: String, arr2thermittype: String, finalRgHolidaychk: String,
                                           finalRgCChk: String, anodeIdentificationNo: String, tpeAnode: String,
                                           installationDepth: String, distancePipeline: String, lengthtailCable: String,
                                           finalRgTermination: String)
    {

        val values = ContentValues()
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatted = current.format(formatter)

        values.put(DbConstant.finaltable.CLIENT_NAME, clientName)
        values.put(DbConstant.finaltable.CONTRACTOR_NAME, contractorName)
        values.put(DbConstant.finaltable.SECTION_NAME, sectionName)
        values.put(DbConstant.finaltable.SECTION_No, sectionNo)
        values.put(DbConstant.finaltable.DIAMETER, diameter)
        values.put(DbConstant.finaltable.SPECIFICATION, specification)
        values.put(DbConstant.finaltable.LENGTH, length)
        values.put(DbConstant.finaltable.TLP_NO, finalTlpno)
        values.put(DbConstant.finaltable.CHAINAGE, finalChainage)
        values.put(DbConstant.finaltable.LOCATION, finalLocation)
        values.put(DbConstant.finaltable.CABLE_TYPE, finalCableType)
        values.put(DbConstant.finaltable.TLP_ATTACHMENT, finalAttachment)

        values.put(DbConstant.finaltable.TLP_IDENTIFICATION_NO, tlpIdentificationNo)
        values.put(DbConstant.finaltable.FOUNDATION_MOUNTING, finalRgFm)
        values.put(DbConstant.finaltable.PAINTING_GASKET, finalRgTsPainting)
        values.put(DbConstant.finaltable.CABLE_TERMINALS,finalRgCableTerminals)
        values.put(DbConstant.finaltable.CABLE_NUMBER,finalNoofcableused)
        values.put(DbConstant.finaltable.CABLE_USED_TYPE,arr1typecable)
        values.put(DbConstant.finaltable.CABLE_USED_NO,arr1cableno)
        values.put(DbConstant.finaltable.THERIT_NUMBER,finalNoofthermitused)
        values.put(DbConstant.finaltable.CABLE_THERMIT_TYPE,arr2thermittype)
        values.put(DbConstant.finaltable.CABLE_THERMIT_USED_NO,arr2thermitno)

        values.put(DbConstant.finaltable.HOLIDAY_CHECK,finalRgHolidaychk)
        values.put(DbConstant.finaltable.CONTINUITY_CHECK,finalRgCChk)
        values.put(DbConstant.finaltable.ANODE_IDENTIFICATION_NO,anodeIdentificationNo)
        values.put(DbConstant.finaltable.ANODE_TYPE,tpeAnode)
        values.put(DbConstant.finaltable.INS_DEPTH,installationDepth)
        values.put(DbConstant.finaltable.DISTANCE_PIPELINE,distancePipeline)
        values.put(DbConstant.finaltable.LENTH_TAIL_CABLE,lengthtailCable)
        values.put(DbConstant.finaltable.TERMINATION,finalRgTermination)
        values.put(DbConstant.finaltable.SERVER_STATUS,"pending")
        values.put(DbConstant.finaltable.DATE_SUBMITED,formatted)  //formatted

        var id = mDatabase.insert(DbConstant.finaltable.TABLE_FINAL, null, values)
        if (id > 0) {
            Toasty.success(mContext,"FINAL TABLE DATA  INSERTED Sucessfully").show()
            mView.final_submit_anodendtlp.visibility=View.GONE
            mView.final_savedraft_anodendtlp.visibility=View.GONE
            mView.noOfTLPInstallation.text=null
            mView.linearlayout_tlpinstalation.visibility=View.GONE
            mView.linearlayout_anodeinstallation.visibility=View.GONE
            var activity: MainActivity = getActivity() as MainActivity
            activity.resetspinervalue()
            mView.linear_header_tlpno.visibility=View.GONE



            Log.i("Insert FinalTAble", "Insert success")
        } else {
            Log.i("Insert FinalTAble", "Insert failed")
        }
    }
// added by kiran9jan2020

    @RequiresApi(Build.VERSION_CODES.O)
    private fun savetempdata(final_tlpno:String)
    {
        val activity: MainActivity = getActivity() as MainActivity;
        val values = activity.getdata()
        val contractorName = values[0]
        val clientName = values[1]
        var sectionName = values[2]
        var sectionNo = values[3]


   /*     if (adapterthermit!!.itemCount>0||adaptorcable!!.itemCount>0)
   {

       val maxList = checkMaxNum()

       var max = maxList.get(0)
       for (maxNumber in maxList) {
           if (maxNumber > max)
               max = maxNumber!!
       }*/



       /* if ((clientName == "") || (clientName == "Select") || (contractorName == "") || (contractorName == "Select") || (sectionName == "") || (sectionName == "Select") || (sectionNo == "") || (sectionNo == "Select")
                || (tlp_identification_no == "") || (finalcableno == "") || (finalthermitno=="") ) {
            Toasty.error(mContext, "Please fill Required Field").show()
            return
        }*/
       updaterowofcable(final_tlpno,clientName,contractorName,sectionName,sectionNo)
       updaterowofthermit(final_tlpno,clientName,contractorName,sectionName,sectionNo)
        if ((clientName == "") || (clientName == "Select") || (contractorName == "") || (contractorName == "Select") || (sectionName == "") || (sectionName == "Select") || (sectionNo == "") || (sectionNo == "Select")
                || (tlp_identification_no == "") || (finalcableno == "") || (finalthermitno=="") ) {
            Toasty.error(mContext, "Please fill Required Field").show()
            return
        }
        else
        {


            Toasty.warning(mContext, "final data Draft Saved Successfully ", Toast.LENGTH_LONG).show()
            final_submit_anodendtlp.visibility=View.VISIBLE
            final_savedraft_anodendtlp.visibility=View.INVISIBLE

        }




    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updaterowofthermit(final_tlpno:String, clientName: String, contractorName: String, sectionName: String, sectionNo: String) {


        tlp_identification_no=if (mView.tlp_id_no.text.toString()=="") "NA" else mView.tlp_id_no.text.toString()
        var rg_foundation_mounting=mView.foundation_ts_mounting.checkedRadioButtonId
        radiobtn=mView.findViewById(rg_foundation_mounting)
        final_rg_fm=radiobtn.text.toString()

        var rg_ts_painting=mView.rg_ts_painting.checkedRadioButtonId
        radiobtn=mView.findViewById(rg_ts_painting)
        final_rg_ts_painting=radiobtn.text.toString()

        var rg_cable_terminals=mView.rg_cable_terminals.checkedRadioButtonId
        radiobtn=mView.findViewById(rg_cable_terminals)
        final_rg_cable_terminals=radiobtn.text.toString()

        var rg_holidaychk=mView.rg_holidaychk.checkedRadioButtonId
        radiobtn=mView.findViewById(rg_holidaychk)
        final_rg_holidaychk=radiobtn.text.toString()

        var rg_c_chk=mView.rg_c_chk.checkedRadioButtonId
        radiobtn=mView.findViewById(rg_c_chk)
        final_rg_c_chk=radiobtn.text.toString()

        anode_identification_no=if (mView.et_anode_id.text.toString()=="") "NA" else mView.et_anode_id.text.toString()
        tpe_anode=if (mView.sp_sacrificial_anode.selectedItem=="Select") "NA" else mView.sp_sacrificial_anode.selectedItem.toString()

        installation_depth=if (mView.et_ins_depth.text.toString()=="") "NA" else mView.et_ins_depth.text.toString()
        distance_pipeline=if (mView.et_distance_pipeline.text.toString()=="") "NA" else mView.et_distance_pipeline.text.toString()
        lengthtail_cable=if (mView.et_length_tcable.text.toString()=="") "NA" else mView.et_length_tcable.text.toString()

        var rg_termination=mView.rg_termination.checkedRadioButtonId
        radiobtn=mView.findViewById(rg_termination)
        final_rg_termination=radiobtn.text.toString()

        if (insertInstallationdetails) {
            val values = ContentValues()
            values.put(DbConstant.InstallationOtherDetails.CLIENT_NAME, clientName)
            values.put(DbConstant.InstallationOtherDetails.CONTRACTOR_NAME, contractorName)
            values.put(DbConstant.InstallationOtherDetails.SECTION_NAME, sectionName)
            values.put(DbConstant.InstallationOtherDetails.SECTION_No, sectionNo)
            values.put(DbConstant.InstallationOtherDetails.TLP_NO, final_tlpno)
            values.put(DbConstant.InstallationOtherDetails.TLP_IDENTIFICATION_NO, tlp_identification_no)
            values.put(DbConstant.InstallationOtherDetails.FOUNDATION_MOUNTING, final_rg_fm)
            values.put(DbConstant.InstallationOtherDetails.PAINTING_GASKET, final_rg_ts_painting)
            values.put(DbConstant.InstallationOtherDetails.CABLE_TERMINALS,final_rg_cable_terminals)
            values.put(DbConstant.InstallationOtherDetails.HOLIDAY_CHECK,final_rg_holidaychk)
            values.put(DbConstant.InstallationOtherDetails.CONTINUITY_CHECK,final_rg_c_chk)
            values.put(DbConstant.InstallationOtherDetails.ANODE_IDENTIFICATION_NO,anode_identification_no)
            values.put(DbConstant.InstallationOtherDetails.ANODE_TYPE,tpe_anode)
            values.put(DbConstant.InstallationOtherDetails.INS_DEPTH,installation_depth)
            values.put(DbConstant.InstallationOtherDetails.DISTANCE_PIPELINE,distance_pipeline)
            values.put(DbConstant.InstallationOtherDetails.LENTH_TAIL_CABLE,lengthtail_cable)
            values.put(DbConstant.InstallationOtherDetails.TERMINATION,final_rg_termination)


            var id = mDatabase.insert(DbConstant.InstallationOtherDetails.TABLE_OTHER_INSTALLATIONDETAILS, null, values)
            if (id > 0) {
                Log.i("Insert INS Details", "Insert success")
            } else {
                Log.i("Insert INS Details", "Insert failed")
            }
        }


        for (i in 1..(adapterthermit?.itemCount ?: 0)) {
            viewHolderthermit = mView.rv_thermitweilding.findViewHolderForAdapterPosition(i - 1) as TLPCAbleAdapter.Holder?
            if ((if (viewHolderthermit != null) viewHolderthermit!!.cableno.getText().length else 0) > 0) {
                try {

                    Log.e("Loop", i.toString())
                    val cable_no = if (viewHolderthermit!!.cableno.toString() == "") "NA" else viewHolderthermit!!.cableno.text.toString()


                    val typcablecable = if (viewHolderthermit!!.sp_cable_type.selectedItem.toString() == "SELECT TYPE OF CABLE") "NA" else viewHolderthermit!!.sp_cable_type.selectedItem.toString()

                    saveContentConstantValues(i,final_tlpno, DbConstant.TemptableNAme.TABLE_TEMP_THERMIT,
                            cable_no, typcablecable,clientName,contractorName,sectionName,sectionNo,tlp_identification_no,final_rg_fm,final_rg_ts_painting,final_rg_cable_terminals,final_rg_holidaychk,final_rg_c_chk,anode_identification_no, tpe_anode,installation_depth,distance_pipeline.toUpperCase(),lengthtail_cable,final_rg_termination)



                    if(i== adapterthermit!!.itemCount)
                    {
                        //mView.noOfTLPInstallation.setText("")
                        mView.final_submit_anodendtlp.visibility=View.VISIBLE
                        mView.final_savedraft_anodendtlp.visibility=View.GONE


                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else
                return

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updaterowofcable(final_tlpno:String, clientName: String, contractorName: String, sectionName: String, sectionNo: String) {


         tlp_identification_no=if (mView.tlp_id_no.text.toString()=="") "NA" else mView.tlp_id_no.text.toString()
         var rg_foundation_mounting=mView.foundation_ts_mounting.checkedRadioButtonId
         radiobtn=mView.findViewById(rg_foundation_mounting)
         final_rg_fm=radiobtn.text.toString()

         var rg_ts_painting=mView.rg_ts_painting.checkedRadioButtonId
         radiobtn=mView.findViewById(rg_ts_painting)
         final_rg_ts_painting=radiobtn.text.toString()

         var rg_cable_terminals=mView.rg_cable_terminals.checkedRadioButtonId
         radiobtn=mView.findViewById(rg_cable_terminals)
         final_rg_cable_terminals=radiobtn.text.toString()

         var rg_holidaychk=mView.rg_holidaychk.checkedRadioButtonId
         radiobtn=mView.findViewById(rg_holidaychk)
         final_rg_holidaychk=radiobtn.text.toString()

         var rg_c_chk=mView.rg_c_chk.checkedRadioButtonId
         radiobtn=mView.findViewById(rg_c_chk)
         final_rg_c_chk=radiobtn.text.toString()

         anode_identification_no=if (mView.et_anode_id.text.toString()=="") "NA" else mView.et_anode_id.text.toString()
         tpe_anode=if (mView.sp_sacrificial_anode.selectedItem=="Select") "NA" else mView.sp_sacrificial_anode.selectedItem.toString()

         installation_depth=if (mView.et_ins_depth.text.toString()=="") "NA" else mView.et_ins_depth.text.toString()
         distance_pipeline=if (mView.et_distance_pipeline.text.toString()=="") "NA" else mView.et_distance_pipeline.text.toString()
         lengthtail_cable=if (mView.et_length_tcable.text.toString()=="") "NA" else mView.et_length_tcable.text.toString()

         var rg_termination=mView.rg_termination.checkedRadioButtonId
         radiobtn=mView.findViewById(rg_termination)
         final_rg_termination=radiobtn.text.toString()
         if (insertInstallationdetails) {
             val values = ContentValues()
             values.put(DbConstant.InstallationOtherDetails.CLIENT_NAME, clientName)
             values.put(DbConstant.InstallationOtherDetails.CONTRACTOR_NAME, contractorName)
             values.put(DbConstant.InstallationOtherDetails.SECTION_NAME, sectionName)
             values.put(DbConstant.InstallationOtherDetails.SECTION_No, sectionNo)
             values.put(DbConstant.InstallationOtherDetails.TLP_NO, final_tlpno)
             values.put(DbConstant.InstallationOtherDetails.TLP_IDENTIFICATION_NO, tlp_identification_no)
             values.put(DbConstant.InstallationOtherDetails.FOUNDATION_MOUNTING, final_rg_fm)
             values.put(DbConstant.InstallationOtherDetails.PAINTING_GASKET, final_rg_ts_painting)
             values.put(DbConstant.InstallationOtherDetails.CABLE_TERMINALS,final_rg_cable_terminals)
             values.put(DbConstant.InstallationOtherDetails.HOLIDAY_CHECK,final_rg_holidaychk)
             values.put(DbConstant.InstallationOtherDetails.CONTINUITY_CHECK,final_rg_c_chk)
             values.put(DbConstant.InstallationOtherDetails.ANODE_IDENTIFICATION_NO,anode_identification_no)
             values.put(DbConstant.InstallationOtherDetails.ANODE_TYPE,tpe_anode)
             values.put(DbConstant.InstallationOtherDetails.INS_DEPTH,installation_depth)
             values.put(DbConstant.InstallationOtherDetails.DISTANCE_PIPELINE,distance_pipeline)
             values.put(DbConstant.InstallationOtherDetails.LENTH_TAIL_CABLE,lengthtail_cable)
             values.put(DbConstant.InstallationOtherDetails.TERMINATION,final_rg_termination)


             var id = mDatabase.insert(DbConstant.InstallationOtherDetails.TABLE_OTHER_INSTALLATIONDETAILS, null, values)
             if (id > 0) {
                 Log.i("Insert INS Details", "Insert success")
             } else {
                 Log.i("Insert INS Details", "Insert failed")
             }
         }

         for (i in 1..(adaptorcable?.itemCount ?: 0)) {
             viewHoldercable = mView.rv_typecable.findViewHolderForAdapterPosition(i - 1) as TLPCAbleAdapter.Holder?
             if ((if (viewHoldercable != null) viewHoldercable!!.cableno.getText().length else 0) > 0) {
                 try {

                     Log.e("Loop", i.toString())
                     val cable_no = if (viewHoldercable!!.cableno.toString() == "") "NA" else viewHoldercable!!.cableno.text.toString()


                     val typcablecable = if (viewHoldercable!!.sp_cable_type.selectedItem.toString() == "SELECT TYPE OF CABLE") "NA" else viewHoldercable!!.sp_cable_type.selectedItem.toString()




                     saveContentConstantValues(i,final_tlpno, DbConstant.TemptableNAme.TABLE_TEMP_CABLE,
                             cable_no, typcablecable,clientName,contractorName,sectionName,sectionNo,tlp_identification_no,final_rg_fm,final_rg_ts_painting,final_rg_cable_terminals,final_rg_holidaychk,final_rg_c_chk,anode_identification_no, tpe_anode,installation_depth,distance_pipeline.toUpperCase(),lengthtail_cable,final_rg_termination)


                     if(i== adaptorcable!!.itemCount)
                     {
                         //mView.noOfTLPInstallation.setText("")
                         mView.final_submit_anodendtlp.visibility=View.VISIBLE
                         mView.final_savedraft_anodendtlp.visibility=View.GONE


                     }

                 } catch (e: Exception) {
                     e.printStackTrace()
                 }
             } else
                 return


         }





    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveContentConstantValues(i: Int, final_tlpno:String, tableTempCable: String, cableNo: String, typcablecable: String, clientName: String, contractorName: String, sectionName: String, sectionNo:  String, tlpIdentificationNo: String, finalRgFm: String, finalRgTsPainting: String, finalRgCableTerminals: String, finalRgHolidaychk: String, finalRgCChk: String, anodeIdentificationNo: String, tpeAnode: String, installationDepth: String, distance_pipeline: String, lengthtailCable: String, finalRgTermination: String) {
        val values=ContentValues()
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatted = current.format(formatter)
        values.put(DbConstant.TemptableNAme.DESCNUM_INSTALLTION,i)
        values.put(DbConstant.TemptableNAme.TLP_NO_OF_INSTALLTION,final_tlpno)
        values.put(DbConstant.TemptableNAme.NO_OF_CABLE_INSTALLATION,cableNo)
        values.put(DbConstant.TemptableNAme.TYPE_OF_CABLE_INSTALLATION,typcablecable)
        values.put(DbConstant.TemptableNAme.TLP_IDENTIFICATION_NO,tlpIdentificationNo)
        values.put(DbConstant.TemptableNAme.FOUNDATION_MOUNTING,finalRgFm)
        values.put(DbConstant.TemptableNAme.PAINTING_GASKET,finalRgTsPainting)
        values.put(DbConstant.TemptableNAme.CABLE_TERMINALS,finalRgCableTerminals)
        values.put(DbConstant.TemptableNAme.HOLIDAY_CHECK,finalRgHolidaychk)
        values.put(DbConstant.TemptableNAme.CONTINUITY_CHECK,finalRgCChk)
        values.put(DbConstant.TemptableNAme.ANODE_IDENTIFICATION_NO,anodeIdentificationNo)
        values.put(DbConstant.TemptableNAme.ANODE_TYPE,tpeAnode)
        values.put(DbConstant.TemptableNAme.INS_DEPTH,installationDepth)
        values.put(DbConstant.TemptableNAme.LENTH_TAIL_CABLE,lengthtailCable)
        values.put(DbConstant.TemptableNAme.TERMINATION,finalRgTermination)
        values.put(DbConstant.TemptableNAme.DISTANCE_PIPELINE,distance_pipeline)
        values.put(DbConstant.TemptableNAme.CLIENT_NAME, clientName)
        values.put(DbConstant.TemptableNAme.CONTRACTOR_NAME, contractorName)
        values.put(DbConstant.TemptableNAme.SECTION_NAME, sectionName)
        values.put(DbConstant.TemptableNAme.SECTION_No, sectionNo)
        values.put(DbConstant.TemptableNAme.DATE_SUBMITTED,formatted)





        val selection = ("(" + DbConstant.TemptableNAme.NO_OF_CABLE_INSTALLATION + " is null OR "
                + DbConstant.TemptableNAme.NO_OF_CABLE_INSTALLATION + "=? ) AND ( "
                + DbConstant.TemptableNAme.TYPE_OF_CABLE_INSTALLATION + " is null OR "
                + DbConstant.TemptableNAme.TYPE_OF_CABLE_INSTALLATION + " =? ) AND "
                + DbConstant.TemptableNAme.TLP_NO_OF_INSTALLTION + "=? AND "
                + DbConstant.TemptableNAme.DESCNUM_INSTALLTION + "=? ")

        val selectionArgs = arrayOf<String>(cableNo, typcablecable, final_tlpno, i.toString())



        val id = mDatabase.update(tableTempCable, values, selection, selectionArgs)
       //(TAG, "updateFirstPage: UPDATED TABLE : $tableTempCable ID: $id")
        if (id == 0) {
            values.put(DbConstant.TemptableNAme.SERVER_STATUS,"Pending")
            val id2 = mDatabase.insertOrThrow(tableTempCable, null, values)
        //    i(TAG, "updateFirstPage: INSERTED TABLE : $tableTempCable ID: $id2")
        }

    }


    private fun checkMaxNum(): java.util.ArrayList<Int> {
        val maxList = java.util.ArrayList<Int>()
    maxList.add(adapterthermit!!.getItemCount())
        maxList.add(adaptorcable!!.getItemCount())

        return maxList
    }


   /* // added by kiran 7jan22020
     @RequiresApi(Build.VERSION_CODES.O)
     private fun finalsaveContentValues(i: String,clientName: String,contractorName: String,sectionName: String,sectionNo:  String, cableNo: String, typcablecable: String, tlpIdentificationNo: String, finalRgFm: String, finalRgTsPainting: String, finalRgCableTerminals: String, finalRgHolidaychk: String, finalRgCChk: String, anodeIdentificationNo: String, tpeAnode: String, installationDepth: String, distance_pipeline: String, lengthtailCable: String, finalRgTermination: String) {
       val current = LocalDateTime.now()
       val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
       val formatted = current.format(formatter)
       val values=ContentValues()
       values.put(DbConstant.TempTLPEntry.DESC_NUM,i)
       values.put(DbConstant.TempTLPEntry.CABLE_USED_NO,cableNo)
       values.put(DbConstant.TempTLPEntry.CABLE_USED_TYPE,typcablecable)
       values.put(DbConstant.TempTLPEntry.TLP_IDENTIFICATION_NO,tlpIdentificationNo)

       values.put(DbConstant.TempTLPEntry.FOUNDATION_MOUNTING,finalRgFm)
       values.put(DbConstant.TempTLPEntry.PAINTING_GASKET,finalRgTsPainting)
       values.put(DbConstant.TempTLPEntry.CABLE_TERMINALS,finalRgCableTerminals)
       values.put(DbConstant.TempTLPEntry.HOLIDAY_CHECK,finalRgHolidaychk)
       values.put(DbConstant.TempTLPEntry.CONTINUITY_CHECK,finalRgCChk)
       values.put(DbConstant.TempTLPEntry.ANODE_IDENTIFICATION_NO,anodeIdentificationNo)
       values.put(DbConstant.TempTLPEntry.ANODE_TYPE,tpeAnode)
       values.put(DbConstant.TempTLPEntry.INS_DEPTH,installationDepth)
       values.put(DbConstant.TempTLPEntry.LENTH_TAIL_CABLE,lengthtailCable)
       values.put(DbConstant.TempTLPEntry.TERMINATION,finalRgTermination)
       values.put(DbConstant.TempTLPEntry.DISTANCE_PIPELINE,distance_pipeline)
       //added
       var condition= arrayOf<String>(clientName,contractorName,sectionName,sectionNo,i.toString())
       var id=mDatabase.update(DbConstant.TempTLPEntry.TABLE_TEMP_TLP,values," "+DbConstant.TempTLPEntry.CLIENT_NAME+"=? AND "+DbConstant.TempTLPEntry.CONTRACTOR_NAME+"=? AND "+DbConstant.TempTLPEntry.SECTION_NAME+"=? AND "+DbConstant.TempTLPEntry.SECTION_No+"=? AND "+DbConstant.TempTLPEntry.DESC_NUM+"=?",condition)
       Log.e("Update",""+id)
       if(id==0)
       {
           values.put(DbConstant.TempTLPEntry.SERVER_STATUS,"Pending")
           mDatabase.insert(DbConstant.TempTLPEntry.TABLE_TEMP_TLP,null,values)
       }

       //mView.noOfTLPInstallation.setText("")
       Toasty.success(mContext,"Final Data Draft Save successfully").show()

   }
*/






  /*  private fun handle_submit_btn(clientName: String, contractorName: String, sectionName: String, sectionNo: String) {
       // var query="INSERT INTO "+DbConstant.TLPEntry.TABLE_TLP+"  SELECT * FROM "+DbConstant.TemptableNAme.TABLE_TEMP_CABLE+" WHERE "+DbConstant.TemptableNAme.CLIENT_NAME+"='"+clientName+"' AND "+DbConstant.TemptableNAme.CONTRACTOR_NAME+"='"+contractorName+"' AND "+DbConstant.TemptableNAme.SECTION_NAME+"='"+sectionName+"' AND "+DbConstant.TemptableNAme.SECTION_No+"='"+sectionNo+"' AND "+DbConstant.TemptableNAme.SERVER_STATUS+"='Pending' ORDER BY "+DbConstant.TemptableNAme.DESCNUM_INSTALLTION


        var query="INSERT INTO "+DbConstant.TLPEntry.TABLE_TLP+"  SELECT * FROM "+DbConstant.TempTLPEntry.TABLE_TEMP_TLP+" WHERE "+DbConstant.TempTLPEntry.CLIENT_NAME+"='"+clientName+"' AND "+DbConstant.TempTLPEntry.CONTRACTOR_NAME+"='"+contractorName+"' AND "+DbConstant.TempTLPEntry.SECTION_NAME+"='"+sectionName+"' AND "+DbConstant.TempTLPEntry.SECTION_No+"='"+sectionNo+"' AND "+DbConstant.TemptableNAme.SERVER_STATUS+"='Pending' ORDER BY "+DbConstant.TempTLPEntry.DESC_NUM
        Log.e("Query",query)
        var id =mDatabase.rawQuery(query,null )

        var values=ContentValues()
        values.put(DbConstant.TempTLPEntry.SERVER_STATUS,"Done")
        var condition= arrayOf<String>(clientName,contractorName,sectionName,sectionNo,"Pending")
        var updateid=mDatabase.update(DbConstant.TempTLPEntry.TABLE_TEMP_TLP,values," "+DbConstant.TempTLPEntry.CLIENT_NAME+"=? AND "+DbConstant.TempTLPEntry.CONTRACTOR_NAME+"=? AND "+DbConstant.TempTLPEntry.SECTION_NAME+"=? AND "+DbConstant.TempTLPEntry.SECTION_No+"=? AND "+DbConstant.TempTLPEntry.SERVER_STATUS+"=?",condition)
        if(updateid>0)
        {
           // mView.noOfTLPInstallation.setText("")
              mView.linearlayout_tlpinstalation.visibility=View.VISIBLE
            mView.final_submit_anodendtlp.visibility=View.VISIBLE
            Toasty.success(mContext,"Data Submitted Sucessfully").show()
        }


    }
*/
    private fun getLocation(): String {
        val loc = arrayOf<String>("")
        SmartLocation.with(mContext).location().oneFix().start { location ->
            Log.i(TAG, "onLocationUpdated: lattitude:" + location.latitude + "Longitude" + location.longitude)
            loc[0] = location.latitude.toString() + " / " + location.longitude
        }
        Log.e("location",loc[0])
        return loc[0]
    }


    @TargetApi(Build.VERSION_CODES.O)
    private fun saveContentValues(i: String, clientName: String, contractorName: String, sectionName: String, sectionNo: String, diameter: String, specification: String, length: String, tlpNo: String, chainage: String, location: String, btnTag: String, clientId: String,valuecable: String) {


        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")  // "yyyy-MM-dd HH:mm:ss.SSS"
        val formatted = current.format(formatter)
        val values=ContentValues()
        values.put(DbConstant.TempTLPEntry.CLIENT_NAME,clientName)
        values.put(DbConstant.TempTLPEntry.CONTRACTOR_NAME,contractorName)
        values.put(DbConstant.TempTLPEntry.SECTION_NAME,sectionName)
        values.put(DbConstant.TempTLPEntry.SECTION_No,sectionNo)
        values.put(DbConstant.TempTLPEntry.DESC_NUM,i)
        values.put(DbConstant.TempTLPEntry.CLIENT_NAME,clientName)
        values.put(DbConstant.TempTLPEntry.CONTRACTOR_NAME,contractorName)
        values.put(DbConstant.TempTLPEntry.SECTION_NAME,sectionName)
        values.put(DbConstant.TempTLPEntry.SECTION_No,sectionNo)
        values.put(DbConstant.TempTLPEntry.DIAMETER,diameter)
        values.put(DbConstant.TempTLPEntry.SPECIFICATION,specification)
        values.put(DbConstant.TempTLPEntry.LENGTH,length)
        values.put(DbConstant.TempTLPEntry.TLP_NO,tlpNo)
        values.put(DbConstant.TempTLPEntry.CHAINAGE,chainage)
        values.put(DbConstant.TempTLPEntry.LOCATION,location)
        values.put(DbConstant.TempTLPEntry.PHOTO,btnTag)
        values.put(DbConstant.TempTLPEntry.DATE_OF_TLP,formatted)
        values.put(DbConstant.TempTLPEntry.CLIENT_ID,clientId)
        //added
        values.put(DbConstant.TempTLPEntry.CABLE_TYPE,valuecable)
     //   values.put(DbConstant.TempTLPEntry.TYPEOFCABLE,typecable)


        //Toasty.info(mContext,""+latLong).show()
        var condition= arrayOf<String>(clientName,contractorName,sectionName,sectionNo,i.toString())
        var id=mDatabase.update(DbConstant.TempTLPEntry.TABLE_TEMP_TLP,values," "+DbConstant.TempTLPEntry.CLIENT_NAME+"=? AND "+DbConstant.TempTLPEntry.CONTRACTOR_NAME+"=? AND "+DbConstant.TempTLPEntry.SECTION_NAME+"=? AND "+DbConstant.TempTLPEntry.SECTION_No+"=? AND "+DbConstant.TempTLPEntry.DESC_NUM+"=?",condition)
        Log.e("Update",""+id)
        if(id==0)
        {
            values.put(DbConstant.TempTLPEntry.SERVER_STATUS,"Pending")
            mDatabase.insert(DbConstant.TempTLPEntry.TABLE_TEMP_TLP,null,values)
        }

        //mView.noOfTLPInstallation.setText("")
        Toasty.success(mContext,"Draft Save successfully").show()



    }


}
