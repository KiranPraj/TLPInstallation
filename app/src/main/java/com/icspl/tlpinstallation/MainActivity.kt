package com.icspl.tlpinstallation

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import android.Manifest
import android.app.AlertDialog
import android.content.*
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.os.Debug
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.FragmentActivity

import com.google.android.material.navigation.NavigationView
import com.icspl.tlpinstallation.DBHelper.DbHelper
import com.icspl.tlpinstallation.Model.CLientModel
import com.icspl.tlpinstallation.Model.ContractorModel
import com.icspl.tlpinstallation.constants.DbConstant
import com.icspl.tlpinstallation.fragments.SyncFragment
import com.icspl.tlpinstallation.fragments.TLPInstallation
import com.icspl.tlpinstallation.fragments.TestStationFragment
import com.icspl.tlpinstallation.handler.ApiService
import com.icspl.tlpinstallation.utils.Comman
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.PermissionRequestErrorListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tlp_installation.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    internal lateinit var navigationview: NavigationView
    internal lateinit var drawerlayout: DrawerLayout
    internal lateinit var toolbar: Toolbar
    internal lateinit var back_textview: TextView
    internal lateinit var drawerToggle: ActionBarDrawerToggle
    internal lateinit var contractor: SearchableSpinner


    private lateinit var mDatabase: SQLiteDatabase
    private var mHelper: DbHelper? = null
    private lateinit var mContext: Context
    private var mActivity: MainActivity? = null
    private lateinit var preferences: SharedPreferences
    private var mService: ApiService? = null
    private var insertSection:Boolean = true
    private var ClientId:String = ""
    private var ClientList:ArrayList<CLientModel.Dtqty>?=null
    private var ClientList1:ArrayList<ContractorModel.Dtqty1>?=null
    //private var ClientID: LinkedHashMap<String, String> = linkedMapOf()
    lateinit var addsectionname: AppCompatButton
    lateinit var addsectionno: AppCompatButton
    lateinit var lineardiameter: LinearLayoutCompat
    lateinit var linearspecification: LinearLayoutCompat
    lateinit var linearlength: LinearLayoutCompat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigationview = findViewById(R.id.navigation_view)
        toolbar = findViewById(R.id.toolbar)
        drawerlayout = findViewById(R.id.drawerlayout)
        back_textview = findViewById(R.id.back_textview_toolbar)
        contractor = findViewById(R.id.spContractor)
        setSupportActionBar(toolbar)
        mContext=this@MainActivity
        drawerToggle = ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.open_drawer, R.string.close_drawer)
        drawerToggle.isDrawerIndicatorEnabled
        drawerlayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        back_textview.setOnClickListener { onBackPressed() }
        requestStoragePermission()
        navigationview.setNavigationItemSelectedListener(this)
        toolbar.title = "TLP Installation"
        mHelper = DbHelper(mContext)
        mDatabase = mHelper!!.writableDatabase
        preferences = mContext.getSharedPreferences(getString(R.string.user), Context.MODE_PRIVATE)
        mService = Comman.getAPI()
        init()
        getviewId()
        supportFragmentManager.addOnBackStackChangedListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.tlp)
            if (fragment != null) {

                updateTitle(fragment)
            }
        }



    }

    private fun init() {
        mService!!.getClient().enqueue(object : Callback<CLientModel> {
            override fun onFailure(call: Call<CLientModel>, t: Throwable) {
                getClient();
            }

            override fun onResponse(call: Call<CLientModel>, response: Response<CLientModel>) {
                if(response.isSuccessful&& response.body()!!.dtqty!!.size>0)
                {
                    getSereverContracttor()
                    ClientList= response.body()!!.dtqty as ArrayList<CLientModel.Dtqty>?
                    for(i in 0..ClientList!!.size-1)
                    {
                        var value=ContentValues()
                        value.put(DbConstant.ClientEntry.CLIENT_ID, ClientList!!.get(i).clientID!!.toUpperCase())
                        value.put(DbConstant.ClientEntry.CLIENT_NAME, ClientList!!.get(i).clientName!!.toUpperCase())
                        mDatabase.replace(DbConstant.ClientEntry.TABLE_CLIENT,null,value)
                    }
                    getClient();
                }
                else
                {
                    getClient();
                }
            }
        })
        btnAddSectionName.setOnClickListener(View.OnClickListener {
            if((spClientName.selectedItem.toString()=="")||(spClientName.selectedItem.toString()=="Select"))
            {
                Toasty.info(mContext,"Please Select Client name").show()
            }
            else
            {
                sectionGroup.visibility=View.VISIBLE
            }

        })

        btnAddSectionNo.setOnClickListener(View.OnClickListener {
            try {
                if ((spSectionName.selectedItem.toString() == "") || (spSectionName.selectedItem.toString() == "Select")) {
                    Toasty.info(mContext, "Please Select section name").show()
                } else {
                    sectionNoGroup.visibility = View.VISIBLE
                }
            }
            catch (ex:Exception)
            {
                Toasty.error(mContext,"Please Select Client Name").show()
            }
        })
        btnsaveSectionName.setOnClickListener(View.OnClickListener {
            if(etSectionName.text.toString()=="")
            {
                Toasty.error(mContext,"Please Enter Section Name").show()
            }
            else
            {
                var values = ContentValues()
                values.put(DbConstant.SectionNameEntry.CLIENT_NAME,spClientName.selectedItem.toString())
                values.put(DbConstant.SectionNameEntry.CONTRACTOR_NAME,spContractor.selectedItem.toString())
                values.put(DbConstant.SectionNameEntry.SECTION_NAME,etSectionName.text.toString().toUpperCase())
                values.put(DbConstant.SectionNameEntry.SERVER_STATUS,"pending")
                var cursor=mDatabase.rawQuery("SELECT * FROM "+DbConstant.SectionNameEntry.TABLE_SECTION_NAME+" WHERE "+DbConstant.SectionNameEntry.CLIENT_NAME+"='"+spClientName.selectedItem.toString()+"' AND "+DbConstant.SectionNameEntry.CONTRACTOR_NAME+"='"+spContractor.selectedItem.toString()+"' AND "+DbConstant.SectionNameEntry.SECTION_NAME+"='"+etSectionName.text.toString()+"'",null);

                if(cursor==null||!cursor.moveToFirst()) {
                    var id=mDatabase.insert(DbConstant.SectionNameEntry.TABLE_SECTION_NAME, null, values)
                    if(id>0)
                    {
                        Toasty.success(mContext,"Section Name Add successfully").show()
                    }
                    getSectionName()
                    etSectionName.setText("")
                    sectionGroup.visibility=View.GONE

                }
                else
                {
                    Toasty.error(mContext,"Already added this Section Name").show()
                }
            }
        })

        btnsaveSectionNo.setOnClickListener(View.OnClickListener {
            if(etSectionNo.text.toString()=="")
            {
                Toasty.error(mContext,"Please Enter Section No").show()
            }
            else
            {
                var values = ContentValues()
                values.put(DbConstant.SectionNoEntry.CLIENT_NAME,spClientName.selectedItem.toString())
                values.put(DbConstant.SectionNoEntry.CONTRACTOR_NAME,spContractor.selectedItem.toString())
                values.put(DbConstant.SectionNoEntry.SECTION_NAME,spSectionName.selectedItem.toString())
                values.put(DbConstant.SectionNoEntry.SECTION_No,etSectionNo.text.toString().toUpperCase())
                values.put(DbConstant.SectionNoEntry.SERVER_STATUS,"pending")
                var cursor=mDatabase.rawQuery("SELECT * FROM "+DbConstant.SectionNoEntry.TABLE_SECTION_NO+" WHERE "+DbConstant.SectionNoEntry.CLIENT_NAME+"='"+spClientName.selectedItem.toString()+"' AND "+DbConstant.SectionNoEntry.CONTRACTOR_NAME+"='"+spContractor.selectedItem.toString()+"'  AND "+DbConstant.SectionNoEntry.SECTION_NAME+"='"+spSectionName.selectedItem.toString()+"' AND "+DbConstant.SectionNoEntry.SECTION_No+"='"+etSectionNo.text.toString()+"'",null);

                if(cursor==null||!cursor.moveToFirst())
                {
                    mDatabase.insert(DbConstant.SectionNoEntry.TABLE_SECTION_NO, null, values)
                    getSectionNo()
                    etSectionNo.setText("")
                    sectionNoGroup.visibility=View.GONE
                    Toasty.success(mContext,"Section No Add successfully").show()
                }
                else
                {
                    Toasty.error(mContext,"Already added add Section No").show()
                }
            }
        })

        spClientName.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0)
                {
                    getContractorName()
                }
                else
                {

                }
            }
        }

        spContractor.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0)
                {
                    getSectionName()
                }
                else
                {

                }
            }
        }
        spSectionName.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0)
                {
                    getSectionNo()
                }
                else
                {

                }
            }
        }
        spSectionNo.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0)
                {
                    getSectionDetails()
                }
                else
                {

                }
            }
        }
    }

    private fun getSereverContracttor() {
        mService!!.getContractor().enqueue(object : Callback<ContractorModel> {
            override fun onFailure(call: Call<ContractorModel>, t: Throwable) {
                //etContractorName();
            }

            override fun onResponse(call: Call<ContractorModel>, response: Response<ContractorModel>) {
                if(response.isSuccessful&& response.body()!!.dtqty1!!.size>0)
                {
                    //getSereverContracttor()
                    ClientList1= response.body()!!.dtqty1 as ArrayList<ContractorModel.Dtqty1>?
                    for(i in 0..ClientList1!!.size-1)
                    {
                        var value=ContentValues()
                        value.put(DbConstant.ContractEntry.CONTRACTOR_ID, ClientList1!!.get(i).contractorID!!.toUpperCase())
                        value.put(DbConstant.ContractEntry.CONTRACTOR_NAME, ClientList1!!.get(i).contractorName!!.toUpperCase())
                        value.put(DbConstant.ContractEntry.CLIENT_NAME, ClientList1!!.get(i).clientName!!.toUpperCase())
                        value.put(DbConstant.ContractEntry.CLIENT_ID, ClientList1!!.get(i).clientID!!.toUpperCase())

                        mDatabase.replace(DbConstant.ContractEntry.TABLE_CONTRACTOR,null,value)

                    }
                   // getContractorName();
                }
                else
                {
                    //getContractorName();
                }
            }
        })
    }

    private fun getContractorName() {
        val dataAdapter = ArrayAdapter(mContext,
                android.R.layout.simple_spinner_item, DbHelper(mContext).getContractor(spClientName.selectedItem.toString())!!)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spContractor!!.adapter = dataAdapter
    }

    private fun getClient() {
        val dataAdapter = ArrayAdapter(mContext,
                android.R.layout.simple_spinner_item, DbHelper(mContext).getClients()!!)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spClientName!!.adapter = dataAdapter

    }
    private fun getSectionName() {
        val dataAdapter = ArrayAdapter(mContext,
                android.R.layout.simple_spinner_item, DbHelper(mContext).getSectionName(spClientName.selectedItem.toString(),spContractor.selectedItem.toString())!!)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spSectionName!!.adapter = dataAdapter
    }
    private fun getSectionNo() {
        val dataAdapter = ArrayAdapter(mContext,
                android.R.layout.simple_spinner_item, DbHelper(mContext).getSectionNo(spClientName.selectedItem.toString(),spContractor.selectedItem.toString(),spSectionName.selectedItem.toString())!!)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spSectionNo!!.adapter = dataAdapter
    }
    private fun getSectionDetails() {
        val mCursor = DbHelper(mContext).getbindSectionData(spClientName.selectedItem.toString(),spContractor.selectedItem.toString(),spSectionName.selectedItem.toString(),spSectionNo.selectedItem.toString())
        if(mCursor.count>0)
        {
            mCursor.moveToLast()
            insertSection=false;
            diameter.setText(mCursor.getString(mCursor.getColumnIndex(DbConstant.SectionEntry.DIAMETER)))
            length.setText(mCursor.getString(mCursor.getColumnIndex(DbConstant.SectionEntry.LENGTH)))
            specification.setText(mCursor.getString(mCursor.getColumnIndex(DbConstant.SectionEntry.SPECIFICATION)))
            mCursor?.close()
        }
        else
        {
            insertSection=true
            diameter.setText("")
            length.setText("")
            specification.setText("")
        }
    }

   fun getviewId() {
       addsectionname=findViewById(R.id.btnAddSectionName)
       addsectionno=findViewById(R.id.btnAddSectionNo)
       lineardiameter=findViewById(R.id.linear_diameter)
       linearspecification=findViewById(R.id.linear_specification)
       linearlength=findViewById(R.id.linear_length)


   }
    fun resetspinervalue()
    {
        spClientName.setSelection(0)
        spContractor.setSelection(0)
        spSectionName.setSelection(0)
        spSectionNo.setSelection(0)
        diameter.text!!.clear()
        specification.text!!.clear()
        length.text!!.clear()
    }

    fun getdata(): Array<String> {

        var contractor=""
        try {
            contractor=spContractor.selectedItem.toString()
        }
        catch (ex:Exception)
        {

        }
        val clientname=spClientName.selectedItem.toString()
        if(clientname!="Select")
        {
            val mcursor=mDatabase.rawQuery("SELECT * FROM " + DbConstant.ClientEntry.TABLE_CLIENT +" WHERE "+DbConstant.ClientEntry.CLIENT_NAME+"='"+clientname+"'",null)
            mcursor.moveToLast()
            ClientId=mcursor.getString(mcursor.getColumnIndex(DbConstant.ClientEntry.CLIENT_ID))

        }

        var sectionname=""
        try {
            sectionname=spSectionName.selectedItem.toString()
        }
        catch (ex:Exception)
        {

        }
        var sectionno=""
        try {
            sectionno=spSectionNo.selectedItem.toString()
        }
        catch (ex:Exception)
        {

        }
        return arrayOf(contractor.toUpperCase(),clientname.toUpperCase(),sectionname.toUpperCase(),sectionno.toUpperCase(),diameter.text.toString().toUpperCase(),specification.text.toString().toUpperCase(),length.text.toString().toUpperCase(),ClientId)
    }

    private fun updateTitle(fragment: Fragment) {
        val name = fragment.javaClass.name
        if (name != null) {
            changetitlename(name)
        }
    }

    private fun changetitlename(name: String) {
        if (name == TLPInstallation::class.java.name) {
            toolbar.title = "TLP INSTALLATION"
        }
        if (name == TestStationFragment::class.java.name) {
            toolbar.title = "TEST STATION"
        }
        if(name=== SyncFragment::class.java.name)
        {
            toolbar.title="SYNC FRAGMENT"
        }
    }

    override fun onBackPressed() {
        if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
            drawerlayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }

    }


    private fun requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            val commit = supportFragmentManager.beginTransaction()
                                    .replace(R.id.tlp, TLPInstallation(), getString(R.string.teststation)).addToBackStack("TLP Installation")
                                    .commit()
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                        token.continuePermissionRequest()
                    }
                }).withErrorListener { Toast.makeText(applicationContext, "Error occurred! ", Toast.LENGTH_SHORT).show() }
                .onSameThread()
                .check()
    }

    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Need Permissions")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS") { dialog, which ->
            dialog.cancel()
            openSettings()
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.show()

    }

    // navigating user to app settings
    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }


    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.first_item -> {
                supportFragmentManager.beginTransaction().replace(R.id.tlp, TLPInstallation(), getString(R.string.tlp_installation))
                        .addToBackStack("TLP Installation").commit()
            }
            R.id.second_item -> {
                supportFragmentManager.beginTransaction().replace(R.id.tlp, TestStationFragment(), getString(R.string.teststation))
                        .addToBackStack("Test Station Installation").commit()
            }
            R.id.sync_item->
                    {

                supportFragmentManager.beginTransaction().replace(R.id.tlp, SyncFragment(), getString(R.string.sync))
                        .addToBackStack("Synced").commit()

            }
        }



        drawerlayout.closeDrawer(GravityCompat.START)
        return true

    }

}
