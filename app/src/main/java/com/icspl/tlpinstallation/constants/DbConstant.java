package com.icspl.tlpinstallation.constants;

import android.provider.BaseColumns;

public class DbConstant {
    public class SectionNameEntry implements BaseColumns{
        public static final String TABLE_SECTION_NAME="table_section_name";
        public static final String SECTION_NAME="section_name";
        public static final String SERVER_STATUS="server_status";
        public static final String TLP="tlp";
        public static final String CLIENT_NAME="client_name";
        public static final String CLIENT_ID="client_id";
        public static final String CONTRACTOR_NAME="contractor_name";
        public static final String CONTRACTOR_ID="contractor_id";

    }
    public class SectionNoEntry implements BaseColumns{
        public static final String TABLE_SECTION_NO="table_section_no";
        public static final String SECTION_No="section_no";
        public static final String SECTION_NAME="section_name";
        public static final String SERVER_STATUS="server_status";
        public static final String TLP="tlp";
        public static final String CLIENT_NAME="client_name";
        public static final String CLIENT_ID="client_id";
        public static final String CONTRACTOR_NAME="contractor_name";
        public static final String CONTRACTOR_ID="contractor_id";
    }

    public class SectionEntry implements BaseColumns{
        public static final String TABLE_SECTION="table_section";

        public static final String SECTION_No="section_no";
        public static final String SECTION_NAME="section_name";
        public static final String SERVER_STATUS="server_status";
        public static final String TLP="tlp";
        public static final String CLIENT_NAME="client_name";
        public static final String CLIENT_ID="client_id";
        public static final String CONTRACTOR_NAME="contractor_name";
        public static final String CONTRACTOR_ID="contractor_id";
        public static final String DIAMETER="diameter";
        public static final String SPECIFICATION="specification";
        public static final String LENGTH="length";

    }
    // insetion installation details
     public class InstallationOtherDetails implements BaseColumns
    {
        public static final String TABLE_OTHER_INSTALLATIONDETAILS="table_other_installationdetails";
        public static final String SECTION_No="section_no";
        public static final String SECTION_NAME="section_name";
        public static final String CLIENT_NAME="client_name";
        public static final String CONTRACTOR_NAME="contractor_name";
        public static final String TLP_NO="contractor_name";
        public static final String TLP_IDENTIFICATION_NO="tlp_identification_no";
        public static final String FOUNDATION_MOUNTING="foundation_mounting";
        public static final String PAINTING_GASKET="painting_gaskt";
        public static final String CABLE_TERMINALS="cable_terminals";
        public static final String HOLIDAY_CHECK="holiday_check";
        public static final String CONTINUITY_CHECK="continuity_chk";
        public static final String ANODE_IDENTIFICATION_NO="anode_identification_no";
        public static final String ANODE_TYPE="anode_type";
        public static final String INS_DEPTH="ins_depth";
        public static final String DISTANCE_PIPELINE="distance_pipeline";
        public static final String LENTH_TAIL_CABLE="length_tail_cable";
        public static final String TERMINATION="termination";
    }



    public class TempTLPEntry implements BaseColumns{
        public static final String TABLE_TEMP_TLP="table_temp_tlp";

        public static final String SECTION_No="section_no";
        public static final String SECTION_NAME="section_name";
        public static final String SERVER_STATUS="server_status";
        public static final String CLIENT_NAME="client_name";
        public static final String CLIENT_ID="client_id";
        public static final String CONTRACTOR_NAME="contractor_name";
        public static final String CONTRACTOR_ID="contractor_id";
        public static final String DIAMETER="diameter";
        public static final String SPECIFICATION="specification";
        public static final String LENGTH="length";
        public static final String TLP_NO="tlp_no";
        public static final String CHAINAGE="chainage";
        public static final String LOCATION="location";
        public static final String DESC_NUM="desc_num";
        public static final String PHOTO="photo";
        public static final String DATE_OF_TLP="date_of_tlp";
       //added
        public static final String CABLE_TYPE="cable_type";
        public static final String TYPEOFCABLE="typeofcable";

