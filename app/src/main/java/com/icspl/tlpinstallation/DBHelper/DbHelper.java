package com.icspl.tlpinstallation.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.icspl.tlpinstallation.constants.DbConstant;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "TLP.db";
    private static final int DB_VERSION = 1;
    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    private String[] temp_tables = {DbConstant.TemptableNAme.TABLE_TEMP_CABLE,
            DbConstant.TemptableNAme.TABLE_TEMP_THERMIT};

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLIENT);
        db.execSQL(CREATE_CONTRACTOR);
        db.execSQL(CREATE_SECTION_NAME);
        db.execSQL(CREATE_SECTION_NO);
        db.execSQL(CREATE_SECTION);
        db.execSQL(CREATE_TEMP_TLP);
        db.execSQL(CREATE_TLP);
        db.execSQL(CREATE_OTHERINSTALLATION_DETAILS);
        db.execSQL(CREATE_FINAL_TABLE);


        for (String temp_table : temp_tables) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + temp_table + " ("
                    + DbConstant.TemptableNAme._ID + " integer primary key autoincrement" + " , "
                    + DbConstant.TemptableNAme.TLP_NO_OF_INSTALLTION + " VARCHAR(250) " + " , "
                    + DbConstant.TemptableNAme.TYPE_OF_CABLE_INSTALLATION + " VARCHAR(250) " + " , "
                    + DbConstant.TemptableNAme.NO_OF_CABLE_INSTALLATION + " VARCHAR(250) " + " , "
                    + DbConstant.TemptableNAme.DESCNUM_INSTALLTION + " integer " + " , "
                    + DbConstant.TemptableNAme.SECTION_NAME + " VARCHAR(250) " + " , "
                    + DbConstant.TemptableNAme.SECTION_No + " VARCHAR(250) " + " , "
                    + DbConstant.TemptableNAme.CLIENT_NAME + " VARCHAR(250) " + " , "
                    + DbConstant.TemptableNAme.CONTRACTOR_NAME+ " VARCHAR(250) " + " , "
                    +DbConstant.TemptableNAme.TLP_IDENTIFICATION_NO+" varchar(255)"+" ,"
                    +DbConstant.TemptableNAme.FOUNDATION_MOUNTING+" varchar(255)"+" ,"
                    +DbConstant.TemptableNAme.PAINTING_GASKET+" varchar(255)"+" ,"
                    +DbConstant.TemptableNAme.CABLE_TERMINALS+" varchar(255)"+" ,"
                    +DbConstant.TemptableNAme.HOLIDAY_CHECK+" varchar(255)"+" ,"
                    +DbConstant.TemptableNAme.CONTINUITY_CHECK+" varchar(255)"+" ,"
                    +DbConstant.TemptableNAme.ANODE_IDENTIFICATION_NO+" varchar(255)"+" ,"
                    +DbConstant.TemptableNAme.ANODE_TYPE+" varchar(255)"+" ,"
                    +DbConstant.TemptableNAme.INS_DEPTH+" varchar(255)"+" ,"
                    +DbConstant.TemptableNAme.DISTANCE_PIPELINE+" varchar(255)"+" ,"
                    +DbConstant.TemptableNAme.LENTH_TAIL_CABLE+" varchar(255)"+" ,"
                    +DbConstant.TemptableNAme.TERMINATION+" varchar(255)"+" ,"

                    +"date_submitted datetime ,"

                    + DbConstant.TemptableNAme.SERVER_STATUS + " VARCHAR(250)" + ") ");

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    private static final String CREATE_SECTION_NAME="CREATE TABLE IF NOT EXISTS "+ DbConstant.SectionNameEntry.TABLE_SECTION_NAME+"("
            +DbConstant.SectionNameEntry.SECTION_NAME+" varchar(255)"+","
            +DbConstant.SectionNameEntry.TLP+" varchar(255)"+" ,"
            +DbConstant.SectionNameEntry.CLIENT_ID+" varchar(255)"+" ,"
            +DbConstant.SectionNameEntry.CLIENT_NAME+" varchar(255)"+" ,"
            +DbConstant.SectionNameEntry.CONTRACTOR_ID+" varchar(255)"+" ,"
            +DbConstant.SectionNameEntry.CONTRACTOR_NAME+" varchar(255)"+" ,"
            + DbConstant.SectionNameEntry.SERVER_STATUS + " VARCHAR(250) " + "); ";

    private static final String CREATE_SECTION_NO="CREATE TABLE IF NOT EXISTS "+ DbConstant.SectionNoEntry.TABLE_SECTION_NO+"("
            +DbConstant.SectionNoEntry.SECTION_No+" varchar(255)"+","
            +DbConstant.SectionNoEntry.SECTION_NAME+" varchar(255)"+","
            +DbConstant.SectionNoEntry.TLP+" varchar(255)"+" ,"
            +DbConstant.SectionNoEntry.CLIENT_ID+" varchar(255)"+" ,"
            +DbConstant.SectionNoEntry.CLIENT_NAME+" varchar(255)"+" ,"
            +DbConstant.SectionNoEntry.CONTRACTOR_ID+" varchar(255)"+" ,"
            +DbConstant.SectionNoEntry.CONTRACTOR_NAME+" varchar(255)"+" ,"
            + DbConstant.SectionNoEntry.SERVER_STATUS + " VARCHAR(250)   " + "); ";

    private static final String CREATE_SECTION="CREATE TABLE IF NOT EXISTS "+ DbConstant.SectionEntry.TABLE_SECTION+"("
            +DbConstant.SectionEntry.SECTION_No+" varchar(255)"+","
            +DbConstant.SectionEntry.SECTION_NAME+" varchar(255)"+","
            +DbConstant.SectionEntry.DIAMETER+" varchar(255)"+","
            +DbConstant.SectionEntry.SPECIFICATION+" varchar(255)"+","
            +DbConstant.SectionEntry.LENGTH+" varchar(255)"+","
            +DbConstant.SectionEntry.TLP+" varchar(255)"+" ,"
            +DbConstant.SectionEntry.CLIENT_ID+" varchar(255)"+" ,"
            +DbConstant.SectionEntry.CLIENT_NAME+" varchar(255)"+" ,"
            +DbConstant.SectionEntry.CONTRACTOR_ID+" varchar(255)"+" ,"
            +DbConstant.SectionEntry.CONTRACTOR_NAME+" varchar(255)"+" ,"
            + DbConstant.SectionEntry.SERVER_STATUS + " VARCHAR(250)   " + "); ";
