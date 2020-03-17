package com.icspl.tlpinstallation.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ContractorModel{
    @SerializedName("dtqty1")
    @Expose
    public val dtqty1: List<Dtqty1>? = null
    class Dtqty1{
        @SerializedName("contractorName")
        @Expose
        public val contractorName: String? = null
        @SerializedName("ContractorID")
        @Expose
        public val contractorID: String? = null
        @SerializedName("ClientID")
        @Expose
        public val clientID: String? = null
        @SerializedName("ClientName")
        @Expose
        public val clientName: String? = null
    }
}
