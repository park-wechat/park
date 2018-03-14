package park

class Hotel {
    String hotelName
    String hotelOwner; //餐厅经营人
    String telephone;        //餐厅联系人号码
    Date createDate;       //餐厅开业时间
    static constraints = {
        createDate nullable: true
    }
}
