package com.icspl.tlpinstallation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.icspl.tlpinstallation.Adaptor.SyncAdaptor
import com.icspl.tlpinstallation.DBHelper.DbHelper
import com.icspl.tlpinstallation.MainActivity
import com.icspl.tlpinstallation.Model.ParentSyncModel

import com.icspl.tlpinstallation.R
import com.icspl.tlpinstallation.handler.ApiService
import com.icspl.tlpinstallation.utils.Comman
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.sync_fragment.view.*

import java.util.ArrayList

class SyncFragment : Fragment() {
    internal lateinit var mView: View
    internal lateinit var list: List<ParentSyncModel>
    private lateinit var mDatabase: SQLiteDatabase
    private var mHelper: DbHelper? = null
    private lateinit var mContext: Context
    private var mActivity: FragmentActivity? = null
    private lateinit var preferences: SharedPreferences
    private var mService: ApiService? = null
    private var adaptor:SyncAdaptor?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.sync_fragment, container, false)

        var activity: MainActivity = getActivity() as MainActivity;
         activity.addsectionname.visibility=View.GONE
         activity.addsectionno.visibility=View.GONE
         activity.linearlength.visibility=View.GONE
         activity.linearspecification.visibility=View.GONE
         activity.lineardiameter.visibility=View.GONE
        mContext = mView.context
        mActivity = (activity) as FragmentActivity
        mHelper = DbHelper(mContext)
        mDatabase = mHelper!!.writableDatabase
        list=ArrayList()

        preferences = mContext.getSharedPreferences(getString(R.string.user), Context.MODE_PRIVATE)
        mService = Comman.getAPI()
        mView.clickbtn.setOnClickListener {
            getLoclPendingdata()
        }
        return mView
    }
    private fun getLoclPendingdata() {
        (list as ArrayList<ParentSyncModel>).clear()
        var activity: MainActivity = getActivity() as MainActivity;
        var values = activity.getdata()
        var contractorName = values[0]
        var clientName = values[1]
        var sectionName = values[2]
        var sectionNo = values[3]
        val query = "select * from table_final where client_name='"+clientName+"' and contractor_name='"+contractorName+"' and section_name='"+sectionName+"' and section_no='"+sectionNo+"' " +
                "and server_status='pending' group by date_submitted  order by date_submitted "


        var cursor=mDatabase.rawQuery(query,null)

        var model:ParentSyncModel
        if(cursor.count>0)
        {

            (list as ArrayList<ParentSyncModel>).clear()
            for(i in 1..cursor.count)
            {
                cursor.moveToNext()
                var tlp_no=cursor.getString(cursor.getColumnIndex("tlp_no"))
                var chainage=cursor.getString(cursor.getColumnIndex("chainage"))
                var location=cursor.getString(cursor.getColumnIndex("location"))
                var tlp_type=cursor.getString(cursor.getColumnIndex("cable_type"))
                var submited_date=cursor.getString(cursor.getColumnIndex("date_submitted"))
                // model= NewSyncModel(cursor.getString(cursor.getColumnIndex("tlp_no")),cursor.getString(cursor.getColumnIndex(DbConstant.TLPEntry.CHAINAGE)),cursor.getString(cursor.getColumnIndex(DbConstant.TLPEntry.LOCATION)),cursor.getString(cursor.getColumnIndex(DbConstant.TLPEntry.CABLE_TYPE)),cursor.getString(cursor.getColumnIndex(DbConstant.TemptableNAme.DATE_SUBMITTED)))

                model=ParentSyncModel(submited_date,tlp_no)
                (list as ArrayList<ParentSyncModel>).add(model)

            }
            if (list.size>0)
            {
                mView.rcpendingData.layoutManager = LinearLayoutManager(mContext)
                adaptor= SyncAdaptor(mContext,list)
                mView.rcpendingData.adapter = adaptor
                adaptor!!.notifyDataSetChanged()

            }
            else
            {
                Toasty.error(mContext,"You dont have any details to sync").show()
                return

            }
        }

        /*model=SyncModel("24-12-2019","MGL Demo","MGL WESTERN","test","test")
        (list as ArrayList<SyncModel>).add(model)
        mView.rcpendingData.layoutManager = LinearLayoutManager(mContext)
        adaptor= SyncAdaptor(mContext,list)
        mView.rvtlp.adapter = adaptor*/
    }


}
