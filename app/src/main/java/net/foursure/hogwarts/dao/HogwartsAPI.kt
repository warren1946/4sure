package net.foursure.hogwarts.dao

import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import net.foursure.hogwarts.interfaces.HogwartsInterface
import net.foursure.hogwarts.models.Character
import net.foursure.hogwarts.models.House
import net.foursure.hogwarts.models.Member
import net.foursure.hogwarts.models.Spell
import net.foursure.hogwarts.utils.Constants
import org.json.JSONException

class HogwartsAPI:HogwartsInterface {
    //Global variables
    val LOG:String = "HogwartsAPI";
    private var requestQueue: RequestQueue? = null

    override fun getHouseList(): ArrayList<House> {
        val url = Constants.CONS_HOUSE_LIST + "?key=" + Constants.CONS_API_KEY
        var houseList = ArrayList<House>()

        val request = JsonArrayRequest(Request.Method.GET, url, null, Response.Listener {
            response ->try {
                // Process api call response
                val jsonResponse = response
                for (i in 0 until jsonResponse.length()) {
                    val jsonHouse = jsonResponse.getJSONObject(i)
                    val house:House = House()
                    house._id = jsonHouse.getString("_id")
                    house.name = jsonHouse.getString("name")
                    house.mascot = jsonHouse.getString("mascot")
                    house.headOfHouse = jsonHouse.getString("headOfHouse")
                    house.houseGhost = jsonHouse.getString("houseGhost")
                    house.founder = jsonHouse.getString("founder")
                    house.school = jsonHouse.getString("school")

                    // Process members
                    var members = ArrayList<Member>()
                    var jsonMembers = jsonHouse.getJSONArray("members")

                    if(jsonMembers.length() > 0) {
                        for (j in 0 until jsonMembers.length()) {
                            val objMember:Member = Member();
                            objMember._id = jsonMembers.get(j).toString()
                            members.add(objMember)
                        }
                        house.members = members
                    }

                    // Add processed house to the list
                    houseList.add(house)

                }
            } catch (e: JSONException) {
                Log.e(LOG, e.message)
            }
        }, Response.ErrorListener { error -> error.printStackTrace() })
        requestQueue?.add(request)

        return houseList
    }

    override fun getHouse(_id: String): House {

        val uri = java.lang.String.format(Constants.CONS_HOUSE_BY_ID + _id + "?key=%1", Constants.CONS_API_KEY)
        val house:House = House()

        val request = JsonArrayRequest(Request.Method.GET, uri, null, Response.Listener {
                response ->try {
            // Process api call response
            val jsonResponse = response
            for (i in 0 until jsonResponse.length()) {
                val jsonHouse = jsonResponse.getJSONObject(i)
                house._id = jsonHouse.getString("_id")
                house.name = jsonHouse.getString("name")
                house.mascot = jsonHouse.getString("mascot")
                house.headOfHouse = jsonHouse.getString("headOfHouse")
                house.houseGhost = jsonHouse.getString("houseGhost")
                house.founder = jsonHouse.getString("founder")
                house.school = jsonHouse.getString("school")

                // Process members
                var members = ArrayList<Member>()
                var jsonMembers = jsonHouse.getJSONArray("members")

                if(jsonMembers.length() > 0) {
                    for (j in 0 until jsonMembers.length()) {
                        val jsonObjMember = jsonMembers.getJSONObject(i)
                        val objMember:Member = Member()

                        objMember._id = jsonObjMember.getString("_id")
                        objMember.name = jsonObjMember.getString("name")
                        members.add(objMember)
                    }
                    house.members = members
                }
            }
        } catch (e: JSONException) {
            Log.e(LOG, e.message)
        }
        }, Response.ErrorListener { error -> error.printStackTrace() })
        requestQueue?.add(request)

        return house
    }

    override fun getCharacterList(): ArrayList<Character> {
        val uri = java.lang.String.format(Constants.CONS_CHARACTER_LIST + "?key=%1", Constants.CONS_API_KEY)
        var characterList = ArrayList<Character>()

        val request = JsonArrayRequest(Request.Method.GET, uri, null, Response.Listener {
                response ->try {
            // Process api call response
            val jsonResponse = response
            for (i in 0 until jsonResponse.length()) {
                val jsonCharacter = jsonResponse.getJSONObject(i)

                val character:Character = Character()
                character._id = jsonCharacter.getString("_id")
                character.name = jsonCharacter.getString("name")
                character.role = jsonCharacter.getString("role")
                character.house = jsonCharacter.getString("house")
                character.ministryOfMagic = jsonCharacter.getBoolean("ministryOfMagic")
                character.orderOfThePhoenix = jsonCharacter.getBoolean("orderOfThePhoenix")
                character.dumbledoresArmy = jsonCharacter.getBoolean("dumbledoresArmy")
                character.deathEater = jsonCharacter.getBoolean("deathEater")
                character.bloodStatus = jsonCharacter.getString("bloodStatus")
                character.species = jsonCharacter.getString("species")

                // Add processed character to the list
                characterList.add(character)

            }
        } catch (e: JSONException) {
            Log.e(LOG, e.message)
        }
        }, Response.ErrorListener { error -> error.printStackTrace() })
        requestQueue?.add(request)

        return characterList
    }

    override fun getCharacter(_id: String): Character {
        val uri = java.lang.String.format(Constants.CONS_CHARACTER_BY_ID + _id + "?key=%1", Constants.CONS_API_KEY)
        val character:Character = Character()

        val request = JsonObjectRequest(Request.Method.GET, uri, null, Response.Listener {
                response ->try {
            // Process api call response
            character._id = response.getString("_id")
            character.name = response.getString("name")
            character.role = response.getString("role")
            character.house = response.getString("house")
            character.ministryOfMagic = response.getBoolean("ministryOfMagic")
            character.orderOfThePhoenix = response.getBoolean("orderOfThePhoenix")
            character.dumbledoresArmy = response.getBoolean("dumbledoresArmy")
            character.deathEater = response.getBoolean("deathEater")
            character.bloodStatus = response.getString("bloodStatus")
            character.species = response.getString("species")

        } catch (e: JSONException) {
            Log.e(LOG, e.message)
        }
        }, Response.ErrorListener { error -> error.printStackTrace() })
        requestQueue?.add(request)

        return character
    }

    override fun getSpellList(): ArrayList<Spell> {
        val uri = java.lang.String.format(Constants.CONS_SPELL_LIST + "?key=%1", Constants.CONS_API_KEY)
        var spellList = ArrayList<Spell>()

        val request = JsonArrayRequest(Request.Method.GET, uri, null, Response.Listener {
                response ->try {
            // Process api call response
            val jsonResponse = response
            for (i in 0 until jsonResponse.length()) {
                val jsonSpell = jsonResponse.getJSONObject(i)

                val spell:Spell = Spell()
                spell._id = jsonSpell.getString("_id")
                spell.spell = jsonSpell.getString("spell")
                spell.type = jsonSpell.getString("type")
                spell.effect = jsonSpell.getString("effect")

                // Add processed spell to the list
                spellList.add(spell)

            }
        } catch (e: JSONException) {
            Log.e(LOG, e.message)
        }
        }, Response.ErrorListener { error -> error.printStackTrace() })
        requestQueue?.add(request)

        return spellList
    }
}