// added by kiran
private static final String CREATE_OTHERINSTALLATION_DETAILS="CREATE TABLE IF NOT EXISTS "+ DbConstant.InstallationOtherDetails.TABLE_OTHER_INSTALLATIONDETAILS+"("
        +DbConstant.InstallationOtherDetails.SECTION_NAME+" varchar(255)"+","
        +DbConstant.InstallationOtherDetails.SECTION_No+" varchar(255)"+" ,"
        +DbConstant.InstallationOtherDetails.CLIENT_NAME+" varchar(255)"+" ,"
        +DbConstant.InstallationOtherDetails.CONTRACTOR_NAME+" varchar(255)"+" ,"
        +DbConstant.InstallationOtherDetails.TLP_IDENTIFICATION_NO+" varchar(255)"+" ,"
        +DbConstant.InstallationOtherDetails.FOUNDATION_MOUNTING+" varchar(255)"+" ,"
        +DbConstant.InstallationOtherDetails.PAINTING_GASKET+" varchar(255)"+" ,"
        +DbConstant.InstallationOtherDetails.CABLE_TERMINALS+" varchar(255)"+" ,"
        +DbConstant.InstallationOtherDetails.HOLIDAY_CHECK+" varchar(255)"+" ,"
        +DbConstant.InstallationOtherDetails.CONTINUITY_CHECK+" varchar(255)"+" ,"
        +DbConstant.InstallationOtherDetails.ANODE_IDENTIFICATION_NO+" varchar(255)"+" ,"
        +DbConstant.InstallationOtherDetails.ANODE_TYPE+" varchar(255)"+" ,"
        +DbConstant.InstallationOtherDetails.INS_DEPTH+" varchar(255)"+" ,"
        +DbConstant.InstallationOtherDetails.DISTANCE_PIPELINE+" varchar(255)"+" ,"
        +DbConstant.InstallationOtherDetails.LENTH_TAIL_CABLE+" varchar(255)"+" ,"
        + DbConstant.InstallationOtherDetails.TERMINATION + " VARCHAR(250) " + "); ";


    private static final String CREATE_TEMP_TLP="CREATE TABLE IF NOT EXISTS "+ DbConstant.TempTLPEntry.TABLE_TEMP_TLP+"("
            +DbConstant.TempTLPEntry.SECTION_No+" varchar(255)"+","
            +DbConstant.TempTLPEntry.SECTION_NAME+" varchar(255)"+","
            +DbConstant.TempTLPEntry.TLP_NO+" varchar(255)"+","
            +DbConstant.TempTLPEntry.CABLE_TYPE+" varchar(255)"+","
            +DbConstant.TempTLPEntry.TYPEOFCABLE+" varchar(255)"+","
            +DbConstant.TempTLPEntry.CHAINAGE+" varchar(255)"+","
            +DbConstant.TempTLPEntry.LOCATION+" varchar(255)"+","
            +DbConstant.TempTLPEntry.PHOTO+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.DATE_OF_TLP+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.DESC_NUM+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.CLIENT_ID+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.CLIENT_NAME+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.CONTRACTOR_NAME+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.DIAMETER+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.SPECIFICATION+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.LENGTH+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.TLP_IDENTIFICATION_NO+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.FOUNDATION_MOUNTING+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.PAINTING_GASKET+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.CABLE_TERMINALS+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.CABLE_USED_TYPE+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.CABLE_USED_NO+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.CABLE_THERMIT_TYPE+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.CABLE_THERMIT_USED_NO+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.HOLIDAY_CHECK+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.CONTINUITY_CHECK+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.ANODE_IDENTIFICATION_NO+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.ANODE_TYPE+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.INS_DEPTH+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.DISTANCE_PIPELINE+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.LENTH_TAIL_CABLE+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.TERMINATION+" varchar(255)"+" ,"
            +"date_submitted datetime ,"
            + DbConstant.TempTLPEntry.SERVER_STATUS + " VARCHAR(250)   " + "); ";


    private static final String CREATE_TLP="CREATE TABLE IF NOT EXISTS "+ DbConstant.TLPEntry.TABLE_TLP+"("
            +DbConstant.TLPEntry.SECTION_No+" varchar(255)"+","
            +DbConstant.TLPEntry.SECTION_NAME+" varchar(255)"+","
            +DbConstant.TLPEntry.TLP_NO+" varchar(255)"+","
            +DbConstant.TLPEntry.CABLE_TYPE+" varchar(255)"+","
            +DbConstant.TLPEntry.TYPEOFCABLE+" varchar(255)"+","
            +DbConstant.TLPEntry.CHAINAGE+" varchar(255)"+","
            +DbConstant.TLPEntry.LOCATION+" varchar(255)"+","
            +DbConstant.TLPEntry.PHOTO+" varchar(255)"+" ,"
            +DbConstant.TLPEntry.DATE_OF_TLP+" varchar(255)"+" ,"
            +DbConstant.TLPEntry.DESC_NUM+" varchar(255)"+" ,"
            +DbConstant.TLPEntry.CLIENT_ID+" varchar(255)"+" ,"
            +DbConstant.TLPEntry.CLIENT_NAME+" varchar(255)"+" ,"
            +DbConstant.TLPEntry.CONTRACTOR_NAME+" varchar(255)"+" ,"
            +DbConstant.TLPEntry.DIAMETER+" varchar(255)"+" ,"
            +DbConstant.TLPEntry.SPECIFICATION+" varchar(255)"+" ,"
            +DbConstant.TLPEntry.LENGTH+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.TLP_IDENTIFICATION_NO+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.FOUNDATION_MOUNTING+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.PAINTING_GASKET+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.CABLE_TERMINALS+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.CABLE_USED_TYPE+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.CABLE_USED_NO+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.CABLE_THERMIT_TYPE+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.CABLE_THERMIT_USED_NO+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.HOLIDAY_CHECK+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.CONTINUITY_CHECK+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.ANODE_IDENTIFICATION_NO+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.ANODE_TYPE+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.INS_DEPTH+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.DISTANCE_PIPELINE+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.LENTH_TAIL_CABLE+" varchar(255)"+" ,"
            +DbConstant.TempTLPEntry.TERMINATION+" varchar(255)"+" ,"

            +"date_submitted datetime default CURRENT_TIMESTAMP ,"
            +DbConstant.TLPEntry.SERVER_STATUS + " VARCHAR(250)   " + "); ";

    private static final String CREATE_CLIENT="CREATE TABLE IF NOT EXISTS "+ DbConstant.ClientEntry.TABLE_CLIENT+"("
            +DbConstant.ClientEntry.CLIENT_ID+" varchar(255) primary key"+" ,"
            +DbConstant.ClientEntry.CLIENT_NAME+" varchar(255)"+" );";

    private static final String CREATE_CONTRACTOR="CREATE TABLE IF NOT EXISTS "+ DbConstant.ContractEntry.TABLE_CONTRACTOR+"("
            +DbConstant.ContractEntry.CONTRACTOR_ID+" varchar(255) primary key "+" ,"
            +DbConstant.ContractEntry.CONTRACTOR_NAME+" varchar(255) "+" ,"
            +DbConstant.ContractEntry.CLIENT_ID+" varchar(255) "+" ,"
            +DbConstant.ContractEntry.CLIENT_NAME+" varchar(255)"+" );";
    // added kiran 10jan2020
    private static final String CREATE_FINAL_TABLE="CREATE TABLE IF NOT EXISTS "+ DbConstant.finaltable.TABLE_FINAL+"("
            +DbConstant.finaltable.SECTION_No+" varchar(255)"+","
            +DbConstant.finaltable.SECTION_NAME+" varchar(255)"+","
            +DbConstant.finaltable.CLIENT_NAME+" varchar(255)"+" ,"
            +DbConstant.finaltable.CONTRACTOR_NAME+" varchar(255)"+" ,"
            +DbConstant.finaltable.DIAMETER+" varchar(255)"+" ,"
            +DbConstant.finaltable.SPECIFICATION+" varchar(255)"+" ,"
            +DbConstant.finaltable.LENGTH+" varchar(255)"+" ,"
            +DbConstant.finaltable.TLP_NO+" varchar(255)"+","
            +DbConstant.finaltable.CHAINAGE+" varchar(255)"+","
            +DbConstant.finaltable.LOCATION+" varchar(255)"+","
            +DbConstant.finaltable.CABLE_TYPE+" varchar(255)"+","
            +DbConstant.finaltable.TLP_ATTACHMENT+" varchar(255)"+" ,"
              +DbConstant.finaltable.TLP_IDENTIFICATION_NO+" varchar(255)"+" ,"
            +DbConstant.finaltable.FOUNDATION_MOUNTING+" varchar(255)"+" ,"
            +DbConstant.finaltable.PAINTING_GASKET+" varchar(255)"+" ,"
            +DbConstant.finaltable.CABLE_TERMINALS+" varchar(255)"+" ,"
            +DbConstant.finaltable.CABLE_NUMBER+" varchar(255)"+" ,"
            +DbConstant.finaltable.CABLE_USED_TYPE+" varchar(255)"+" ,"
            +DbConstant.finaltable.CABLE_USED_NO+" varchar(255)"+" ,"
            +DbConstant.finaltable.THERIT_NUMBER+" varchar(255)"+" ,"
            +DbConstant.finaltable.CABLE_THERMIT_TYPE+" varchar(255)"+" ,"
            +DbConstant.finaltable.CABLE_THERMIT_USED_NO+" varchar(255)"+" ,"
            +DbConstant.finaltable.HOLIDAY_CHECK+" varchar(255)"+" ,"
            +DbConstant.finaltable.CONTINUITY_CHECK+" varchar(255)"+" ,"
            +DbConstant.finaltable.ANODE_IDENTIFICATION_NO+" varchar(255)"+" ,"
            +DbConstant.finaltable.ANODE_TYPE+" varchar(255)"+" ,"
            +DbConstant.finaltable.INS_DEPTH+" varchar(255)"+" ,"
            +DbConstant.finaltable.DISTANCE_PIPELINE+" varchar(255)"+" ,"
            +DbConstant.finaltable.LENTH_TAIL_CABLE+" varchar(255)"+" ,"
            +DbConstant.finaltable.TERMINATION+" varchar(255)"+" ,"
            +DbConstant.finaltable.DATE_SUBMITED+" varchar(255)"+","

            + DbConstant.finaltable.SERVER_STATUS + " VARCHAR(250)   " + "); ";

    //new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())

    @Nullable
    public ArrayList<String> getClients() {
        ArrayList<String> mList = new ArrayList<>();
        mList.add("Select");
        SQLiteDatabase mDatabase = this.getWritableDatabase();
/*        ContentValues values= new ContentValues();
        values.put(DbConstant.ClientEntry.CLIENT_ID,"1");
        values.put(DbConstant.ClientEntry.CLIENT_NAME,"Dummy");
        mDatabase.insert(DbConstant.ClientEntry.TABLE_CLIENT,null,values);*/
        Cursor mCursor = mDatabase.rawQuery("SELECT * FROM " + DbConstant.ClientEntry.TABLE_CLIENT +" ORDER BY "+DbConstant.ClientEntry.CLIENT_ID, null);
        if (mCursor != null && mCursor.getCount() > 0) {
            while (mCursor.moveToNext()) {
                if (!mList.contains(mCursor.getString(mCursor.getColumnIndex(DbConstant.ClientEntry.CLIENT_NAME))))
                    mList.add(mCursor.getString(mCursor.getColumnIndex(DbConstant.ClientEntry.CLIENT_NAME)));
            }
        }
        if (mCursor != null) {
            mCursor.close();
        }
        mDatabase.close();
        return mList;
    }

    @Nullable
    public ArrayList<String> getTLpNo1(String clientname,String contractorname,String sectionname,String sectionno) {
        ArrayList<String> mList = new ArrayList<>();
        mList.add("SELECT TLP NO");
        SQLiteDatabase mDatabase = this.getWritableDatabase();
/*        ContentValues values= new ContentValues();
        values.put(DbConstant.ClientEntry.CLIENT_ID,"1");
        values.put(DbConstant.ClientEntry.CLIENT_NAME,"Dummy");
        mDatabase.insert(DbConstant.ClientEntry.TABLE_CLIENT,null,values);*/
      String query=  "select tlp_no from table_temp_tlp where client_name='"+clientname+"' and contractor_name='"+contractorname+"' and section_name='"+sectionname+"' and section_no='"+sectionno+"'";
        Cursor mCursor = mDatabase.rawQuery(query, null);
        if (mCursor != null && mCursor.getCount() > 0) {
            while (mCursor.moveToNext()) {
                if (!mList.contains(mCursor.getString(mCursor.getColumnIndex(DbConstant.TempTLPEntry.TLP_NO))))
                    mList.add(mCursor.getString(mCursor.getColumnIndex(DbConstant.TempTLPEntry.TLP_NO)));
            }
        }
        if (mCursor != null) {
            mCursor.close();
        }
        mDatabase.close();
        return mList;
    }





    @NotNull
    public ArrayList<String> getContractor(@NotNull String clientName) {
        ArrayList<String> mList = new ArrayList<>();
        mList.add("Select");
        SQLiteDatabase mDatabase = this.getWritableDatabase();
        Cursor mCursor = mDatabase.rawQuery("SELECT * FROM " + DbConstant.ContractEntry.TABLE_CONTRACTOR+" WHERE "+DbConstant.ContractEntry.CLIENT_NAME+"='"+clientName+"'", null);
        if (mCursor != null && mCursor.getCount() > 0) {
            while (mCursor.moveToNext()) {
                if (!mList.contains(mCursor.getString(mCursor.getColumnIndex(DbConstant.ContractEntry.CONTRACTOR_NAME))))
                    mList.add(mCursor.getString(mCursor.getColumnIndex(DbConstant.ContractEntry.CONTRACTOR_NAME)));
            }
        }
        if (mCursor != null) {
            mCursor.close();
        }
        mDatabase.close();
        return mList;
    }

    @Nullable
    public ArrayList<String> getSectionName(String ClientName,String ContractorName) {
        ArrayList<String> mList = new ArrayList<>();
        mList.add("Select");
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        Cursor mCursor = mDatabase.rawQuery("SELECT * FROM " + DbConstant.SectionNameEntry.TABLE_SECTION_NAME +" WHERE "+DbConstant.SectionNameEntry.CLIENT_NAME+"='"+ClientName+"' AND "+DbConstant.SectionNameEntry.CONTRACTOR_NAME+"='"+ContractorName+"'" , null);
        if (mCursor != null && mCursor.getCount() > 0) {
            while (mCursor.moveToNext()) {
                if (!mList.contains(mCursor.getString(mCursor.getColumnIndex(DbConstant.SectionNameEntry.SECTION_NAME))))
                    mList.add(mCursor.getString(mCursor.getColumnIndex(DbConstant.SectionNameEntry.SECTION_NAME)));
            }
        }
        if (mCursor != null) {
            mCursor.close();
        }
        mDatabase.close();
        return mList;
    }

    @Nullable
    public ArrayList<String> getSectionNo(String ClientName,String ContractorName,String SectionName) {
        ArrayList<String> mList = new ArrayList<>();
        mList.add("Select");
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        Cursor mCursor = mDatabase.rawQuery("SELECT * FROM " + DbConstant.SectionNoEntry.TABLE_SECTION_NO +" WHERE "+DbConstant.SectionNoEntry.CLIENT_NAME+"='"+ClientName+"' AND "+DbConstant.SectionNoEntry.CONTRACTOR_NAME+"='"+ContractorName+"' AND "+DbConstant.SectionNoEntry.SECTION_NAME+"='"+SectionName+"'" , null);
        if (mCursor != null && mCursor.getCount() > 0) {
            while (mCursor.moveToNext()) {
                if (!mList.contains(mCursor.getString(mCursor.getColumnIndex(DbConstant.SectionNoEntry.SECTION_No))))
                    mList.add(mCursor.getString(mCursor.getColumnIndex(DbConstant.SectionNoEntry.SECTION_No)));
            }
        }
        if (mCursor != null) {
            mCursor.close();
        }
        mDatabase.close();
        return mList;
    }

    @NotNull
    public Cursor getbindSectionData(@NotNull String clietnName,@NotNull String contractorName, @NotNull String SectionName, @NotNull String SectionNo) {
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        return mDatabase.rawQuery("select * From "+DbConstant.SectionEntry.TABLE_SECTION+" WHERE "+DbConstant.SectionNoEntry.CLIENT_NAME+"='"+clietnName+"' AND "+DbConstant.SectionNoEntry.CONTRACTOR_NAME+"='"+contractorName+"' AND "+DbConstant.SectionNoEntry.SECTION_NAME+"='"+SectionName+"' AND "+DbConstant.SectionNoEntry.SECTION_No+"='"+SectionNo+"'",null);
    }

    @NotNull
    public Cursor getTLPDetailsforinitial(@NotNull String clientName, @NotNull String contractorName, @NotNull String sectionName, @NotNull String sectionNo,@NotNull String finaltlpno) {
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        return mDatabase.rawQuery("select * From "+DbConstant.TempTLPEntry.TABLE_TEMP_TLP+" WHERE "+DbConstant.TempTLPEntry.CLIENT_NAME+"='"+clientName+"'AND "+DbConstant.TempTLPEntry.CONTRACTOR_NAME+"='"+contractorName+"' AND  "+DbConstant.TempTLPEntry.SECTION_NAME+"='"+sectionName+"' AND "+DbConstant.TempTLPEntry.SECTION_No+"='"+sectionNo+"'  AND "+DbConstant.TempTLPEntry.TLP_NO+"='"+finaltlpno+"' order by "+DbConstant.TempTLPEntry.DESC_NUM,null);
    }
