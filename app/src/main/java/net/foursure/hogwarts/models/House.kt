package net.foursure.hogwarts.models

class House(){
    // Global variables
    var _id:String?=null
    var name:String?=null
    var mascot:String?=null
    var headOfHouse:String?=null
    var houseGhost:String?=null
    var founder:String?=null
    var school:String?=null
    var members = ArrayList<Member>()

    constructor(_id:String, name:String, mascot:String, headOfHouse:String, houseGhost:String,
                founder:String, school:String, members: ArrayList<Member>):this(){
        this._id = _id
        this.name = name
        this.mascot = mascot
        this.headOfHouse = headOfHouse
        this.houseGhost = houseGhost
        this.founder = founder
        this.school = school
        this.members = members
    }
}