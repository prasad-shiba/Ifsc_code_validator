package projects.java.android.ifsccodevalidator;

import com.google.gson.annotations.SerializedName;

public class BankDetails {
    @SerializedName("ADDRESS")
    private final String Address;
    @SerializedName("BANK")
    private final String BankName;
    @SerializedName("BRANCH")
    private final String BranchName;
    @SerializedName("CITY")
    private final String CityName;
    @SerializedName("CONTACT")
    private final String Contact;
    @SerializedName("DISTRICT")
    private final String DistrictName;
    @SerializedName("STATE")
    private final String StateName;
    @SerializedName("MICR")
    private final String MICR;
    @SerializedName("IFSC")
    private final String IFSC;
    @SerializedName("SWIFT")
    private final String SWIFT;
    @SerializedName("RTGS")
    private final boolean RTGS;
    @SerializedName("UPI")
    private final boolean UPI;
    @SerializedName("NEFT")
    private final boolean NEFT;
    @SerializedName("IMPS")
    private final boolean IMPS;
    @SerializedName("BANKCODE")
    private final String BANK_CODE;

    public BankDetails(String address, String bankName, String branchName, String cityName, String contact, String districtName, String stateName, String MICR, String IFSC, String SWIFT, boolean RTGS, boolean UPI, boolean NEFT, boolean IMPS, String BANK_CODE) {
        Address = address;
        BankName = bankName;
        BranchName = branchName;
        CityName = cityName;
        Contact = contact;
        DistrictName = districtName;
        StateName = stateName;
        this.MICR = MICR;
        this.IFSC = IFSC;
        this.SWIFT = SWIFT;
        this.RTGS = RTGS;
        this.UPI = UPI;
        this.NEFT = NEFT;
        this.IMPS = IMPS;
        this.BANK_CODE = BANK_CODE;
    }

    public String getAddress() {
        return Address;
    }

    public String getBankName() {
        return BankName;
    }

    public String getBranchName() {
        return BranchName;
    }

    public String getCityName() {
        return CityName;
    }

    public String getContact() {
        return Contact;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public String getStateName() {
        return StateName;
    }

    public String getMICR() {
        return MICR;
    }

    public String getIFSC() {
        return IFSC;
    }

    public String getSWIFT() {
        return SWIFT;
    }

    public boolean isRTGS() {
        return RTGS;
    }

    public boolean isUPI() {
        return UPI;
    }

    public boolean isNEFT() {
        return NEFT;
    }

    public boolean isIMPS() {
        return IMPS;
    }

    public String getBANK_CODE() {
        return BANK_CODE;
    }
}