        //added7jan2020
        public static final String TLP_IDENTIFICATION_NO="tlp_identification_no";
        public static final String FOUNDATION_MOUNTING="foundation_mounting";
        public static final String PAINTING_GASKET="painting_gaskt";
        public static final String CABLE_TERMINALS="cable_terminals";
        public static final String CABLE_USED_TYPE="cable_used_typ";
        public static final String CABLE_USED_NO="cable_used_no";
        public static final String CABLE_THERMIT_TYPE="cable_thermit_type";
        public static final String CABLE_THERMIT_USED_NO="cable_thermit_used_no";
        public static final String HOLIDAY_CHECK="holiday_check";
        public static final String CONTINUITY_CHECK="continuity_chk";
        public static final String ANODE_IDENTIFICATION_NO="anode_identification_no";
        public static final String ANODE_TYPE="anode_type";
        public static final String INS_DEPTH="ins_depth";
        public static final String DISTANCE_PIPELINE="distance_pipeline";
        public static final String LENTH_TAIL_CABLE="length_tail_cable";
        public static final String TERMINATION="termination";

    }
    public class TLPEntry implements BaseColumns{
        public static final String TABLE_TLP="table_tlp";

        public static final String SECTION_No="section_no";
        public static final String SECTION_NAME="section_name";
        public static final String SERVER_STATUS="server_status";
        public static final String CLIENT_NAME="client_name";
        public static final String CLIENT_ID="client_id";
        public static final String CONTRACTOR_NAME="contractor_name";
        public static final String CONTRACTOR_ID="contractor_id";
        public static final String DIAMETER="diameter";
        public static final String SPECIFICATION="specification";
        public static final String LENGTH="length";
        public static final String TLP_NO="tlp_no";
        //added
        public static final String CABLE_TYPE="cable_type";
        public static final String TYPEOFCABLE="typeofcable";

        public static final String CHAINAGE="chainage";
        public static final String LOCATION="location";
        public static final String DESC_NUM="desc_num";
        public static final String PHOTO="photo";
        public static final String DATE_OF_TLP="date_of_tlp";

        //added7jan2020
        public static final String TLP_IDENTIFICATION_NO="tlp_identification_no";
        public static final String FOUNDATION_MOUNTING="foundation_mounting";
        public static final String PAINTING_GASKET="painting_gaskt";
        public static final String CABLE_TERMINALS="cable_terminals";
        public static final String CABLE_USED_TYPE="cable_used_typ";
        public static final String CABLE_USED_NO="cable_used_no";
        public static final String CABLE_THERMIT_TYPE="cable_thermit_type";
        public static final String CABLE_THERMIT_USED_NO="cable_thermit_used_no";
        public static final String HOLIDAY_CHECK="holiday_check";
        public static final String CONTINUITY_CHECK="continuity_chk";
        public static final String ANODE_IDENTIFICATION_NO="anode_identification_no";
        public static final String ANODE_TYPE="anode_type";
        public static final String INS_DEPTH="ins_depth";
        public static final String DISTANCE_PIPELINE="distance_pipeline";
        public static final String LENTH_TAIL_CABLE="length_tail_cable";
        public static final String TERMINATION="termination";

    }
    public class ClientEntry implements BaseColumns{
        public static final String TABLE_CLIENT="table_client";

        public static final String CLIENT_NAME="client_name";
        public static final String CLIENT_ID="client_id";
    }
    public class ContractEntry implements BaseColumns{
        public static final String TABLE_CONTRACTOR="table_contractor";

        public static final String CONTRACTOR_NAME="contractor_name";
        public static final String CONTRACTOR_ID="contractor_id";
        public static final String CLIENT_NAME="client_name";
        public static final String CLIENT_ID="client_id";
    }

public class TemptableNAme implements BaseColumns
{
    public static final String TABLE_TEMP_CABLE="table_temp_cable";
    public static final String TABLE_TEMP_THERMIT="table_temp_thermit";

