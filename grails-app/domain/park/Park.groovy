package park


class Park {
    String parkName
    String parkContent
    Date crateTime
    static constraints = {
        parkName blank: false,unique: true
        parkContent maxSize: 20000
        crateTime nullable: true
    }
}
