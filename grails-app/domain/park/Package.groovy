package park

/**
 * 套餐
 */
class Package {
    String packageName
    double price;
    int sale; //打折
    String packageUrl //套餐图片

    Restaurant restaurant;

    static belongsTo = [Restaurant]

    static mapping = {
        version(false)
        restaurant column: "restaurant_id"
    }
    static constraints = {
        packageName nullable: false
    }
}