    public static final String DESCNUM_INSTALLTION="desc_num_installation";
    public static final String TLP_NO_OF_INSTALLTION="tlp_no_of_installation";
    public static final String TYPE_OF_CABLE_INSTALLATION="type_of_cable_installation";
    public static final String NO_OF_CABLE_INSTALLATION="no_of_cable_installation";

    public static final String SECTION_No="section_no";
    public static final String SECTION_NAME="section_name";
    public static final String CLIENT_NAME="client_name";
    public static final String CONTRACTOR_NAME="contractor_name";
    //added7jan2020
    public static final String TLP_IDENTIFICATION_NO="tlp_identification_no";
    public static final String FOUNDATION_MOUNTING="foundation_mounting";
    public static final String PAINTING_GASKET="painting_gaskt";
    public static final String CABLE_TERMINALS="cable_terminals";
    public static final String CABLE_USED_TYPE="cable_used_typ";
    public static final String CABLE_USED_NO="cable_used_no";
    public static final String CABLE_THERMIT_TYPE="cable_thermit_type";
    public static final String CABLE_THERMIT_USED_NO="cable_thermit_used_no";
    public static final String HOLIDAY_CHECK="holiday_check";
    public static final String CONTINUITY_CHECK="continuity_chk";
    public static final String ANODE_IDENTIFICATION_NO="anode_identification_no";
    public static final String ANODE_TYPE="anode_type";
    public static final String INS_DEPTH="ins_depth";
    public static final String DISTANCE_PIPELINE="distance_pipeline";
    public static final String LENTH_TAIL_CABLE="length_tail_cable";
    public static final String TERMINATION="termination";
    public static final String SERVER_STATUS="server_status";
    public static final String DATE_SUBMITTED="date_submitted";


}

//added kiran 10jan2020
    public class finaltable implements BaseColumns
   {
       public static final String TABLE_FINAL="table_final";
       public static final String SECTION_No="section_no";
       public static final String SECTION_NAME="section_name";
       public static final String CLIENT_NAME="client_name";
       public static final String CONTRACTOR_NAME="contractor_name";
       public static final String DIAMETER="diameter";
       public static final String SPECIFICATION="specification";
       public static final String LENGTH="length";
       public static final String TLP_NO="tlp_no";
       public static final String CHAINAGE="chainage";
       public static final String LOCATION="location";
       public static final String CABLE_TYPE="cable_type";
       public static final String TLP_ATTACHMENT="attachment";
       public static final String TLP_IDENTIFICATION_NO="tlp_identification_no";
       public static final String FOUNDATION_MOUNTING="foundation_mounting";
       public static final String PAINTING_GASKET="painting_gaskt";
       public static final String CABLE_TERMINALS="cable_terminals";
       public static final String CABLE_NUMBER="cable_number";
       public static final String CABLE_USED_TYPE="cable_used_typ";
       public static final String CABLE_USED_NO="cable_used_no";
       public static final String THERIT_NUMBER="thermit_number";
       public static final String CABLE_THERMIT_TYPE="cable_thermit_type";
       public static final String CABLE_THERMIT_USED_NO="cable_thermit_used_no";
       public static final String HOLIDAY_CHECK="holiday_check";
       public static final String CONTINUITY_CHECK="continuity_chk";
       public static final String ANODE_IDENTIFICATION_NO="anode_identification_no";
       public static final String ANODE_TYPE="anode_type";
       public static final String INS_DEPTH="ins_depth";
       public static final String DISTANCE_PIPELINE="distance_pipeline";
       public static final String LENTH_TAIL_CABLE="length_tail_cable";
       public static final String TERMINATION="termination";
       public static final String SERVER_STATUS="server_status";
       public static final String DATE_SUBMITED="date_submitted";




   }
}
