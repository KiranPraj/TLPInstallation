package com.icspl.tlpinstallation.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class CLientModel{
    @SerializedName("dtqty")
    @Expose
    public val dtqty: List<Dtqty>? = null
    class Dtqty{
        @SerializedName("ClientName")
        @Expose
        public val clientName: String? = null
        @SerializedName("ClientID")
        @Expose
        public val clientID: String? = null
    }
}