//getTLPDetails
//getTLPDetailsforinitial
    @NotNull
    public Cursor getTLPDetails(@NotNull String clientName, @NotNull String contractorName, @NotNull String sectionName, @NotNull String sectionNo) {
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        return mDatabase.rawQuery("select * From "+DbConstant.TempTLPEntry.TABLE_TEMP_TLP+" WHERE "+DbConstant.TempTLPEntry.CLIENT_NAME+"='"+clientName+"'AND "+DbConstant.TempTLPEntry.CONTRACTOR_NAME+"='"+contractorName+"' AND  "+DbConstant.TempTLPEntry.SECTION_NAME+"='"+sectionName+"' AND "+DbConstant.TempTLPEntry.SECTION_No+"='"+sectionNo+"'  order by "+DbConstant.TempTLPEntry.DESC_NUM,null);
    }

    @NotNull
    public Cursor getTLPDetailsTHermit(@NotNull String clientName, @NotNull String contractorName, @NotNull String sectionName, @NotNull String sectionNo,@NotNull String tlpno) {
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        return mDatabase.rawQuery("select * From "+DbConstant.TemptableNAme.TABLE_TEMP_THERMIT+" WHERE "+DbConstant.TemptableNAme.CLIENT_NAME+"='"+clientName+"'AND "+DbConstant.TemptableNAme.CONTRACTOR_NAME+"='"+contractorName+"' AND  "+DbConstant.TemptableNAme.SECTION_NAME+"='"+sectionName+"' AND "+DbConstant.TemptableNAme.SECTION_No+"='"+sectionNo+"' AND "+DbConstant.TemptableNAme.TLP_IDENTIFICATION_NO+"='"+tlpno+"'  order by "+DbConstant.TemptableNAme.DESCNUM_INSTALLTION,null);
    }

    @NotNull
    public Cursor getTLPDetailsCAble(@NotNull String clientName, @NotNull String contractorName, @NotNull String sectionName, @NotNull String sectionNo,@NotNull String tlpno) {
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        return mDatabase.rawQuery("select * From "+DbConstant.TemptableNAme.TABLE_TEMP_CABLE+" WHERE "+DbConstant.TemptableNAme.CLIENT_NAME+"='"+clientName+"'AND "+DbConstant.TemptableNAme.CONTRACTOR_NAME+"='"+contractorName+"' AND  "+DbConstant.TemptableNAme.SECTION_NAME+"='"+sectionName+"' AND "+DbConstant.TemptableNAme.SECTION_No+"='"+sectionNo+"' AND "+DbConstant.TemptableNAme.TLP_IDENTIFICATION_NO+"='"+tlpno+"'  order by "+DbConstant.TemptableNAme.DESCNUM_INSTALLTION,null);
    }



}
