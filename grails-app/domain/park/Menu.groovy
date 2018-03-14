package park

class Menu {
    String menuName
    double price;
    int sale;  //打折
    String menuUrl; //菜单图片
    Long restaurant_id;
    String menuPicture;
    String menuIntroduce;
    String menuInfomation;
    String mark;
    String classifyName
    static mapping = {
//        menuName unique: true
        version false
    }

    static constraints = {
        menuName nullable: false
        restaurant_id nullable: true
        menuPicture nullable: true
    }

}
