package park

class Restaurant {
    String restaurantName;  //餐厅名称
    String restaurantOwner; //餐厅经营人
    String telephone;        //餐厅联系人号码
    Date createDate;       //餐厅开业时间
    static constraints = {
        restaurantName nullable: false
        restaurantOwner nullable: false
        telephone nullable: false
        createDate nullable: true
    }
}
