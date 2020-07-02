package net.foursure.hogwarts.models

class Character(){
    // Global variables
    var _id:String?=null
    var name:String?=null
    var role:String?=null
    var house:String ?=null
    var school:String ?=null
    var ministryOfMagic:Boolean?=null
    var orderOfThePhoenix:Boolean?=null
    var dumbledoresArmy:Boolean?=null
    var deathEater:Boolean?=null
    var bloodStatus:String?=null
    var species:String?=null

    constructor(_id:String, name:String, role:String, house:String, school:String, ministryOfMagic:Boolean,
                orderOfThePhoenix:Boolean, dumbledoresArmy:Boolean, deathEater:Boolean,
                bloodStatus:String, species:String):this(){
        this._id = _id
        this.name = name
        this.role = role
        this.house = house
        this.school = school
        this.ministryOfMagic = ministryOfMagic
        this.orderOfThePhoenix = orderOfThePhoenix
        this.dumbledoresArmy = dumbledoresArmy
        this.deathEater = deathEater
        this.bloodStatus = bloodStatus
        this.species = species
    }


}